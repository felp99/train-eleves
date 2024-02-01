package train;

/**
 * The BadPositionForTrainException is a custom exception class.
 * It is thrown when a train is assigned a position that is invalid 
 * or not allowed in the context of the railway system.
 */
public class BadPositionForTrainException extends Exception {
    
    // Unique ID for serialization, ensuring class consistency during serialization and deserialization
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructor for BadPositionForTrainException.
     * Initializes a new instance of the exception with a specific message.
     * 
     * @param name The detailed message that explains the cause of the exception.
     */
    public BadPositionForTrainException(String name){
        super(name); // Calls the constructor of the superclass (Exception) with the provided message
    }
    
    /**
     * Retrieves the detail message of this exception.
     * The detail message is provided at the time of the exception's construction.
     * 
     * @return The detail message string of this Throwable instance.
     */
    @Override
    public String getMessage(){
        return super.getMessage(); // Returns the detail message from the superclass (Exception)
    }
}
