package demo.poi;

import demo.StrKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * you must import apache poi, if you want to use it
 */
public class Excel {
	private Workbook workBook;
	private ExcelType excelType;
	private Sheet sheet;
	
	private Excel(Workbook workBook, ExcelType excelType){
		this.excelType = excelType;
        this.workBook = workBook;
	}
	
	/**
	 * create excel
	 */
	public static Excel create(ExcelType excelType){
		switch (excelType) {
			case XLS: return new Excel(new HSSFWorkbook(), excelType);
			case XLSX: return new Excel(new XSSFWorkbook(), excelType);
			default: return null;
		}
	}
	
	/**
	 * read excel
	 */
	public static Excel read(String filePath){
		FileInputStream in = null;
		try {
			if(filePath == null) return null;
			String ext = filePath.substring(filePath.lastIndexOf("."));
			in = new FileInputStream(filePath);
			Excel result = null;
			if(StrKit.equals(ext, ExcelType.XLS.getExtName())){
				result = new Excel(new HSSFWorkbook(in), ExcelType.XLS);
			}else if(StrKit.equals(ext, ExcelType.XLSX.getExtName())){
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
	
	/**
	 * get this excel extension name
	 */
	public String getExtName(){
		return excelType.getExtName();
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
	 * read now select sheet a line <br />
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
	
	/**
	 * the ext about excel
	 */
	public enum ExcelType{
		XLS(".xls"), XLSX(".xlsx");
		
		private String extName;
		
		private ExcelType(String extName) {
			this.extName = extName;
		}

		private String getExtName() {
			return extName;
		}
	}
}
