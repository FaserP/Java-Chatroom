package FP_MainView;
import javax.swing.*;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
* 
* * A JoptionPane that is used for creating a new chat room.
* 
*   Faser Parvez
*	December 16th 2015
*/

public class LoginGUI extends JOptionPane {
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	// JPanel for Login Dialog
	private JPanel LoginPanel;
	// TextField for user name
	private JTextField username;
	// TextField for password
	private JPasswordField password;
	
	/**
	 * Default Constructor 
	 */
	public LoginGUI(){
		super();
		
		//Grabs the New Room Dialog
		LoginDialog();
	}
	
	@SuppressWarnings("unused")
	private void initGUI() {
		try {
			{
				this.setPreferredSize(new java.awt.Dimension(262, 92));
				this.setLayout(null);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This Method is used to create the GUI for the Login Dialog
	 */
	private void LoginDialog(){
		// Creating a Jpanel
		LoginPanel = new JPanel();  
		LoginPanel.setLayout(new BoxLayout(LoginPanel, BoxLayout.PAGE_AXIS));
		 
		// Adding some Jlabels to explain
		LoginPanel.add(new JLabel("Create a new user before using the chatroom"));
		LoginPanel.add(new JLabel(""));
		
		// Creating the username textfield with text limit
		LoginPanel.add(new JLabel("Username"));  
		username = new JTextField(14); 
		username.setDocument(new TextLimitOnDialogs(14));
		LoginPanel.add(username); 
		
		// Creating the password textfield with text limit
		LoginPanel.add(new JLabel("Password"));
		password = new JPasswordField(14);
		password.setDocument(new TextLimitOnDialogs(14));
		LoginPanel.add(password);  
	}

	/**
	 * This method will make sure that the user & password must be inputted and
	 * can't be left blank. If the user decides to not input both/or one and
	 * clicks "OK" then the window will reappear again until both fields has
	 * been inputted. 
	 * 
	 */
	public boolean showForceLoginWindow(){
		boolean getError = true;	
		do{
			// This gets the dialog to be shown
			int response = showLoginWindow();
			if(response != JOptionPane.OK_OPTION){
				getError = true;
				break;
			}
			// This method will be used to see if the textboxes is empty
			boolean username = !getUsername().isEmpty();
			boolean password = !getPassword().isEmpty();

			getError = (username && password) ? false : true;
		}while(getError);
		return getError;
	}
	
	/**
	 * This method gets the create the login dialog for the user
	 */
	public int showLoginWindow(){
		// Get the Login Dialog
		return showConfirmDialog(null, LoginPanel, "Please Login", 
								 JOptionPane.OK_CANCEL_OPTION);
	}
	
	/**
	 * This will grab the text that has been inserted into the
	 * text box for username.
	 */
	public String getUsername(){
		return new String(username.getText());
	}
	
	/**
	 * This will grab the text that has been inserted into the
	 * text box for password.
	 */
	public String getPassword(){
		return new String(password.getPassword());
	}	
}
