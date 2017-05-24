package FP_MainModel;

/**
 * 
 * Template Class which will be used to create a template for the convo
 * model by using the template_FP1 class.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class ConvoTemplate_FP1 extends MainTemplate_FP1 {
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;

	public ConvoTemplate_FP1(){	
	}
	/**
	 * Conversation that will create a template
	 */
	public ConvoTemplate_FP1(Integer room_ID){
		super(room_ID);
		this.TemplateType = TailType.templateCONVO.toString();
	}
}