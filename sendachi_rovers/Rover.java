package sendachi_rovers;

/**
 * Corey Cole
 * Sendachi rovers problem
 *
 * Representation of a rover
 */
public class Rover {
	public Position pos;
	
	public Rover(String pos) {
		int x = Integer.parseInt(pos.substring(0, 1));
		int y = Integer.parseInt(pos.substring(1, 2));
		String dir = pos.substring(2, 3);
		this.pos = new Position(x, y, dir);
	}
	
	public void go(String commands) {
		for (int i = 0; i < commands.length(); i++) {
			issueCommand(commands.substring(i, i+1));
		}
	}
	
	private void issueCommand(String command) {
		switch (command) {
			case "L":
				pos.turnLeft();
				break;
			case "R":
				pos.turnRight();
				break;
			case "M":
				pos.moveForward();
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
}
