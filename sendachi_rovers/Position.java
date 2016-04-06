package sendachi_rovers;

/**
 * Corey Cole
 * Sendachi rovers problem
 *
 * Representation of a position
 */
public class Position {
	private int x;
	private int y;
	private String direction;
	
	/**
	 * Instantiate the Position
	 * @param x 
	 * @param y
	 * @param direction
	 */
	public Position(int x, int y, String direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	/**
	 * Turns the Position left
	 */
	public void turnLeft() {
		switch (direction) {
			case "N":
				direction = "W";
				break;
			case "W":
				direction = "S";
				break;
			case "S":
				direction = "E";
				 break;
			case "E":
				direction = "N";
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Turns the Position right
	 */
	public void turnRight() {
		switch (direction) {
			case "N":
				direction = "E";
				break;
			case "E":
				direction = "S";
				break;
			case "S":
				direction = "W";
				 break;
			case "W":
				direction = "N";
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Move the Position forward
	 */
	public void moveForward() {
		switch (direction) {
			case "N":
				y += 1;
				break;
			case "E":
				x += 1;
				break;
			case "S":
				y -= 1;
				 break;
			case "W":
				x -= 1;
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Returns the String representation of this position
	 */
	public String toString() {
		return "" + x + y + direction;
	}
}
