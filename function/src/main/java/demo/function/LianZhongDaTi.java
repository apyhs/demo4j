package demo.function;

import org.junit.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 联众答题DEMO
 * https://www.jsdati.com/
 */
public class LianZhongDaTi {

	@Test
	public void testJsdati(){
		String BOUNDARY = "---------------------------68163001211748"; //boundary就是request头和上传文件内容的分隔符
		String str="http://v1-httpclient-api.jsdama.com/api.php?mod=php&act=upload";
		String filePath="D:\\2222.jpg"; //本地验证码图片路径
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
		} catch (Exception e) {
			e.printStackTrace();
		}
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
