package train;

/**
 * This abstract class represents a generic element of a railway circuit. It encapsulates
 * common functionalities of its subclasses: the entry and exit of a train, and the association
 * with a railway circuit.
 * 
 * The specific elements that extend this class are:
 * <ol>
 *   <li>Station: Represented by the {@link Station} class</li>
 *   <li>Railway track section: Represented by the {@link Section} class</li>
 * </ol>
 * 
 * The class provides basic functionalities like assigning a name to the element and linking it to a railway.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public abstract class Element {
    // Name of the element
    private final String name;
    
    // The railway to which this element belongs
    protected Railway railway;
    
    /**
     * Constructor for Element.
     * Initializes a new instance of an element with a given name.
     * 
     * @param name The name of the element. Cannot be null.
     * @throws NullPointerException if the name is null.
     */
    protected Element(String name) {
        if (name == null)
            throw new NullPointerException("The name of the element cannot be null.");
        
        this.name = name;
    }
    
    /**
     * Sets the railway to which this element belongs.
     * 
     * @param railway The railway to be associated with this element. Cannot be null.
     * @throws NullPointerException if the railway is null.
     */
    public void setRailway(Railway railway) {
        if (railway == null)
            throw new NullPointerException("The railway cannot be null.");
        
        this.railway = railway;
    }
    
    /**
     * Returns a string representation of the element, primarily the name.
     * 
     * @return The name of the element.
     */
    @Override
    public String toString() {
        return this.name;
    }
}
