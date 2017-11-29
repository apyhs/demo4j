package demo.poi;

import java.util.ArrayList;
import java.util.List;

public class PoiKit {
	private static Excel.ExcelType defaultExcelType = Excel.ExcelType.XLSX;
	
	public static void setDefaultExcelType(Excel.ExcelType excelType){
		defaultExcelType = excelType;
	}
	
	/**
	 * new excel quick save
	 * @see #newExcel(List, List)
	 */
	public static <T> void newExcel(List<Field> fields, List<T> beans, String filePath){
		newExcel(fields, beans).save(filePath);
	}
	
	/**
	 * new excel quick
	 */
	public static <T> Excel newExcel(List<Field> fields, List<T> beans){
		Excel excel = Excel.create(defaultExcelType).createSheet();
		excel.writeLine(0, Field.getTexts(fields));
		try {
			int len = beans.size();
			for (int i = 0; i < len; ++i) {
				excel.writeLine(i+1, Field.parse(fields, beans.get(i)));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return excel;
	}
	
	/**
	 * read excel quick from file path
	 * @see #readExcel(List, Excel, Class)
	 */
	public static <T> List<T> readExcel(List<Field> fields, String filePath, Class<T> clazz){
		return readExcel(fields, Excel.read(filePath).selectSheet(0), clazz);
	}
	
	/**
	 * read excel quick (just used simple pojo)
	 */
	public static <T> List<T> readExcel(List<Field> fields, Excel excel, Class<T> clazz){
		List<T> result = new ArrayList<>();
		try {
			int col = fields.size();
			int len = excel.getLastRowNum();
			for (int i = 1; i <= len; i++) {
				result.add(Field.make(fields, excel.readLine(i, col), clazz));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
}
