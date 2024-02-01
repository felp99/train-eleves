package train;

/**
 * Represents a train in the railway system. A train is characterized by:
 * <ol>
 *   <li>Its name for display purposes.</li>
 *   <li>The position it occupies in the circuit (an element with a direction), represented by the {@link Position} class.</li>
 * </ol>
 * This class provides the functionality to move the train along the railway and ensures that it starts from a station.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Mayte Segarra <mt.segarra@imt-atlantique.fr>
 * Test if the first element of a train is a station
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * @version 0.3
 */
public class Train implements Runnable {
    // Name of the train for display purposes
    private final String name;
    
    // Current position of the train in the railway circuit
    private Position pos;
    
    // The railway on which the train is running
    private final Railway railway;

    /**
     * Constructor for Train.
     * Initializes a new instance of a train with the specified name, railway, and initial position.
     * 
     * @param name The name of the train. Cannot be null.
     * @param railway The railway on which the train is running. Cannot be null.
     * @param p The initial position of the train. Must be a station. Cannot be null.
     * @throws NullPointerException if the name or position is null.
     * @throws BadPositionForTrainException if the initial position is not a station.
     */
    public Train(String name, Railway railway, Position p) throws BadPositionForTrainException {
        if (name == null || p == null)
            throw new NullPointerException("Name and position cannot be null.");

        // A train should first be in a station
        if (!(p.getPos() instanceof Station))
            throw new BadPositionForTrainException(name);

        this.name = name;
        this.pos = p.clone();
        this.railway = railway;
    }
    
    /**
     * Moves the train to the next position along the railway.
     * 
     * @throws InterruptedException if the thread executing the train's movement is interrupted.
     */
    public void moveToNextPosition() throws InterruptedException {
        if (pos.getDir() == Direction.RL) {          
            this.pos = this.railway.moveRightToLeft(this.pos);            
        } else {
            this.pos = this.railway.moveLeftToRight(this.pos);
        }
        System.out.println(this);
        // Additional logging or processing can be added here
    }

    /**
     * Returns a string representation of the train, including its name and current position.
     * 
     * @return A string representation of the train.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Train[");
        result.append(this.name);
        result.append("]");
        result.append(" is on ");
        result.append(this.pos);
        return result.toString();
    }

    /**
     * The main entry point for the train's thread.
     * This method is called when the train's thread is started, and it controls the train's movement.
     */
    @Override
    public void run() {
        int i = 1;
        while (i < 200) {
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
