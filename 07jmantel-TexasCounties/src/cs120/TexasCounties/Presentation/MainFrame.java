package cs120.TexasCounties.Presentation;

import javax.swing.JFrame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import cs120.TexasCounties.BackEnd.County;
import cs120.TexasCounties.Controller.QuizManager;

/**
 * This class is the Presentation layer of the Texas Counties game. It holds the class to start everything off as well as 
 * two private helper classes. One helps with interacting with the map of texas and the other helps with the buttons.
 * @author JonathanMantel
 *
 */

public class MainFrame extends JFrame {
	QuizManager q = new QuizManager(); // a quiz manager to help with the back end stuff
	
	private final int MAX_WIDTH = 800; // max width
	private final int MAX_HEIGHT = 800; // max height
	
	private JPanel pnlStatus; // a panel on the north to hold the status of the game
	private JPanel pnlTexas; // a panel in the center to hold the map of texas
	private JPanel pnlButtons; // a panel on the south to hold the buttons for the game
	
	private JLabel lblStatus; // a label to show the status of the game
	private JButton[] countyNames; // buttons to hold the name of the counties
	
	private County selected; // a handle on the current selected county
	
	private ButtonHelper bh; // a button helper
	private JLabel lblPercent; // a JLabel to show the correct guesses compared to the total guesses
	private int correct; // correct guesses
	private int total; // total guesses
	
	public MainFrame() {
		// Size specifications
		this.setMinimumSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
		this.setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		pnlStatus = new JPanel();
		getContentPane().add(pnlStatus, BorderLayout.NORTH);
		
		lblStatus = new JLabel("What is the county name?");
		pnlStatus.add(lblStatus);
		
		lblPercent = new JLabel("Correct Guesses/Total Guesses");
		pnlStatus.add(lblPercent);
		
		pnlTexas = new innerPanel();
		pnlTexas.addMouseListener(new innerPanel());
		getContentPane().add(pnlTexas, BorderLayout.CENTER);
		
		pnlButtons = new JPanel();
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		
		countyNames = new JButton[5];
		
		bh = new ButtonHelper();
		// make all of the buttons and add an action listener to them
		for(int i=0; i<countyNames.length; i++) {
			countyNames[i] = new JButton();
			countyNames[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					for(JButton b: countyNames) {
						if(e.getSource()==b) bh.guess(b);
					}
				}
				
			});
			
			pnlButtons.add(countyNames[i]); // add the buttons to the button panel
			countyNames[i].setVisible(false); // make them invisible
		}
		
		correct = 0; // start out with no correct guesses
		total = 0; // and no guesses
		
		this.pack();
		this.setVisible(true);
	}
	
	/**
	 * This private class works as a way to draw the map of texas and help with interactions
	 * @author JonathanMantel
	 *
	 */
	private class innerPanel extends JPanel implements MouseListener {
		
		public innerPanel() {
			this.repaint(); // paint the texas panel
		}
		
		/**
		 * Actually paints the map
		 */
		@Override
		public void paint(Graphics g) {
			g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);
			q.drawCounties(g); // draw every county
			
			// For highlighting the county
			Graphics2D g2 = (Graphics2D)g;
			BasicStroke fat = new BasicStroke(3.0f); // fat line 3 pixels wide 
			g2.setStroke(fat); // uses fat lines when drawing.
			
			// if a county is selected
			if(selected!=null) selected.drawOn(g2); // draw it with the fat stroke
		}

		
		/**
		 * If the map is clicked, this method decides what to do. It can either select a county, 
		 * in which case it will highlight that county and show the buttons at the bottom which allow the user
		 * to guess which county is selected. Otherwise, the buttons will be hidden and no county will be selected.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			int mx = e.getX(); // get the x position of the mouse click
			int my = e.getY(); // get the y position of the mouse click

			County c = q.findCounty(mx, my); // find the county at mx, my, set it to c
			if(c!=null) { // if the county exists
				Random r = new Random(); // generate a new random number generator
				q.setSelection(c); // set the selected county to c
				selected = q.getSelection(); // get the selected county.
				County[] cs = q.randomCount(5, c.getPid()); // get 5 random counties excluding c
			
				for(int i=0; i<cs.length; i++) { // go through all the counties
					countyNames[i].setText(cs[i].getName()); // change all of the names
				}
				
				int j = r.nextInt(cs.length); // get a random position for the county to swap with
				
				countyNames[j].setText(c.getName()); // set the name of that button to the correct county's name
				
				for(JButton cb: countyNames) { // for every button cb in countyNames
					cb.setVisible(true); // set the buttons visible
				}
			} else { // otherwise
				for(JButton cb: countyNames) { // for every button
					cb.setText(""); // clear the text
					cb.setVisible(false); // set all the buttons to be invisible
				}
				selected = null; // nothing is selected	
			}
			this.repaint(); // repaint the window
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * A simple button helper class to change what the correct/total guess value is.
	 * @author JonathanMantel
	 *
	 */
	private class ButtonHelper {
		public void guess(JButton b) { // the button entered
			total++; // the number of guesses goes up by 1
			if(b.getText()==selected.getName()) { // if the correct button is pressed 
				lblStatus.setText("Correct!"); // show correct at the top of the screen
				correct++; // add 1 to correct guesses
			} else { // otherwise
				lblStatus.setText("Wrong!"); // show the user is wrong and don't do anything else
			}
			lblPercent.setText(correct+"/"+total); // update the label that shows the correct/total guesses
		}
	}
	
	/**
	 * Start everything off
	 * @param args
	 */
	public static void main(String[] args) {
		new MainFrame();
	}
}
