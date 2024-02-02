package train;

/**
 * Represents the position of a train on the track. A position is characterized by two values:
 * <ol>
 *   <li>
 *     The element where the train is positioned: a station (class {@link Station})
 *     or a section of railway track (class {@link Section}).
 *   </li>
 *   <li>
 *     The direction it is heading (enumeration {@link Direction}): from left to
 *     right or from right to left.
 *   </li>
 * </ol>
 * 
 * This class is responsible for modeling the position of a train within the railway system.
 * 
 * Authors:
 * - Fabien Dagnat <fabien.dagnat@imt-atlantique.fr> (Modified by Mayte Segarra)
 * - Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 * 
 * @version 0.3
 */
public class Position implements Cloneable {

    // The direction the train is heading
    private Direction direction;

    // The element (station or section) where the train is positioned
    private final Element pos;

    /**
     * Constructor for creating a Position object.
     * 
     * @param elt The element where the train is positioned (station or section).
     * @param d   The direction the train is heading.
     * @throws NullPointerException If either elt or d is null.
     */
    public Position(Element elt, Direction d) {
        if (elt == null || d == null)
            throw new NullPointerException();

        this.pos = elt;
        this.direction = d;
    }

    /**
     * Clones the Position object.
     * 
     * @return A cloned Position object.
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
     * Gets the element where the train is positioned.
     * 
     * @return The element (station or section) where the train is positioned.
     */
    public Element getPos() {
        return pos;
    }

    /**
     * Changes the direction of the train.
     */
    public void changeDir() {
        if (direction == Direction.LR) {
            this.direction = Direction.RL;
        } else {
            this.direction = Direction.LR;
        }
    }

    /**
     * Gets the direction the train is heading.
     * 
     * @return The direction of the train.
     */
    public Direction getDir() {
        return direction;
    }

    /**
     * Generates a string representation of the Position object.
     * 
     * @return A string describing the position and direction of the train.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(this.pos.toString());
        result.append(" going ");
        result.append(this.direction);
        return result.toString();
    }
}
