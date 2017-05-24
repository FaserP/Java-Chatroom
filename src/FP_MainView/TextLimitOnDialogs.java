package FP_MainView;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
* This page is used to create the limit of how many text 
* user can insert into both the Login and New Room Dialog
* 
*   Faser Parvez
*	December 16th 2015
*/

public class TextLimitOnDialogs extends PlainDocument
{
	/**
	 * Default Serial Version
	 */
	private static final long serialVersionUID = 1L;
	// create a text limit
	private int textlimit;
	// Optional uppercase conversion
	private boolean toUppercase = false;

	/**
	 * Create the text limit that can be inserted.
	 */	
	TextLimitOnDialogs(int limit) {
	   super();
	   this.textlimit = limit;
	}

	/**
	 * Create the text limit that can be inserted with upper case conversion.
	 */
	TextLimitOnDialogs(int limit, boolean upper) {
	   super();
	   this.textlimit = limit;
	   toUppercase = upper;
	}

	/**
	 * This method checks to see the text that has been inserted and works with
	 * the uppercase conversion.
	 */
	@Override
	public void insertString (int offset, String  str, AttributeSet attr) throws BadLocationException {
	   if (str == null)
	   {
	       return;
	   }
	   if ((getLength() + str.length()) <= textlimit)
	   {
	     if (toUppercase) str = str.toUpperCase();
	     super.insertString(offset, str, attr);
	   }
	}
}