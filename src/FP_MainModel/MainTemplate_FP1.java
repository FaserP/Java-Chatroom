package FP_MainModel;
import net.jini.core.entry.Entry;

/**
 * 
 * The Tail Model that will increment the position for each
 * of the model (Person, Room and Convo). This will hold the information
 * will be the TailID, Type (Person, Room and Convo) and Position.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class MainTemplate_FP1 implements Entry {
	/**
	 * Default Constructor
	 */
	private static final long serialVersionUID = 1L;
	// Set up the fields that will be used within the model
	public Integer TemplateID;
	public Integer TemplatePosition;
	public String TemplateType;
	public enum TailType { templateROOM, templateCONVO, templatePERSON }

	/**
	 * Default Serial Version
	*/
	public MainTemplate_FP1(){
	}

	/**
	 * Set the Template ID and Position
	 */
	public MainTemplate_FP1(Integer id){
		this.TemplateID = id;
		this.TemplatePosition = new Integer(0);
	}

	/**
	 * Put up the increment positionl
	 */
	public void upPosition(){
		if(this.TemplatePosition == null){
			this.TemplatePosition = new Integer(0);
		}
		this.TemplatePosition = new Integer(this.TemplatePosition.intValue() + 1);
	}
}