package demo.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import kit4j.HttpKit;
import kit4j.StreamKit;

public class Demo {
	
	@Test
	public void sendGet(){
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setSocketTimeout(1000)  
		        .setConnectTimeout(1000)  
		        .build();  
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://118.178.235.174:8080/summary/all?agc=NLCC");
		httpGet.setConfig(requestConfig);
		httpGet.setHeader("aaaaaa", "b");
		
		CloseableHttpResponse response = null;
		try {  
			response = httpClient.execute(httpGet);  
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			String tmp = null;
			while((tmp = reader.readLine())!=null){
				System.out.println(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    HttpClientUtils.closeQuietly(response);
		}  
	}
	
	@Test
	public void sendPost(){
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setSocketTimeout(1000)  
		        .setConnectTimeout(1000)  
		        .build();
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://127.0.0.1:80/users");
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "I am superman");
		
		List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
		formParams.add(new BasicNameValuePair("name", "习近平"));  
		formParams.add(new BasicNameValuePair("user", "xjp"));  
		formParams.add(new BasicNameValuePair("mob", "135684523652"));  
		formParams.add(new BasicNameValuePair("ip", "192.168.168.254"));  
		formParams.add(new BasicNameValuePair("mac", "00-50-56-C0-00-01"));  
		UrlEncodedFormEntity uefEntity = null;
		
		CloseableHttpResponse response = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
			httpPost.setEntity(uefEntity); 
			
			response = httpClient.execute(httpPost);
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			String tmp = null;
			while((tmp = reader.readLine())!=null){
				System.out.println(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {  
		    HttpClientUtils.closeQuietly(response);
		}  
	}
	
	@Test
	public void sendGet1(){
//		System.out.println(HttpKit.get("http://118.178.235.174:8080/summary/all?agc=NLCC"));
		String url = "http://127.0.0.1:8081/users";
		Map<String, String> queryParas = new HashMap<>();
		queryParas.put("name", "习近平");
		queryParas.put("user", "xjp");
		queryParas.put("mob", "135684523652");
		queryParas.put("ip", "192.168.168.254");
		queryParas.put("mac", "00-50-56-C0-00-01");
		System.out.println(HttpKit.post(url, queryParas, ""));
	}
	
	@Test
	public void goIt() throws InterruptedException{
		for (;;) {
			System.out.println(HttpKit.get("http://127.0.0.1/api/fz/queByWhere?fzNo&gCode&ts=&te="));
//			Thread.sleep(100);
		}
	}
	
	@Test
	public void test1() throws Exception{
		URL url = new URL("https://my.oschina.net/jfinal/blog");
//		URL url = new URL("http://www.baidu.com");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		InputStream inputStream = connection.getInputStream();
		System.out.println(new String(StreamKit.read(inputStream)));
	}
	
	@Test
	public void test2() throws Exception{
//		URLConnection conn = new URL("https://my.oschina.net/jfinal/blog").openConnection();
//		System.out.println(conn.getClass().getName());
//		System.out.println(HttpKit.get("https://my.oschina.net/jfinal/blog"));
		Map<String, String> headers = new HashMap<>();
		headers.put("Referer", "http://m.weather.com.cn/mweather/101020100.shtml");
		System.out.println(HttpKit.get("http://d1.weather.com.cn/dingzhi/101020100.html", null, headers));
	}

	@Test
	public void test3() throws Exception{
		Map<String, String> header = new HashMap<>();
		header.put("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36");
		System.out.println(com.jfinal.kit.HttpKit.get("https://p.10086.cn", null, header));
//		System.out.println(HttpKit.get("https://p.10086.cn", null, header));
	}
}
