package demo.demo.poi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;



public class ExcelDemo {
	
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

}
