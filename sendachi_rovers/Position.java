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
	
	public Position(int x, int y, String direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
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
	
	public String toString() {
		return "" + x + y + direction;
	}
}
