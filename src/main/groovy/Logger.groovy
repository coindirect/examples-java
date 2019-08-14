import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

class Logger {
    static setLevel(Level level) {
        ch.qos.logback.classic.Logger root =
                (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }
}
