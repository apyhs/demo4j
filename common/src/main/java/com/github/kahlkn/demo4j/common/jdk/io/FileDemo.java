package com.github.kahlkn.demo4j.common.jdk.io;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FileDemo {
	
	/**
	 * 目录分离
	 * @param fileName 文件名
	 * @return 该文件的子目录
	 */
	public static String getSubDir(String fileName){
		int hashCode = fileName.hashCode();
		return new StringBuilder("/")
			.append(hashCode & 15).append("/")
			.append((hashCode >>> 2) & 15).append("/")
			.append((hashCode >>> 3) & 15).append("/")
			.append((hashCode >>> 4) & 15).append("/")
			.toString();
	}
	
	/**
	 * 获取格式化文件名
	 */
	public static String getFileName(){
		return  String.format("%s%s", 
				new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), 
				UUID.randomUUID().toString().replace("-", "").substring(0, 18));
	}
	
	/**
	 * 尝试删除一个文件
	 * @param filePath 该文件的全路径
	 * @return 是否删除成功
	 */
	public static Boolean deleteFile(String filePath){
		return deleteFile(new File(filePath));
	}
	
	/**
	 * 尝试删除一个文件
	 * @param file 该文件的File对象
	 * @return 是否删除成功
	 */
	public static Boolean deleteFile(File file){
		if(file != null && file.exists() && file.isFile())
			return file.delete();
		return false;
	}
}
