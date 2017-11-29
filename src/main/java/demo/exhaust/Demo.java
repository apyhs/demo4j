package demo.exhaust;

import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import kit4j.*;
import org.junit.Test;

public class Demo {
	
	@Test
	public void t1() {
		List<String> data = StrKit.toList("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
//		List<String> list = new ArrayList<>();
//		for (int i = 0; i < data.length(); i++) {
//			list.add(data.charAt(i)+"");
//		}
		
//		List<List<String>> table = new ArrayList<>();
//		for (int i = 0; i < 6; i++) table.add(list);
		List<List<String>> table = StrKit.list(data, data, data, data, data);
		
		// ... 3, 2, 1, 0
		int len = table.size();
//		int[] counter = new int[len + 1];
		
		for(int[] counter = new int[len + 1]; ; ){
			// get now string
			StringBuffer nowString = new StringBuffer();
			for(int i = len - 1; i >= 0; --i){
				nowString.append(table.get(i).get(counter[i]));
			}
			
			// do something
			System.out.println(nowString.toString());
			
			// clear nowString
			nowString.setLength(0);
			
			// counter increment
			for (int i = 0; i < len; i++) {
				int tmp = counter[i] + 1;
				if(tmp == table.get(i).size()) {
					if(i == len - 1) {
						counter[len - 1] = 0;
						counter[len] = 1;
						break;
					} else {
						counter[i] = 0;
						continue;
					}
				} else {
					counter[i] = tmp;
					break;
				}
			}
			
			// is ok?
			if(counter[len] == 1) break;
		}
		
	}
	
	@Test
	public void t2() {
		List<String> data = StrKit.toList("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		List<List<String>> table = StrKit.list(data, data, data, data, data);
		JKit.exhaustion(table, new Function<Void, String>() {
			@Override
			public Void callback(String... args) {
				System.out.println(args[0]);
				return null;
			}
		});
	}

	@Test
	public void t3() {
		JKit.exhaustion(StrKit.list(
					StrKit.toList("0123456789"),
					StrKit.toList("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
					StrKit.toList("JKLMNOPQR"),
					StrKit.toList("01234PQRSTUVWX"),
					StrKit.toList("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
				),
				new Function<Void, String>() {
					@Override
					public Void callback(String... args) {
						System.out.println(args[0]);
						return null;
					}
				});
	}
	@Test
	public void t4() throws Exception {
		final File path = new File("d:\\dic.txt");
		FileKit.create(path);
		final BufferedWriter writer = FileKit.write(path, "utf-8", true);
		JKit.exhaustion(StrKit.list(
				StrKit.toList("0123456789"),
				StrKit.toList("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
				StrKit.toList("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
				StrKit.toList("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
				StrKit.toList("JKLMNOPQR"),
				StrKit.toList("01234PQRSTUVWX"),
				StrKit.toList("ABCDEFGHIJKLMNOPQRSTUVWXYZ")),
		new Function<Void, String>() {
			@Override
			public Void callback(String... args) {
				try {
					System.out.println(args[0]);
					writer.write(args[0] + "");
					writer.newLine();
					writer.flush();
					return null;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	@Test
	public void t5() {
		JKit.exhaustion(StrKit.list(
				StrKit.list("1", "2", "3", "4", "5"),
				StrKit.list("."),
				StrKit.list("1", "2", "3", "4"),
				StrKit.list("192.168.")),
		new Function<Void, String>() {
			@Override
			public Void callback(String... args) {
				System.out.println(args[0]);
				return null;
			}
		});
	}

	@Test
	public void t6() {
		int now = Integer.parseInt(TimeKit.format("yyyy"));
		List<String> year = new ArrayList<>();
		for (int i = now - 100, end = now + 100; i < end; i++)
			year.add(i + "");
		List<String> month = new ArrayList<>();
		for (int i = 1; i <= 12; i++)
			month.add(((i + "").length() == 1 ? "0" : "") + i);
		List<String> day = new ArrayList<>();
		for (int i = 1; i <= 31; i++)
			day.add(((i + "").length() == 1 ? "0" : "") + i);
		JKit.exhaustion(StrKit.list(day, month, year), new Function<Void, String>() {
			@Override
			public Void callback(String... args) {
				System.out.println(args[0]);
				return null;
			}
		});
	}

}
