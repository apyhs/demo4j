package demo;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * http kit
 */
public class HttpKit {

	/**
	 * https hostname verifier
	 */
	private class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) { return true; }
	}

	/**
	 * https certificate manager
	 */
	private class TrustAnyTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() { return null; }
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
	}

	private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpKit().new TrustAnyHostnameVerifier();
	private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
	private static String DEFAULT_CHARSET_NAME = Charset.defaultCharset().name();
	private static final String GET  = "GET";
	private static final String POST = "POST";

	private static SSLSocketFactory initSSLSocketFactory() {
		try {
			TrustManager[] tm = { new HttpKit().new TrustAnyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("TLS");	// ("TLS", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			return sslContext.getSocketFactory();
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		URL _url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
			((HttpsURLConnection)conn).setHostnameVerifier(trustAnyHostnameVerifier);
		}

		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);

		conn.setConnectTimeout(19000);
		conn.setReadTimeout(19000);

		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

		if (headers != null && !headers.isEmpty())
			for (Map.Entry<String, String> entry : headers.entrySet())
				conn.setRequestProperty(entry.getKey(), entry.getValue());

		return conn;
	}

	public static void setDefCharsetName(String charset) {
		if (StrKit.isBlank(charset)) {
			throw new IllegalArgumentException("charset name can not be blank!");
		} DEFAULT_CHARSET_NAME = charset;
	}

	/**
	 * Build queryString of the url
	 */
	public static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
		if (queryParas == null || queryParas.isEmpty()) return url;

		StringBuilder builder = new StringBuilder(url);
		boolean isFirst;
		if (!url.contains("?")) {
			isFirst = true;
			builder.append("?");
		} else {
			isFirst = false;
		}

		for (Map.Entry<String, String> entry : queryParas.entrySet()) {
			if (isFirst) isFirst = false;
			else builder.append("&");

			String key = entry.getKey();
			String value = entry.getValue();
			if (StrKit.notBlank(value))
				value = CodeKit.encodeUrl(value, DEFAULT_CHARSET_NAME);
			builder.append(key).append("=").append(value);
		}

		return builder.toString();
	}

	public static String readResponseString(HttpURLConnection conn) {
		try {
			InputStream in = conn.getInputStream();
			return StreamKit.readAndClose(new InputStreamReader(in, DEFAULT_CHARSET_NAME));
		} catch (Exception e) { throw new RuntimeException(e); }
	}

	public static void close(HttpURLConnection conn) {
		if (conn != null) {
			conn.disconnect();
			conn = null;
		}
	}

	public static HttpURLConnection send(String url, String method, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(url, method, headers);
			conn.connect();
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static HttpURLConnection send(String url, String method, Map<String, String> headers, InputStream in) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(url, method, headers);
			conn.connect();

			if (in == null) {
				throw new IllegalArgumentException("InputStream must not null! ");
			}
			OutputStream out = conn.getOutputStream();
			StreamKit.write(in, out);
			out.flush();
			out.close();

			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] sendGet(String url, Map<String, String> queryParas, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), GET, headers);
			conn.connect();
			return StreamKit.readAndClose(conn.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally { close(conn); }
	}

	public static byte[] sendPost(String url, Map<String, String> queryParas, Map<String, String> headers, InputStream in) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers);
			conn.connect();

			if (in != null) {
				OutputStream out = conn.getOutputStream();
				StreamKit.write(in, out);
				out.flush();
				out.close();
			}

			return StreamKit.readAndClose(conn.getInputStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally { close(conn); }
	}

	/**
	 * Send GET request
	 */
	public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), GET, headers);
			conn.connect();
			return readResponseString(conn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally { close(conn); }
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
	public static String post(String url, Map<String, String> queryParas, Map<String, String> headers, String data) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers);
			conn.connect();

			if (StrKit.notBlank(data)) {
				OutputStream out = conn.getOutputStream();
				out.write(data.getBytes(DEFAULT_CHARSET_NAME));
				out.flush();
				out.close();
			}

			return readResponseString(conn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally { close(conn); }
	}

	public static String post(String url, Map<String, String> queryParas, String data) {
		return post(url, queryParas, null, data);
	}

	public static String post(String url, String data, Map<String, String> headers) {
		return post(url, null, headers, data);
	}

	public static String post(String url, String data) {
		return post(url, null, null, data);
	}

}