package tests;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.junit.*;
import static org.junit.Assert.*;
import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;
import FP_Main.SpaceUtils;
import FP_MainController.*;
import FP_MainModel.*;
/**
 * A test class that will test the various functionality of the ChatroomController
 * class, and any supporting classes. 
 *
 *  Faser Parvez
 *  December 16th 2015
 */
public class RoomControllerTest {
	// Number of loops that a test must perform
	public static final int LOOP = 10;
	// Chat room Controller reference
	private RoomController controller;
	// JavaSpace reference
	private JavaSpace space;
	
	
	/**
	 * This method sets up some global variables for use within the test class. 
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		controller = new RoomController();
		space = SpaceUtils.getSpace();
	}
	
	
	/**
	 * This method will clean up any global variables used within the tests.
	 * 
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		controller = null;
		space = null;
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
	 * This method will test to ensure that a chat room is correctly written to 
	 * the JavaSpace. Objects that are written within this method will be removed.
	 */
	@Test
	public void testCreateNewChatroom() {
		for(int i = 0; i < LOOP; i++){
			// Input Data
			String name = getRandomString();
			String desc = getRandomString();
			Person_FP1 user = new Person_FP1(getRandomString(), getRandomString());
			
			// Test the method
				try {
					controller.createRoom(name, desc, user);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExceptionController e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransactionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			try{
				// Create a template
				Room_FP1 template = new Room_FP1();
				template.Room_Name = name;
				template.Room_Owner = user;
				
				// Take the object back out
				Room_FP1 room = (Room_FP1) space.take(template, null, 1000);
				
				// Check the assertions
				assertNotNull(room);
				assertEquals(room.Room_Name, name);
				assertEquals(room.RoomID, desc);
				assertEquals(room.Room_Owner.PersonName, user.PersonName);
				
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	
	/**
	 * This will test to ensure that when two IDs have been generated in order, 
	 * that the ID's have incremented by one. Objects that are written within 
	 * this method will be removed.
	 */
	@Test
	public void testIncrementID(){
		for(int j = 0; j < LOOP; j++){
			// Integer Array
			Integer[] num = new Integer[2];
			
			// Make the assertions
			assertNotNull(num[0]);
			assertNotNull(num[1]);
			// Make sure the difference between the IDs is 1
			int difference = num[1].intValue() - num[0].intValue();
			assertEquals(1, difference);
		}
	}
	
	
	/**
	 * This method will test to ensure that a chat room is successfully removed. 
	 * A successful removal must remove the chat room object, all associated 
	 * messages and any supporting tail objects. Objects that are written within 
	 * this method will be removed.
	 */
	@Test
	public void testChatroomRemoval(){
		for(int i = 0; i < LOOP; i++){
			try{
				// Create a chat room
				Room_FP1 room = new Room_FP1();
				
				// Add the room attributes
				room = new Room_FP1();
				room.RoomID = new Random().nextInt();
				room.Room_Name = getRandomString();
				
				// Write the object
				space.write(room, null, 1000);
				
				// try to remove the chat room
				controller.removeRoom(room);
				
				// try and get the chat room
				Room_FP1 returnRoom = (Room_FP1) space.take(room, null, 1000);
				assertNull(returnRoom);
				
			}catch(Exception e){
				fail(e.getMessage());
			}
		}
	}
	
	
	/**
	 * This method will test whether or not the getChatroom method correctly 
	 * returns a chat room. Objects that are written within this method will be 
	 * removed.
	 */
	@Test
	public void testGetChatroom(){
		for(int i = 0; i < LOOP; i++){
			// Input Data
			String name = getRandomString();
			String desc = getRandomString();
			Person_FP1 user = new Person_FP1(getRandomString(), getRandomString());
			
			// Room objects
			Room_FP1 savedRoom = null;
			Room_FP1 returnRoom = null;
			
			try{
				// Create a chat room object
				savedRoom = new Room_FP1();
				savedRoom.RoomID = new Random().nextInt();
				savedRoom.Room_Name = name;
				savedRoom.Room_Owner = user;
							
				// Write the object
				space.write(savedRoom, null, 1000);
				
				// Get the object
				returnRoom = controller.grabRoom(savedRoom.RoomID);
				
				// take the object out of the space
				space.take(savedRoom, null, 1000);			
			} catch(Exception e){
				fail(e.getMessage());
			}
			
			// Make Assertions
			assertNotNull(returnRoom);
			assertNotNull(savedRoom);
			assertEquals(savedRoom.RoomID, returnRoom.RoomID);
			assertEquals(savedRoom.Room_Name, returnRoom.Room_Name);
			assertEquals(savedRoom.Room_Owner.PersonName, returnRoom.Room_Owner.PersonName);
		}
	}
	
	
	/**
	 * This will test getting all the chat rooms that are currently in the 
	 * JavaSpace. This will also test to ensure that the order of entry is the 
	 * same as the order of exit. Objects that are written within this method 
	 * will be removed.
	 */
	@Test
	public void testGetAllChatrooms(){
		for(int j = 0; j < LOOP; j++){
			// Create a default room
			Room_FP1 room = new Room_FP1();
			room.Room_Name = getRandomString();
			
			// Make 10 insertions
			try{
				for(int i = 0; i < 10; i++){
					space.write(room, null, 1000);
				}
			}catch(Exception e){
				fail(e.getMessage());
			}
			
			// Check the returns are ok
			try{
				// Get all the rooms
				ArrayList<Room_FP1> rooms = controller.getAllChatrooms();
				
				// Make sure there are 10 rooms
				assertEquals(10, rooms.size());
				
				// Loop over each room & assert it manually
				for(int i = 0; i < 10; i++){
					// Get the Room
					Room_FP1 returnRoom = rooms.get(i);
					
					// Make the assertions
					assertEquals(i, returnRoom.RoomID.intValue());
					assertEquals(room.Room_Name, returnRoom.Room_Name);
					
					// Clean up the JavaSpace
					space.take(returnRoom, null, 1000);
				}
				
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
	}
}
