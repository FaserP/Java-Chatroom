package FP_MainController;
import java.rmi.RemoteException;
import java.util.ArrayList;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.UnknownEventException;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.*;
import FP_MainModel.*;

/**
 * 
 * The Room Controller class which will create a new room, make
 * sure that the rooms are displayed in a list and remove any
 * rooms that the user requests.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class RoomController extends MainController {
	/**
	 * Default Constructor
	 */
	public RoomController() {
		super(new RoomTemplate_FP1());
	}
	
	/**
	 * This method will create a new chat room that will be placed within the
	 * javaspace.
	 * @throws TransactionException 
	 * @throws RemoteException 
	 */
	public Room_FP1 createRoom(String name, String description, Person_FP1 owner) 
			throws ExceptionController, RemoteException, TransactionException {
				// Get RoomID
				Integer RoomID = CreateNewTemplateID(new RoomTemplate_FP1(), null);						
				// Create the Chat room object
				Room_FP1 room = new Room_FP1(RoomID, name, owner);
				// Write room to the space
				space.write(room, null, GetTenMins);
				space.write(new ConvoTemplate_FP1(RoomID), null, GetTenMins);
				// Being back the Room
				return room;
	}
	
	/**
	 * This method will check to see if a room exist and if it does
	 * then it will remove the rooms from the javaspace.
	 */
	public void removeRoom(Room_FP1 room) 
			throws RemoteException, 
					UnusableEntryException, 
					TransactionException, InterruptedException {
		// Remove the chat if eist
		space.takeIfExists(room, null, GetThreeSecond);
		
		// Increment the delete counter
		RoomTemplate_FP1 RoomTemplate = (RoomTemplate_FP1) space.takeIfExists(new RoomTemplate_FP1(), null, GetFiveSecond);
		space.write(RoomTemplate, null, Lease.FOREVER);
		
		// Remove the convo template
		ConvoTemplate_FP1 template = new ConvoTemplate_FP1(room.RoomID);
		template.TemplatePosition = null;
		ConvoTemplate_FP1 tail = (ConvoTemplate_FP1) space.takeIfExists(template, null, GetThreeSecond);
		
		// Remove all messages
		if(tail != null){
			for(int i = 0; i < tail.TemplatePosition; i++){
				space.takeIfExists(new Convo_FP1(i, room.RoomID), null, GetThreeSecond);
			}
		}
	}
	
	/**
	 * This method will check to see if a room exist and if it does
	 * then it will remove the rooms from the javaspace in a arraylist.
	 * @throws InterruptedException 
	 * @throws TransactionException 
	 * @throws UnusableEntryException 
	 * @throws RemoteException 
	 */
	public void removeAllChatrooms() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException  {
		// Get a list of all the current chat rooms and removes them
		ArrayList<Room_FP1> rooms = getAllChatrooms();
		for(Room_FP1 room : rooms) {
			removeRoom(room);
		}
	}
	
	/**
	 * This method will check to see if a room exist and if it does
	 * then it will grab the rooms from the javaspace.
	 * @throws InterruptedException 
	 * @throws TransactionException 
	 * @throws UnusableEntryException 
	 * @throws RemoteException 
	 */
	public Room_FP1 grabRoom(Integer id) throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		// Create a template and find the room
		Room_FP1 template = new Room_FP1();
		template.RoomID = id;
		Room_FP1 room = (Room_FP1) space.readIfExists(template, null, GetThreeSecond);
	
		return room;
	}
	
	/**
	 * This method will check to see if a room exist and if it does
	 * then it will grab the rooms in an arraylist from the javaspace.
	 * @throws InterruptedException 
	 * @throws TransactionException 
	 * @throws UnusableEntryException 
	 * @throws RemoteException 
	 */
	public ArrayList<Room_FP1> getAllChatrooms() throws RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		// Get an array list of the rooms
		RoomTemplate_FP1 roomTemplate = new RoomTemplate_FP1();
		RoomTemplate_FP1 allroomTemplate = (RoomTemplate_FP1) space.read(roomTemplate, null, GetThreeSecond);
		ArrayList<Room_FP1> rooms = new ArrayList<Room_FP1>();
		if(allroomTemplate != null){
			for(int i = 0; i <= allroomTemplate.TemplatePosition; i++){
				Room_FP1 room = grabRoom(Integer.valueOf(i));
				if(room != null){
					rooms.add(room);
				}
			}	
		}
		return rooms;
	}

	@Override
	public void notify(RemoteEvent arg0) throws UnknownEventException,
			RemoteException {
		// TODO Auto-generated method stub
		
	}
}
