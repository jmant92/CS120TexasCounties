package cs120.TexasCounties.Controller;

import java.awt.Graphics;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import cs120.TexasCounties.BackEnd.Coord2D;
import cs120.TexasCounties.BackEnd.County;
import cs120.TexasCounties.BackEnd.Region;

/**
 * The quiz manager works as a class to interact between the presentation layer and the backend layer.
 * 
 * It has a constructor that creates an instance of counties and regions
 * 
 * It reads in the county files and the coordinate files
 * 
 * The coordinate file reader has a helper method to parse the coordinates
 * 
 * It makes the polygons of each county
 * 
 * It converts longitude and latitude to pixels on screen
 * 
 * It finds the county based on the id or x and y coordinate
 * 
 * It selects a random number of counties
 * 
 * And it draws every county
 * 
 * @author JonathanMantel
 *
 */
public class QuizManager {
	private List<County> counties;
	private List<Region> regions;
	private float minX, maxX, minY, maxY;
	private int widthInPixels, heightInPixels;
	private County selection;
	
	public QuizManager() {
		counties = new LinkedList<County>();
		regions = new LinkedList<Region>();
		
		widthInPixels = 800;
		heightInPixels = 800;
		
		readCounties("data/co48_d00a.txt");
		readCoordinates("data/co48_d00.txt");
		makePolygons();
	}
	
	public List<County> getCounties() {
		return counties;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public float getMinX() {
		return minX;
	}

	public float getMaxX() {
		return maxX;
	}

	public float getMinY() {
		return minY;
	}

	public float getMaxY() {
		return maxY;
	}

	public int getWidthInPixels() {
		return widthInPixels;
	}

	public int getHeightInPixels() {
		return heightInPixels;
	}

	public County getSelection() {
		return selection;
	}

	public void setCounties(List<County> counties) {
		this.counties = counties;
	}

	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public void setWidthInPixels(int widthInPixels) {
		this.widthInPixels = widthInPixels;
	}

	public void setHeightInPixels(int heightInPixels) {
		this.heightInPixels = heightInPixels;
	}

	public void setSelection(County selection) {
		this.selection = selection;
	}
	
	/**
	 * Read and add counties from the county/attribute file; add counties
	 * to the county list. Each county will be missing its polygon (ie, null handle)...to be
	 * supplied later.
	 * @param fname
	 */
	private void readCounties(String fname) {
		
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(fname);
			br = new BufferedReader(fr);
			int lnNum; // keep track of the line number
			String str = "000000";
			for(lnNum=0; lnNum<7; lnNum++) { //skip the first few lines and keep track of the line number
				str = br.readLine();
			}
			
			County tempCounty = null;
			
			while (str != null) {
				// process data in str
				str = str.trim();
				str = str.replaceAll("\"", "");
				
				if(lnNum%7==0) tempCounty = new County(); // if it's on a new county
				
				switch(lnNum%7) {
				case 1:
					int pid = Integer.parseInt(str);
					tempCounty.setPid(pid);
					break; // set the Pid if the remainder is 1
				case 2: tempCounty.setStateCode(str); break; // same with state code
				case 3: tempCounty.setCountyCode(str); break; // same with county code
				case 4: tempCounty.setName(str); break; // same with name
				}
				
				if(lnNum%7==3) counties.add(tempCounty); // add the tempCounty if it's on the last set of info needed
				
				str = br.readLine() ; // read the line
				lnNum++;
			}
		}
		catch (IOException ex) {
			// handle exception here
			System.err.println("trouble with file: "+ex.getMessage());
		}

		finally {
			/*
			 ** attempt to close the file
			 */
			try {
				if (br != null) {
					br.close();
				}
			}
			catch (Exception ex1) {
				ex1.printStackTrace(); // give up
				System.exit(-1);
			}
		}
		
	}
	
	/**
	 *  Read the polygon file into a list of coordinates. 
	 *  Then compute the extent (minx, maxx, miny, maxy) over all counties.
	 * @param fname
	 */
	private void readCoordinates(String fname) {
		
		BufferedReader br = null;
		try {
			FileReader fr = new FileReader(fname);
			br = new BufferedReader(fr);
			int regionSet = 0; // tells which county the region is on
			String str = br.readLine(); // read the first line
			
			while (str != null) {
				
				if(!"END".equalsIgnoreCase(str)) {
					float[] coords = parseHelp(str); // parse the string to only return the coordinate pair as floats
					if(coords.length>2) { // if there are more than 2 values in the returned value
						regionSet = ((int) coords[0]-1); // read the first index, it tells us the 
						// number we're on. Since the indexes start at zero, we need to subtract 1.
					//	this.getCounties().get(regionSet).getCoords().add(new Coord2D(coords[1], coords[2]));
						// since the first index is occupied by the region set number, the next two are the coordinates
					//	this.getCounties().get(regionSet).allMinMax();
						// find all of the mins and maxes
					}
					County c = this.getCounties().get(regionSet);
					
					if(coords.length == 2) { // if the returned coordinates have exactly two add index 0 and index 1
						c.getCoords().add(new Coord2D(coords[0], coords[1]));
						// add that coordinate pair to the county
						//System.out.println(coords[0]+" , "+coords[1]);
						// find the max and min x and y
						c.allMinMax();
					}
					
					
				}
				str = br.readLine() ; // read the line
			}
		}
		catch (IOException ex) {
			// handle exception here
			System.err.println("trouble with file: "+ex.getMessage());
		}

		finally {
			/*
			 ** attempt to close the file
			 */
			try {
				if (br != null) {
					br.close();
				}
			}
			catch (Exception ex1) {
				ex1.printStackTrace(); // give up
				System.exit(-1);
			}
		}
		
		this.bigMaxMin(); // find out the extremes of the coordinates
	}
	
