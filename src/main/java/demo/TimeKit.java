package demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeKit {
	
	/**
	 * default date format string
	 */
	private static String DEFAULT_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	
	public static void setDefaultFormatPattern(String format){
		if (StrKit.isBlank(format)) {
			throw new IllegalArgumentException("format string can not be blank!");
		}
		DEFAULT_FORMAT_PATTERN = format;
	}
	
	/**
	 * get now timestamp
	 */
	public static int ts(){
		return ts(new Date());
	}
	
	/**
	 * get date timestamp
	 */
	public static int ts(Date date){
		return (int)(ts0(date) / 1000);
	}
	
	/**
	 * get now long timestamp
	 */
	public static long ts0(){
		return ts0(new Date());
	}
	
	/**
	 * get date long timestamp
	 */
	public static long ts0(Date date){
		return date.getTime();
	}
	
	/**
	 * get current day 00:00 timestamp
	 */
	public static int dayStartTs(){
		return dayStartTs(new Date());
	}
	
	/**
	 * get date day 00:00 timestamp
	 */
	public static int dayStartTs(Date date){
		return (int) (dayStartTs0(date) / 1000);
	}
	
	/**
	 * get current day 00:00 long timestamp
	 */
	public static long dayStartTs0(){
		return dayStartTs0(new Date());
	}
 	
	/**
	 * get date day 00:00 long timestamp
	 */
	public static long dayStartTs0(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * get current day 24:00 timestamp
	 */
	public static int dayEndTs(){
		return dayEndTs(new Date());
	}
	
	/**
	 * get date day 24:00 timestamp
	 */
	public static int dayEndTs(Date date){
		return (int) (dayEndTs0(date) / 1000);
	}
	
	/**
	 * get current day 24:00 long timestamp
	 */
	public static long dayEndTs0(){
		return dayEndTs0(new Date());
	}
	
	/**
	 * get date day 24:00 long timestamp
	 */
	public static long dayEndTs0(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 24);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * get current start month (this month first day 00:00) timestamp
	 */
	public static int monthStartTs(){
		return monthStartTs(new Date());
	}
	
	/**
	 * get date start month (this month first day 00:00) timestamp
	 */
	public static int monthStartTs(Date date){
		return (int) (monthStartTs0(date) / 1000);
	}
	
	/**
	 * get current start month (this month first day 00:00) long timestamp
	 */
	public static long monthStartTs0(){
		return monthStartTs0(new Date());
	}
	
	/**
	 * get date start month (this month first day 00:00) long timestamp
	 */
	public static long monthStartTs0(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	
	/**
	 * get current end month (this month last day 24:00) timestamp
	 */
	public static int monthEndTs(){
		return monthEndTs(new Date());
	}
	
	/**
	 * get date end month (this month last day 24:00) timestamp
	 */
	public static int monthEndTs(Date date){
		return (int) (monthEndTs0(date) / 1000);
	}
	
	/**
	 * get current end month (this month last day 24:00) long timestamp
	 */
	public static long monthEndTs0(){
		return monthEndTs0(new Date());
	}
	
	/**
	 * get date end month (this month last day 24:00) long timestamp
	 */
	public static long monthEndTs0(Date date){
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
	
	/**
	 * get now default format time
	 * @see #DEFAULT_FORMAT_PATTERN
	 * @see #format(String)
	 */
	public static String format(){
		return format(DEFAULT_FORMAT_PATTERN);
	}
	
	/**
	 * @see #format(String,Long)
	 */
	public static String format(Integer timestamp){
		return format(DEFAULT_FORMAT_PATTERN, timestamp);
	}
	
	/**
	 * @see #format(String,Long)
	 */
	public static String format(Long timestamp){
		return format(DEFAULT_FORMAT_PATTERN, timestamp);
	}
	
	/**
	 * get now format time
	 * @see #format(String,Date)
	 */
	public static String format(String format){
		return format(format, new Date());
	}
	
	/**
	 * using default date format string
	 * @see #format(String,Date)
	 */
	public static String format(Date date){
		return format(DEFAULT_FORMAT_PATTERN, date);
	}
	
	/**
	 * @see #format(String,Long)
	 */
	public static String format(String format, Integer timestamp){
		return format(format, new Date(timestamp * 1000L));
	}
	
	/**
	 * format timestamp to string (using SimpleDateFormat)
	 * @see #format(String,Date)
	 */
	public static String format(String format, Long timestamp){
		return format(format, new Date(timestamp));
	}
	
	/**
	 * format time to string (using SimpleDateFormat)
	 */
	public static String format(String format, Date date){
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * parse timestamp string to date object
	 * @see #parseTs(Integer)
	 */
	public static Date parseTs(String timestamp){
		return parseTs(Integer.valueOf(timestamp));
	}
	
	/**
	 * parse timestamp to date object
	 */
	public static Date parseTs(Integer timestamp){
		return new Date(timestamp * 1000L);
	}
	
	/**
	 * parse long timestamp string to date object
	 * @see #parseTs0(Long)
	 */
	public static Date parseTs0(String timestamp){
		return parseTs0(Long.valueOf(timestamp));
	}
	
	/**
	 * parse long timestamp to date object
	 */
	public static Date parseTs0(Long timestamp){
		return new Date(timestamp);
	}
	
	/**
	 * parse time string to date object <br />
	 * by default date format string
	 * @see #DEFAULT_FORMAT_PATTERN
	 * @see #parse(String, String)
	 */
	public static Date parse(String time){
		return parse(DEFAULT_FORMAT_PATTERN, time);
	}
	
	/**
	 * parse time string to date object
	 */
	public static Date parse(String format, String time){
		try { return new SimpleDateFormat(format).parse(time); } 
		catch (Exception e) { throw new RuntimeException(e); }
	}
}
