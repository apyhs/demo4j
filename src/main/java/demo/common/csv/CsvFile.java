package demo.common.csv;

import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFile {
    private static final String separator = ",";
    private List<List<String>> data = new ArrayList<>();

    public CsvFile() {}

    public CsvFile(List<List<String>> data) {
        if (data == null) {
            throw new NullPointerException("Data cannot be null.");
        }
        this.data.addAll(data);
    }

    public CsvFile(BufferedReader reader) throws IOException {
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

    public void write(BufferedWriter writer) throws IOException {
        if (!CollectionUtils.isEmpty(data)) {
            StringBuilder builder = new StringBuilder();
            for (List<String> list : data) {
                builder.setLength(0);
                if (list != null && list.size() > 0) {
                    for (String s : list) {
                        builder.append(s).append(separator);
                    }
                }
                writer.write(builder.toString());
                writer.newLine();
                writer.flush();
            }
        }
    }

}
