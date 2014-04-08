import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
	public static void main(String[] args) {
		
		new Server(5000, new Process() {

			@Override
			public String run(String input) {
				return "Thank you.";
			}
		
		}, getLogger());	
	}
	
	public static Logger getLogger() {
		Logger logger = Logger.getLogger("log");  
	    try { 
	    	FileHandler fh = new FileHandler("server.log");  
	    	fh.setFormatter(new SimpleFormatter());
	        logger.addHandler(fh);
	    } catch (Exception e) {  
	        e.printStackTrace();
	    }
	    return logger;
	}
}
