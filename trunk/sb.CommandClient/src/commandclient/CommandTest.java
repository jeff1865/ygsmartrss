package commandclient;

import org.eclipse.osgi.framework.console.*;

public class CommandTest implements CommandProvider {

	@Override
	public String getHelp() {
		String strHelp = "===Smart RSS Command Line Service===\n" +
				"\ttest [Your Message]\n";
		return strHelp;
	}
	
	public void _test(CommandInterpreter ci) throws Exception {
		System.out.println("Test Message : " + ci.nextArgument());
	}
	
}
