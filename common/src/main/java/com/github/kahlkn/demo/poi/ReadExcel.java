package com.github.kahlkn.demo.poi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	private Workbook workBook;
	private Sheet sheet;
	private Row row;
	private Integer rowNum;
	private Integer colNum;
	private InputStream in;
	private String filePath;

	/**
	 * 构造 读取Excel 的工具类对象
	 * @param filePath Excel的路径
	 */
	public ReadExcel(String filePath) throws IOException {  
        if(filePath == null){  
            return;  
        }
        String ext = filePath.substring(filePath.lastIndexOf("."));  
        in = new FileInputStream(filePath);  
        if(".xls".equals(ext)){  
            workBook = new HSSFWorkbook(in);  
        }else if(".xlsx".equals(ext)){  
            workBook = new XSSFWorkbook(in);  
        }else{
        	throw new RuntimeException("It is not a excel document!");
        }
        this.filePath = filePath;
        sheet = workBook.getSheetAt(0);
        rowNum = sheet.getLastRowNum() + 1;
        colNum = sheet.getRow(0).getPhysicalNumberOfCells();
    }
	
	/**
	 * 这个Excel的第一个sheet的总行数
	 */
	public Integer getRowNum(){
		return rowNum;
	}
	
	/**
	 * 这个Excel的第一个sheet以顶格第一行列的总列数
	 */
	public Integer getColNum(){
		return colNum;
	}
	
	/**
	 * 获取这个文件的路径
	 */
	public String getFilePath(){
		return filePath;
	}

	/**
	 * 获取这个Excel第一个sheet顶格第一行内容的集合
	 */
	public List<Object> getTitleRow() {
		return getAnyRow(0);
	}
	
	/**
	 * 获取这个Excel第一个sheet任意行内容的集合
	 */
	public List<Object> getAnyRow(Integer rowNum) {
		if( this.rowNum <= rowNum || rowNum < 0 ){
			return null;
		}
		List<Object> rowContent = new ArrayList<Object>();
		row = sheet.getRow(rowNum);
		for (int i = 0; i < colNum; i++) {
			rowContent.add(getCellFormatValue(row.getCell(i)));
		}
		return rowContent;
	}
	
	/**
	 * 释放这个对象所占用的资源，重点是关闭输入流
	 */
	public void close(){
		try {
			colNum = null;
			rowNum = null;
			row = null;
			sheet = null;
			workBook = null;
			in.close();
		} catch (IOException e) {
		} finally {
			in = null;
		}
	}
	
	/**
	 * 获取任意单元格的值
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellValue = "";
		if (cell != null) {
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_NUMERIC:
				case Cell.CELL_TYPE_FORMULA: {
					if (DateUtil.isCellDateFormatted(cell)) {
						// 当前的cell是Date类型
						cellValue = cell.getDateCellValue();
					} else {
						// 如果是纯数字，取得当前Cell的数值
						cellValue = cell.getNumericCellValue();
					} break;
				}
				case Cell.CELL_TYPE_STRING:
					cellValue = cell.getRichStringCellValue().getString(); break;
				default:cellValue = "";
			}
		}
		return cellValue;
	}

}
