package demo.poi.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import kit4j.poi.Excel;
import kit4j.poi.Field;
import kit4j.poi.PoiKit;


public class Demo {
	
	@Test
	public void readExcel() throws IOException{
		ReadExcel excel = new ReadExcel("d:\\aa.xlsx");
		for (int i = 0; i < excel.getRowNum(); i++) {
			System.out.println(excel.getAnyRow(i));
		}
	}
	
	@Test
	public void writeExcel(){
		WriteExcel excel = WriteExcel.getXlsxInstance();
		List<String> list = new ArrayList<>();
		Collections.addAll(list, "aa", "bb", "cc", "dd", "ee", "ff");
		excel.setAnyRow(0, list);
		list.clear();
		Collections.addAll(list, "01", "02", "03", "04", "05", "06");
		excel.setAnyRow(1, list);
		list.clear();
		Collections.addAll(list, "11", "12", "13", "14", "15", "16");
		excel.setAnyRow(2, list);
		list.clear();
		Collections.addAll(list, "21", "22", "23", "24", "25", "26");
		excel.setAnyRow(3, list);
		excel.save("d:\\aa.xlsx");
		System.out.println("OK!");
	}
	
	@Test
	public void test(){
		System.out.println(Excel.ExcelType.XLS);
		System.out.println(Excel.ExcelType.XLSX);
	}
	
	@Test
	public void writeExcel1(){
		List<AA> list = new ArrayList<>();
		list.add(new AA("aa", "bb", "cc", "dd", "ee", "ff"));
		list.add(new AA("01", "02", "03", "04", "05", "06"));
		list.add(new AA("11", "12", "13", "14", "15", "16"));
		list.add(new AA("21", "22", "23", "24", "25", "26"));
		List<Field> fields = Field.convert(new String[][]{{"a","AAA"},{"b","BBB"},{"c","CCC"},{"d","DDD"},{"e","EEE"},{"f","FFF"}});
		PoiKit.newExcel(fields, list).save("d:\\aa.xlsx");
		System.out.println("OK!");
		Excel excel = Excel.read("d:\\aa.xlsx").selectSheet(0);
		for (int i = 0; i <= excel.getLastRowNum(); i++) {
			System.out.println(excel.readLine(i));
		}
	}
	
	@Test
	public void readExcel11(){
		List<Field> fields = Field.convert(new String[][]{{"a","AAA"},{"b","BBB"},{"c","CCC"},{"d","DDD"},{"e","EEE"},{"f","FFF"}});
		List<AA> list = PoiKit.readExcel(fields, Excel.read("d:\\aa.xlsx").selectSheet(0), AA.class);
		System.out.println(list);
	}
}
