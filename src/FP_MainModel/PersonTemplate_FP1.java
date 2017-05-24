package FP_MainModel;

/**
 * 
 * Template Class which will be used to create a template for the person
 * model by using the template_FP1 class.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */


public class PersonTemplate_FP1 extends MainTemplate_FP1 {
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;

	public PersonTemplate_FP1(){
	}
	/**
	 * Person that will create a template
	 */
	public PersonTemplate_FP1(Integer chatroom_id){
		super(chatroom_id);
		this.TemplateType = TailType.templatePERSON.toString();
	}
	
}
