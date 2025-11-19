import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

    private static Logger log = LogManager.getLogger();

    public static void info(String message) {
        log.info(message);
    }
}
