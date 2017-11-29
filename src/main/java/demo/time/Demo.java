package demo.time;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import kit4j.TimeKit;

public class Demo {
	
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
		System.out.println(Timestamp.getCurrent00Time() + " ==>> 当天00:00的时间戳");
		System.out.println(Timestamp.getCurrent24Time() + " ==>> 当天24:00的时间戳");
		System.out.println(Timestamp.getCurrentStartMonthTime() + " ==>> 当月初始00:00的时间戳");
		System.out.println(Timestamp.getCurrentEndMonthTime() + " ==>> 当月月末24:00的时间戳");
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
	public void test1(){
		System.out.println(TimeKit.format(TimeKit.ts()));
		System.out.println(TimeKit.format(TimeKit.ts0()));
		System.out.println(TimeKit.format(TimeKit.dayStartTs0()));
		System.out.println(TimeKit.format(TimeKit.dayEndTs0()));
		System.out.println(TimeKit.dayEndTs0() - TimeKit.dayStartTs0());
		System.out.println(TimeKit.format(TimeKit.monthStartTs0()));
		System.out.println(TimeKit.format(TimeKit.monthEndTs0()));
		System.out.println(TimeKit.format());
	}
}
