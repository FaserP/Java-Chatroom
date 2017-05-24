package FP_MainController;
import java.rmi.RemoteException;
import net.jini.core.entry.UnusableEntryException;
import net.jini.core.event.RemoteEvent;
import net.jini.core.event.UnknownEventException;
import net.jini.core.transaction.*;
import FP_MainModel.Person_FP1;
import FP_MainModel.PersonTemplate_FP1;

/**
 * 
 * The Person Controller class which will have all the methods to make
 * sure that a person is created from the model to use the program.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class PersonController extends MainController {

	/**
	 * Default Constructor
	 */
	public PersonController() {
		super();
	}
	
	/**
	 * This method will check to see if a user exist and will get that user
	 * @throws InterruptedException 
	 * @throws TransactionException 
	 * @throws UnusableEntryException 
	 * @throws RemoteException 
	 */
	public Person_FP1 findUser(String username) throws ExceptionController, RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		// Try to find the user
		return findUser(username, null);	
	}
	
	/**
	 * This method will check to see if a user exist and will get that user
	 * by creating a template.
	 * @throws InterruptedException 
	 * @throws TransactionException 
	 * @throws UnusableEntryException 
	 * @throws RemoteException 
	 */
	public Person_FP1 findUser(String username, String password) throws ExceptionController, RemoteException, UnusableEntryException, TransactionException, InterruptedException {
		// User Object
		Person_FP1 user = null;

			// Create a template
			Person_FP1 PersonTemplate = new Person_FP1(username, password);
			
			// Try to find the user
			user = (Person_FP1) space.readIfExists(PersonTemplate, null, GetThreeSecond);
		
		return user;
	}
	
	/**
	 * This method will create a new user for that person to use the program.
	 * @throws TransactionException 
	 * @throws RemoteException 
	 */
	public Person_FP1 newUser(String name, String password) throws ExceptionController, RemoteException, TransactionException {
		// User object
		Person_FP1 user = null;
				// Generate an ID
				Integer PersonID = CreateNewTemplateID(new PersonTemplate_FP1(), null);
				// Create the Chat room object
				user = new Person_FP1(PersonID, name, password);
				
				// Write to the space
				space.write(user, null, GetTenMins);
		return user;
	}
	
	public void notify(RemoteEvent evt) 
			throws UnknownEventException, RemoteException {
	}
}
