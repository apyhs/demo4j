package demo.others;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Csv {
    private List<List<String>> data = new ArrayList<>();
    private static String separator = ",";

    public Csv() { }

    public Csv(List<List<String>> data) {
        this.data = data;
    }

    public Csv(String csvPath) {
        this(csvPath, Charset.defaultCharset().name());
    }

    public Csv(String csvPath, String charset) {
        try {
            FileInputStream in = new FileInputStream(csvPath);
            Reader r = new InputStreamReader(in, charset);
            BufferedReader reader = new BufferedReader(r);
            for (String line; (line = reader.readLine()) != null;) {
                String[] split = line.split(separator);
                data.add(Arrays.asList(split));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public Csv(BufferedReader reader) {
        try {
            for (String line; (line = reader.readLine()) != null;) {
                String[] split = line.split(separator);
                data.add(Arrays.asList(split));
            }
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public int rowSize() {
        return data.size();
    }

    public int columnSize(int rowIndex) {
        return data.get(rowIndex).size();
    }

    public List<String> readRow(int rowIndex) {
        return data.get(rowIndex);
    }

    public void writeRow(List<String> rowContent) {
        data.add(rowContent);
    }

    public void setRow(int rowIndex, List<String> rowContent) {
        data.set(rowIndex, rowContent);
    }

    public String readCell(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public void writeCell(int rowIndex, String columnContent) {
        data.get(rowIndex).add(columnContent);
    }

    public void setCell(int rowIndex, int columnIndex, String columnContent) {
        data.get(rowIndex).set(columnIndex, columnContent);
    }

    public void save(BufferedWriter writer) throws IOException {
        if (data != null && data.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (List<String> list : data) {
                builder.setLength(0);
                if (list != null && list.size() > 0) {
                    for (String s : list) {
                        builder.append(s).append(separator);
                    }
                }
                writer.write(builder.toString());
                writer.newLine(); writer.flush();
            }
        }
    }

    public void save(String savePath) {
        save(savePath, Charset.defaultCharset().name());
    }

    public void save(String savePath, String charset) {
        BufferedWriter writer = null;
        try {
            FileOutputStream out = new FileOutputStream(savePath);
            Writer w = new OutputStreamWriter(out, charset);
            writer = new BufferedWriter(w);
            save(writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try { if (writer != null) writer.close(); }
            catch (Exception e) { writer = null; }
        }
    }

}
