package cs120.TexasCounties.BackEnd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.LinkedList;

/**
 * The region class holds the coordinates of each county, the polygon calculated using those coordinates and the default
 * background of the polygon.
 * 
 * It also has methods which find the extremes of the polygon
 * @author JonathanMantel
 *
 */
public class Region {
	private LinkedList<Coord2D> coords; // a linked list of each coordinate
	private Polygon poly; // a polygon handle which draws the region using the coordinates from the list
	private Color color; // color of the region
	
	private float maxX, minX, maxY, minY;
	
	public Region() {
		color = Color.black;
		coords = new LinkedList<Coord2D>();
		poly = new Polygon();
	}
	
	public float getMaxX() {
		return maxX;
	}

	public float getMinX() {
		return minX;
	}

	public float getMaxY() {
		return maxY;
	}

	public float getMinY() {
		return minY;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public LinkedList<Coord2D> getCoords() {
		return coords;
	}

	public void setCoords(LinkedList<Coord2D> coords) {
		this.coords = coords;
	}

	public Polygon getPoly() {
		return poly;
	}

	public void setPoly(Polygon poly) {
		this.poly = poly;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Finds the max x of the coordinates
	 * @return
	 */
	public void theMaxX() {
		float maxx = coords.getFirst().getX(); // set the largest x to the first one
		for(Coord2D c: coords) { // go through the whole linked list
			if(c.getX() > maxx) { // if the x of the current coordinate set is larger than the current max
				maxx = c.getX(); // make that current coordinate the new max
			}
		}
		this.setMaxX(maxx);
	}
	
	/**
	 * Finds the min x of the coordinates
	 * @return
	 */
	public void theMinX() {
		float minx = coords.getFirst().getX(); // set the smallest x to the first one
		for(Coord2D c: coords) { // go through the whole linked list
			if(c.getX() < minx) { // if the x of the current coordinate set is smaller than the current min
				minx = c.getX(); // make that current coordinate the new min
			}
		}
		this.setMinX(minx);
	}
	
	/**
	 * Finds the max y of the coordinates
	 * @return
	 */
	public void theMaxY() {
		float maxy = coords.getFirst().getY(); // set the largest y to the first one
		for(Coord2D c: coords) { // go through the whole linked list
			if(c.getY() > maxy) { // if the y of the current coordinate set is larger than the current max
				maxy = c.getY(); // make that current coordinate the new max
			}
		}
		this.setMaxY(maxy);
	}
	
	/**
	 * Finds the min y of the coordinates
	 * @return
	 */
	public void theMinY() {
		float miny = coords.getFirst().getY(); // set the smallest y to the first one
		for(Coord2D c: coords) { // go through the whole linked list
			if(c.getY() < miny) { // if the y of the current coordinate set is smaller than the current min
				miny = c.getY(); // make that current coordinate the new min
			}
		}
		this.setMinY(miny);
	}
	
	/**
	 * A method to make sure all of the mins and maxes are calculated
	 */
	public void allMinMax() {
		this.theMaxX();
		this.theMinX();
		this.theMaxY();
		this.theMinY();
	}
	
	public void drawOn(Graphics gfx) {
		gfx.setColor(color);
		gfx.drawPolygon(poly);
	}
}
