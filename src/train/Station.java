package train;

/**
 * Represents a railway station. This is a subclass of the {@link Element} class.
 * A station is characterized by a name and a number of platforms (indicating the number of trains
 * it can accommodate at any given time).
 * 
 * The `Station` class inherits properties and methods from the `Element` class and introduces
 * an additional property to represent the capacity of the station in terms of the number of trains.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Station extends Element {
    // Number of platforms in the station, indicating its capacity
    private final int size;

    /**
     * Constructor for Station.
     * Initializes a new instance of a station with the specified name and size (number of platforms).
     * 
     * @param name The name of the station. Cannot be null.
     * @param size The number of platforms in the station. Must be a positive integer.
     * @throws NullPointerException if the name is null or the size is less than or equal to zero.
     */
    public Station(String name, int size) {
        super(name); // Calls the constructor of the superclass (Element) with the provided name
        
        // Validates the input parameters
        if (name == null || size <= 0)
            throw new NullPointerException("Name cannot be null and size must be greater than zero.");
        
        this.size = size; // Sets the number of platforms in the station
    }
}
