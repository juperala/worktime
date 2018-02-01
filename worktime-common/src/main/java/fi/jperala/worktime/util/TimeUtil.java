package fi.jperala.worktime.util;

import java.util.Date;
import org.joda.time.DateTime;

/**
 * Utilities for handling time and date conversions.
 */
public class TimeUtil {

    private TimeUtil() {
    }

    public static Date getToday() {
        return new DateTime().toLocalDate().toDate();
    }

    public static Date getThisDate(Date date) {
        return new DateTime(date).toLocalDate().toDate();
    }

    public static Date getNextDate(Date date) {
        return new DateTime(date).toLocalDate().plusDays(1).toDate();
    }

    public static Date getFirstDateOfMonth() {
        return new DateTime().dayOfMonth().withMinimumValue().toDate();
    }

    public static Date getLastDateOfMonth() {
        return new DateTime().dayOfMonth().withMaximumValue().toDate();
    }
}
