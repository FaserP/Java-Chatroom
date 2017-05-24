package FP_MainModel;
import net.jini.core.entry.Entry;

/**
 * 
 * The Room Model that will create the room that will use the 
 * chatroom. This will hold the information such as Room ID,
 * and details of the room such as the room name and the owner
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class Room_FP1 implements Entry {
	/**
	 * Default Serial Version
	*/
	private static final long serialVersionUID = 1L;
	// Set up the fields that will be used within the model
	public Integer RoomID = null;
	public String Room_Name = null;
	public Person_FP1 Room_Owner = null;
	
	/**
	 * Default Constructor
	 */
	public Room_FP1(){
	}
	
	/**
	 * Set the Rooms ID and Name
	 */
	public Room_FP1(Integer rID, String rName) {
		this.RoomID = rID;
		this.Room_Name = rName;
	}
	
	/**
	 * Set the Room ID, Name and the user from the person model
	 */
	public Room_FP1(Integer rID, String rName, Person_FP1 rUser) {
		this.RoomID = rID;
		this.Room_Name = rName;
		this.Room_Owner = rUser;	
	}
}
