package idea.verlif.easy.language.resource;

import java.util.Locale;
import java.util.Properties;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/28 14:07
 */
public class MessageResource {

    private final Locale locale;
    private final Properties properties;

    public MessageResource(Locale locale) {
        this.locale = locale;
        this.properties = new Properties();
    }

    public Locale getLocale() {
        return locale;
    }

    public String get(String code) {
        return properties.getProperty(code);
    }

    public String get(String code, String defaultValue) {
        return properties.getProperty(code, defaultValue);
    }

    public Properties getProperties() {
        return properties;
    }
}
