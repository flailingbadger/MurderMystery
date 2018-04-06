/*****************************************************
 *  Authors: Melinda Frandsen
 *  		 Jason Carter
 *  A09 Final Project
 *****************************************************/
package murderMystery;

import javax.swing.ImageIcon;

/**
 * represents the weapons involved at the scene of the crime
 * @author Melinda Frandsen & Jason Carter
 *
 */
public enum WeaponType {
	CANDLESTICK(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Candlestick.png")),"candlestick"),
	KNIFE(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Knife.png")),"knife"),
	LEADPIPE(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Leadpipe.png")),"lead pipe"),
	REVOLVER(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Revolver.png")),"revolver"),
	ROPE(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Rope.png")),"rope"),
	WRENCH(new ImageIcon(LocationName.class.getResource("/MurderMystery/Images/Wrench.png")),"wrench");
	
	public ImageIcon image;
	public String name;

	/**
	 * constructor allows WeaponType to be used in other classes
	 * @param image
	 * @param name
	 */
	private WeaponType(ImageIcon image, String name) {
		this.image = image;
		this.name = name;
	}
	
}
