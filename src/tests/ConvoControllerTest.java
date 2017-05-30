package tests;
import static org.junit.Assert.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import FP_MainController.*;
import FP_MainModel.Room_FP1;
import FP_MainModel.Convo_FP1;
import FP_MainModel.Person_FP1;
/**
 * A test class that will test the various functionality of the MessageController
 * class, and any supporting classes.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */
public class ConvoControllerTest {
	// Number of loops that a test must perform
	public static final int LOOP = 10;
	// Message Controller reference
	private ConvoController controller;
	// Chat room Controller reference
	private RoomController crController;
	
	
	/**
	 * This method sets up some global variables for use within the test class. 
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		controller = new ConvoController();
		crController = new RoomController();	
	}
	
	
	/**
	 * This method will clean up any global variables used within the tests.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		controller = null;
		crController = null;
	}


	/**
	 * This method will generate a random String.
	 * 
	 * @return a random String.
	 */
	private String getRandomString()
	{
		return new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	
	/**
	 * This method will test to ensure that a private message message will be 
	 * sent to a given chat room. The total number of messages sent to that chat 
	 * room should be the same as the messages returned when requested. A private 
	 * message is denoted by a non-null receiver variable within the message.
	 */
	@Test
	public void testSendPrivateMessage() {
		// Owners
		Person_FP1 owner = new Person_FP1(6600, "Faser", "pass1");
		Person_FP1 sender = new Person_FP1(6601, "Parvez", "pass2");
		// repeat LOOP times
		for(int i = 0; i < LOOP; i++){
			try{
				// Create a chat room
				String name = getRandomString();
				String desc = getRandomString();
				Room_FP1 room = crController.createRoom(name, desc, owner);
				
				String message = "Hello Messages #";
						
				// Send 10 PRIVATE messages
				for(int m = 0; m < LOOP; m++){
					controller.OwnerMessages(room, sender, message+m);
				}
				
				// Get all the messages back
				ArrayList<Convo_FP1> messages = controller.getAllMessages(room);
				for(int m = 0; m < messages.size(); m++){
					Convo_FP1 roomMessage = messages.get(m);
					// Make the assertions
					assertNotNull(roomMessage.RoomID);
					assertEquals(room.RoomID.intValue(), roomMessage.RoomID.intValue());
					assertEquals(message+m, roomMessage.Convo);
					// Sender
					assertEquals(sender.PersonID, roomMessage.Send.PersonID);
					assertEquals(sender.PersonName, roomMessage.Send.PersonName);
					assertEquals(sender.PersonPass, roomMessage.Send.PersonPass);
					// Owner
					assertEquals(owner.PersonID, roomMessage.Recieve.PersonID);
					assertEquals(owner.PersonName, roomMessage.Recieve.PersonName);
					assertEquals(owner.PersonPass, roomMessage.Recieve.PersonPass);
				}
				
				//Make sure there are LOOP number of messages
				assertEquals(LOOP, messages.size());
				
				// Remove the chat room
				crController.removeRoom(room);
			} catch(Exception e){
				fail(e.getMessage());
			}
		}
	}
	
	
	/**
	 * This method will test to ensure that a public message message will be 
	 * sent to a given chat room. The total number of messages sent to that chat 
	 * room should be the same as the messages returned when requested. A public 
	 * message is denoted by a null receiver variable within the message.
	 */
	@Test
	public void testSendPublicMessage() {
		// Owners
		Person_FP1 owner = new Person_FP1(6600, "Parvez", "pass1");
		Person_FP1 sender = new Person_FP1(6601, "Faser", "pass2");
		// repeat LOOP times
		for(int i = 0; i < LOOP; i++){
			try{
				// Create a chat room
				String name = getRandomString();
				String desc = getRandomString();
				Room_FP1 room = crController.createRoom(name, desc, owner);
				
				String message = "Hello Messages #";
						
				// Send 10 PRIVATE messages
				for(int m = 0; m < LOOP; m++){
					controller.EveryoneMessages(room, sender, message+m);
				}
				
				// Get all the messages back
				ArrayList<Convo_FP1> messages = controller.getAllMessages(room);
				for(int m = 0; m < messages.size(); m++){
					Convo_FP1 roomMessage = messages.get(m);
					// Make the assertions
					assertNotNull(roomMessage.RoomID);
					assertEquals(room.RoomID.intValue(), roomMessage.RoomID.intValue());
					assertEquals(message+m, roomMessage.AllMessage());
					// Sender
					assertEquals(sender.PersonID, roomMessage.Send.PersonID);
					assertEquals(sender.PersonName, roomMessage.Send.PersonName);
					assertEquals(sender.PersonPass, roomMessage.Send.PersonPass);
					// Owner
					assertNull(roomMessage.Recieve);
				}
				
				//Make sure there are LOOP number of messages
				assertEquals(LOOP, messages.size());
				
				// Remove the chat room
				crController.removeRoom(room);
			} catch(Exception e){
				fail(e.getMessage());
			}
		}
	}
	
	
	/**
	 * 
	 */
	@Test	
	public void testGetAllMessages(){
		testSendPrivateMessage();
		testSendPublicMessage();
	}
	
}
