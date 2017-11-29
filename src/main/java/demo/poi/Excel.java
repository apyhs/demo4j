package demo.poi;

import demo.jdk.StrKit;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Excel {
	private Workbook workBook;
	private ExcelType excelType;
	private Sheet sheet;

	private Excel(Workbook workBook, ExcelType excelType){
		this.excelType = excelType;
        this.workBook = workBook;
	}

	public static Excel create(ExcelType excelType){
		switch (excelType) {
			case XLS: return new Excel(new HSSFWorkbook(), excelType);
			case XLSX: return new Excel(new XSSFWorkbook(), excelType);
			default: return null;
		}
	}

	public static <T> Excel create(List<T> beans, ExcelType excelType){
		try {
			Excel excel = create(excelType).createSheet();

			if (beans != null && beans.size() > 0) {
				List<Method> usefulGetMethods = new ArrayList<>();
				List rowContent = new ArrayList();

				Class<?> clazz = beans.get(0).getClass();
				Method[] methods = clazz.getMethods();
				for (Method method : methods) {
					String name = method.getName();
					if (method.isAccessible() &&
							name.length() > 3 &&
							name.indexOf("get") == 0) {
						usefulGetMethods.add(method);
						name = name.substring(3, name.length());
						name = StrKit.firstCharToLowerCase(name);
						rowContent.add(name);
					}
				} excel.writeLine(0, rowContent);

				for (int i = 0, len = beans.size(); i < len; i++) {
					rowContent.clear();
					for (Method method : usefulGetMethods) {
						rowContent.add(method.invoke(beans.get(i)));
					}
					excel.writeLine(i + 1, rowContent);
				}
			}

			return excel;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Excel read(InputStream in, ExcelType excelType){
		try {
			switch (excelType) {
				case XLS: return new Excel(new HSSFWorkbook(in), excelType);
				case XLSX: return new Excel(new XSSFWorkbook(in), excelType);
				default: return null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Excel read(String filePath){
		FileInputStream in = null;
		try {
			if(filePath == null) return null;
			String ext = filePath.substring(filePath.lastIndexOf("."));
			in = new FileInputStream(filePath);
			Excel result = null;
			if(StrKit.equals(ext, ExcelType.XLS.getSuffix())){
				result = new Excel(new HSSFWorkbook(in), ExcelType.XLS);
			}else if(StrKit.equals(ext, ExcelType.XLSX.getSuffix())){
				result = new Excel(new XSSFWorkbook(in), ExcelType.XLSX);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if(in != null){
				try { in.close(); } 
				catch (IOException e) {
					in = null;
				}
			}
		}
	}

	public <T> List<T> readToBeans(Class<T> clazz) {
		try {
			if (sheet == null) {
				throw new RuntimeException("Create or select sheet first.");
			}
			List<T> result = new ArrayList<>();
			Integer lastRowNum = getLastRowNum();

			if (lastRowNum > 1) {
				List<Method> usefulSetMethods = new ArrayList<>();
				List rowContent = new ArrayList();

				Method[] methods = clazz.getMethods();
				rowContent = readLine(0);
				for (Object data : rowContent) {
					String name = "set" + StrKit.firstCharToUpperCase(data + "");
					int size = usefulSetMethods.size();
					for (Method method : methods) {
						if (StrKit.equals(method.getName(), name)) {
							usefulSetMethods.add(method);
						}
					}
					if (size + 1 != usefulSetMethods.size()) {
						throw new RuntimeException("Method [" + name + "] not found in class [" + clazz.getName() + "].");
					}
				}

				for (int i = 1; i <= lastRowNum; i++) {
					rowContent.clear();
					rowContent = readLine(i);
					T bean = clazz.newInstance();
					for (int j = 0, size = usefulSetMethods.size(); j < size; j++) {
						Object val = rowContent.get(j);
						Class<?> valType = usefulSetMethods.get(j).getParameterTypes()[0];
						if (!val.getClass().equals(valType)) {
							val = ConvertUtils.convert(val, valType);
						}
						usefulSetMethods.get(j).invoke(bean, val);
					}
					result.add(bean);
				}
			}

			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * get this excel extension name
	 */
	public String getExtName(){
		return excelType.getSuffix();
	}
	
	/**
	 * create a sheet, and select it
	 */
	public Excel createSheet(){
		this.sheet = workBook.createSheet();
		return this;
	}
	
	/**
	 * create a sheet, and select it
	 */
	public Excel createSheet(String sheetname){
		this.sheet = workBook.createSheet(sheetname);
		return this;
	}
	
	/**
	 * select a sheet
	 */
	public Excel selectSheet(int index){
		this.sheet = workBook.getSheetAt(index);
		return this;
	}
	
	/**
	 * select a sheet
	 */
	public Excel selectSheet(String sheetname){
		this.sheet = workBook.getSheet(sheetname);
		return this;
	}
	
	/**
	 * get the last row (based 0, contained n) on the now select sheet
	 */
	public Integer getLastRowNum(){
		return sheet.getLastRowNum();
	}
	
	/**
	 * write now select sheet rowNum row a line
	 */
	public void writeLine(Integer rowNum, List<?> rowContent){
		int len = rowContent.size();
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < len; ++i) {
			setCellValue(row.createCell(i), rowContent.get(i));
		}
	}
	
	/**
	 * try auto get first and last cell number
	 * @see #readLine(Integer, Integer, Integer)
	 */
	public List<?> readLine(Integer rowNum) {
		Row row = sheet.getRow(rowNum);
		return readLine(rowNum, (int)row.getFirstCellNum(), (int)row.getLastCellNum());
	}
	
	/**
	 * get 0 to colNum cell (not contain colNum)
	 * @see #readLine(Integer, Integer, Integer)
	 */
	public List<?> readLine(Integer rowNum, Integer colNum) {
		return readLine(rowNum, 0, colNum);
	}
	
	/**
	 * getFileInputStream now select sheet a line <br />
	 * rowNum begin 0, firstCellNum begin 0, not contain lastCellNum
	 */
	public List<?> readLine(Integer rowNum, Integer firstCellNum, Integer lastCellNum) {
		List<Object> rowContent = new ArrayList<>();
		Row row = sheet.getRow(rowNum);
		for (int i = firstCellNum; i < lastCellNum; i++) {
			rowContent.add(getCellFormatValue(row.getCell(i)));
		}
		return rowContent;
	}
	
	/**
	 * set value to cell
	 */
	private static void setCellValue(Cell cell, Object value){
		if(value == null){
			cell.setCellValue("");
		}else if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Number){
			cell.setCellValue(((Number)value).doubleValue());
		}else if(value instanceof RichTextString){
			cell.setCellValue((RichTextString)value);
		}else if(value instanceof Boolean){
			cell.setCellValue((Boolean)value);
		}else{
			cell.setCellValue(value.toString());
		}
	}
	
	/**
	 * get cell value
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellValue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
				case Cell.CELL_TYPE_FORMULA: {
					if (DateUtil.isCellDateFormatted(cell)) {
						cellValue = cell.getDateCellValue();
					} else {
						cellValue = (Double)(cell.getNumericCellValue());
					} break;
				}
				case Cell.CELL_TYPE_BOOLEAN:
					cellValue = (Boolean)(cell.getBooleanCellValue()); break;
				default :
					cellValue = cell.getStringCellValue();
			}
		}
		return cellValue;
	}
	
	/**
	 * write workbook to byte array
	 */
	public byte[] save(){
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			workBook.write(out);
			out.flush();
			byte[] result = out.toByteArray();
			out.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally{
			if(out != null){
				try {out.close();} 
				catch (IOException e){
					out = null;
				}
			}
		}
	}
	
	/**
	 * write workbook to outputstream, <br />
	 * don't try close outputstream
	 */
	public void save(OutputStream out){
		try {
			workBook.write(out);
			out.flush();
		} catch (IOException e) {
			throw new RuntimeException("fail to write workbook in OutputStream!");
		}
	}
	
	/**
	 * write workbook to file where in filepath
	 */
	public void save(String filePath){
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			workBook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(out != null){
				try { out.close(); } 
				catch (IOException e){
					out = null;
				}
			}
		}
	}

}
