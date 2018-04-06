/*****************************************************
 *  Authors: Melinda Frandsen
 *  		 Jason Carter
 *  A09 Final Project
 *****************************************************/
package murderMystery;

import javax.swing.ImageIcon;

/**
 * represents the persons involved at the scene of the crime
 * @author Melinda Frandsen & Jason Carter
 *
 */
public enum SuspectName {
	GREEN(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Green.png")),"Mr. Green"),
	MUSTARD(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Mustard.png")),"Col. Mustard"),
	PEACOCK(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Peacock.png")),"Mrs. Peacock"),
	PLUM(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Plum.png")),"Prof. Plum"),
	SCARLET(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Scarlet.png")),"Miss Scarlet"),
	WHITE(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/White.png")),"Mrs. White");
	
	public ImageIcon image;
	public String name;
	
	/**
	 * constructor allows SuspectName to be used in other classes 
	 * @param image
	 * @param name
	 */
	private SuspectName(ImageIcon image, String name) {
		this.image = image;
		this.name = name;
	}
	
}
