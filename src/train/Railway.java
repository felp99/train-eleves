package train;

import java.util.Arrays;

/**
 * Représentation d'un circuit constitué d'éléments de voie ferrée : gare ou
 * section de voie
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Railway {
	private final Element[] elements;
	private int countTrainsLR = 0;
    private int countTrainsRL = 0;
	private boolean[] withTrain;	

	public Railway(Element[] elements) {
		if(elements == null)
			throw new NullPointerException();		
		this.elements = elements;
		for (Element e : elements)
			e.setRailway(this);

		this.withTrain = new boolean[elements.length];
        Arrays.fill(withTrain, false);
	}

	public synchronized Position moveLeftToRigth(Position pos) {
		int index = getElementIndexByPosition(pos);
		while ((this.countTrainsRL != 0 && isEdge(pos) && hasTrainOnRailway())  || (index < (elements.length-1) && this.withTrain[index + 1]) ) {
			try {
				System.out.println("Waiting in moveLeftToRight");
				System.out.println(printWithTrain());
				System.out.println("LR:"+this.countTrainsLR);
				System.out.println("RL:"+this.countTrainsRL);
				System.out.println(isEdge(pos));
				System.out.println(hasTrainOnRailway());
				wait(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return controlState(pos, index);
	}

    public synchronized Position moveRigthToLeft(Position pos) {
		int index = getElementIndexByPosition(pos);
		while ((this.countTrainsLR != 0 && isEdge(pos)) || (index > 0 && this.withTrain[index - 1])) {
			try {
				System.out.println("Waiting in moveRightToLeft");	
				System.out.println(printWithTrain());
				System.out.println("LR:"+this.countTrainsLR);
				System.out.println("RL:"+this.countTrainsRL);
				System.out.println(isEdge(pos));
				wait(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}			
		}
		return controlState(pos, index);
    }

	public synchronized void incrementCountLR() {
        countTrainsLR++;
    }

    public synchronized void incrementCountRL() {
        countTrainsRL++;
    }

    public synchronized void decrementCountLR() {
        countTrainsLR--;
    }

    public synchronized void decrementCountRL() {
        countTrainsRL--;
    }

    public synchronized int getCountTrainsLR() {
        return countTrainsLR;
    }

    public synchronized int getCountTrainsRL() {
        return countTrainsRL;
    }

	public boolean[] getElementsWithTrain(){
		return this.withTrain;
	}
	
	public int getElementIndexByPosition (Position position) {
        for (int i=0; i < elements.length; i++) {
            if (elements[i].equals(position.getPos())) {
            	return i; 
            }
		}
		return -1;
	}

	public synchronized boolean isEdge(Position pos){		
		int lastIndex = elements.length-1;
		int elementIndex = this.getElementIndexByPosition(pos);
		if ((elementIndex == 0 && pos.getDir() == Direction.LR) 
		|| (elementIndex == lastIndex && pos.getDir() == Direction.RL)){
			return true;			
		}else{
			return false;
		}
	}

	public synchronized Position controlState(Position pos, int index){

		if (pos.getDir() == Direction.LR){
			if (index < elements.length - 1) {
				if (index+1 == elements.length - 1){
					this.withTrain[index+1] = false;
					this.withTrain[index] = false;
					//Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
				}else{
					this.withTrain[index+1] = true;
					this.withTrain[index] = false;
					//Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
				}	
				notifyAll();		
				return new Position(elements[index + 1], Direction.LR);			
			} else if (index == elements.length - 1) {
				this.withTrain[index] = false;
				incrementCountRL();
				decrementCountLR();
				notifyAll();
				return new Position(elements[index], Direction.RL);			
			} else {
				return new Position(elements[index - 1], Direction.RL);
			}
		}else{
			if (index > 0) {
				if (index-1 == 0){
					this.withTrain[index-1] = false;
					this.withTrain[index] = false;
					//Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
				}else{
					this.withTrain[index-1] = true;
					this.withTrain[index] = false;
					//Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
				  }
				notifyAll();
				return new Position(elements[index - 1], Direction.RL);
			} else if (index == 0) {
				this.withTrain[index] = false;
				incrementCountLR();
				decrementCountRL();
				notifyAll();
				return new Position(elements[index], Direction.LR);
			} else {
				return new Position(elements[index + 1], Direction.LR);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (Element e : this.elements) {
			if (first)
				first = false;
			else
				result.append("--");
			result.append(e);
		}
		return result.toString();
	}    

	public String printWithTrain() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < withTrain.length; i++) {
            result.append(withTrain[i]);
            if (i < withTrain.length - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

	public synchronized boolean hasTrainOnRailway() {
		for (int i = 0; i < withTrain.length; i++) {
            if (this.withTrain[i] && this.elements[i] instanceof Section ) {
                return true;
            }
        }
        return false;
    }
}
