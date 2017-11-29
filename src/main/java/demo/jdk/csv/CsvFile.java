package demo.jdk.csv;

import demo.jdk.StreamKit;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFile {
    private static final String separator = ",";
    private List<List<String>> data;

    public CsvFile() {
        this.data = new ArrayList<>();
    }

    public CsvFile(List<List<String>> data) {
        if (data == null) {
            throw new NullPointerException("Data cannot be null. ");
        }
        this.data = data;
    }

    public CsvFile(BufferedReader reader) throws IOException {
        this.data = new ArrayList<>();
        for (String line; (line = reader.readLine()) != null;) {
            String[] split = line.split(separator);
            data.add(Arrays.asList(split));
        }
    }

    public CsvFile(File path, String charset) throws IOException {
        this.data = new ArrayList<>();
        FileInputStream in = new FileInputStream(path);
        Reader r = new InputStreamReader(in, charset);
        BufferedReader reader = new BufferedReader(r);
        for (String line; (line = reader.readLine()) != null;) {
            String[] split = line.split(separator);
            data.add(Arrays.asList(split));
        }
    }

    public int getLastRowNum() {
        return data.size();
    }

    public int getRowSize(int rowIndex) {
        return data.get(rowIndex).size();
    }

    public List<String> readRow(int rowIndex) {
        return data.get(rowIndex);
    }

    public String readCell(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    public void writeRow(List<String> rowContent) {
        data.add(rowContent);
    }

    public void writeCell(int rowIndex, String columnContent) {
        data.get(rowIndex).add(columnContent);
    }

    public void setRow(int rowIndex, List<String> rowContent) {
        data.set(rowIndex, rowContent);
    }

    public void setCell(int rowIndex, int columnIndex, String columnContent) {
        data.get(rowIndex).set(columnIndex, columnContent);
    }

    public String save() throws IOException {
        StringWriter sw = new StringWriter();
        save(new BufferedWriter(sw));
        return sw.toString();
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

    public void save(File path, String charset) throws IOException {
        BufferedWriter writer = null;
        try {
            FileOutputStream out = new FileOutputStream(path);
            Writer w = new OutputStreamWriter(out, charset);
            writer = new BufferedWriter(w);
            save(writer);
        } finally {
            StreamKit.close(writer);
        }
    }

}
