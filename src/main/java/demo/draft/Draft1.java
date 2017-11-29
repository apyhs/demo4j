package demo.draft;

import kit4j.CodeKit;
import kit4j.FileKit;
import kit4j.HttpKit;
import kit4j.ThreadKit;
import kit4j.captcha.Captcha;
import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Draft1 {

	@Test
	public void test1(){
		Captcha captcha = new Captcha();
		captcha.write("d:\\out.png");
	}
	
	@Test
	public void test2(){
		Random random = new Random();
		for (int i = 0; i < 1000; ++i) {
			String str = HttpKit.get("https://www.baidu.com/s?ie=UTF-8&wd=%E5%AE%89%E8%90%8C%E7%89%B9%E7%9A%84%E9%9A%8F%E6%83%B3%E5%BD%95");
			System.out.println(i + "]：" + str.contains("安萌特的随想录"));
			ThreadKit.sleep((random.nextInt(50) + 50) * 1000);
		}
		
	}
	
	@Test
	public void test3(){
		try {
			String str = HttpKit.get("http://118.178.235.174/");
			System.out.println(str);
		} catch (Exception e) {
			System.out.println("站点不可达！！！");
		}
	}
	
	@Test
	public void test4(){
		Captcha captcha = new Captcha();
		for (int i = 0; i < 5; i++) {
			captcha.create();
			captcha.write("d:\\" + i + ".png");
			System.out.println("d:\\" + i + ".png");
		}
		System.out.println("OK");
	}
	
	@Test
	public void test5(){
		for (int i = 0; i < 100; i++) {
			SecureRandom random = new SecureRandom();
			System.out.println(random.nextInt(100));
		}
	}

	@Test
	public void test6(){
		StringBuffer buffer = new StringBuffer();
		for (int i = 1; i < 177; i++) {
			buffer.append(i).append(",");
		} System.out.println(buffer.toString());
	}

	@Test
	public void test7(){
		for (int i = 0; i < 5; i++) {
			System.out.println(aaaa("d:\\" + i + ".png"));
		}
	}

	@Test
	public void test8(){
		String url = "https://cashier.cmpay.com/wapcsh/csh/getgraphcode.xhtml";
		Map<String, String> query = new HashMap<>();
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36");
		byte[] bytes = HttpKit.send(url, "GET", query, header);
		FileKit.write2File(bytes, new File("d:\\c\\out.png"));
		System.out.println("OK");
	}

//	@Test
	public static String aaaa(String filePath){
		String BOUNDARY = "---------------------------68163001211748"; //boundary就是request头和上传文件内容的分隔符
		String str="http://v1-http-api.jsdama.com/api.php?mod=php&act=upload";
//		String filePath="D:\\2222.jpg";//本地验证码图片路径
		Map<String, String> paramMap = getParamMap();
		try {
			URL url=new URL(str);
			HttpURLConnection connection=(HttpURLConnection)url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("content-type", "multipart/form-data; boundary="+BOUNDARY);
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);

			OutputStream out = new DataOutputStream(connection.getOutputStream());
			// 普通参数
			if (paramMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator<Map.Entry<String, String>> iter = paramMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String,String> entry = iter.next();
					String inputName = entry.getKey();
					String inputValue = entry.getValue();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\""
							+ inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			// 图片文件
			if (filePath != null) {
				File file = new File(filePath);
				String filename = file.getName();
				String contentType = "image/jpeg";//这里看情况设置
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
				strBuf.append("Content-Disposition: form-data; name=\""
						+ "upload" + "\"; filename=\"" + filename+ "\"\r\n");
				strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
				out.write(strBuf.toString().getBytes());
				DataInputStream in = new DataInputStream(
						new FileInputStream(file));
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				in.close();
			}
			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();

			//读取URLConnection的响应
			InputStream in = connection.getInputStream();
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			while (true) {
				int rc = in.read(buf);
				if (rc <= 0) {
					break;
				} else {
					bout.write(buf, 0, rc);
				}
			}
			in.close();
			//结果输出
			String s = new String(bout.toByteArray());
			System.out.println(s);
			System.out.println(CodeKit.unicode2String(s.replace(" ", "")));
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private static Map<String, String> getParamMap() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user_name", "11");
		paramMap.put("user_pw", "22");
		paramMap.put("yzm_minlen", "4");
		paramMap.put("yzm_maxlen", "4");
		paramMap.put("yzmtype_mark", "0");
		paramMap.put("zztool_token", "123");

		return paramMap;
	}
}
