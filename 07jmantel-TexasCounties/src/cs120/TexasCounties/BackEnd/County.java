package cs120.TexasCounties.BackEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.Random;

/**
 * The county class holds the information read off from the county file. This includes:
 * the pid
 * the state code
 * the county code
 * and the name
 * 
 * aside from getters and setters it also has a draw on method for drawing the county.
 * 
 * It extends region, so it gets those methods as well.
 * The finalized color of the region is decided in the constructor
 * @author JonathanMantel
 *
 */
public class County extends Region {
	private int pid; // pid of the county
	private String stateCode; // state code
	private String countyCode; // county code
	private String name; // county name
	private Polygon shape; // shape of the county
	private Color color; // color of the county

	/**
	 * The constructor makes a random color for the county
	 */
	public County() {
		Random r = new Random();
		int red = r.nextInt(255);
		int blue = r.nextInt(255);
		int green = r.nextInt(255);
		
		color = new Color(red, blue, green);
		
		shape = new Polygon();
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Polygon getShape() {
		return shape;
	}

	public void setShape(Polygon shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Draw the county.
	 */
	@Override
	public void drawOn(Graphics gfx) {
		gfx.setColor(color);
		gfx.drawPolygon(this.getPoly());
	}
	
}
