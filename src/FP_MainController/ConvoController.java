package FP_MainController;
import FP_MainModel.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.UnknownEventException;
import net.jini.core.transaction.*;

/**
 * 
 * The Convo Controller class which will have all the methods to make
 * sure that the messages being sent and receive will works with the GUI.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class ConvoController extends MainController {

	/**
	 * Default Constructor
	 * Gets the Convo_FP1 Model
	 */
	public ConvoController(){
		super(new Convo_FP1());
	}
	
	/**
	 * 
	 * This method will get an Array List that will make sure that all the
	 * Messages are being taken out of the javaspace and being placed into
	 * a list that will be used for the GUI.
	 */
	public ArrayList<Convo_FP1> getAllMessages(Room_FP1 room) throws RemoteException, 
           UnusableEntryException, TransactionException, InterruptedException {
		
		// This will create a convo tail
		ConvoTemplate_FP1 Template = new ConvoTemplate_FP1(room.RoomID);
		Template.TemplatePosition = null;
		ConvoTemplate_FP1 templateConvo = (ConvoTemplate_FP1) space.read(Template, null, GetThreeSecond);
		
		// This will create an array list for the convo
		ArrayList<Convo_FP1> convo = new ArrayList<Convo_FP1>();
		if(templateConvo != null){
			// Loop
			for(int i = 0; i <= templateConvo.TemplatePosition; i++){
				
				// Create a convo template
				Convo_FP1 template = new Convo_FP1();
				template.ConvoID = Integer.valueOf(i);
				template.RoomID = room.RoomID;
				
				// Find the convo from the javaspace
				Convo_FP1 message = (Convo_FP1) space.readIfExists(template, null, GetThreeSecond);
				
				// Add the convo
				if(message != null){
					convo.add(message);
				}
			}	
		}
		// Return list of convo
		return convo;
	}
	
	/**
	 * 
	 * This method will create messages that are intended to been seen by 
	 * everyone in the chat room including new users.
	 * @throws TransactionException 
	 * @throws RemoteException 
	 */

    public void EveryoneMessages(Room_FP1 room, Person_FP1 sender, String message) 
             throws ExceptionController, RemoteException, TransactionException {
				// Get the message tail
				ConvoTemplate_FP1 convoTemplate = new ConvoTemplate_FP1(room.RoomID);
				convoTemplate.TemplatePosition = null;
				
				// Generate a new ID
				Integer ConvoID = CreateNewTemplateID(convoTemplate, null);
				
				// Create the message object
				Convo_FP1 chatMessage = new Convo_FP1(ConvoID, room.RoomID);
				chatMessage.Convo = message;
				chatMessage.Send = sender;
				
				// Write the messages to javaspace
				space.write(chatMessage, null, GetTenMins);
	}
	
	/**
	 * 
	 * This method will create messages that are only intended to been seen by the
	 * the person who has sent the message and the person who owns the room that was
	 * created in. 
	 * @throws TransactionException 
	 * @throws RemoteException 
	 */
    public void OwnerMessages(Room_FP1 room, Person_FP1 sender, String message) 
            throws ExceptionController, RemoteException, TransactionException {
				// Get the convo tail
				ConvoTemplate_FP1 templateConvo1 = new ConvoTemplate_FP1(room.RoomID);
				templateConvo1.TemplatePosition = null;
				
				// Create an Id
				Integer id = CreateNewTemplateID(templateConvo1, null);
				
				// Create a message object
				Convo_FP1 convoMessages = new Convo_FP1(id, room.RoomID);
				convoMessages.Convo = message;
				convoMessages.Send = sender;
				convoMessages.Recieve = room.Room_Owner;
				
				// Write the changes
				space.write(convoMessages, null, GetTenMins);
	}

	@Override
	public void notify(RemoteEvent evt) 
			throws UnknownEventException, RemoteException {
	}
		
}
