package FP_MainView;
import javax.swing.*;

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
* A JoptionPane that is used for creating a new chat room.
* 
*   Faser Parvez
*	December 16th 2015
*/

public class NewRoomGUI extends JOptionPane {
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	// JPanel for the New Room Dialog
	private JPanel NewRoomPanel;
	// Text Field to input Room name
	private JTextField NewRoom;
	
	/**
	 * Default Constructor 
	 */
	public NewRoomGUI(){
		super();
		//Grabs the New Room Dialog
		NewRoomDialog();
	}
	
	/**
	 * This Method is used to create the GUI for the New Room Dialog
	 */
	private void NewRoomDialog(){
		// Creating a Jpanel
		NewRoomPanel = new JPanel();  
		NewRoomPanel.setLayout(new BoxLayout(NewRoomPanel, BoxLayout.PAGE_AXIS));
		
		// Adding Jlabels to the panel
		NewRoomPanel.add(new JLabel("Create a Room to chat"));
		NewRoomPanel.add(new JLabel(" "));
		NewRoomPanel.add(new JLabel("Room Name"));
		
		// Creating a JTextField to input the room name
		NewRoom = new JTextField(12);
		// This will set the text limit that can be inputted into the JTextField
		NewRoom.setDocument(new TextLimitOnDialogs(20));
		// Adds the Text field to the panel
		NewRoomPanel.add(NewRoom); 
	}

	/**
	 * This method will make sure that the room name must be inputted and
	 * can't be left blank. If the user decides to not input any name and
	 * clicks "OK" then the window will reappear again until a room name has
	 * been inputted. 
	 * 
	 */
	public boolean ForceNewRoomDialog(){
		boolean getError = true;	
		do{
			// This gets the dialog to be shown
			int response = showNewRoomDialog();
			if(response != JOptionPane.OK_OPTION){
				getError = true;
				break;
			}
			
			// This method will be used to see if the textbox is empty
			boolean room = !getNewRoomText().isEmpty();
			getError = (room) ? false : true;
		}while(getError);

		return getError;
	}	
	
	/**
	 * This method gets the create new room dialog for the user
	 */
	public int showNewRoomDialog(){
		// This will bring up the new room dialog
		return showConfirmDialog(null, NewRoomPanel, "Create a new room", 
								 JOptionPane.YES_NO_OPTION);
	}
	
	/**
	 * This will grab the text that has been inserted into the
	 * text box for room name.
	 */
	public String getNewRoomText(){
		return new String(NewRoom.getText());
	}
}
