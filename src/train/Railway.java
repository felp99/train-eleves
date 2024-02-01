package train;

/**
 * Représentation d'un circuit constitué d'éléments de voie ferrée : gare ou
 * section de voie
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Railway {
	private final Element[] elements;

	public Railway(Element[] elements) {
		if(elements == null)
			throw new NullPointerException();		
		this.elements = elements;
		for (Element e : elements)
			e.setRailway(this);
	}

	public synchronized Position moveLeftToRigth(Position pos) {
		int index = getElementIndexByPosition(pos);
		while (pos.getDir() == Direction.RL) {
			try {
				System.out.println("Waiting in moveLeftToRight");
				wait(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		return controlState(pos, index);
	}

    public synchronized Position moveRigthToLeft(Position pos) {
		int index = getElementIndexByPosition(pos);
		while (pos.getDir() == Direction.LR) {
			try {
				System.out.println("Waiting in moveRightToLeft");
				wait(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}			
		}
		return controlState(pos, index);
    }	
	
	public int getElementIndexByPosition (Position position) {
        for (int i=0; i < elements.length; i++) {
            if (elements[i].equals(position.getPos())) {
            	return i; 
            }
		}
		return -1;
	}

	public synchronized Position controlState(Position pos, int index){
		if (pos.getDir() == Direction.LR){
			if (index < elements.length - 1) {
				notifyAll();		
				return new Position(elements[index + 1], Direction.LR);			
			} else if (index == elements.length - 1) {
				notifyAll();
				return new Position(elements[index], Direction.RL);			
			} else {
				return new Position(elements[index - 1], Direction.RL);
			}
		}else{
			if (index > 0) {
				return new Position(elements[index - 1], Direction.RL);
			} else if (index == 0) {
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
}
