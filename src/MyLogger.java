import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	static Logger logger;
	public Handler fileHandler;
    Formatter plainText;
    
    private MyLogger() throws IOException{
    	//instance the logger
        logger = Logger.getLogger(MyLogger.class.getName());
        logger.setLevel(Level.FINE);
        
        //instance the file handler
        String log_name = "log.txt";
        File f = new File(log_name);
        if (f.exists()) f.delete();
        fileHandler = new FileHandler(log_name, true);
        
        //instance formatter, set formatting, and handler
        plainText = new SimpleFormatter();
        fileHandler.setFormatter(plainText);
        fileHandler.setLevel(Level.FINE);
        logger.addHandler(fileHandler);
    }
    
    private static Logger getLogger(){
        if(logger == null){
            try {
                new MyLogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
    
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
    }
}
