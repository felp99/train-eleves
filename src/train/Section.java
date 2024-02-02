package train;

/**
 * Represents a section of railway track. This class is a subclass of the {@link Element} class.
 * It inherits the properties and behavior of the Element class and represents a segment of the railway.
 * 
 * Authors:
 * - Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 * - Philippe Tanguy <philippe.tanguy@imt-atlantique.fr>
 */
public class Section extends Element {

    /**
     * Constructor for creating a Section object with the specified name.
     * 
     * @param name The name of the section.
     */
    public Section(String name) {
        super(name);
    }
}
