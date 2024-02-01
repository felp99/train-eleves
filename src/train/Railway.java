package train;

/**
 * Represents a railway circuit consisting of railway elements: stations or track sections.
 * This class manages the sequence of these elements and provides functionalities to control
 * and synchronize the movement of trains along the railway.
 * 
 * It ensures that the trains move correctly according to their positions and directions,
 * and handles the synchronization between different trains moving on the railway.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Railway {
    // Array of elements (stations or track sections) that make up the railway
    private final Element[] elements;

    /**
     * Constructor for Railway.
     * Initializes a new railway with the specified array of elements.
     * 
     * @param elements The array of elements (stations or sections) composing the railway.
     * @throws NullPointerException if the array of elements is null.
     */
    public Railway(Element[] elements) {
        if (elements == null)
            throw new NullPointerException("Array of elements cannot be null.");
        
        this.elements = elements;
        
        // Set this railway to each element
        for (Element e : elements)
            e.setRailway(this);
    }

    /**
     * Moves a train from the left to the right along the railway.
     * This method is synchronized to manage concurrent access by multiple trains.
     * 
     * @param pos The current position of the train.
     * @return The new position of the train after the move.
     */
    public synchronized Position moveLeftToRight(Position pos) {
        int index = getElementIndexByPosition(pos);
        
        // Wait if the direction is not left to right
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

    /**
     * Moves a train from the right to the left along the railway.
     * This method is synchronized to manage concurrent access by multiple trains.
     * 
     * @param pos The current position of the train.
     * @return The new position of the train after the move.
     */
    public synchronized Position moveRightToLeft(Position pos) {
        int index = getElementIndexByPosition(pos);
        
        // Wait if the direction is not right to left
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
    
    /**
     * Retrieves the index of the element corresponding to the current position of the train.
     * 
     * @param position The current position of the train.
     * @return The index of the element in the elements array.
     */
    public int getElementIndexByPosition(Position position) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i].equals(position.getPos())) {
                return i; 
            }
        }
        return -1; // Return -1 if the position is not found
    }

    /**
	 * Controls the state of the train and calculates its next position based on the current position and direction.
	 * This method also handles the case when a train reaches the end of the railway and needs to change direction.
	 * 
	 * @param pos The current position of the train.
	 * @param index The index of the current element where the train is located.
	 * @return The new position of the train.
	 */
	public synchronized Position controlState(Position pos, int index){
		if (pos.getDir() == Direction.LR) {
			if (index < elements.length - 1) {
				// If the train is not at the last element, move to the next element in the same direction
				notifyAll();       
				return new Position(elements[index + 1], Direction.LR);          
			} else if (index == elements.length - 1) {
				// If the train is at the last element, change the direction to right to left
				notifyAll();
				return new Position(elements[index], Direction.RL);          
			} else {
				// Handle unexpected case (index out of bounds)
				return new Position(elements[index - 1], Direction.RL);
			}
		} else {
			// Direction is RL
			if (index > 0) {
				// If the train is not at the first element, move to the previous element in the same direction
				return new Position(elements[index - 1], Direction.RL);
			} else if (index == 0) {
				// If the train is at the first element, change the direction to left to right
				return new Position(elements[index], Direction.LR);
			} else {
				// Handle unexpected case (index out of bounds)
				return new Position(elements[index + 1], Direction.LR);
			}
		}
	}

    /**
     * Returns a string representation of the railway, showing the sequence of elements.
     * 
     * @return A string representation of the railway.
     */
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
