package demo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpKit {
	public static final String GET  = "GET";
	public static final String POST = "POST";
	private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
	private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpKit.TrustAnyHostnameVerifier();
	private static String DEFAULT_CHARSET_NAME = "UTF-8";
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) { return true; }
	}
	
	private static class TrustAnyTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() { return null; }
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
	}
	
	private static SSLSocketFactory initSSLSocketFactory() {
		try {
			TrustManager[] tm = {new HttpKit.TrustAnyTrustManager()};
			SSLContext sslContext = SSLContext.getInstance("TLS");	// ("TLS", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setDefaultCharsetName(String charset) {
		if (StrKit.isBlank(charset))
			throw new IllegalArgumentException("charset can not be blank!");
		DEFAULT_CHARSET_NAME = charset;
	}

	/**
	 * Build queryString of the url
	 */
	private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
		if (queryParas == null || queryParas.isEmpty()) return url;

		StringBuilder builder = new StringBuilder(url);
		boolean isFirst;
		if (url.indexOf("?") == -1) {
			isFirst = true;
			builder.append("?");
		} else {
			isFirst = false;
		}

		for (Entry<String, String> entry : queryParas.entrySet()) {
			if (isFirst) isFirst = false;
			else builder.append("&");

			String key = entry.getKey();
			String value = entry.getValue();
			if (StrKit.notBlank(value)) {
				try {value = URLEncoder.encode(value, DEFAULT_CHARSET_NAME);}
				catch (UnsupportedEncodingException e) {throw new RuntimeException(e);}
			}
			builder.append(key).append("=").append(value);
		}

		return builder.toString();
	}
	
	private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers
			) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
			((HttpsURLConnection)conn).setHostnameVerifier(trustAnyHostnameVerifier);
		}
		
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		conn.setConnectTimeout(19000);
		conn.setReadTimeout(19000);
		
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) " +
				"AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		
		if (headers != null && !headers.isEmpty())
			for (Entry<String, String> entry : headers.entrySet())
				conn.setRequestProperty(entry.getKey(), entry.getValue());

		return conn;
	}
	
	public static HttpURLConnection getHttpConnection(
			String url, String method, Map<String, String> queryParas, Map<String, String> headers)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
		if(StrKit.isBlank(url) || StrKit.isBlank(method)) throw new RuntimeException("url or method is blank!");
		HttpURLConnection conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), method, headers);
		conn.connect();
		return conn;
	}
	
	public static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> queryParas, byte[] data, Map<String, String> headers)
			throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
		HttpURLConnection conn = getHttpConnection(url, method, queryParas, headers);
		OutputStream out = conn.getOutputStream();
		out.write(data); out.flush(); 
		StreamKit.close(out);
		return conn;
	}
	
	public static byte[] send(String url, String method, Map<String, String> queryParas, Map<String, String> headers){
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(url, method, queryParas, headers);
			return StreamKit.read(conn.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	public static byte[] send(String url, String method, Map<String, String> queryParas, byte[] data, Map<String, String> headers){
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(url, method, queryParas, data, headers);
			return StreamKit.read(conn.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
	
	/**
	 * Send GET request
	 */
	public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
		try {
			return new String(send(url, GET, queryParas, headers), DEFAULT_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) { throw new RuntimeException(e); }
	}
	
	public static String get(String url, Map<String, String> queryParas) {
		return get(url, queryParas, null);
	}
	
	public static String get(String url) {
		return get(url, null, null);
	}
	
	/**
	 * Send POST request
	 */
	public static String post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
		try {
			return new String(send(url, POST, queryParas, data.getBytes(DEFAULT_CHARSET_NAME), headers), DEFAULT_CHARSET_NAME);
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static String post(String url, Map<String, String> queryParas, String data) {
		return post(url, queryParas, data, null);
	}
	
	public static String post(String url, String data, Map<String, String> headers) {
		return post(url, null, data, headers);
	}
	
	public static String post(String url, String data) {
		return post(url, null, data, null);
	}

	public static String post(String url, Map<String, String> queryParas, Map<String, String> headers) {
		try {
			return new String(send(url, POST, queryParas, headers), DEFAULT_CHARSET_NAME);
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static String post(String url, Map<String, String> queryParas) {
		Map<String, String> headers = null;
		return post(url, queryParas, headers);
	}

	public static String post(String url) {
		Map<String, String> headers = null;
		Map<String, String> queryParas = null;
		return post(url, queryParas, headers);
	}
	
}