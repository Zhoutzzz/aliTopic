package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zhoutzzz
 */
public class LogUtil {
    private static final Logger LOG = Logger.getAnonymousLogger();

    public static void log(String msg) {
        LOG.log(Level.WARNING, msg);
    }
}
