package FP_MainView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.*;

import FP_MainController.RoomController;
import FP_MainModel.Person_FP1;
import FP_MainModel.Room_FP1;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.transaction.TransactionException;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
* 
* This set the GUI Panel for the main GUI to display rooms that are being taken
* from the javaspace.
* 
*   Faser Parvez
*	December 16th 2015
*/

public class MainGUI_Panel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JFrame GUIFrame;
	// For the ChatGUIPanel
	private ChatGUI_Panel ChatGUIPanel;
	
	// For the enter and remove buttons
	private JButton enterRoom;
	private JButton removeRoom;
	
	// For the details of room, user and the room controller
	private Room_FP1 newRoom; 
	private Person_FP1 newUser;
	private RoomController roomController;
	
	/**
	 * Get the information from the room controller
	 */
	public MainGUI_Panel(RoomController roomcontroller) {
		setBackground(Color.WHITE);
		this.roomController = roomcontroller;
	}
	
	@SuppressWarnings("unused")
	private void initGUI() {
		try {
			{
				this.setBackground(new java.awt.Color(255,255,255));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Default Constructor
	 */
	public MainGUI_Panel(Room_FP1 getRoom, Person_FP1 getUser, RoomController getController) {
		super();
		// gets all the information about the user and room and copy them
		this.newRoom = getRoom;
		this.newUser = getUser;
		this.roomController = getController;
		// Add the main GUi Panel
		MainGUIPanel();
	}

	/**
	 * Create the MainGUIPanel to display the room information
	 */
	private void MainGUIPanel() {
		
		// Create the panel for the interface
		ChatGUIPanel = new ChatGUI_Panel();
		ChatGUIPanel.setSize(100, 40);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(new java.awt.Color(255,255,255));
		scrollPane.setViewportView(ChatGUIPanel);
		ChatGUIPanel.setBackground(new java.awt.Color(255,255,255));

		// Display the chat rooms from the getRoomName method
		getRoomName(newRoom.Room_Name);

		// Add the enter room button to open that room
		enterRoom = new JButton("Enter Room");
		enterRoom.setPreferredSize(new java.awt.Dimension(150, 20));
		enterRoom.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				openChatGUI(evt);
			}
		});
		ChatGUIPanel.add(enterRoom);

		// Set up the remove room button and compare to see if that room
		// is owned by the owner, does not display if it isn't.
		if(newUser.PersonID.compareTo(newRoom.Room_Owner.PersonID) == 0){
			// Add the remove room button to remove the room
			removeRoom = new JButton("Remove Room");
			removeRoom.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent evt) {
					removeRoom(evt);
				}
			});
			ChatGUIPanel.add(removeRoom);
		}
		add(BorderLayout.EAST, ChatGUIPanel);
	}
	
	/**
	 * Open up the ChatGUI.
	 */
	private void openChatGUI(ActionEvent evt) {
		new ChatGUI(newRoom, newUser);
	}
	
	/**
	 * Take the room name and passes through a string
	 */
	public void getRoomName(String name) {
		ChatGUIPanel.mainPersonName(name);
	}
	
	/**
	 * Create a messagebox to confirm if you want to remove the room
	 */
	private void removeRoom(ActionEvent evt) {
		String message = "You are about too room this room, are you sure? ";
		String title = "";
		// Get the dialog box
		int reply = JOptionPane.showConfirmDialog(null, 
												  message, 
												  title, 
												  JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
		{
			try {
				roomController.removeRoom(newRoom);
				JOptionPane.showMessageDialog(GUIFrame,
					    "Room has been removed, press refresh.",
					    "", JOptionPane.NO_OPTION);
			} catch (RemoteException | UnusableEntryException
					| TransactionException | InterruptedException e) {
			}			
		}
	}

	/**
	 * This method make sure that all the chat room gets updated by refreshing
	 */
	public void updateAllRooms(ArrayList<Room_FP1> rooms, Person_FP1 user){
		// This will remove everything first
		removeAll();
		// Goes through a loop to see all the rooms
		for (Room_FP1 room : rooms){
			// Add a MainGUIPanel to display all the rooms that have been got from the loop
			add(new MainGUI_Panel(room, user, roomController));
		}
		updateUI();
	}
}
