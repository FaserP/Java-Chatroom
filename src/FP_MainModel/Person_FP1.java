package FP_MainModel;
import net.jini.core.entry.Entry;

/**
 * 
 * The Person Model that will create the Person that will use the 
 * chatroom. This will hold the information such as Person ID,
 * and login details such as name and password.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class Person_FP1 implements Entry {
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	// Set up the fields that will be used within the model
	public Integer PersonID;
	public String PersonName;
	public String PersonPass;

	/**
	 * Default Constructor
	 */
	public Person_FP1(){		
	}

	/**
	 * Model the Person ID
	 */
	public Person_FP1(Integer pID){
		this.PersonID = pID;
	}

	/**
	 * Model the Person name
	 */
	public Person_FP1(String pName){
		this.PersonName = pName;
	}

	/**
	 * Model the Person name and password
	 */
	public Person_FP1(String pName, String pPass){
		this.PersonName = pName;
		this.PersonPass = pPass;
	}
	
	/**
	 * Model the Person id, name and password together
	 */
	public Person_FP1(Integer pID, String pName, String pPass){
		this.PersonID = pID;
		this.PersonName = pName;
		this.PersonPass = pPass;
	}

}