package cs120.TexasCounties.BackEnd;

/**
 * Coord2D holds a single coordinate as read from the county polygon file.
 * Floats are assigned to the x and y instance variables.
 * @author JonathanMantel
 *
 */
public class Coord2D {
	private float x, y;
	
	public Coord2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}	
}
