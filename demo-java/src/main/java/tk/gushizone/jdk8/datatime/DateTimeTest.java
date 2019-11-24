package tk.gushizone.jdk8.datatime;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * DateTime API
 *
 * @author gushizone@gmail.com
 * @date 2019-05-14 10:55
 */
public class DateTimeTest {


    /**
     * LocalDate 是一个不可变的类，它表示日期，默认格式是yyyy-MM-dd
     */
    @Test
    public void localDate() {

        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate); // 2019-05-14
        System.out.println(nowDate.getYear() + "-" + nowDate.getMonthValue() + "-" + nowDate.getDayOfMonth()); // 2019-5-14

        LocalDate customDate = LocalDate.of(9102, Month.JANUARY, 1);
        System.out.println(customDate); // 9102-01-01
    }

    /**
     * LocalTime 是一个不可变的类，它的实例代表一个符合人类可读格式的时间，默认格式是 hh:mm:ss.zzz
     */
    @Test
    public void localTime() {

        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime); // 11:14:47.718
        System.out.println(nowTime.getHour() + ":" + nowTime.getMinute() + ":" + nowTime.getSecond()); // 11:18:32

        LocalTime customTime = LocalTime.of(11, 2, 33);
        System.out.println(customTime);
    }

    /**
     * LocalDateTime 是一个不可变的日期-时间对象，它表示一组日期-时间，默认格式是 yyyy-MM-ddTHH-mm-ss.zzz
     */
    @Test
    public void localDateTime() {

        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println(nowDateTime); // 2019-05-14T11:21:27.766
        System.out.println(nowDateTime.getYear() + "-" + nowDateTime.getMonthValue() + "-" + nowDateTime.getDayOfMonth()); //2019-5-14

        LocalDate customDate = LocalDate.of(9102, Month.JANUARY, 1);
        LocalTime customTime = LocalTime.of(11, 2, 33);
        LocalDateTime customDateTime = LocalDateTime.of(customDate, customTime);
        System.out.println(customDateTime); // 9102-01-01T11:02:33

    }

    @Test
    public void old() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2019-05-14 13:54:31");
        System.out.println(date); // Tue May 14 13:54:31 CST 2019

        String strDate = sdf.format(date);
        System.out.println(strDate); // 2019-05-14 13:54:31

    }

    /**
     * DateTimeFormatter
     */
    @Test
    public void formatAndParse() {
        // format
        LocalDate date = LocalDate.now();
        System.out.println(date.format(DateTimeFormatter.BASIC_ISO_DATE));// 20190514
        System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));// 2019-05-14

        LocalTime time = LocalTime.now();
        System.out.println(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));// 13:54:31

        LocalDateTime dateTime = LocalDateTime.of(date, time);
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))); // 2019-05-14 13:54:31

        // parse
        LocalDateTime dt = LocalDateTime.parse("2019-05-14 13:54:31", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dt); // 2019-05-14T13:54:31
    }

    @Test
    public void dealAndAnalyse(){

        LocalDate today = LocalDate.now(); // 2019-05-14

        System.out.println("当前是否是闰年: " + today.isLeapYear()); // 当前是否是闰年: false
        System.out.println(today.isAfter(LocalDate.of(9102,1,1))); // false

        // plus and minus : Day, Week, Month, Year
        System.out.println("一天后: " + today.plusDays(1)); // 一天后: 2019-05-15
        System.out.println(today); // 2019-05-15
        System.out.println("一天前: " + today.minusDays(1)); // 一天前: 2019-05-13

        LocalDate dt = LocalDate.parse("2020-03-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(dt.minusMonths(1));// 2020-02-29
        System.out.println(dt.minusYears(1).minusMonths(1));// 2019-02-28

        // lastDayOfMonth, lastDayOfYear
        LocalDate lastDayOfYear = today.with(TemporalAdjusters.lastDayOfYear());
        System.out.println(lastDayOfYear); // 2019-12-31

        // 时间间隔
        Period period = today.until(lastDayOfYear);
        System.out.println(period.getDays()); // 17
        System.out.println(period.getMonths()); // 7
        System.out.println(period.getYears()); // 0

    }

    @Test
    public void old2new(){
        // Date,Calendar  -> Instant -> LocalDateTime
        Instant timestamp1 = new Date().toInstant();
        Instant ts1 = Calendar.getInstance().toInstant();

        LocalDateTime dateTime1 = LocalDateTime.ofInstant(timestamp1, ZoneId.systemDefault());
        LocalDate date1 = dateTime1.toLocalDate();
        LocalTime time1 = dateTime1.toLocalTime();
    }

    @Test
    public void new2old(){
        // LocalDateTime,LocalDate -> Instant -> Date
        Instant timestamp2 = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        Instant ts2 = LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        Date date2 = Date.from(timestamp2); // Tue May 14 16:41:08 CST 2019
        Date d2 = Date.from(ts2); // Tue May 14 00:00:00 CST 2019
    }


}
