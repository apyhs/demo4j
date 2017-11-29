package demo.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import saber.jdk.HttpKit;
import saber.jdk.StreamKit;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
		httpPost.setHeader("UserSmall-Agent", "I am superman");
		
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
	public void sendGet1() throws Exception {
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
	public void goIt() throws Exception{
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
		header.put("UserSmall-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36");
		System.out.println(com.jfinal.kit.HttpKit.get("https://p.10086.cn", null, header));
//		System.out.println(HttpKit.get("https://p.10086.cn", null, header));
	}

	@Test
	public void sendPost11(){
		CloseableHttpClient httpClient = HttpClients.createDefault();

//		RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
//				.setSocketTimeout(19000)
//				.setConnectTimeout(19000)
//				.build();

		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("name", "习近平"));
		formParams.add(new BasicNameValuePair("user", "xjp"));
		formParams.add(new BasicNameValuePair("mob", "135684523652"));
		formParams.add(new BasicNameValuePair("ip", "192.168.168.254"));
		formParams.add(new BasicNameValuePair("mac", "00-50-56-C0-00-01"));
		HttpEntity entity = EntityBuilder.create()
				.setContentType(ContentType.create("application/x-www-form-urlencoded", "UTF-8"))
				.setParameters(formParams)
				.build();

		HttpUriRequest request = RequestBuilder.post()
				.setConfig(RequestConfig.DEFAULT)
				.setUri("http://www.baidu.com")
				.addHeader("Content-Type", "application/x-www-form-urlencoded")
				.addHeader("UserSmall-Agent", "I am superman")
				.setEntity(entity)
//				.addParameter("name", "习近平")
//				.addParameter("user", "xjp")
				.build();

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(request);
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

}
