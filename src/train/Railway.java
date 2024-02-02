package train;

import java.util.Arrays;

/**
 * Represents a railway circuit composed of railway elements: stations or sections of railway track.
 * 
 * Authors:
 * - Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * - Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Railway {
    
    // Array of railway elements
    private final Element[] elements;

    // Count of trains moving from left to right
    private int countTrainsLR = 0;

    // Count of trains moving from right to left
    private int countTrainsRL = 0;

    // Array indicating whether a train is present on each element
    private boolean[] withTrain;    

    /**
     * Constructor for creating a Railway object.
     * 
     * @param elements An array of railway elements to be included in the circuit.
     * @throws NullPointerException If the elements array is null.
     */
    public Railway(Element[] elements) {
        if(elements == null)
            throw new NullPointerException();        
        this.elements = elements;
        for (Element e : elements)
            e.setRailway(this);

        this.withTrain = new boolean[elements.length];
        Arrays.fill(withTrain, false);
    }

    /**
     * Moves a train from left to right within the railway circuit.
     * 
     * @param pos The current position of the train.
     * @return The new position of the train after moving.
     */
    public synchronized Position moveLeftToRigth(Position pos) {
        int index = getElementIndexByPosition(pos);
        while ((this.countTrainsRL != 0 && isEdge(pos) && hasTrainOnRailway()) || (index < (elements.length-1) && this.withTrain[index + 1])) {
            try {
                System.out.println("Waiting in moveLeftToRight");
                System.out.println(printWithTrain());
                System.out.println("LR:"+this.countTrainsLR);
                System.out.println("RL:"+this.countTrainsRL);
                System.out.println(isEdge(pos));
                System.out.println(hasTrainOnRailway());
                wait(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return controlState(pos, index);
    }

    /**
     * Moves a train from right to left within the railway circuit.
     * 
     * @param pos The current position of the train.
     * @return The new position of the train after moving.
     */
    public synchronized Position moveRigthToLeft(Position pos) {
        int index = getElementIndexByPosition(pos);
        while ((this.countTrainsLR != 0 && isEdge(pos)) || (index > 0 && this.withTrain[index - 1])) {
            try {
                System.out.println("Waiting in moveRightToLeft");    
                System.out.println(printWithTrain());
                System.out.println("LR:"+this.countTrainsLR);
                System.out.println("RL:"+this.countTrainsRL);
                System.out.println(isEdge(pos));				
                System.out.println(hasTrainOnRailway());
                wait(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }           
        }
        return controlState(pos, index);
    }

    /**
     * Increments the count of trains moving from left to right.
     */
    public synchronized void incrementCountLR() {
        countTrainsLR++;
    }

    /**
     * Increments the count of trains moving from right to left.
     */
    public synchronized void incrementCountRL() {
        countTrainsRL++;
    }

    /**
     * Decrements the count of trains moving from left to right.
     */
    public synchronized void decrementCountLR() {
        countTrainsLR--;
    }

    /**
     * Decrements the count of trains moving from right to left.
     */
    public synchronized void decrementCountRL() {
        countTrainsRL--;
    }

    /**
     * Gets the count of trains moving from left to right.
     * 
     * @return The count of trains moving from left to right.
     */
    public synchronized int getCountTrainsLR() {
        return countTrainsLR;
    }

    /**
     * Gets the count of trains moving from right to left.
     * 
     * @return The count of trains moving from right to left.
     */
    public synchronized int getCountTrainsRL() {
        return countTrainsRL;
    }

    /**
     * Gets an array indicating whether a train is present on each element.
     * 
     * @return An array indicating whether a train is present on each element.
     */
    public boolean[] getElementsWithTrain(){
        return this.withTrain;
    }
    
    /**
     * Gets the index of an element in the circuit based on its position.
     * 
     * @param position The position of the element.
     * @return The index of the element in the circuit, or -1 if not found.
     */
    public synchronized int getElementIndexByPosition(Position position) {
        for (int i=0; i < elements.length; i++) {
            if (elements[i].equals(position.getPos())) {
                return i; 
            }
        }
        return -1;
    }

    /**
     * Checks if a position is at the edge of the railway circuit.
     * 
     * @param pos The position to check.
     * @return True if the position is at the edge, false otherwise.
     */
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

   /**
     * Controls the state of the railway after a train moves.
     * 
     * @param pos   The current position of the train.
     * @param index The index of the element in the circuit.
     * @return The new position of the train.
     */
    public synchronized Position controlState(Position pos, int index) {

        int nextIndex = (pos.getDir() == Direction.LR) ? index + 1 : index - 1;

        if (isValidIndex(nextIndex) && this.elements[nextIndex] instanceof Section) {
            this.withTrain[nextIndex] = true;
        }

        this.withTrain[index] = false;

        notifyAll();

        if (isValidIndex(nextIndex)) {
            return new Position(elements[nextIndex], pos.getDir());
        } else {
            incrementDecrementCount(pos.getDir());
            return new Position(elements[index], (pos.getDir() == Direction.LR) ? Direction.RL : Direction.LR);
        }
    }

    /**
     * Checks if the given index is valid within the bounds of the railway elements array.
     * 
     * @param index The index to check.
     * @return True if the index is valid, false otherwise.
     */
    private boolean isValidIndex(int index) {
        return index >= 0 && index < elements.length;
    }

    /**
     * Increments or decrements the count of trains based on the given direction.
     * 
     * @param dir The direction of the train.
     */
    private void incrementDecrementCount(Direction dir) {
        if (dir == Direction.LR) {
            incrementCountRL();
            decrementCountLR();
        } else {
            incrementCountLR();
            decrementCountRL();
        }
    }

	/**
     * Generates a string representation of the railway circuit.
     * 
     * @return A string describing the railway circuit.
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

    /**
     * Generates a string representation of the withTrain array.
     * 
     * @return A string describing the presence of trains on each element.
     */
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

    /**
     * Checks if there is at least one train on the railway.
     * 
     * @return True if there is at least one train on the railway, false otherwise.
     */
    public synchronized boolean hasTrainOnRailway() {
        for (int i = 0; i < withTrain.length; i++) {
            if (this.withTrain[i] && this.elements[i] instanceof Section ) {
                return true;
            }
        }
        return false;
    }
}