package FP_MainModel;
import net.jini.core.entry.Entry;

/**
 * 
 * The main class which will run the chat room program by getting the  
 * chat room GUI.
 * NOTE: Please make sure you put "-Djava.security.policy=policy.all" into
 * 		 the VM Arguments so that it accepts the security policy file.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class Convo_FP1 implements Entry {
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	// Set up the fields that will be used within the model
	public Integer ConvoID;
	public Integer RoomID;
	public String Convo;
	public Person_FP1 Send, Recieve;
	
	/**
	 * Default Constructor
	 */
	public Convo_FP1(){	
	}

	/**
	 * Conversation that will get Convo and Room ID
	 */
	public Convo_FP1(Integer cID, Integer rID){
		this.ConvoID = cID;
		this.RoomID = rID;
	}
	
	/**
	 * Set the Messages only for the owner
	 */
	public boolean OwnerMessage(){
		return (this.Recieve == null) ? false : true;
	}
	
	/**
	 * Set the Messages for everyone in the room
	 */
	public boolean AllMessage(){
		return !OwnerMessage();
	}	
}