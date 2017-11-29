package demo.jdk;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeKit {

    public static boolean equal(Date d1, Date d2) {
        return d1 == null ? d2 == null : d2 != null && (d1 == d2 || StrKit.equals(format(d1), format(d2)));
    }

    public static int getNowYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static int getNowMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getNowDay() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    public static int getNowHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getNowMinute() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);
    }

    public static int getNowSecond() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    public static int getNowMillisecond() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MILLISECOND);
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    public static int getMillisecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MILLISECOND);
    }

    public static Date newDate(Integer year, Integer month, Integer day) {
        return setToDate(null
                , year != null ? year : 0
                , month != null ? month : 0
                , day != null ? day : 0
                , 0, 0, 0, 0);
    }

    public static Date newDate(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        return setToDate(null
                , year != null ? year : 0
                , month != null ? month : 0
                , day != null ? day : 0
                , hour != null ? hour : 0
                , minute != null ? minute : 0
                , second != null ? second : 0
                , 0);
    }

    public static Date newDate(Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer millisecond) {
        return setToDate(null
                , year != null ? year : 0
                , month != null ? month : 0
                , day != null ? day : 0
                , hour != null ? hour : 0
                , minute != null ? minute : 0
                , second != null ? second : 0
                , millisecond != null ? millisecond : 0);
    }

    public static Date addToNow(Integer addYear, Integer addMonth, Integer addDay) {
        return addToDate(null, addYear, addMonth, addDay, null, null, null, null);
    }

    public static Date addToNow(Integer addHour, Integer addMinute, Integer addSecond, Integer addMillisecond) {
        return addToDate(null, null, null, null, addHour, addMinute, addSecond, addMillisecond);
    }

    public static Date addToNow(Integer addYear, Integer addMonth, Integer addDay, Integer addHour, Integer addMinute, Integer addSecond, Integer addMillisecond) {
        return addToDate(null, addYear, addMonth, addDay, addHour, addMinute, addSecond, addMillisecond);
    }

    public static Date addToDate(Date date, Integer addYear, Integer addMonth, Integer addDay) {
        return addToDate(date, addYear, addMonth, addDay, null, null, null, null);
    }

    public static Date addToDate(Date date, Integer addHour, Integer addMinute, Integer addSecond, Integer addMillisecond) {
        return addToDate(date, null, null, null, addHour, addMinute, addSecond, addMillisecond);
    }

    public static Date addToDate(Date date, Integer addYear, Integer addMonth, Integer addDay, Integer addHour, Integer addMinute, Integer addSecond, Integer addMillisecond) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) calendar.setTime(date);
        if (addYear != null) calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + addYear);
        if (addMonth != null) calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + addMonth );
        if (addDay != null) calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + addDay);
        if (addHour != null) calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + addHour);
        if (addMinute != null) calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + addMinute);
        if (addSecond != null) calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + addSecond);
        if (addMillisecond != null) calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) + addMillisecond);
        return calendar.getTime();
    }

    public static Date setToDate(Date date, Integer year, Integer month, Integer day) {
        return setToDate(date, year, month, day, null, null, null, null);
    }

    public static Date setToDate(Date date, Integer hour, Integer minute, Integer second, Integer millisecond) {
        return setToDate(date, null, null, null, hour, minute, second, millisecond);
    }

    public static Date setToDate(Date date, Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second) {
        return setToDate(date, year, month, day, hour, minute, second, null);
    }

    public static Date setToDate(Date date, Integer year, Integer month, Integer day, Integer hour, Integer minute, Integer second, Integer millisecond) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) calendar.setTime(date);
        if (year != null) calendar.set(Calendar.YEAR, year);
        // truth month = input month + 1
        if (month != null) {
            month = year != null
                    && (year == 0 || year == 1)
                    && month == 0
                    ? month : month - 1;
            calendar.set(Calendar.MONTH, month );
        }
        if (day != null) {
            day = year != null
                    && (year == 0 || year == 1)
                    && month != null
                    && (month == 0 || month == 1)
                    && day == 0
                    ? 1 : day;
            calendar.set(Calendar.DATE, day);
        }
        if (hour != null) calendar.set(Calendar.HOUR_OF_DAY, hour);
        if (minute != null) calendar.set(Calendar.MINUTE, minute);
        if (second != null) calendar.set(Calendar.SECOND, second);
        if (millisecond != null) calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    public static String format() {
        return new SimpleDateFormat(defaultTimePattern).format(new Date());
    }

    public static String format(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    public static String format(Integer timestamp) {
        return new SimpleDateFormat(defaultTimePattern).format(new Date(timestamp * 1000L));
    }

    public static String format(Integer timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(timestamp * 1000L));
    }

    public static String format(Long timestamp) {
        return new SimpleDateFormat(defaultTimePattern).format(new Date(timestamp));
    }

    public static String format(Long timestamp, String pattern) {
        return new SimpleDateFormat(pattern).format(new Date(timestamp));
    }

    public static String format(Date date) {
        return new SimpleDateFormat(defaultTimePattern).format(date);
    }

    public static String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    public static Date parse(String source) throws ParseException {
        return new SimpleDateFormat(defaultTimePattern).parse(source);
    }

    public static Date parse(String source, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(source);
    }

    public static int ts() {
        return (int) (new Date().getTime() / 1000);
    }

    public static long tsl() {
        return new Date().getTime();
    }

    public static int ts(Date date) {
        return (int) (date.getTime() / 1000);
    }

    public static long tsl(Date date) {
        return date.getTime();
    }

    public static Date parseTs(Integer timestamp) {
        return new Date(timestamp * 1000L);
    }

    public static Date parseTsl(Long timestamp) {
        return new Date(timestamp);
    }

    public static int dayStartTs() {
        return (int) (dayStartTsl() / 1000);
    }

    public static long dayStartTsl() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int dayStartTs(Date date) {
        return (int) (dayStartTsl(date) / 1000);
    }

    public static long dayStartTsl(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int dayEndTs() {
        return (int) (dayEndTsl() / 1000);
    }

    public static long dayEndTsl() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int dayEndTs(Date date) {
        return (int) (dayEndTsl(date) / 1000);
    }

    public static long dayEndTsl(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int monthStartTs() {
        return (int) (monthStartTsl() / 1000);
    }

    public static long monthStartTsl() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int monthStartTs(Date date) {
        return (int) (monthStartTsl(date) / 1000);
    }

    public static long monthStartTsl(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int monthEndTs() {
        return (int) (monthEndTsl() / 1000);
    }

    public static long monthEndTsl() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int monthEndTs(Date date) {
        return (int) (monthEndTsl(date) / 1000);
    }

    public static long monthEndTsl(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private static final String defaultTimePattern = "yyyy-MM-dd HH:mm:ss SSS";

}
