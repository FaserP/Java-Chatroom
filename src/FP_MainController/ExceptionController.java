package FP_MainController;

/**
 * 
 * The Exception controller which is used for handling the exception
 * for the program.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */


public class ExceptionController extends Exception {

	/**
	 * Default Constructor
	 */
	private static final long serialVersionUID = 1L;

	public ExceptionController(){
		super();
	}

	public ExceptionController(String Chat){
		super(Chat);
	}
	
}
