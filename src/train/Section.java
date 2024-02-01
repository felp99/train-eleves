package train;

/**
 * Represents a section of a railway track. It is a subclass of the {@link Element} class.
 * A track section is mainly characterized by its name and is a fundamental part of the railway,
 * connecting stations or other track sections.
 * 
 * The `Section` class inherits properties and methods from the `Element` class and is mainly used
 * to represent and manage individual sections of the railway track in the railway network.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * @author Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Section extends Element {
    
    /**
     * Constructor for Section.
     * Initializes a new instance of a track section with the specified name.
     * 
     * @param name The name of the track section. Cannot be null.
     * @throws NullPointerException if the name is null.
     */
    public Section(String name) {
        super(name); // Calls the constructor of the superclass (Element) with the provided name
    }
}
