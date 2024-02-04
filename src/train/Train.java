package train;

/**
 * Represents a train. A train is characterized by two values:
 * <ol>
 *   <li>
 *     Its name for display purposes.
 *   </li>
 *   <li>
 *     The position it occupies in the railway circuit (an element with a direction): class {@link Position}.
 *   </li>
 * </ol>
 * 
 * Authors:
 * - Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * - Mayte Segarra <mt.segarra@imt-atlantique.fr>
 *   (Test if the first element of a train is a station)
 * - Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * @version 0.3
 */
public class Train implements Runnable {

    // Name of the train
    private final String name;

    // Position of the train in the railway circuit
    private Position pos;

    // The railway on which the train operates
    private final Railway railway;

    /**
     * Constructor for creating a Train object.
     * 
     * @param name    The name of the train.
     * @param railway The railway on which the train operates.
     * @param p       The initial position of the train.
     * @throws BadPositionForTrainException If the initial position is not a station.
     * @throws NullPointerException         If name or p is null.
     */
    public Train(String name, Railway railway, Position p) throws BadPositionForTrainException {
        if (name == null || p == null)
            throw new NullPointerException();

        // A train should first be in a station
        if (!(p.getPos() instanceof Station))
            throw new BadPositionForTrainException(name);

        this.name = name;
        this.pos = p.clone();
        this.railway = railway;
    }

    /**
     * Moves the train to the next position based on its current direction.
     * 
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public synchronized void moveToNextPosition() throws InterruptedException {
        if (pos.getDir() == Direction.RL) {           
            this.pos = this.railway.moveRigthToLeft(this.pos);          
        } else {
            this.pos = this.railway.moveLeftToRigth(this.pos);
        }
        System.out.println(this);
    }

    /**
     * Generates a string representation of the Train object.
     * 
     * @return A string describing the train and its current position.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Train[");
        result.append(this.name);
        result.append("] is on ");
        result.append(this.pos);
        return result.toString();
    }

    /**
     * The main logic executed by the train thread.
     */
    @Override
    public void run() {
        int i = 1;
        while (i < 50) {
            try {
                moveToNextPosition();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // Handle the interruption, if necessary
            }
            i++;
        }
    }
}
