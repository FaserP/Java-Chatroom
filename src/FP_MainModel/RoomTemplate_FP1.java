package FP_MainModel;
/**
 * 
 * Template Class which will be used to create a template for the Room
 * model by using the template_FP1 class.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class RoomTemplate_FP1 extends MainTemplate_FP1 {
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	public Integer Counter;
	public RoomTemplate_FP1(){
	}

	/**
	 * Room that will create a template
	 */
	public RoomTemplate_FP1(Integer id){
		super(id);
		this.TemplateType = TailType.templateROOM.toString();
	}

	public void deleteCounter(){
		if(this.Counter == null){
			this.Counter = new Integer(0);
		}
		this.Counter = new Integer(this.Counter.intValue() + 1);
	}

}