	/**
	 * A helper method for parsing coordinate strings that the program reads in. Returns either 2 or 3
	 * floats depending on the line read in.
	 * @param str
	 * @return
	 */
	public float[] parseHelp(String str) {
		// process data in str
		str = str.trim();
		str = str.replaceAll("\\s+", " "); // replace multiple spaces with a single space
		String[] strCoords = str.split(" "); // split the string when a space is found
		float[] coords = new float[strCoords.length]; // make an array of floats to hold the values
		
		for(int i=0; i<strCoords.length; i++) { // for every number that was put into the string array
			coords[i] = Float.parseFloat(strCoords[i]); // parse it into a float
		}
		return coords;
	}
	
	/**
	 * A helper method to set the extremes for the quiz manager
	 */
	public void bigMaxMin() {
		// the first county has the largest and smallest x's and y's by default
		maxX = counties.get(0).getMaxX();
		minX = counties.get(0).getMinX();
		maxY = counties.get(0).getMaxY();
		minY = counties.get(0).getMinY();
		
		
		
		// for every county c in counties
		for(County c: counties) {
			if(c.getMaxX()>maxX) maxX = c.getMaxX(); // if the current county has a larger max x
			if(c.getMinX()<minX) minX = c.getMinX(); // if the current county has a smaller min x
			if(c.getMaxY()>maxY) maxY = c.getMaxY(); // if the current county has a larger max y
			if(c.getMinY()<minY) minY = c.getMinY(); // if the current county has a smaller min y
			// set them all.
		}
	}
	
	/**
	 * After establishing the coordinate lists and the extent, iterate over the list 
	 * of counties (and regions). For each county/region, convert the list of coordinates
	 * to pixel coordinates and create an instance of a java.awt.Point and add the point
	 * to the current county’s (or region’s) polygon.
	 */
	private void makePolygons() {
		double lon;
		double lat;
		for(County c: counties) { // for every county c in counties
			for(int i=0; i<c.getCoords().size(); i++) { // go through all of the coordinates in that county
				lon = c.getCoords().get(i).getX(); // set the longitude to x
				lat = c.getCoords().get(i).getY(); // set the longitude to y
				Point pt = this.toPixels(lon, lat);  // convert it to pixels
				
				c.getPoly().addPoint((int)pt.getX(), (int)pt.getY()); // add that point to the polygon.
			}
		}
	}
	
	/**
	 * Creates and returns a handle on an instance of a java.awt.Point whose x 
	 * corresponds to longitude and y corresponds to latitude.
	 * @param x
	 * @param y
	 * @return
	 */
	private Point toPixels(double lon, double lat) {
		Point pixel = new Point(); // make a new point
		
		// Convert from longitude and latitude to pixels
		double px = ((lon-minX)/(maxX-minX))*widthInPixels; // find the proportion constant and multiply
		// it by the width of the screen to find the location of the x
		double py = heightInPixels-((lat-minY)/(maxY-minY))*heightInPixels; // do the same
		// but with the y
		pixel.setLocation(px, py); // set the location
		
		return pixel;
	}
	
	/**
	 * Returns a handle on the county in your county list with matching id.
	 * @param id
	 */
	public County findCountyById(int id) {
		for(County c: counties) { // for every county c in counties
			if(c.getPid()==id) return c; // if the pid of c is the same as the id integer passed through, return the county
		}
		return null; // otherwise return nothing
	}
	
	/**
	 * Define an algorithm that would iterate over each county and draw the county. 
	 * Then iterate over each exclusion regions and render each exclusion polygon in 
	 * the background color. This method will be used by during the rendering process.
	 * @param g
	 */
	public void drawCounties(Graphics g) {
		for(County c: counties) { // for each county c in counties
			c.drawOn(g);
			g.fillPolygon(c.getPoly()); // fill the polygon of that county
		}
	}
	
	/**
	 * Iterate over all counties in your county list. If you find a county whose 
	 * polygon contains the given point (x,y), then return that polygon. Otherwise, 
	 * return null. This will be used later when the user clicks on the rendered county 
	 * in order to locate the user’s desired selection.
	 * @param x
	 * @param y
	 */
	public County findCounty(int x, int y) {
		for(County c: counties) { // for every county c in counties
			if(c.getPoly().contains(x, y)) return c; // if the polygon of c contains the x and y, return the county
		}
		return null; // otherwise the x's and y's are not contained within the county
	}
	
	/**
	 * randomly pick and return n handles (use an array) on a county other than one 
	 * whose pid is specified. This method will be used to randomly fetch several 
	 * other real counties for our quiz buttons.
	 * @param n
	 * @param pid
	 * @return
	 */
	public County[] randomCount(int n, int pid) {
		County[] rc = new County[n]; // get an array of size n to hold the counties to be returned
		Random r = new Random(); // a random number generator

		int i=0; // somewhere to determine the iterations
		int chosen; // a handle on the random integer chosen
		while(i<n) { // while the iterations are less than the number of
			chosen = r.nextInt(counties.size()); // figure out what the number is
			if(chosen!=(pid-1)) { // if it isn't the pid-1 (in other words, if the pid is 1 then the chosen can't be 0)
				rc[i] = counties.get(chosen); // add it to the array in th ith slot
				i++; // increase i by one to move to the next slot in the array
			}
		}
		return rc;
	}
}
