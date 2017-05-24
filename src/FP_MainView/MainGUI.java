package FP_MainView;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import FP_MainController.RoomController;
import FP_MainController.ExceptionController;
import FP_MainController.PersonController;
import FP_MainModel.Person_FP1;
import java.rmi.RemoteException;
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
* 	This is the main GUI for the program which will display the application
* 	when running.
* 
*   Faser Parvez
*	December 16th 2015
*/

public class MainGUI extends RoomController {

	// The Main Frame
	private JFrame GUIFrame;
	private Container GUIContainer;
	private JScrollPane GUIScrollPane;
	
	// The panels
	private JPanel BottomJPanel;
	private JPanel MiddleJPanel;
	private JPanel TopJPanel;
	
	// Buttons and MainGUI Panel
	private JButton createRoom;
	private JButton refreshRoom;
	private MainGUI_Panel rooms;
	
	// New User
	private Person_FP1 newUser;

	// Label for headers
	JLabel HeaderJLabel, SubJLabel;
		
	/**
	 * Default Constructor
	 */
	public MainGUI(){
		super();
		// Setup the main JFrame
		GUIFrame = new JFrame("Welcome to chatroom");
		GUIFrame.setSize(630, 400);
		GUIFrame.setResizable(true);
		GUIFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

	    // Setup the layout for the frame
		GUIContainer = GUIFrame.getContentPane();
	    BorderLayout containerLayout = new BorderLayout();
        GUIContainer.setLayout(containerLayout);
        // Setting up the Top JPanel to display the headers
        {
        	// Creating the labels for the header of the program
        	TopJPanel = new JPanel();
        	BoxLayout jPanel1Layout = new BoxLayout(TopJPanel, javax.swing.BoxLayout.Y_AXIS);
        	TopJPanel.setLayout(jPanel1Layout);
        	GUIContainer.add(TopJPanel, BorderLayout.NORTH);
        	TopJPanel.setPreferredSize(new java.awt.Dimension(494, 41));
        	{
    		HeaderJLabel = new JLabel("Welcome to Chatroom", SwingConstants.CENTER);
    		HeaderJLabel.setFont(new Font("Sans-Serif", Font.BOLD, 18));
    		HeaderJLabel.setPreferredSize(new java.awt.Dimension(492, 24));
    		SubJLabel = new JLabel("Please create a new room or join an exisiting room", SwingConstants.LEFT);
    		TopJPanel.add(HeaderJLabel);
    		TopJPanel.add(SubJLabel);
    		SubJLabel.setPreferredSize(new java.awt.Dimension(0, 200));
    		SubJLabel.setSize(249, 17);
        	}
        }
        // Setting up the middle JPanel to display all the chatrooms
        {
        	// Creating the panel
        	MiddleJPanel = new JPanel();
        	GUIContainer.add(MiddleJPanel, BorderLayout.CENTER);
        	MiddleJPanel.setPreferredSize(new java.awt.Dimension(476, 283));
        	MiddleJPanel.setBackground(new java.awt.Color(240,240,240));
        	{
        		// Creating a new Main GUI Panel to display the rooms
        		rooms = new MainGUI_Panel(this);
        		rooms.setBackground(Color.WHITE);
        	    GUIScrollPane = new JScrollPane();
        	    MiddleJPanel.add(GUIScrollPane);
        	    GUIScrollPane.setViewportView(rooms);
        		GUIScrollPane.setPreferredSize(new Dimension(624, 274));
        		GUIScrollPane.setBackground(new java.awt.Color(192,192,192));
        		GUIScrollPane.getVerticalScrollBar().setBackground(new java.awt.Color(192,192,192));
        		GUIScrollPane.getHorizontalScrollBar().setBackground(new java.awt.Color(192,192,192));
        		rooms.setPreferredSize(new java.awt.Dimension(458, 270));
        	}
        }
        // Creating the Bottom JPanels to put the new room and refresh buttons
        {
        	BottomJPanel = new JPanel();
        	GUIContainer.add(BottomJPanel, BorderLayout.SOUTH);
        	BottomJPanel.setPreferredSize(new java.awt.Dimension(494, 47));
        	{
        		// Creating the new room button
        		createRoom = new JButton("Create a Room");
        		BottomJPanel.add(createRoom);
        		createRoom.setPreferredSize(new java.awt.Dimension(230, 35));
        		createRoom.setSize(200, 23);
        		createRoom.setForeground(new java.awt.Color(255,0,0));
        		createRoom.setFont(new java.awt.Font("Tahoma",1,14));
        		createRoom.addActionListener(new ActionListener(){
        			@Override
        			public void actionPerformed(ActionEvent e){
        				try {
							newRoom(e);
						} catch (RemoteException | TransactionException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        			}
        		});
        		// Creating the refresh button
        		refreshRoom = new JButton("Refresh");
        		BottomJPanel.add(refreshRoom);
        		refreshRoom.setEnabled(true);
        		refreshRoom.setPreferredSize(new java.awt.Dimension(230, 35));
        		refreshRoom.setFont(new java.awt.Font("Tahoma",1,14));
        		refreshRoom.setForeground(new java.awt.Color(255,0,0));
        		refreshRoom.addActionListener(new ActionListener(){
        			public void actionPerformed(ActionEvent e){
        				refreshRooms();
        			}
        		});
        	}
        }
        GUIFrame.setVisible(true);
        
        // Prompt the Login GUI when started
        PromptLogin();
	}
	
	/**
	 * This method will prompt up the NewRoomGUI and ask the user to
	 * create a new room.
	 * @throws TransactionException 
	 * @throws RemoteException 
	 */	
	private void newRoom(ActionEvent evt) throws RemoteException, TransactionException{
		// Create the NewRoomGUI and shows to the user
		NewRoomGUI dialog = new NewRoomGUI();
		boolean response = dialog.ForceNewRoomDialog();
		if(!response){		
			try {
				String name = dialog.getNewRoomText();
				// This will create the new room
				createRoom(name, name, newUser);
				JOptionPane.showMessageDialog(GUIFrame,
					    "Room has been added!",
					    "", JOptionPane.NO_OPTION);
				refreshRooms();
			} catch (ExceptionController err) {
				// Inform the user something went wrong
				JOptionPane.showMessageDialog(GUIFrame,
						err.getMessage(),
					    "Internal Error",
					    JOptionPane.NO_OPTION);
			}
		}
	}
	
	/**
	 * This method will refresh all the chatrooms and display the rooms
	 * onto the GUI
	 */
	private void refreshRooms() {		
			// Get the rooms from javaspace and update them to the GUI
			try {
				rooms.updateAllRooms(getAllChatrooms(), newUser);
			} catch (RemoteException | UnusableEntryException
					| TransactionException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * This method will get the user login details
	 */
	private void userLogin(Person_FP1 user){
		// Get the user login detail
		this.newUser = user;
	}
	
	/**
	 * This method prompt up the login GUI and ask the user to create
	 * a login before using the program. If they decide not to then the
	 * program will close.
	 */
	private void PromptLogin(){
		// This will create a new LoginGUI and personController
		LoginGUI login = new LoginGUI();
		PersonController personController = new PersonController();
		
		// Check to see if the user is logging in
		boolean invalid = true;
		do{
			// Show the login window
			boolean promptError = login.showForceLoginWindow();
			if(promptError == true){
				// Force shut down the program
				JOptionPane.showMessageDialog(GUIFrame,
				    "This programe has been closed.",
				    null, JOptionPane.NO_OPTION);
				System.exit(0);
			}
			// Check to see if the user if correct
			try{
				// Get the inputs from the textboxes
				String username = login.getUsername();
				String password = login.getPassword();
				
				// Get the user from the perosn controller
				Person_FP1 user = personController.findUser(username);
				
				if(user == null){
					// If the user doesn't exist then it create a new user
					try {
						user = personController.newUser(username, password);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransactionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					userLogin(user);
					invalid = false;
					JOptionPane.showMessageDialog(GUIFrame,
						    "New User has been created!",
						    "", JOptionPane.NO_OPTION);
				} else {
					// If the user exist then the program check if they inserted the correct pass
					if(user.PersonPass.equals(password)){
						userLogin(user);
						invalid = false;
					}else{
						invalid = true;
						JOptionPane.showMessageDialog(GUIFrame,
							    "Incorrect, Please try again.",
							    "Error", JOptionPane.NO_OPTION);
					}
				}
			}catch(ExceptionController e){
				String errorMessage = "Error can not create user";
				JOptionPane.showMessageDialog(GUIFrame,
						errorMessage,
					    "Internal Error",
					    JOptionPane.NO_OPTION);
				System.exit(1);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnusableEntryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransactionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(invalid);
		
		// This update all the rooms
		refreshRooms();		
	}	
}