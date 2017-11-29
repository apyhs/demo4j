package demo.jdk;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpKit {

    public static void setDefCharsetName(String charsetName) {
        if (StrKit.isBlank(charsetName)) {
            throw new IllegalArgumentException("CharsetName cannot be blank. ");
        }
        defaultCharsetName = charsetName;
    }

    public static String get(String url) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> queryParas) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return get(url, queryParas, null);
    }

    public static String get(String url, Map<String, String> queryParas, Map<String, String> headers) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), GET, headers);
            conn.connect();
            InputStream in = conn.getInputStream();
            return new String(StreamKit.readAndClose(in));
        } finally {
            disconnect(conn);
        }
    }

    public static String post(String url, String data) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return post(url, null, null, data);
    }

    public static String post(String url, String data, Map<String, String> headers) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return post(url, null, headers, data);
    }

    public static String post(String url, Map<String, String> queryParas, String data) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return post(url, queryParas, null, data);
    }

    public static String post(String url, Map<String, String> queryParas, Map<String, String> headers, String data) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers);
            conn.connect();

            if (StrKit.notBlank(data)) {
                OutputStream out = conn.getOutputStream();
                out.write(data.getBytes(defaultCharsetName));
                out.flush();
                out.close();
            }
            InputStream in = conn.getInputStream();
            return new String(StreamKit.readAndClose(in));
        } finally { disconnect(conn); }
    }

    public static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
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

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent", defaultUserAgent);

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }

    public static String buildUrlWithQueryString(String url, Map<String, String> queryParas) throws UnsupportedEncodingException {
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
                value = CodeKit.encodeUrl(value, defaultCharsetName);
            builder.append(key).append("=").append(value);
        }

        return builder.toString();
    }

    private static void disconnect(HttpURLConnection conn) {
        if (conn != null) { conn.disconnect(); }
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = { new HttpKit().new TrustAnyTrustManager() };
            SSLContext sslContext = SSLContext.getInstance("TLS");	// ("TLS", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

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

    private static final String POST = "POST";
    private static final String GET  = "GET";
    private static String defaultCharsetName = Charset.defaultCharset().name();
    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpKit().new TrustAnyHostnameVerifier();
    private static final String defaultUserAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";

}
