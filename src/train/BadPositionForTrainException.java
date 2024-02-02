package train;

/**
 * Custom exception class for handling cases where a train is in a bad position.
 * Extends the built-in Exception class.
 */
public class BadPositionForTrainException extends Exception {

    // Unique identifier for serialization
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for BadPositionForTrainException.
     *
     * @param name A String representing the name or details of the exception.
     */
    public BadPositionForTrainException(String name) {
        // Call the constructor of the parent class (Exception) with the provided message
        super(name);
    }

    /**
     * Retrieve the detailed message associated with this exception.
     *
     * @return A String representing the exception message.
     */
    public String getMessage() {
        // Call the getMessage method of the parent class (Exception) to get the exception message
        return super.getMessage();
    }
}
