/*****************************************************
 *  Authors: Melinda Frandsen
 *  		 Jason Carter
 *  A09 Final Project
 *****************************************************/
package murderMystery;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.GridLayout;

/**
 * Graphic User Interface app for the Murder Mystery Game
 * 
 * @author Melinda Frandsen & Jason Carter
 */
public class MurderMysteryGameGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5725321292837290619L;
	private JPanel contentPane;
	private final ButtonGroup btnGroupSuspect = new ButtonGroup();
	private final ButtonGroup btnGroupLocation = new ButtonGroup();
	private final ButtonGroup btnGroupWeapon = new ButtonGroup();
	Icon map1 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navBall.png"));
	Icon map1s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navBallSelected.png"));
	Icon map2 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navBill.png"));
	Icon map2s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navBillSelected.png"));
	Icon map3 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navCons.png"));
	Icon map3s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navConsSelected.png"));
	Icon map4 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navDin.png"));
	Icon map4s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navDinSelected.png"));
	Icon map5 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navHall.png"));
	Icon map5s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navHallSelected.png"));
	Icon map6 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navKitch.png"));
	Icon map6s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navKitchSelected.png"));
	Icon map7 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navLib.png"));
	Icon map7s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navLibSelected.png"));
	Icon map8 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navLoun.png"));
	Icon map8s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navLounSelected.png"));
	Icon map9 = new ImageIcon(getClass().getResource("/MurderMystery/Images/navStud.png"));
	Icon map9s = new ImageIcon(getClass().getResource("/MurderMystery/Images/navStudSelected.png"));
	Icon intro = new ImageIcon(getClass().getResource("/MurderMystery/Images/ClueTemp.jpg"));
	Icon gameSheet = new ImageIcon(getClass().getResource("/MurderMystery/Images/Sheet.png"));
	Icon suspectsLabel = new ImageIcon(getClass().getResource("/MurderMystery/Images/suspectsLabel.png"));
	Icon weaponsLabel = new ImageIcon(getClass().getResource("/MurderMystery/Images/weaponsLabel.png"));
	Icon roomsLabel = new ImageIcon(getClass().getResource("/MurderMystery/Images/roomsLabel.png"));
	Icon fancy = new ImageIcon(getClass().getResource("/MurderMystery/Images/fancy.png"));
	private JLabel lblSuspectCard;
	private JLabel lblWeaponCard;
	private JLabel lblRoom;
	private JButton btnLeft;
	private JButton btnRight;
	private JButton btnUp;
	private JButton btnDown;
	private JTextArea lblOutput;
	private int mapLocation = 5;
	private JRadioButton rdbtnBallroom;
	private JRadioButton rdbtnBilliardRoom;
	private JRadioButton rdbtnConservatory;
	private JRadioButton rdbtnDiningRoom;
	private JRadioButton rdbtnHall;
	private JRadioButton rdbtnKitchen;
	private JRadioButton rdbtnLibrary;
	private JRadioButton rdbtnLounge;
	private JRadioButton rdbtnStudy;
	private Guess myGuess;
	private LocationName ln;
	private SuspectName sn;
	private WeaponType wt;
	private JLabel lblIntroImage;
	private JTextArea txtAreaIntro;
	private JTextArea txtAreaTime;
	private JButton btnPlayAgain;
	private JButton btnQuit;
	long timeStart;
	long timeStop;
	long highScore;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MurderMysteryGameGui frame = new MurderMysteryGameGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MurderMysteryGameGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 5, 1200, 760);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel accusationPanel = createAccusationPanel();
		contentPane.add(accusationPanel, BorderLayout.WEST);

		JPanel navigationPanel = createNavigationPanel();
		contentPane.add(navigationPanel, BorderLayout.EAST);

		JLayeredPane mainLayeredPane = createMainPane();
		contentPane.add(mainLayeredPane, BorderLayout.CENTER);

		// computer generates winning elements of game
		// these will appear on output in eclipse for verification purposes only
		myGuess = new Guess();
		System.out.println(myGuess.getGuiltyWeapon());
		System.out.println(myGuess.getGuiltySuspect());
		System.out.println(myGuess.getGuiltyLocation());

		// reads fastest score from file, converts from milliseconds and
		// displays in intro
		try (Scanner reader = new Scanner(MurderMysteryGameGui.class.getResourceAsStream("Files/HighScore.txt"))) {
			String temp = reader.nextLine();
			highScore = Long.parseLong(temp);
			txtAreaIntro.setText(txtAreaIntro.getText() + "\nCurrent Fastest Time: " + convertLongToTime(highScore));
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * creates layered pane for the middle display area
	 * 
	 * @return mainLayeredPane
	 */
	private JLayeredPane createMainPane() {
		JLayeredPane mainLayeredPane = new JLayeredPane();

		JButton btnPlayGame = new JButton("Play Game!");
		btnPlayGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timeStart = System.currentTimeMillis();
				btnPlayGame.setVisible(false);
				lblIntroImage.setVisible(false);
				txtAreaIntro.setVisible(false);
			}
		});
		btnPlayGame.setBounds(290, 580, 100, 43);
		mainLayeredPane.add(btnPlayGame);

		// intro screen supplies back story
		txtAreaIntro = new JTextArea();
		txtAreaIntro.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtAreaIntro.setText("Kanye West has been murdered at a dinner party!\n"
				+ "Earlier in the evening he interupted the host yelling, \n"
				+ "\"Yo, Taylor, I'm really happy for you, I'ma let you finish, \n"
				+ "but Beyoncé had one of the best dinner parties of all time! \n"
				+ "One of the best parties of all time!\n\n" + "It's up to you to solve the crime.\n"
				+ "Make your way from room to room and make your accusations\n"
				+ "of who you think did it and with what weapon.\n" + "Now, I'ma let you finish.\n");
		txtAreaIntro.setBounds(85, 347, 489, 230);
		mainLayeredPane.add(txtAreaIntro);

		lblIntroImage = new JLabel();
		lblIntroImage.setIcon(intro);
		lblIntroImage.setBounds(20, 85, 640, 560);

		mainLayeredPane.add(lblIntroImage);

		JLabel lblMurderMysteryGame = new JLabel("");
		lblMurderMysteryGame.setIcon(fancy);
		lblMurderMysteryGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblMurderMysteryGame.setBounds(70, 19, 546, 55);
		mainLayeredPane.add(lblMurderMysteryGame);

		lblSuspectCard = new JLabel();
		lblSuspectCard.setBounds(100, 231, 180, 230);
		mainLayeredPane.add(lblSuspectCard);

		lblWeaponCard = new JLabel();
		lblWeaponCard.setBounds(310, 231, 180, 230);
		mainLayeredPane.add(lblWeaponCard);

		lblRoom = new JLabel();
		lblRoom.setIcon(LocationName.HALL.image);
		ln = LocationName.HALL;
		lblRoom.setBounds(20, 85, 640, 415);
		mainLayeredPane.add(lblRoom);

		lblOutput = new JTextArea("Navigation buttons on the right move you to your choice of rooms.\n"
				+ "Choose your weapon and suspect on the left.");
		lblOutput.setWrapStyleWord(true);
		lblOutput.setLineWrap(true);
		lblOutput.setBorder(new EmptyBorder(10, 10, 5, 5));
		lblOutput.setFont(new Font("Monospaced", Font.PLAIN, 14));
		lblOutput.setBounds(190, 510, 300, 113);
		mainLayeredPane.add(lblOutput);

		txtAreaTime = new JTextArea();
		txtAreaTime.setBorder(new EmptyBorder(5, 5, 5, 5));
		txtAreaTime.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtAreaTime.setBounds(241, 630, 201, 40);
		mainLayeredPane.add(txtAreaTime);
		txtAreaTime.setVisible(false);

		// button allows player to restart the game
		btnPlayAgain = new JButton("PLAY AGAIN");
		btnPlayAgain.setVisible(false);
		btnPlayAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					MurderMysteryGameGui frame2 = new MurderMysteryGameGui();
					frame2.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		btnPlayAgain.setBounds(175, 682, 117, 29);
		mainLayeredPane.add(btnPlayAgain);

		// button allows player to leave the game
		btnQuit = new JButton("QUIT");
		btnQuit.setVisible(false);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnQuit.setBounds(373, 682, 117, 29);
		mainLayeredPane.add(btnQuit);
		txtAreaTime.setVisible(false);
		return mainLayeredPane;

	}

	/**
	 * navigation panel allows user to move from room to room synchronizes with
	 * the map area in navigation panel, lblRoom in mainLayeredPane and location
	 * radio button in Accusation panel
	 * 
	 * @return
	 */
	private JPanel createNavigationPanel() {
		JPanel navigationPanel = new JPanel();
		navigationPanel.setBorder(new EmptyBorder(30, 10, 100, 20));
		navigationPanel.setLayout(new GridLayout(0, 3, 0, 0));

		JLabel label = new JLabel("");
		navigationPanel.add(label);

		JLabel label_1 = new JLabel("");
		navigationPanel.add(label_1);

		JLabel label_2 = new JLabel("");
		navigationPanel.add(label_2);

		// map area labels 1 - 9
		JLabel lblMap1 = new JLabel();
		navigationPanel.add(lblMap1);
		lblMap1.setIcon(map1);

		JLabel lblMap2 = new JLabel();
		navigationPanel.add(lblMap2);
		lblMap2.setIcon(map2);

		JLabel lblMap3 = new JLabel();
		navigationPanel.add(lblMap3);
		lblMap3.setIcon(map3);

		JLabel lblMap4 = new JLabel();
		navigationPanel.add(lblMap4);
		lblMap4.setIcon(map4);

		JLabel lblMap5 = new JLabel();
		navigationPanel.add(lblMap5);
		lblMap5.setIcon(map5s);

		JLabel lblMap6 = new JLabel();
		navigationPanel.add(lblMap6);
		lblMap6.setIcon(map6);

		JLabel lblMap7 = new JLabel();
		navigationPanel.add(lblMap7);
		lblMap7.setIcon(map7);

		JLabel lblMap8 = new JLabel();
		navigationPanel.add(lblMap8);
		lblMap8.setIcon(map8);

		JLabel lblMap9 = new JLabel();
		navigationPanel.add(lblMap9);
		lblMap9.setIcon(map9);

		JLabel lblEmpty = new JLabel("empty");
		lblEmpty.setVisible(false);
		navigationPanel.add(lblEmpty);

		JLabel lblEmpty_1 = new JLabel("empty");
		lblEmpty_1.setVisible(false);
		navigationPanel.add(lblEmpty_1);

		JLabel lblEmpty_2 = new JLabel("empty");
		lblEmpty_2.setVisible(false);
		navigationPanel.add(lblEmpty_2);

		JButton btnEmpty = new JButton("empty");
		btnEmpty.setVisible(false);
		navigationPanel.add(btnEmpty);

		// Up, Down, Left, Right navigation buttons for moving between rooms
		btnUp = new JButton("Up");
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMapIcons(lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7, lblMap8, lblMap9);
				resetDisableNavButtons();
				mapLocation = moveUp(mapLocation);
				displayMapLocationIcon(mapLocation, lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7,
						lblMap8, lblMap9);
				disableDirectionalNavButtons(mapLocation);
			}
		});
		navigationPanel.add(btnUp);

		JButton btnEmpty_1 = new JButton("empty");
		btnEmpty_1.setVisible(false);
		navigationPanel.add(btnEmpty_1);

		btnLeft = new JButton("Left");
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMapIcons(lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7, lblMap8, lblMap9);
				resetDisableNavButtons();
				mapLocation = moveLeft(mapLocation);
				displayMapLocationIcon(mapLocation, lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7,
						lblMap8, lblMap9);
				disableDirectionalNavButtons(mapLocation);
			}
		});
		navigationPanel.add(btnLeft);

		JButton btnEmpty_2 = new JButton("empty");
		btnEmpty_2.setVisible(false);
		navigationPanel.add(btnEmpty_2);

		btnRight = new JButton("Right");
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMapIcons(lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7, lblMap8, lblMap9);
				resetDisableNavButtons();
				mapLocation = moveRight(mapLocation);
				displayMapLocationIcon(mapLocation, lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7,
						lblMap8, lblMap9);
				disableDirectionalNavButtons(mapLocation);
			}
		});
		navigationPanel.add(btnRight);

		JButton btnEmpty_3 = new JButton("empty");
		btnEmpty_3.setVisible(false);
		navigationPanel.add(btnEmpty_3);

		btnDown = new JButton("Down");
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetMapIcons(lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7, lblMap8, lblMap9);
				resetDisableNavButtons();
				mapLocation = moveDown(mapLocation);
				displayMapLocationIcon(mapLocation, lblMap1, lblMap2, lblMap3, lblMap4, lblMap5, lblMap6, lblMap7,
						lblMap8, lblMap9);
				disableDirectionalNavButtons(mapLocation);
			}
		});
		navigationPanel.add(btnDown);

		JButton btnEmpty_4 = new JButton("empty");
		btnEmpty_4.setVisible(false);
		navigationPanel.add(btnEmpty_4);
		return navigationPanel;
	}

	/**
	 * Allows user to choose the suspect and weapon. Will also display location
	 * choice from navigation
	 * 
	 * @return
	 */
	@SuppressWarnings("serial")
	private JPanel createAccusationPanel() {
		JPanel accusationPanel = new JPanel() {
			/* 
			 * allows an image to be placed on the JPanel
			 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
			 */
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				gameSheet.paintIcon(this, g, 0, 0);
			}
		};
		accusationPanel.setBorder(new EmptyBorder(0, 20, 0, 20));
		accusationPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblSuspects = new JLabel();
		lblSuspects.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuspects.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		lblSuspects.setIcon(suspectsLabel);
		accusationPanel.add(lblSuspects);

		JRadioButton rdbtnGreen = new JRadioButton("Mr. Green");
		rdbtnGreen.setBackground(new Color(255, 255, 255));
		rdbtnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSuspectCard.setIcon(SuspectName.GREEN.image);
				sn = SuspectName.GREEN;
			}
		});
		btnGroupSuspect.add(rdbtnGreen);
		accusationPanel.add(rdbtnGreen);

		JRadioButton rdbtnMustard = new JRadioButton("Colonel Mustard                             ");
		rdbtnMustard.setBackground(new Color(255, 255, 255));
		rdbtnMustard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSuspectCard.setIcon(SuspectName.MUSTARD.image);
				sn = SuspectName.MUSTARD;
			}
		});
		btnGroupSuspect.add(rdbtnMustard);
		accusationPanel.add(rdbtnMustard);

		JRadioButton rdbtnPeacock = new JRadioButton("Mrs. Peacock");
		rdbtnPeacock.setBackground(new Color(255, 255, 255));
		rdbtnPeacock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSuspectCard.setIcon(SuspectName.PEACOCK.image);
				sn = SuspectName.PEACOCK;
			}
		});
		btnGroupSuspect.add(rdbtnPeacock);
		accusationPanel.add(rdbtnPeacock);

		JRadioButton rdbtnPlum = new JRadioButton("Professor Plum");
		rdbtnPlum.setBackground(new Color(255, 255, 255));
		rdbtnPlum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSuspectCard.setIcon(SuspectName.PLUM.image);
				sn = SuspectName.PLUM;
			}
		});
		btnGroupSuspect.add(rdbtnPlum);
		accusationPanel.add(rdbtnPlum);

		JRadioButton rdbtnScarlett = new JRadioButton("Miss Scarlett");
		rdbtnScarlett.setBackground(new Color(255, 255, 255));
		rdbtnScarlett.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSuspectCard.setIcon(SuspectName.SCARLET.image);
				sn = SuspectName.SCARLET;
			}
		});
		btnGroupSuspect.add(rdbtnScarlett);
		accusationPanel.add(rdbtnScarlett);

		JRadioButton rdbtnWhite = new JRadioButton("Mrs. White");
		rdbtnWhite.setBackground(new Color(255, 255, 255));
		rdbtnWhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSuspectCard.setIcon(SuspectName.WHITE.image);
				sn = SuspectName.WHITE;
			}
		});
		btnGroupSuspect.add(rdbtnWhite);
		accusationPanel.add(rdbtnWhite);

		JLabel lblWeapon = new JLabel();
		lblWeapon.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeapon.setIcon(weaponsLabel);
		accusationPanel.add(lblWeapon);

		JRadioButton rdbtnCandlestick = new JRadioButton("Candlestick");
		rdbtnCandlestick.setBackground(new Color(255, 255, 255));
		rdbtnCandlestick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWeaponCard.setIcon(WeaponType.CANDLESTICK.image);
				wt = WeaponType.CANDLESTICK;
			}
		});
		btnGroupWeapon.add(rdbtnCandlestick);
		accusationPanel.add(rdbtnCandlestick);

		JRadioButton rdbtnDagger = new JRadioButton("Knife");
		rdbtnDagger.setBackground(new Color(255, 255, 255));
		rdbtnDagger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWeaponCard.setIcon(WeaponType.KNIFE.image);
				wt = WeaponType.KNIFE;
			}
		});
		btnGroupWeapon.add(rdbtnDagger);
		accusationPanel.add(rdbtnDagger);

		JRadioButton rdbtnPipe = new JRadioButton("Lead Pipe");
		rdbtnPipe.setBackground(new Color(255, 255, 255));
		rdbtnPipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWeaponCard.setIcon(WeaponType.LEADPIPE.image);
				wt = WeaponType.LEADPIPE;
			}
		});
		btnGroupWeapon.add(rdbtnPipe);
		accusationPanel.add(rdbtnPipe);

		JRadioButton rdbtnRevolver = new JRadioButton("Revolver");
		rdbtnRevolver.setBackground(new Color(255, 255, 255));
		rdbtnRevolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWeaponCard.setIcon(WeaponType.REVOLVER.image);
				wt = WeaponType.REVOLVER;
			}
		});
		btnGroupWeapon.add(rdbtnRevolver);
		accusationPanel.add(rdbtnRevolver);

		JRadioButton rdbtnRope = new JRadioButton("Rope");
		rdbtnRope.setBackground(new Color(255, 255, 255));
		rdbtnRope.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWeaponCard.setIcon(WeaponType.ROPE.image);
				wt = WeaponType.ROPE;
			}
		});
		btnGroupWeapon.add(rdbtnRope);
		accusationPanel.add(rdbtnRope);

		JRadioButton rdbtnWrench = new JRadioButton("Wrench");
		rdbtnWrench.setBackground(new Color(255, 255, 255));
		rdbtnWrench.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblWeaponCard.setIcon(WeaponType.WRENCH.image);
				wt = WeaponType.WRENCH;
			}
		});
		btnGroupWeapon.add(rdbtnWrench);
		accusationPanel.add(rdbtnWrench);

		JLabel lblLocation = new JLabel();
		lblLocation.setHorizontalAlignment(SwingConstants.CENTER);
		lblLocation.setIcon(roomsLabel);
		accusationPanel.add(lblLocation);

		rdbtnBallroom = new JRadioButton("Ballroom");
		rdbtnBallroom.setBackground(new Color(255, 255, 255));
		rdbtnBallroom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ln = LocationName.BALLROOM;
			}
		});
		btnGroupLocation.add(rdbtnBallroom);
		accusationPanel.add(rdbtnBallroom);

		rdbtnBilliardRoom = new JRadioButton("Billiard Room");
		rdbtnBilliardRoom.setBackground(new Color(255, 255, 255));
		rdbtnBilliardRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ln = LocationName.BILLIARD_ROOM;
			}
		});
		btnGroupLocation.add(rdbtnBilliardRoom);
		accusationPanel.add(rdbtnBilliardRoom);

		rdbtnConservatory = new JRadioButton("Conservatory");
		rdbtnConservatory.setBackground(new Color(255, 255, 255));
		rdbtnConservatory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ln = LocationName.CONSERVATORY;
			}
		});
		btnGroupLocation.add(rdbtnConservatory);
		accusationPanel.add(rdbtnConservatory);

		rdbtnDiningRoom = new JRadioButton("Dining Room");
		rdbtnDiningRoom.setBackground(new Color(255, 255, 255));
		rdbtnDiningRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ln = LocationName.DINING_ROOM;
			}
		});
		btnGroupLocation.add(rdbtnDiningRoom);
		accusationPanel.add(rdbtnDiningRoom);

		rdbtnHall = new JRadioButton("Hall");
		rdbtnHall.setBackground(new Color(255, 255, 255));
		rdbtnHall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblRoom.setIcon(LocationName.HALL.image);
				ln = LocationName.HALL;
			}
		});
		btnGroupLocation.add(rdbtnHall);
		accusationPanel.add(rdbtnHall);
		rdbtnHall.setSelected(true);

		rdbtnKitchen = new JRadioButton("Kitchen");
		rdbtnKitchen.setBackground(new Color(255, 255, 255));
		rdbtnKitchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblRoom.setIcon(LocationName.KITCHEN.image);
				ln = LocationName.KITCHEN;
			}
		});
		btnGroupLocation.add(rdbtnKitchen);
		accusationPanel.add(rdbtnKitchen);

		rdbtnLibrary = new JRadioButton("Library");
		rdbtnLibrary.setBackground(new Color(255, 255, 255));
		rdbtnLibrary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblRoom.setIcon(LocationName.LIBRARY.image);
				ln = LocationName.LIBRARY;
			}
		});
		btnGroupLocation.add(rdbtnLibrary);
		accusationPanel.add(rdbtnLibrary);

		rdbtnLounge = new JRadioButton("Lounge");
		rdbtnLounge.setBackground(new Color(255, 255, 255));
		rdbtnLounge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblRoom.setIcon(LocationName.LOUNGE.image);
				ln = LocationName.LOUNGE;
			}
		});
		btnGroupLocation.add(rdbtnLounge);
		accusationPanel.add(rdbtnLounge);

		rdbtnStudy = new JRadioButton("Study");
		rdbtnStudy.setBackground(new Color(255, 255, 255));
		rdbtnStudy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblRoom.setIcon(LocationName.STUDY.image);
				ln = LocationName.STUDY;
			}
		});
		btnGroupLocation.add(rdbtnStudy);
		accusationPanel.add(rdbtnStudy);

		// This will use the evaluate method and compare user vs computer
		// generated elements
		JButton btnSubmit = new JButton("SUBMIT");
		JRadioButton[] suspectButtons = new JRadioButton[] { rdbtnGreen, rdbtnMustard, rdbtnPeacock, rdbtnPlum,
				rdbtnScarlett, rdbtnWhite };
		JRadioButton[] weaponButtons = new JRadioButton[] { rdbtnCandlestick, rdbtnDagger, rdbtnPipe, rdbtnRevolver,
				rdbtnRope, rdbtnWrench };
		JRadioButton[] locationButtons = new JRadioButton[] { rdbtnBallroom, rdbtnBilliardRoom, rdbtnConservatory,
				rdbtnDiningRoom, rdbtnHall, rdbtnKitchen, rdbtnLibrary, rdbtnLounge, rdbtnStudy };

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean[] ba = myGuess.evaluate(sn, wt, ln);
				// Output of winning elements for verification pruposes
				for (boolean el : ba) {
					System.out.println(el);
				}

				// sets radio buttons to disabled if no correct guesses
				btnGroupSuspect.getSelection().setEnabled(ba[0]);
				btnGroupWeapon.getSelection().setEnabled(ba[1]);
				btnGroupLocation.getSelection().setEnabled(ba[2]);

				// displays feedback to player for all possible guess
				// combinations
				if (!ba[0] && !ba[1] && !ba[2]) {
					lblOutput.setText("I'm sorry one or more of your guesses is incorrect. Try again!");
				} else {
					if (ba[0]) {
						setButtonGroupDisabled(suspectButtons, btnGroupSuspect);
						setOutput("suspect " + sn.name, "weapon", "crime scene");
					}
					if (ba[1]) {
						setButtonGroupDisabled(weaponButtons, btnGroupWeapon);
						setOutput(wt.name, "suspect", "crime scene");
					}
					if (ba[2]) {
						setButtonGroupDisabled(locationButtons, btnGroupLocation);
						disableNavButtons();
						setOutput(ln.name + " crime scene", "suspect", "weapon");
					}
					if (ba[0] && ba[1]) {
						setDoubleOutput("suspect " + sn.name, wt.name, "crime scene");
						setButtonGroupDisabled(suspectButtons, btnGroupSuspect);
						setButtonGroupDisabled(weaponButtons, btnGroupWeapon);
					}
					if (ba[0] && ba[2]) {
						setDoubleOutput("suspect " + sn.name, ln.name + " crime scene", "weapon");
						setButtonGroupDisabled(suspectButtons, btnGroupSuspect);
						setButtonGroupDisabled(locationButtons, btnGroupLocation);
						disableNavButtons();
					}
					if (ba[1] && ba[2]) {
						setDoubleOutput(wt.name, ln.name + " crime scene", "suspect");
						setButtonGroupDisabled(weaponButtons, btnGroupWeapon);
						setButtonGroupDisabled(locationButtons, btnGroupLocation);
						disableNavButtons();
					}
					if (ba[0] && ba[1] && ba[2]) {
						timeStop = System.currentTimeMillis();
						long score = calculateTime(timeStart, timeStop);
						String scoreConverted = convertLongToTime(score);
						btnPlayAgain.setVisible(true);
						btnQuit.setVisible(true);

						if (score < highScore) {
							
							try (PrintWriter writer = new PrintWriter("src/murderMystery/Files/HighScore.txt")) {
								writer.print(String.valueOf(score));
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
						}
						lblOutput.setFont(new Font("Monospaced", Font.BOLD, 14));
						lblOutput.setForeground(Color.RED);
						lblOutput.setText("Yes! You found the murderer! \n" + "Unfortunately they have to go to "
								+ "jail now because murder is a crime, and it's not cool to kill people just "
								+ "because they're obnoxious.");
						txtAreaTime.setVisible(true);
						txtAreaTime.setText("Your time: " + scoreConverted + "\nFastest time: "
								+ (score < highScore ? scoreConverted : convertLongToTime(highScore)));
					}
				}
			}
		});
		accusationPanel.add(btnSubmit);
		return accusationPanel;

	}

	/**
	 * this makes it appear that the magnifying glass is moving around the map
	 * by removing magnifying glass from previous positions
	 * 
	 * @param lblMap1
	 * @param lblMap2
	 * @param lblMap3
	 * @param lblMap4
	 * @param lblMap5
	 * @param lblMap6
	 * @param lblMap7
	 * @param lblMap8
	 * @param lblMap9
	 */
	private void resetMapIcons(JLabel lblMap1, JLabel lblMap2, JLabel lblMap3, JLabel lblMap4, JLabel lblMap5,
			JLabel lblMap6, JLabel lblMap7, JLabel lblMap8, JLabel lblMap9) {
		lblMap1.setIcon(map1);
		lblMap2.setIcon(map2);
		lblMap3.setIcon(map3);
		lblMap4.setIcon(map4);
		lblMap5.setIcon(map5);
		lblMap6.setIcon(map6);
		lblMap7.setIcon(map7);
		lblMap8.setIcon(map8);
		lblMap9.setIcon(map9);
	}

	/**
	 * this method disables the up, down, left, right buttons as needed
	 * dependent on players location on the map
	 * 
	 * @param mapLocation
	 */
	private void disableDirectionalNavButtons(int mapLocation) {
		int i = mapLocation;

		if (i % 3 == 1) {
			btnLeft.setEnabled(false);
		}
		if (i % 3 == 2) {
			btnLeft.setEnabled(true);
			btnRight.setEnabled(true);
		}
		if (i % 3 == 0) {
			btnRight.setEnabled(false);
		}
		if (i < 4) {
			btnUp.setEnabled(false);
		}
		if (i == 5) {
			btnLeft.setEnabled(true);
			btnRight.setEnabled(true);
		}
		if (i > 6) {
			btnDown.setEnabled(false);
		}
	}

	/**
	 * this method works in conjunction with the disableDirectionalNavButtons()
	 * method it resets the disabled navigation buttons to enabled upon each
	 * move
	 */
	private void resetDisableNavButtons() {
		btnUp.setEnabled(true);
		btnDown.setEnabled(true);
		btnLeft.setEnabled(true);
		btnRight.setEnabled(true);
	}

	/**
	 * this method disables all navigation button upon the correct location
	 * guess
	 */
	private void disableNavButtons() {
		btnUp.setEnabled(false);
		btnDown.setEnabled(false);
		btnLeft.setEnabled(false);
		btnRight.setEnabled(false);
	}

	/**
	 * button moves player up one spot in map
	 * 
	 * @param mapLocation
	 * @return mapLocation
	 */
	public int moveUp(int mapLocation) {
		mapLocation -= 3;
		return mapLocation;
	}

	/**
	 * button moves player down one spot in map
	 * 
	 * @param mapLocation
	 * @return mapLocation
	 */
	public int moveDown(int mapLocation) {
		mapLocation += 3;
		return mapLocation;
	}

	/**
	 * button moves player left one spot in map
	 * 
	 * @param mapLocation
	 * @return mapLocation
	 */
	public int moveLeft(int mapLocation) {
		mapLocation -= 1;
		return mapLocation;
	}

	/**
	 * button moves player right one spot in map
	 * 
	 * @param mapLocation
	 * @return mapLocation
	 */
	public int moveRight(int mapLocation) {
		mapLocation += 1;
		return mapLocation;
	}

	/**
	 * this method moves the magnifying glass around the map according to the
	 * player's current position
	 * 
	 * @param mapLocation
	 * @param lblMap1
	 * @param lblMap2
	 * @param lblMap3
	 * @param lblMap4
	 * @param lblMap5
	 * @param lblMap6
	 * @param lblMap7
	 * @param lblMap8
	 * @param lblMap9
	 */
	public void displayMapLocationIcon(int mapLocation, JLabel lblMap1, JLabel lblMap2, JLabel lblMap3, JLabel lblMap4,
			JLabel lblMap5, JLabel lblMap6, JLabel lblMap7, JLabel lblMap8, JLabel lblMap9) {
		if (mapLocation == 1) {
			lblMap1.setIcon(map1s);
			lblRoom.setIcon(LocationName.BALLROOM.image);
			rdbtnBallroom.setSelected(true);
			ln = LocationName.BALLROOM;
		} else if (mapLocation == 2) {
			lblMap2.setIcon(map2s);
			lblRoom.setIcon(LocationName.BILLIARD_ROOM.image);
			rdbtnBilliardRoom.setSelected(true);
			ln = LocationName.BILLIARD_ROOM;
		} else if (mapLocation == 3) {
			lblMap3.setIcon(map3s);
			lblRoom.setIcon(LocationName.CONSERVATORY.image);
			rdbtnConservatory.setSelected(true);
			ln = LocationName.CONSERVATORY;
		} else if (mapLocation == 4) {
			lblMap4.setIcon(map4s);
			lblRoom.setIcon(LocationName.DINING_ROOM.image);
			rdbtnDiningRoom.setSelected(true);
			ln = LocationName.DINING_ROOM;
		} else if (mapLocation == 5) {
			lblMap5.setIcon(map5s);
			lblRoom.setIcon(LocationName.HALL.image);
			rdbtnHall.setSelected(true);
			ln = LocationName.HALL;
		} else if (mapLocation == 6) {
			lblMap6.setIcon(map6s);
			lblRoom.setIcon(LocationName.KITCHEN.image);
			rdbtnKitchen.setSelected(true);
			ln = LocationName.KITCHEN;
		} else if (mapLocation == 7) {
			lblMap7.setIcon(map7s);
			lblRoom.setIcon(LocationName.LIBRARY.image);
			rdbtnLibrary.setSelected(true);
			ln = LocationName.LIBRARY;
		} else if (mapLocation == 8) {
			lblMap8.setIcon(map8s);
			lblRoom.setIcon(LocationName.LOUNGE.image);
			rdbtnLounge.setSelected(true);
			ln = LocationName.LOUNGE;
		} else if (mapLocation == 9) {
			lblMap9.setIcon(map9s);
			lblRoom.setIcon(LocationName.STUDY.image);
			rdbtnStudy.setSelected(true);
			ln = LocationName.STUDY;
		}
	}

	// when a correct element is guessed the button group is set to disabled
	private void setButtonGroupDisabled(JRadioButton[] btnArray, ButtonGroup buttonGroup) {
		Enumeration<AbstractButton> allRadioButtons = buttonGroup.getElements();
		while (allRadioButtons.hasMoreElements()) {
			allRadioButtons.nextElement();
			for (JRadioButton btn : btnArray) {
				btn.setEnabled(false);
			}
		}
	}

	// calculates time it took for player to play the game
	private long calculateTime(long timeStart, long timeStop) {
		long score = timeStop - timeStart;
		return score;
	}

	// converts time from milliseconds to hh:mm:ss:mmm
	private String convertLongToTime(long score) {
		long second = (score / 1000) % 60;
		long minute = (score / (1000 * 60)) % 60;
		long hour = (score / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d:%d", hour, minute, second, score % 1000);
		return time;
	}

	// displays feedback to user regarding progress in the game
	private void setOutput(String a, String b, String c) {

		lblOutput.setText("Congratulations! You found the " + a + "! "
				+ "But to convict, you need to make sure you find the " + b + " and " + c + ".\nTry again.");
	}

	// displays feedback to user regarding progress in the game
	private void setDoubleOutput(String a, String b, String c) {
		lblOutput.setText("Congratulations! You found the " + a + " and the " + b
				+ "! But to convict, you need to make sure you find the " + c + ".\nTry again.");
	}
}
