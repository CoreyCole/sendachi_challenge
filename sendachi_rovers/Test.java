package sendachi_rovers;

/**
 * Corey Cole
 * Sendachi rovers problem
 *
 * Main testing class
 */
public class Test {
	public static void main(String[] args) {
		Rover r1 = new Rover("12N");
		r1.go("LMLMLMLMM");
		Rover r2 = new Rover("33E");
		r2.go("MMRMMRMRRM");
		System.out.println(r1.pos.toString());
		System.out.println(r2.pos.toString());
	}
}
