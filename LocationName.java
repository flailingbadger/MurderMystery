/*****************************************************
 *  Authors: Melinda Frandsen
 *  		 Jason Carter
 *  A09 Final Project
 *****************************************************/
package murderMystery;

import javax.swing.ImageIcon;

/**
 * represents the possible crime scenes involved in the game
 * @author Melinda Frandsen & Jason Carter
 *
 */
public enum LocationName {
	
	BALLROOM(1, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Ballroom.png"))), "ballroom"),
	BILLIARD_ROOM(2, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Billiard.png"))), "billiard room"),
	CONSERVATORY(3, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Conservatory.png"))), "conservatory"),
	DINING_ROOM(4, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Dining.png"))), "dining room"),
	HALL(5, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Hall.png"))), "hall"),
	KITCHEN(6,(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Kitchen.png"))), "kitchen"),
	LIBRARY(7, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Library.png"))), "library"),
	LOUNGE(8, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Lounge.png"))), "lounge"),
	STUDY(9, (new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Study.png"))), "study");
	
	public int mapLocation;
	public ImageIcon image;
	public String name;
	
	/**
	 * constructor allows LocationName to be used in other classes
	 * @param mapLocation
	 * @param image
	 * @param name
	 */
	private LocationName(int mapLocation, ImageIcon image, String name) {
		this.mapLocation = mapLocation;
		this.image = image;
		this.name = name;
	}
	
}
