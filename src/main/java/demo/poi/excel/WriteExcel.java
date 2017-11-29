package demo.poi.excel;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfinal.json.Json;

public class WriteExcel {
	
	private Workbook workBook;
	private Sheet sheet;
	private String ext;
	
	private WriteExcel(Workbook workBook, String ext){
		this.ext = ext;
        this.workBook = workBook;
		sheet = workBook.createSheet("sheet0");
	}
	
	/**
	 * 创建xls文件格式的WriteExcel
	 */
	public static WriteExcel getXlsInstance(){
		return new WriteExcel(new HSSFWorkbook(), ".xls");
	}
	
	/**
	 * 创建xlsx文件格式的WriteExcel
	 */
	public static WriteExcel getXlsxInstance(){
		return new WriteExcel(new XSSFWorkbook(), ".xlsx");
	}
	
	/**
	 * 向单元格保存值
	 * @param cell 单元格
	 * @param value 要保存的值
	 */
	private static void setCellValue(Cell cell, Object value){
		if(value == null){
			cell.setCellValue("");
		}else if(value instanceof String){
			cell.setCellValue((String)value);
		}else if(value instanceof Integer){
			cell.setCellValue((Integer)value);
		}else if(value instanceof Double){
			cell.setCellValue((Double)value);
		}else if(value instanceof RichTextString){
			cell.setCellValue((RichTextString)value);
		}else if(value instanceof Boolean){
			cell.setCellValue((Boolean)value);
		}else{
			cell.setCellValue(Json.getJson().toJson(value));
		}
	}
	
	/**
	 * 获取生成的Excel的后缀名
	 */
	public String getExt(){
		return ext;
	}
	
	/**
	 * 将一个字符串集合写入指定行中
	 * @param rowNum 指定行
	 * @param rowContent 字符串集合
	 */
	@SuppressWarnings("rawtypes")
	public void setAnyRow(Integer rowNum, List rowContent){
		int len = rowContent.size();
		Row row = sheet.createRow(rowNum);
		for (int i = 0; i < len; ++i) {
			setCellValue(row.createCell(i), rowContent.get(i));
		}
	}
	
	/**
	 * 获取这个workBook的byte数组
	 */
	public byte[] getResult(){
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			workBook.write(out);
			out.flush();
			byte[] bytes = out.toByteArray();
			out.close();
			return bytes;
		} catch (IOException e) {
		} finally{
			if(out != null){
				try {out.close();} 
				catch (IOException e1){
					out = null;
				}
			}
		}
		return null;
	}
	
	/**
	 * 将workBook写入到输出流中，不尝试关闭流
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
	 * 将workBook写入某个文件中
	 * @param filePath 文件所在的路径
	 * @param fileName 文件的名称（不带后缀）
	 */
	public void save(String filePath){
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			workBook.write(out);
			out.flush();
			out.close();
		} catch (IOException e) {
		} finally{
			if(out != null){
				try {out.close();} 
				catch (IOException e1){
					out = null;
				}
			}
		}
	}
}
