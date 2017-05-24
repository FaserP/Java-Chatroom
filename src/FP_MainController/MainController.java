package FP_MainController;
import java.rmi.RemoteException;
import net.jini.core.entry.Entry;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.lease.Lease;
import net.jini.core.transaction.*;
import net.jini.space.JavaSpace;
import net.jini.export.Exporter;
import net.jini.jeri.BasicILFactory;
import net.jini.jeri.BasicJeriExporter;
import net.jini.jeri.tcp.TcpServerEndpoint;
import FP_Main.SpaceUtils;
import FP_MainModel.MainTemplate_FP1;

/**
 * 
 * The Main Controller class which will handle getting the javaspace so
 * all the information can then be retrived, read and inserted into that space.
 * 
 *  Faser Parvez
 *  December 16th 2015
 */

public abstract class MainController implements RemoteEventListener {
	// JavaSpace Object.
	protected JavaSpace space;
	protected RemoteEventListener theStub;
	
	public static final long GetThreeSecond = 1000*3;
	public static final long GetFiveSecond = 1000*5;
	public static final long GetTenMins = 1000*60*10;

	protected MainController(){
		// Get the Space reference
		space = SpaceUtils.getSpace();
		SpaceUtils.getManager();
	}
	
	/**
	 * 
	 * The main controller will check to see if the javaspace exists and will
	 * then get that javaspace.
	 */
	protected MainController(Entry template){
		// Get the Space
		space = SpaceUtils.getSpace();
		SpaceUtils.getManager();
		
		try {
			Exporter myDefaultExporter = 
					new BasicJeriExporter(TcpServerEndpoint.getInstance(0),
							new BasicILFactory(), false, true);
			RemoteEventListener rel = (RemoteEventListener) myDefaultExporter.export(this);	    
		    space.notify(template, null, rel, Lease.FOREVER, null);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	/**
	 * 
	 * The method will create a new ID for the tail which can be used as an 
	 * template.
	 */
	public Integer CreateNewTemplateID(MainTemplate_FP1 template, Transaction transaction) 
			throws RemoteException, TransactionException {
		
		// Get the tail
		MainTemplate_FP1 newTemplate;
		
		try{
			// Get the tail based upon the given template 
			newTemplate = (MainTemplate_FP1) space.take(template, transaction, GetThreeSecond);
			
			// User the template if nothing could be found in the space.
			if(newTemplate == null){
				newTemplate = template;
			}
		} catch(Exception e) {
			newTemplate = template;
		}
		newTemplate.upPosition();
		
		// Write back to the space and return the position
		space.write(newTemplate, transaction, Lease.FOREVER);
		return newTemplate.TemplatePosition;
	}
	
}
