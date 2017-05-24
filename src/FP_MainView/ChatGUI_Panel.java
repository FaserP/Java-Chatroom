package FP_MainView;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import FP_MainModel.Convo_FP1;
import FP_MainModel.Person_FP1;

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
* This set the GUI Panel for the chat room meesages that are being taken
* from the javaspace.
* 
*   Faser Parvez
*	December 16th 2015
*/

public class ChatGUI_Panel extends JPanel {
	// Serial Version for serialisation/deserialisation security purposes.
	private static final long serialVersionUID = 3502217729174259608L;
	// Jlabels that will be used for the person name and the messages
	private JTextArea personName;
	private JTextArea chatMessage;
	
	/**
	 * The Default Constructor
	 */
	public ChatGUI_Panel() {
		super();
		setBorder(null);
		// Create the panel for the chat room messages
		JPanel chatroomPanel = new JPanel();
		chatroomPanel.setLayout(new BoxLayout(chatroomPanel, BoxLayout.Y_AXIS));
		chatroomPanel.setBackground(new java.awt.Color(192,192,192));
		// Grabs the method to create the text areas.
		SenderTextArea();
		MessageSentTextArea();
		//updates the user interface.
		updateUI();
	}
	
	/**
	 * This method is used to create the Text area for the person sending the
	 * message by showing their name.
	 */
	private void SenderTextArea(){
		// Create the TextArea for the Person name
		personName = new JTextArea();
		personName.setLineWrap(true);
		personName.setEditable(false);
		personName.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		personName.setForeground(Color.BLACK);
		personName.setOpaque(false);
		
		// Add the Person Name to the Layout
		GridLayout thisLayout = new GridLayout(1, 1);
		thisLayout.setVgap(10);
		thisLayout.setColumns(1);
		this.setLayout(thisLayout);
		this.setBackground(Color.WHITE);
		this.add(personName);
		personName.setBackground(new java.awt.Color(192,192,192));
		personName.setSize(60, 42);
	}
	
	/**
	 * This method is used to create the Text area for the person sending the
	 * message by showing their message.
	 */
	private void MessageSentTextArea(){
		// Create the text area for the message being sent
		chatMessage = new JTextArea();
		chatMessage.setLineWrap(true);
		chatMessage.setEditable(false);
		chatMessage.setFont(new Font("Sans-Serif", Font.BOLD, 14));
		chatMessage.setForeground(Color.BLACK);
		chatMessage.setOpaque(false);
		
		// Add the message to the panel
		this.add(chatMessage);
		chatMessage.setBackground(new java.awt.Color(192,192,192));
		chatMessage.setSize(150, 23);
	}
	
	/**
	 * This method will create an arraylist and check through all the messages
	 * to make sure that they are being updated and taken out of the javaspace
	 */
	public void CheckforUpdatedMessages(ArrayList<Convo_FP1> messages, Person_FP1 user){
	// This will remove everything first
	removeAll();
	
	// This will loop through all the messages
	for(Convo_FP1 message : messages){
			// Create a Chat GUI Panel for new rooms
			ChatGUI_Panel chatGUI_Panel = new ChatGUI_Panel();
		
			// Set up the messages being sent
			String title = String.format(message.Send.PersonName);
			chatGUI_Panel.chatPersonName(title);
			chatGUI_Panel.setMessageSent(message.Convo);
		
			// This method checks to see if the message to the owner or to all
			if(message.OwnerMessage()){ 
				// Make sure that this checks all the IDs
				int sender_id = message.Send.PersonID.intValue();
				int reciever_id = message.Recieve.PersonID.intValue();
				int user_id = user.PersonID.intValue();
			
				// Sends a message to the owner of the chat and not show to everyone else
				if(sender_id == user_id || reciever_id == user_id){
					title = String.format(message.Send.PersonName, "has sent a message to ",
                                       message.Recieve.PersonName);
					chatGUI_Panel.chatPersonName(title);
				}else{
			// Shows to everyone
					continue;
				}
			}
		// Add the the Jpanel
		add(chatGUI_Panel);
		}
	updateUI();
	}

	/**
	 * This will set the text that has been inserted into the
	 * text box for the person name.
	 */
	public void mainPersonName(String s){
		personName.setText(s);
	}
	
	/**
	 * This will set the text that has been inserted into the
	 * text box for the person name.
	 */
	public void chatPersonName(String s){
		personName.setText(s + " just said: ");
	}


	/**
	 * This will set the text that has been inserted into the
	 * text box for the message sent.
	 */
	public void setMessageSent(String s){
		chatMessage.setText(s);
	}
}