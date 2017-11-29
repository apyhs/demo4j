package demo.demo.jdk;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeDemo {

	@Test
	public void timeLen(){
		long time = new Date().getTime();
		System.out.println(time);
		System.out.println((time+"").length());
		int time1 = (int)(time / 1000);
		System.out.println(time1);
		int time2 = time1 * 2;
		System.out.println(time2);
	}

	@Test
	public void timestamp(){
		System.out.println(new Date().getTime() + " ==>> 现在时间的时间戳");
		System.out.println(getCurrent00Time() + " ==>> 当天00:00的时间戳");
		System.out.println(getCurrent24Time() + " ==>> 当天24:00的时间戳");
		System.out.println(getCurrentStartMonthTime() + " ==>> 当月初始00:00的时间戳");
		System.out.println(getCurrentEndMonthTime() + " ==>> 当月月末24:00的时间戳");
	}

	/**
	 * 获取当天凌晨00:00的时间戳
	 */
	public static Long getCurrent00Time(){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			return dateFormat.parse(dateFormat.format(new Date())).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当天晚上24:00的时间戳
	 */
	public static Long getCurrent24Time(){
		return getCurrent00Time() + 86400000;
	}

	/**
	 * 获取当月月初00:00的时间戳
	 */
	public static Long getCurrentStartMonthTime(){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			return dateFormat.parse(dateFormat.format(new Date())).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取当月月末24:00的时间戳
	 */
	public static Long getCurrentEndMonthTime(){
		try {
			// 当月月末24:00不就相当于下月月初00:00
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
			String dateString = dateFormat.format(new Date());
			Integer monthString = Integer.parseInt(dateString.substring(4, 6));
			if (monthString == 12){
				dateString = (Integer.parseInt(dateString.substring(0, 4)) + 1) + "01";
			} else if (monthString == 10 || monthString == 11) {
				dateString = dateString.substring(0, 4) + (monthString + 1);
			} else {
				dateString = dateString.substring(0, 4) + "0" + (monthString + 1);
			}
			return dateFormat.parse(dateString).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Test
	public void test(){
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime().getTime());
		System.out.println(new Date().getTime());
		showCalendar(calendar);
	}

	public void showCalendar(Calendar calendar){
		System.out.print(calendar.get(Calendar.DATE)+" - ");
		System.out.print(calendar.get(Calendar.MONTH)+1+" - ");
		System.out.print(calendar.get(Calendar.YEAR)+" - ");
	}

	@Test
	public void test11() throws Exception {
		System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss +SSSS"));
		System.out.println(DateUtils.parseDate("2017-10-13T07:31:26.000Z", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
	}

}
