package tk.gushizone.java.jdk8.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @date 2021/6/13 7:40 下午
 */
public class DateUtils {

    public static Date date(LocalDate localDate) {

        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static LocalDate localdate(Date date) {

        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * [startDate, endDate]
     */
    public static boolean between(LocalDate date, LocalDate startDate, LocalDate endDate) {

        return date.compareTo(startDate) >= 0 && date.compareTo(endDate) <= 0;
    }

    /**
     * 季度的第几个月
     */
    public static int monthOfQuarter(LocalDate localDate) {
        return (localDate.getMonthValue() + 2) % 3 + 1;
    }

    /**
     * 月中的第几天
     */
    public static boolean dayOfMonth(LocalDate localDate, int dayOfMonth) {
        if (localDate.getDayOfMonth() == localDate.lengthOfMonth()
                && dayOfMonth >= localDate.lengthOfMonth()) {
            return true;
        }
        return localDate.getDayOfMonth() == dayOfMonth;
    }

}
