package demo.builder;

import demo.FileKit;
import demo.StrKit;
import com.alibaba.fastjson.JSON;

import java.io.File;
import java.util.List;

/**
 * key java file about code from json file
 */
public class StatusCode {
	
	private String name;
	private Integer code;
	private String text;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * reference this code to do it
	 * <pre> String jsonPath = PathKit.path(CodeMaker.class)+"\\"+"code.json";
	 * String packageName = "me.anmt.demo.draft";
	 * String destPath = PathKit.rootPath() + "\\src\\main\\java\\" + packageName.replaceAll("\\.", "/");
	 * CodeMaker.make(jsonPath, "utf-8", packageName, "CodeTest", destPath, "utf-8");</pre>
	 */
	public static void make(String srcPath, String srcCharset, 
			String packageName, String className, String destPath, String destCharset){
		try {
			String json = FileKit.read2String(new File(srcPath), srcCharset);
			String result = make(JSON.parseArray(json, StatusCode.class), packageName, className);
			File dest = new File(destPath);
			if(!dest.exists() && !dest.mkdirs()){
				throw new RuntimeException("create dest dir failure!");
			}
			File target = new File(dest, className + ".java");
			if(!target.exists() && !target.createNewFile()){
				throw new RuntimeException("create target java file failure!");
			}
			FileKit.write2File(result, destCharset, target, false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * key java code
	 */
	private static String make(List<StatusCode> list, String packageName, String className){
		StringBuffer result = new StringBuffer(String.format("package %s;%s%s", packageName, StrKit.LSEP, StrKit.LSEP));
		result.append(String.format("public interface %s {", className)).append(StrKit.LSEP);
		if(list != null && list.size() > 0){
			for (StatusCode code : list) {
				result.append(StrKit.LSEP);
				result.append("\t/**").append(StrKit.LSEP);
				result.append(String.format("\t * %s", code.getText())).append(StrKit.LSEP);
				result.append("\t */").append(StrKit.LSEP);
				result.append(String.format("\tpublic static final Integer %s = %s;", code.getName(), code.getCode())).append(StrKit.LSEP);
			}
		}
		return result.append("}").toString();
	}
}
