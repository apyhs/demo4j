package demo.jdk.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CsvKit {

    public static CsvFile createByList(List<List<String>> data) {
        return new CsvFile(data);
    }

    public static CsvFile createByReader(BufferedReader reader) throws IOException {
        return new CsvFile(reader);
    }

    public static CsvFile createByFile(File path, String charset) throws IOException {
        return new CsvFile(path, charset);
    }

    public static <K, V> CsvFile createByMap(List<Map<K, V>> data) {
        List<List<String>> result = new ArrayList<>();
        if (data != null && data.size() > 0) {
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
        if (data != null && data.size() > 0 && title != null && title.size() > 0) {
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

}
