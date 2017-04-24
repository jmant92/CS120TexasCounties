package cs120.TexasCounties.BackEnd;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

/**
 * A set of test cases to see if the region can properly assign the max/min x and y
 */
public class RegionTests {

	/**
	 * Testing for Max/Min X and Y
	 */
	@Test
	public void maxMinXYTests() {
		Region r = new Region();
		LinkedList<Coord2D> c = new LinkedList<Coord2D>();
		
		// Add coordinates to the linked list
		c.add(0, new Coord2D(1, 10));
		c.add(1, new Coord2D(6, 3));
		c.add(2, new Coord2D(4, 19));
		c.add(3, new Coord2D(2, 19));
		
		// Make sure the one in the region is the same
		r.setCoords(c);
		
		// Find the mins and maxes
		r.theMaxX();
		r.theMinX();
		r.theMaxY();
		r.theMinY();
		
		// Test the Max/Min x/y
		assertTrue(r.getMaxX() == 6); // 6 is the largest x coordinate in the linked list
		assertTrue(r.getMaxY() == 19); // 19 is the largest y coordinate in the linked list
		assertTrue(r.getMinX() == 1); // 1 is the smallest x coordinate in the linked list
		assertTrue(r.getMinY() == 3); // 3 is the smallest y coordinate in the linked list
	}

}
