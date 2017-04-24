package cs120.TexasCounties.Controller;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * This class holds the tests for the Quiz Manager. It tests:
 * Initializing the class which includes:
 * Reading and placing the counties correctly
 * Reading and placing their coordinates correctly
 * And making the polygons for the counties
 * 
 * It also covers:
 * Finding a county based on the x and y coordinate
 * And finding an array of counties
 * @author JonathanMantel
 *
 */

public class QuizManagerTests {
	QuizManager q = new QuizManager();
	
	@Ignore
	@Test
	public void readCountiesTest() {
		
		
		// Test for the first county
		assertTrue(1==q.getCounties().get(0).getPid()); // is the Pid right?
		assertTrue("48".equalsIgnoreCase(q.getCounties().get(0).getStateCode())); // is the state code right?
		assertTrue("111".equalsIgnoreCase(q.getCounties().get(0).getCountyCode())); // is the county code right?
		assertTrue("Dallam".equalsIgnoreCase(q.getCounties().get(0).getName())); // is the name right?
		
		
		// Test for the last county
		int countyS = q.getCounties().size(); // a way to get the last index of the county
		
		assertTrue(265==q.getCounties().get(countyS-1).getPid()); // is the Pid right?
		assertTrue("48".equalsIgnoreCase(q.getCounties().get(countyS-1).getStateCode())); // is the state code right?
		assertTrue("061".equalsIgnoreCase(q.getCounties().get(countyS-1).getCountyCode())); // is the county code right?
		assertTrue("Cameron".equalsIgnoreCase(q.getCounties().get(countyS-1).getName())); // is the name right?
	}
	
	/**
	 * This set of tests is designed to check if the parsing helper method returns 2 floats correctly.
	 */
	@Ignore
	@Test
	public void parseHelpTest() {
		String testStr = "         1      -0.102635043741763E+03       0.362141434382716E+02";
		float[] c1 = q.parseHelp(testStr);
		System.out.println("The floats are: ");
		for(int i=0; i<c1.length; i++) System.out.println(c1[i]);
		
		Assert.assertArrayEquals(new float[]{(float) 1.0, (float) -102.64, (float)36.21}, c1, 1);
		
		testStr = " -0.103002434000000E+03       0.365003970000000E+02";
		float[] c2 = q.parseHelp(testStr);
		System.out.println();
		System.out.println("The second coordinate set is: ");
		for(int i=0; i<c2.length; i++) System.out.println(c2[i]);
		
		Assert.assertArrayEquals(new float[]{(float) -103.002, (float) 36.5}, c2, 1);
	}
	
	/**
	 * This set of tests is for reading the coordinates correctly. It covers reading off the first and last coordinate
	 * pair for the first region set as well as reading off the last coordinate pair on the last region
	 * set.
	 */
	@Ignore
	@Test
	public void readCoordinatesTest() {
		
		Assert.assertArrayEquals(new float[]{(float) -102.64, (float)36.21},
				new float[]{q.getCounties().get(0).getCoords().get(0).getX(), 
				q.getCounties().get(0).getCoords().get(0).getY()}, 1); // is the first set of coordinates right?
		
		Assert.assertArrayEquals(new float[]{(float) -103.002, (float)36.5},
				new float[]{q.getCounties().get(0).getCoords().get(1).getX(), 
				q.getCounties().get(0).getCoords().get(2).getY()}, 1); // is the second set of coordinates right?
		
		// Now to check the last set in the last county
		int lastC = q.getCounties().size()-1;
		int lastCoord = q.getCounties().get(lastC).getCoords().size()-1;
		String str = "      -0.973824849332018E+02       0.264113260666133E+02"; // used as a reference
		
		Assert.assertArrayEquals(new float[]{(float) -97.38, (float)26.411},
				new float[]{q.getCounties().get(lastC).getCoords().get(1).getX(), 
				q.getCounties().get(lastC).getCoords().get(lastCoord).getY()}, 1); // is the very last set of coordinates right?
	}
	
	/**
	 * This is a set of tests to see if a county can be found by its id
	 */
	@Ignore
	@Test
	public void findCountyIDTests() {
		// The county should have the same Pid if it's the one returned
		Assert.assertTrue(q.getCounties().get(0).getPid() == q.findCountyById(1).getPid()); // first county
		Assert.assertTrue("Dallam".equalsIgnoreCase(q.findCountyById(1).getName())); // first is Dallam
		
		Assert.assertTrue(q.getCounties().get(264).getPid() == q.findCountyById(265).getPid()); // last county
		Assert.assertTrue("Cameron".equalsIgnoreCase(q.findCountyById(265).getName())); // last is Cameron
	}
	
	/**
	 * This set of tests is to see if the algorithm can find the county by the x and y entered
	 */
	@Test
	public void findCountyXY() {
		assertTrue("Dallam".equalsIgnoreCase(q.findCounty(222, 0).getName())); // is the correct county found?
	}

}
