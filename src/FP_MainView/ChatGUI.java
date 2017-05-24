package FP_MainView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.*;
import FP_MainController.ExceptionController;
import FP_MainController.ConvoController;
import FP_MainModel.Convo_FP1;
import FP_MainModel.Person_FP1;
import FP_MainModel.Room_FP1;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.UnknownEventException;
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
* 	This is the main GUI that will be used to create the interface for
* 	showing the chat room and sending messages to other users.
* 
*   Faser Parvez
*	December 16th 2015
*/

public class ChatGUI extends ConvoController {
	// For the Dialog
	private JDialog GUIDialog;
	private Container GUIContainer;
	
	// For the Panels
	private JPanel MiddlePanel;
	private JPanel BottomPanel;
	private JPanel TopPanel;
	
	// For Jbutton and JTextArea
	private JButton sendMessage;
	private JButton refreshButton;
	private JTextArea inputMessage;
	JLabel HeaderJLabel;
	
	// For the Radio Buttons
	private JRadioButton ownerMessages, everyoneMessages;
	private ButtonGroup MessagesGroup;
	
	// For the information and chat panel
	private Room_FP1 chatroom;
	private Person_FP1 person;
	private ChatGUI_Panel ChatGUIPanel;
	
	/**
	 * Default Constructor
	 * 
	 * Sets the Room assigned to and the user who created the room
	 */
	public ChatGUI(Room_FP1 newroom, Person_FP1 newuser){
		super();
		this.chatroom = newroom;
		this.person = newuser;
		
		// Main Window and Layout
		GUIDialog = new JDialog();
		GUIDialog.setTitle("Welcome to chat window");
		GUIDialog.setSize(400,630);
		GUIDialog.setResizable(false);
		GUIContainer = GUIDialog.getContentPane();
		
		// Sets the full layout of the window
		GUIContainer.setLayout(new BorderLayout());
		{
			// The first panel is the top panel that sets the text label
			TopPanel = new JPanel();
			FlowLayout jPanel1Layout = new FlowLayout();
			GUIContainer.add(TopPanel, BorderLayout.NORTH);
			TopPanel.setLayout(jPanel1Layout);
			TopPanel.setPreferredSize(new java.awt.Dimension(394, 59));
			{
				// This sets the Jlabel for the header of the chat room
				HeaderJLabel = new JLabel("You are now in the chatroom" , SwingConstants.CENTER);
				TopPanel.add(HeaderJLabel);
						HeaderJLabel.setFont(new java.awt.Font("Segoe UI",1,16));
			}
			{
				JPanel radioPanel = new JPanel();
				TopPanel.add(radioPanel);
				BoxLayout visibilityPanelLayout = new BoxLayout(radioPanel, javax.swing.BoxLayout.X_AXIS);
				radioPanel.setLayout(visibilityPanelLayout);
				MessagesGroup = new ButtonGroup();
				{
					// create radio button for everyone messages and set default
					everyoneMessages = new JRadioButton("Send Message to Everyone");
							radioPanel.add(everyoneMessages);
							MessagesGroup.add(everyoneMessages);
							everyoneMessages.setText("Message Owner");
				}
				{
					// create radio button for owner messages
					ownerMessages = new JRadioButton("Send Message to Owner", true);
					radioPanel.add(ownerMessages);
							MessagesGroup.add(ownerMessages);
							ownerMessages.setText("Message Everyone");
				}
			}
		}
		// The second panel is the middle panel that sets the chat messages
		{
			MiddlePanel = new JPanel();
			MiddlePanel.setBackground(Color.WHITE);
			GUIContainer.add(MiddlePanel, BorderLayout.CENTER);
			MiddlePanel.setPreferredSize(new java.awt.Dimension(394, 381));
			{
				// set up the scroll bars for the chat pane
				JScrollPane scrollPane = new JScrollPane();
				MiddlePanel.add(scrollPane);
				scrollPane.setSize(374, 369);
				scrollPane.setPreferredSize(new java.awt.Dimension(374, 369));
				{
					// Grabs a new Chat GYU Panel and update the messages
					ChatGUIPanel = new ChatGUI_Panel();
					ChatGUIPanel.setBackground(Color.WHITE);
					updateAndDisplayMessages();
					scrollPane.setViewportView(ChatGUIPanel);
					ChatGUIPanel.setPreferredSize(new java.awt.Dimension(371, 359));
					ChatGUIPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
				}
			}
		}
		// The last panel is the bottom panel that sets the send messages and who to send to
		{
			// Create the bottom panel
			BottomPanel = new JPanel();
			GUIContainer.add(BottomPanel, BorderLayout.SOUTH);
			BottomPanel.setPreferredSize(new java.awt.Dimension(394, 156));
			{
				// Create a panel within the panel for another layout
				JPanel finalPanel = new JPanel();
				BottomPanel.add(finalPanel);
				FlowLayout messagePanelLayout = new FlowLayout();
				finalPanel.setLayout(messagePanelLayout);
				finalPanel.setPreferredSize(new java.awt.Dimension(364, 31));
				
				refreshButton = new JButton("Refresh");
				finalPanel.add(refreshButton);
				refreshButton.setPreferredSize(new java.awt.Dimension(363, 27));
				refreshButton.setFont(new java.awt.Font("Tahoma",1,12));
				refreshButton.setForeground(new java.awt.Color(255,0,0));
				refreshButton.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent evt) {
						refreshMessages(evt);
					}
				});
				{
					// Create the JTextArea for sending a message
					inputMessage = new JTextArea("");
					finalPanel.add(inputMessage);
					inputMessage.setRows(3);
					inputMessage.setLineWrap(true);
					inputMessage.setPreferredSize(new java.awt.Dimension(361, 63));
					inputMessage.addFocusListener(new FocusListener(){
						@Override
						public void focusLost(FocusEvent e) { } 
						@Override
						public void focusGained(FocusEvent e) {
							if(inputMessage.getText().equals("")){
								inputMessage.setText("");
							}
						}
					});		
				}
				// Create a panel for the radio buttons
			}
			// Create scroll bars
			{
				JScrollPane scrollPane = new JScrollPane(inputMessage, 
						ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				BottomPanel.add(scrollPane);
				scrollPane.setPreferredSize(new java.awt.Dimension(364, 72));
			}
			// Create Button to send the message
			{
				sendMessage = new JButton("Send");
				BottomPanel.add(sendMessage);
				sendMessage.setPreferredSize(new java.awt.Dimension(363, 30));
				sendMessage.setFont(new java.awt.Font("Tahoma",1,12));
				sendMessage.setForeground(new java.awt.Color(255,0,0));
				sendMessage.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent evt) {
						sendAllMessages(evt);
					}
				});
			}
		}
		// Make the frame visible
		GUIDialog.setVisible(true);   
		GUIDialog.setPreferredSize(new java.awt.Dimension(400, 620));
	}
		
	/**
	 * This method will get the messages for each chat room and update
	 * each time a new message has been added to the javaspace.
	 */
	private void updateAndDisplayMessages(){	
		try {
			// Get all the messages for the chatroom
			ArrayList<Convo_FP1> messages = getAllMessages(chatroom);
			// Update the panel with the new messages
			ChatGUIPanel.CheckforUpdatedMessages(messages, person);			
		} catch (RemoteException | UnusableEntryException
				| TransactionException | InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
	
	/**
	 * This method will refresh all the messages and display the messages
	 * onto the GUI
	 * @param evt 
	 */
	private void refreshMessages(ActionEvent evt) {		
			// Get the rooms from javaspace and update them to the GUI
			try {
				ArrayList<Convo_FP1> messages = getAllMessages(chatroom);
				ChatGUIPanel.CheckforUpdatedMessages(messages, person);
			} catch (RemoteException | UnusableEntryException
					| TransactionException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * This method will send messages when clicking on the send button.
	 * It will send both everyone and owner messages.
	 */
	private void sendAllMessages(ActionEvent evt){
		// Get the message from the chatbox
		String getMessage = inputMessage.getText();
		
		// See if the user has inserted the correct input
		if(getMessage.trim().length() == 0 || 
				getMessage.equals("Send a message...")){
			//Inform the user no message was entered
			JOptionPane.showMessageDialog(GUIDialog,
				    "You must input a message before sending",
				    "",
				    JOptionPane.WARNING_MESSAGE);			
			return;
		}
		
		// This will check to see what radio button is selected before
		// sending a message.
		try {
			if(ownerMessages.isSelected()){
				EveryoneMessages(chatroom, person, getMessage);
			} else {
				OwnerMessages(chatroom, person, getMessage);
			}
			// This clear the textbox
			inputMessage.setText("");
		} catch (ExceptionController e) {

			// Inform the user the chat room is now closed.
			String errMessage = "Chat room has ended.";
			JOptionPane.showMessageDialog(GUIDialog, 
										  errMessage, 
										  "Room Ended", 
										  JOptionPane.ERROR_MESSAGE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is implemented for the remote event notifications.
	 */
	@Override
	public void notify(RemoteEvent evt) 
			throws UnknownEventException, RemoteException {
		// Update the messages within a new thread
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				updateAndDisplayMessages();
			}
		});
	}
}