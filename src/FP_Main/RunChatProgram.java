package FP_Main;
import FP_MainView.MainGUI;

/**
 * 
 * The main class which will run the chat room program by getting the  
 * chat room GUI.
 * NOTE: Please make sure you put "-Djava.security.policy=policy.all" into
 * 		 the VM Arguments so that it accepts the security policy file.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public class RunChatProgram{
	// Start up the MainGUI
	public static void main(String[] args) {
		new MainGUI();
	}

}
