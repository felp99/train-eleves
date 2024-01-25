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
	private int trainLR = 0;
	private int trainRL = 0;

	public Railway(Element[] elements) {
		if(elements == null)
			throw new NullPointerException();		
		this.elements = elements;
		for (Element e : elements)
			e.setRailway(this);
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

	public synchronized Position moveLeftToRigth(Position pos) {
		int index = getElementIndexByPosition(pos);
			if (index < elements.length - 1) {
				return new Position(elements[index + 1], Direction.LR);
			} else if (index == elements.length - 1) {
				return new Position(elements[index], Direction.RL);
			} else {
				return new Position(elements[index - 1], Direction.RL);
			}	
	}

    public synchronized Position moveRigthToLeft(Position pos) {
		int index = getElementIndexByPosition(pos);
		while (this.trainLR == 0) {
			// Espera activa: El hilo espera hasta que la condición cambie
			try {
				wait(); // Se libera el bloqueo mientras espera
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				// Manejar la interrupción si es necesario
			}
			if (index > 0) {
				return new Position(elements[index - 1], Direction.RL);
			} else if (index == 0) {
				return new Position(elements[index], Direction.LR);
			} else {
				return new Position(elements[index + 1], Direction.LR);
			}
		}
		return pos;
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
}
