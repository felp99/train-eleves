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
	private boolean trainLR = false;
	private boolean trainRL = false;
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

	public synchronized boolean conditionWithTrain(int index, Position pos){
		if ( (pos.getDir() == Direction.LR) && (index < elements.length - 1) && (getElementIndexByPosition(pos)-1 == 0)) {
			return this.withTrain[index + 1];
		}else if((pos.getDir() == Direction.RL) && (index > 0) && (getElementIndexByPosition(pos)+1 == elements.length - 1)){
			return this.withTrain[index - 1];
		}else{
			return false;
		}
	}

	public synchronized Position moveLeftToRigth(Position pos) {
		int index = getElementIndexByPosition(pos);
		//System.out.println(conditionWithTrain(index,pos));
		while ((this.trainRL && isEdge(pos)) || (conditionWithTrain(index,pos)) ) {
		//while ( (conditionWithTrain(index))) {
			try {
				System.out.println("Waiting in moveLeftToRight");
				wait(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		if (index < elements.length - 1) {
			this.withTrain[index+1] = true;
			this.withTrain[index] = false;
			notifyAll();
			return new Position(elements[index + 1], Direction.LR);			
		} else if (index == elements.length - 1) {
			this.withTrain[index] = false;
			this.trainLR = false;
			notifyAll();
			return new Position(elements[index], Direction.RL);			
		} else {
			return new Position(elements[index - 1], Direction.RL);
		}
	}

    public synchronized Position moveRigthToLeft(Position pos) {
		int index = getElementIndexByPosition(pos);
		//System.out.println(conditionWithTrain(index,pos));
		while ((this.trainLR && isEdge(pos)) || (conditionWithTrain(index,pos)) ) {
		//while ( (conditionWithTrain(index))) {
			try {
				System.out.println("Waiting in moveRightToLeft");
				wait(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}			
		}
		if (index > 0) {
			this.withTrain[index-1] = true;
			this.withTrain[index] = false;
			notifyAll();
			return new Position(elements[index - 1], Direction.RL);
		} else if (index == 0) {
			this.withTrain[index] = false;
			this.trainRL = false;
			notifyAll();
			return new Position(elements[index], Direction.LR);
		} else {
			return new Position(elements[index + 1], Direction.LR);
		}
    }

	public synchronized void setTrainLR(boolean value) {
		this.trainLR = value;
		notifyAll(); // Awake the threads
	}
	
	public synchronized void setTrainRL(boolean value) {
		this.trainRL = value;
		notifyAll(); // Awake the threads
	}

	public boolean getTrainLR(){
		return this.trainLR;
	}

	public boolean getTrainRL(){
		return this.trainRL;
	}
	
	public int getElementIndexByPosition (Position position) {
        for (int i=0; i < elements.length; i++) {
            if (elements[i].equals(position.getPos())) {
            	return i; 
            }
		}
		return -1;
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
}
