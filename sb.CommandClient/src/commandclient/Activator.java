package commandclient;

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.apache.log4j.*;
import org.eclipse.osgi.framework.console.CommandProvider;


public class Activator implements BundleActivator {
	
	Logger log = Logger.getLogger(Activator.class.getName());
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");
		
		context.registerService(CommandProvider.class.getName(), new CommandTest(), new Hashtable());
		log.info("====>Command Service Registered..");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
	}

}
