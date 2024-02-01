package train;

/**
 * The Main class of the railway system simulation.
 * This class sets up the railway network components including stations, track sections,
 * and creates trains. It then initiates the simulation by starting the train threads.
 * 
 * This is where the entire railway system comes to life and demonstrates the interaction
 * between various components like stations, sections, trains, and their movement.
 * 
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {
    
    /**
     * The entry point of the application.
     * 
     * @param args Command-line arguments (not used in this application).
     * @throws InterruptedException if any thread has interrupted the current thread.
     */
    public static void main(String[] args) throws InterruptedException {
        // Creating stations with the specified name and capacity (number of platforms)
        Station A = new Station("GareA", 3);
        Station D = new Station("GareD", 3);
        
        // Creating sections of the railway track with the specified name
        Section AB = new Section("AB");
        Section BC = new Section("BC");
        Section CD = new Section("CD");
        
        // Creating the railway and adding elements (stations and sections) to it
        Railway r = new Railway(new Element[] { A, AB, BC, CD, D });
        
        // Displaying the structure of the railway
        System.out.println("The railway is:");
        System.out.println("\t" + r);
        
        // Setting up the initial position for a train
        Position p1 = new Position(A, Direction.LR);
        
        try {
            // Creating a train with the specified ID, assigned railway, and initial position
            Train t1 = new Train("1", r, p1);
            
            // Displaying the train details
            System.out.println(t1);

            // Starting the train movement in a separate thread
            Thread thread1 = new Thread(t1);
            thread1.setName("T1");
            thread1.start();
            
        } catch (BadPositionForTrainException e) {
            // Handling the exception in case of an invalid position for the train
            System.out.println("Le train " + e.getMessage());
        }
    }
}
