package train;

/**
 * @author Fabien Dagnat <fabien.dagnat@imt-atlantique.fr>
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		Station A = new Station("GareA", 3);
		Station D = new Station("GareD", 3);
		Section AB = new Section("AB");
		Section BC = new Section("BC");
		Section CD = new Section("CD");
		Railway r = new Railway(new Element[] { A, AB, BC, CD, D });
		System.out.println("The railway is:");
		System.out.println("\t" + r);
		Position p1 = new Position(A, Direction.LR);
		Position p2 = new Position(D, Direction.RL);
		try {
			Train t1 = new Train("1", r, p1);
			Train t2 = new Train("2", r, p2);			
			System.out.println(t1);
			System.out.println(t2);

			Thread thread1 = new Thread(t1); thread1.setName("T1"); thread1.start();
			r.incrementCountLR();
			Thread.sleep(5);	
			Thread thread2 = new Thread(t2); thread2.setName("T2"); thread2.start();
			r.incrementCountRL();
			
		} catch (BadPositionForTrainException e) {
			System.out.println("Le train " + e.getMessage());
		}
	}
}
