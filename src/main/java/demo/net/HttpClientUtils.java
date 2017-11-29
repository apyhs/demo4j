package demo.net;

import demo.base.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    public static HttpClient createHttpClient() {
        return HttpClients.createDefault();
    }

    public static RequestConfig.Builder createReqConfigBuilder() {
        return RequestConfig.copy(RequestConfig.DEFAULT);
    }

    public static RequestBuilder createRequestBuilder(String method) {
        return RequestBuilder.create(method);
    }

    public static EntityBuilder createEntityBuilder() {
        return EntityBuilder.create();
    }

    public static MultipartEntityBuilder createMultipartEntityBuilder() {
        return MultipartEntityBuilder.create();
    }

    public static List<NameValuePair> createNameValuePairs(Map<String, String> params) {
        List<NameValuePair> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                result.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return result;
    }

    public static void setHeaders(RequestBuilder builder, Map<String, String> headers) {
        if (CollectionUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    public static byte[] toByteArray(HttpEntity entity) throws IOException {
        return EntityUtils.toByteArray(entity);
    }

    public static String toString(HttpEntity entity, String defaultCharset) throws IOException, ParseException {
        return EntityUtils.toString(entity, defaultCharset);
    }

    public static String toString(HttpEntity entity) throws IOException, ParseException {
        return EntityUtils.toString(entity);
    }

}
