package train;

/**
 * Represents the position of a train in the railway circuit. A position is
 * characterized by two attributes:
 * <ol>
 *   <li>The element where the train is positioned: a station ({@link Station}) or a railway track section ({@link Section}).</li>
 *   <li>The direction in which the train is moving ({@link Direction}): from left to right or from right to left.</li>
 * </ol>
 * This class provides functionalities to manage and represent the train's position and direction within the railway network.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr> Modified by Mayte Segarra
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * @version 0.3
 */
public class Position implements Cloneable {
    // The direction in which the train is moving
    private Direction direction;
    
    // The element (station or track section) where the train is currently located
    private final Element pos;

    /**
     * Constructor for Position.
     * Initializes a new instance of a train's position with the specified element and direction.
     * 
     * @param elt The element (station or section) where the train is positioned. Cannot be null.
     * @param d The direction in which the train is moving. Cannot be null.
     * @throws NullPointerException if the element or direction is null.
     */
    public Position(Element elt, Direction d) {
        if (elt == null || d == null)
            throw new NullPointerException("Element and direction cannot be null.");
        
        this.pos = elt;
        this.direction = d;
    }

    /**
     * Creates and returns a copy of this object.
     * 
     * @return A clone of this instance.
     */
    @Override
    public Position clone() {
        try {
            return (Position) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves the current element (station or section) where the train is located.
     * 
     * @return The current position of the train.
     */
    public Element getPos() {
        return pos;
    }
    
    /**
     * Changes the direction of the train to the opposite direction.
     * If the current direction is LR (left to right), it changes to RL (right to left), and vice versa.
     */
    public void changeDir() {
        if (direction == Direction.LR) {
            this.direction = Direction.RL;
        } else {
            this.direction = Direction.LR;
        }       
    }
    
    /**
     * Retrieves the current direction of the train.
     * 
     * @return The current direction of the train.
     */
    public Direction getDir() {
        return direction;
    }

    /**
     * Returns a string representation of the position, including the current element and direction.
     * 
     * @return A string representation of the position.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.pos.toString());
        result.append(" going ");
        result.append(this.direction);
        return result.toString();
    }
}
