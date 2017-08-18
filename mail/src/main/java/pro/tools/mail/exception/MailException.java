package pro.tools.mail.exception;

import java.util.Properties;

/**
 * Created on 17/7/27 21:56 星期四.
 *
 * @author sd
 */
public class MailException extends Exception {
    private Properties prop;

    public MailException(Properties properties) {
        super();
        this.prop = properties;
    }

    public MailException(Properties properties, String msg) {
        super(msg);
        this.prop = properties;
    }

    public MailException(Properties properties, Exception e) {
        super(e);
        this.prop = properties;
    }

    public MailException(Properties properties, String msg, Exception e) {
        super(msg, e);
        this.prop = properties;
    }

    @Override
    public String getLocalizedMessage() {
        return super.getMessage().concat("\r\nPROP:\t").concat(prop.toString());
    }
}
