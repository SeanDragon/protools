package pro.tools.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created on 17/4/8 18:55 星期六.
 *
 * @author SeanDragon
 */
public final class ToolDatePlus {

    private ToolDatePlus() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static LocalDateTime date2LocalDateTime(final Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(DEFAULT_ZONE_ID);
        return zdt.toLocalDateTime();
    }

    public static LocalDateTime time2LocalDateTime(final long time) {
        return date2LocalDateTime(new Date(time));
    }

    public static Date localDateTime2Date(final LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(DEFAULT_ZONE_ID).toInstant();
        return Date.from(instant);
    }

    public static String format(final Date date, final String pattern) {
        return date2LocalDateTime(date).format(DateTimeFormatter.ofPattern(pattern));
    }

}
