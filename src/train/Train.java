package train;

/**
 * Représentation d'un train. Un train est caractérisé par deux valeurs :
 * <ol>
 *   <li>
 *     Son nom pour l'affichage.
 *   </li>
 *   <li>
 *     La position qu'il occupe dans le circuit (un élément avec une direction) : classe {@link Position}.
 *   </li>
 * </ol>
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Mayte segarra <mt.segarra@imt-atlantique.fr>
 * Test if the first element of a train is a station
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * @version 0.3
 */
public class Train implements Runnable {
	private final String name;
	private Position pos;
	private final Railway railway;

	public Train(String name, Railway railway, Position p) throws BadPositionForTrainException {
		if (name == null || p == null)
			throw new NullPointerException();

		// A train should be first be in a station
		if (!(p.getPos() instanceof Station))
			throw new BadPositionForTrainException(name);

		this.name = name;
		this.pos = p.clone();
		this.railway = railway;
	}
	
	public void moveToNextPosition() throws InterruptedException {
		if (pos.getDir() == Direction.RL) {			
			this.pos = this.railway.moveRigthToLeft(this.pos);			
		}else{
			this.pos = this.railway.moveLeftToRigth(this.pos);
		}
		System.out.println(this);
		//System.out.println(this+" "+this.railway.printWithTrain()+" LR:"+this.railway.getCountTrainsLR()+" RL:"+this.railway.getCountTrainsRL()+" "+this.railway.isEdge(this.pos));
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Train[");
		result.append(this.name);
		result.append("]");
		result.append(" is on ");
		result.append(this.pos);
		return result.toString();
	}

	@Override
	public void run() {
		int i = 1;
		while (i < 200) {
			try {
				moveToNextPosition();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				// Manejar la interrupción, si es necesario
			}
			i++;
		}
	}
}
