package train;

/**
 * Main class for testing the train simulation.
 * 
 * Author:
 * - Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {

    /**
     * The main method to initiate and run the train simulation.
     * 
     * @param args Command-line arguments (not used in this context).
     * @throws InterruptedException If a thread is interrupted during sleep.
     */
    public static void main(String[] args) throws InterruptedException {
        
        // Decompose to test exercise solution 1
        //exerciseOne();
        // Decompose to test exercise solution 2
        exerciseTwo();
    }

    public static void exerciseOne() throws InterruptedException{
        // Create stations A and D with capacities of 3
        Station A = new Station("GareA", 3);
        Station D = new Station("GareD", 3);

        // Create sections AB, BC, and CD
        Section AB = new Section("AB");
        Section BC = new Section("BC");
        Section CD = new Section("CD");

        // Create a railway with elements A, AB, BC, CD, and D
        Railway r = new Railway(new Element[] { A, AB, BC, CD, D });

        // Print the initial state of the railway
        System.out.println("The railway is:");
        System.out.println("\t" + r);

        // Create positions for trains at stations A and D
        Position p1 = new Position(A, Direction.LR);

        try {
            // Create trains with IDs 1, 2, and 3 at positions p1 and p2
            Train t1 = new Train("1", r, p1);

            // Print train details
            System.out.println(t1);

            // Start threads for trains t1, t2, and t3
            Thread thread1 = new Thread(t1);
            thread1.setName("T1");
            thread1.start();
            r.incrementCountLR();

        } catch (BadPositionForTrainException e) {
            System.out.println("Le train " + e.getMessage());
        }
    }

    public static void exerciseTwo() throws InterruptedException{
        // Create stations A and D with capacities of 3
        Station A = new Station("GareA", 3);
        Station D = new Station("GareD", 3);

        // Create sections AB, BC, and CD
        Section AB = new Section("AB");
        Section BC = new Section("BC");
        Section CD = new Section("CD");

        // Create a railway with elements A, AB, BC, CD, and D
        Railway r = new Railway(new Element[] { A, AB, BC, CD, D });

        // Print the initial state of the railway
        System.out.println("The railway is:");
        System.out.println("\t" + r);

        // Create positions for trains at stations A and D
        Position p1 = new Position(A, Direction.LR);
        Position p2 = new Position(D, Direction.RL);

        try {
            // Create trains with IDs 1, 2, and 3 at positions p1 and p2
            Train t1 = new Train("1", r, p1);
            Train t2 = new Train("2", r, p1);
            Train t3 = new Train("3", r, p2);

            // Print train details
            System.out.println(t1);
            System.out.println(t2);

            // Start threads for trains t1, t2, and t3
            Thread thread1 = new Thread(t1);
            thread1.setName("T1");
            thread1.start();
            r.incrementCountLR();
            Thread.sleep(5);
            Thread thread2 = new Thread(t2);
            thread2.setName("T2");
            thread2.start();
            r.incrementCountLR();
            Thread thread3 = new Thread(t3);
            thread3.setName("T3");
            thread3.start();
            r.incrementCountRL();

        } catch (BadPositionForTrainException e) {
            System.out.println("Le train " + e.getMessage());
        }
    }
}
