package Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtils {

    private static Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public static void info(String... message) {
        log().info(String.join(" ", message));
    }

    public static void error(String... message) {
        log().error(String.join(" ", message));
    }

    public static void debug(String... message) {
        log().debug(String.join(" ", message));
    }

    public static void warn(String... message) {
        log().warn(String.join(" ", message));
    }

    public static void fatal(String... message) {
        log().fatal(String.join(" ", message));
    }
}