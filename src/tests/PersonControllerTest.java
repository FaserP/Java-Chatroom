package tests;
import static org.junit.Assert.*;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.SecureRandom;

import net.jini.core.transaction.Transaction;
import net.jini.core.transaction.TransactionException;
import net.jini.space.JavaSpace;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import FP_Main.SpaceUtils;
import FP_MainController.*;
import FP_MainModel.*;
/**
 * A test class that will test the various functionality of the UserController
 * class, and any supporting classes. 
 * 
 *  Faser Parvez
 *  December 16th 2015
 */
public class PersonControllerTest {
	// Number of loops that a test must perform
	public static final int LOOP = 10;
	// Chat room Controller reference
	private PersonController controller;
	// JavaSpace reference
	private JavaSpace space;
	
	
	/**
	 * This method sets up some global variables for use within the test class. 
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		controller = new PersonController();
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
	 * This method will test to ensure that a user is correctly written to the
	 * JavaSpace. Objects that are written within this method will be removed.
	 */
	@Test
	public void testCreateNewUser() {
		for(int i = 0; i < LOOP; i++){
			// Input Data
			String username = getRandomString();
			String password = getRandomString();
			
			// Test the method
				try {
					controller.newUser(username, password);
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
				Person_FP1 template = new Person_FP1();
				template.PersonName = username;
				template.PersonPass = password;
				
				// Take the object back out
				Person_FP1 user = (Person_FP1) space.take(template, null, 1000);
				
				// Check the assertions
				assertNotNull(user);
				assertNotNull(user.PersonID);
				assertEquals(user.PersonName, username);
				assertEquals(user.PersonPass, password);
				
			} catch(Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	/**
	 * This method will test to ensure that a user can be retrieved by the  
	 * username and the password. This method will call the CreateNewUser test 
	 * method, as the internal methods are the same. Objects that are written 
	 * within this method will be removed.
	 */
	@Test
	public void testGetExistingUser() {
		testCreateNewUser();
	}
	
	
	/**
	 * This method will test to ensure that a user can be retrieved only by the 
	 * username. Objects that are written within this method will be removed.
	 */
	@Test
	public void testgetExistingUserByUsername() {
		for(int i = 0; i < LOOP; i++){
			// Input Data
			String username = getRandomString();
			String password = getRandomString();
			
				try {
					controller.newUser(username, password);
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
				Person_FP1 template = new Person_FP1();
				template.PersonName = username;
				
				// Take the object back out
				Person_FP1 user = (Person_FP1) space.take(template, null, 1000);
				
				// Check the assertions
				assertNotNull(user);
				assertNotNull(user.PersonID);
				assertEquals(user.PersonName, username);
				assertEquals(user.PersonPass, password);
				
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

}
