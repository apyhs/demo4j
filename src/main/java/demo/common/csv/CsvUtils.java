package demo.common.csv;

import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CsvUtils {

    public static CsvFile createByList(List<List<String>> data) {
        return new CsvFile(data);
    }

    public static CsvFile createByReader(BufferedReader reader) throws IOException {
        return new CsvFile(reader);
    }

    public static CsvFile createByFile(File path, String charset) throws IOException {
        FileInputStream in = new FileInputStream(path);
        Reader r = new InputStreamReader(in, charset);
        BufferedReader reader = new BufferedReader(r);
        return new CsvFile(reader);
    }

    public static <K, V> CsvFile createByMap(List<Map<K, V>> data) {
        List<List<String>> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(data)) {
            // add title
            Set<K> ks = data.get(0).keySet();
            List<String> title = new ArrayList<>();
            for (K k : ks) title.add(k.toString());
            result.add(title);
            // add data
            for (Map<K, V> kvMap : data) {
                List<String> d = new ArrayList<>();
                for (K k : ks) d.add(kvMap.get(k).toString());
                result.add(d);
            }
        }
        return new CsvFile(result);
    }

    public static <K, V> CsvFile createByMap(List<Map<K, V>> data, List<K> title) {
        List<List<String>> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(data) && !CollectionUtils.isEmpty(title)) {
            // add title
            List<String> titleResult = new ArrayList<>();
            for (K k : title) titleResult.add(k.toString());
            result.add(titleResult);
            // add data
            for (Map<K, V> kvMap : data) {
                List<String> d = new ArrayList<>();
                for (K k : title) d.add(kvMap.get(k).toString());
                result.add(d);
            }
        }
        return new CsvFile(result);
    }

    public static String saveToString(CsvFile csv) throws IOException {
        StringWriter sw = new StringWriter();
        csv.write(new BufferedWriter(sw));
        return sw.toString();
    }

    public static void saveToFile(CsvFile csv, File path) throws IOException {
        saveToFile(csv, path, Charset.defaultCharset().name());
    }

    public static void saveToFile(CsvFile csv, File path, String charset) throws IOException {
        BufferedWriter writer = null;
        try {
            if (!path.exists() && !path.createNewFile()) {
                throw new RuntimeException(String.format("Create new file[%s] failure!", path.toString()));
            }
            FileOutputStream out = new FileOutputStream(path);
            Writer w = new OutputStreamWriter(out, charset);
            writer = new BufferedWriter(w);
            csv.write(writer);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                }
                catch (IOException e) {

                }
            }
        }
    }

}
