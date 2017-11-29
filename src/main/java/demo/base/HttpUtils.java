package demo.base;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtils {

    /**
     * https certificate manager
     */
    private class TrustAnyTrustManager implements X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() { return null; }
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }
    }

    /**
     * https hostname verifier
     */
    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) { return true; }
    }

    private static final SSLSocketFactory SSL_SOCKET_FACTORY = initSSLSocketFactory();
    private static final HttpUtils.TrustAnyHostnameVerifier TRUST_ANY_HOSTNAME_VERIFIER = new HttpUtils().new TrustAnyHostnameVerifier();
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";
    private static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final int DEFAULT_CONNECT_TIMEOUT = 19000;
    private static final int DEFAULT_READ_TIMEOUT = 19000;
    private static final String GET  = "GET";
    private static final String POST = "POST";
    private static String charsetName = Charset.defaultCharset().name();

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = { new HttpUtils().new TrustAnyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("TLS");	// ("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static String getCharsetName() {
        return charsetName;
    }

    public static void setCharsetName(String charsetName) {
        HttpUtils.charsetName = charsetName;
    }

    public static HttpURLConnection getHttpConnection(String url, Map<String, String> queryParas, String method, Map<String, String> headers) throws IOException, GeneralSecurityException {
        if (CollectionUtils.isNotEmpty(queryParas)) {
            if (!url.contains("?")) url += "?";
            else url += "&";
            url += CodecUtils.encodeUrlMap(queryParas);
        }

        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)_url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection)conn).setSSLSocketFactory(SSL_SOCKET_FACTORY);
            ((HttpsURLConnection)conn).setHostnameVerifier(TRUST_ANY_HOSTNAME_VERIFIER);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
        conn.setReadTimeout(DEFAULT_READ_TIMEOUT);

        conn.setRequestProperty("Content-Type", DEFAULT_CONTENT_TYPE);
        conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }

    public static void disconnect(HttpURLConnection conn) {
        if (conn != null) { conn.disconnect(); }
    }

    public static String get(String url) throws IOException, GeneralSecurityException {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> queryParas) throws IOException, GeneralSecurityException {
        return get(url, queryParas, null);
    }

    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) throws IOException, GeneralSecurityException {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(url, queryParas, GET, headers);
            conn.connect();
            InputStream in = conn.getInputStream();
            return new String(StreamUtils.readAndClose(in));
        } finally {
            disconnect(conn);
        }
    }

    public static String post(String url, String data) throws IOException, GeneralSecurityException {
        return post(url, null, null, data);
    }

    public static String post(String url, Map<String, String> queryParas) throws IOException, GeneralSecurityException {
        return post(url, queryParas, null, null);
    }

    public static String post(String url, Map<String, String> headers, String data) throws IOException, GeneralSecurityException {
        return post(url, null, headers, data);
    }

    public static String post(String url, Map<String, String> queryParas, Map<String, String> headers, String data) throws IOException, GeneralSecurityException {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(url, queryParas, POST, headers);
            conn.connect();

            if (StrUtils.isNotBlank(data)) {
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes(charsetName));
                out.flush();
                out.close();
            }
            InputStream in = conn.getInputStream();
            return new String(StreamUtils.readAndClose(in));
        } finally { disconnect(conn); }
    }

}
