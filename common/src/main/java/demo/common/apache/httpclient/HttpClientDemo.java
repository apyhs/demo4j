package demo.common.apache.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpClientDemo {

	@Test
	public void test1() throws IOException {
		// HttpClient
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//		HttpGet httpGet = new HttpGet("http://www.gxnu.edu.cn/default.html");
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
//		System.out.println(httpGet.getRequestLine());
		// 执行get请求
		HttpResponse httpResponse = httpClient.execute(httpGet);
		// 获取响应消息实体
		HttpEntity entity = httpResponse.getEntity();
		// 响应状态
		System.out.println("status:" + httpResponse.getStatusLine());
		// 判断响应实体是否为空
		if (entity != null) {
			System.out.println("contentEncoding:" + entity.getContentEncoding());
			System.out.println("response content:" + EntityUtils.toString(entity));
		}
		httpClient.close();
	}

	@Test
	public void test2() throws IOException {
		Header userAgent = new BasicHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36");
		List<Header> headers = new ArrayList<>();
		headers.add(userAgent);

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultHeaders(headers).build();
		HttpGet httpGet = new HttpGet("https://p.10086.cn");
		HttpResponse httpResponse = httpClient.execute(httpGet);

		HttpEntity entity = httpResponse.getEntity();
		System.out.println("status:" + httpResponse.getStatusLine());
		if (entity != null) {
			System.out.println("contentEncoding:" + entity.getContentEncoding());
			System.out.println("response content:" + EntityUtils.toString(entity));
		}

		httpClient.close();
	}

	@Test
	public void sendGet() {
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setSocketTimeout(1000)  
		        .setConnectTimeout(1000)  
		        .build();  
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/summary?s=123");
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
	public void sendPost() {
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
		formParams.add(new BasicNameValuePair("name", "习大大"));
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
		    HttpClientUtils.closeQuietly(httpClient);
		}
	}

	@Test
	public void sendPost1(){
		CloseableHttpClient httpClient = HttpClients.createDefault();

//		RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
//				.setSocketTimeout(19000)
//				.setConnectTimeout(19000)
//				.build();

		List<NameValuePair> formParams = new ArrayList<>();
		formParams.add(new BasicNameValuePair("name", "习大大"));
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
				.addHeader("User-Agent", "I am superman")
				.setEntity(entity)
//				.addParameter("name", "习大大")
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
			HttpClientUtils.closeQuietly(httpClient);
		}

	}

}
