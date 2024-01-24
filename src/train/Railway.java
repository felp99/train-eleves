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

	public boolean isEdge(Position pos){		
		int lastIndex = elements.length-1;
		int elementIndex = this.getElementIndexByPosition(pos);
		if ((lastIndex == elementIndex && pos.getDir() == Direction.LR)
		|| (elementIndex == 0 && pos.getDir() == Direction.RL)){
			return true;
		}else{
			return false;
		}		
	}

	public synchronized boolean thereIsTrainComing(Position trainPos){
		for (int i=getElementIndexByPosition(trainPos) ; i < elements.length; i++) {
			if (this.elements[i].thereIsTrain() && this.elements[i].trainDirection() != trainPos.getDir()){
				return true;
			}
		}
		return false;
	}

	public boolean canTrainMove(Train train){
		Position trainPos = train.getPos();
		if (trainPos.getPos() instanceof Station){

			int iPos = this.getElementIndexByPosition(trainPos);

			while (true) {
				if (trainPos.getDir() == Direction.LR) {
					iPos++;
				} else {
					iPos--;
				}
			}
		}

		return false;
	}
	
	public Position nextPosition(Position position) {
		int i = getElementIndexByPosition(position);		
		if (position.getDir() == Direction.LR) {
			return new Position(elements[i+1], Direction.LR);
		} else {
			return new Position(elements[i-1], Direction.RL);
		}
	}
	
	public int getElementIndexByPosition (Position position) {
        for (int i=0; i < elements.length; i++) {
            if (elements[i].equals(position.getPos())) {
            	return i; 
            }}
		return -1;
		}
	
	public int getElementsSize() {
		return this.elements.length;
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
