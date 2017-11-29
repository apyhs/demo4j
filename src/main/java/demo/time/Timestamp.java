package demo.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timestamp {
	
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
}
