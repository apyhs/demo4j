package demo.common.csv;

import artoria.beans.BeanUtils;
import artoria.exception.ExceptionUtils;
import artoria.io.IOUtils;
import artoria.reflect.ReflectUtils;
import artoria.util.Assert;
import artoria.util.CollectionUtils;
import artoria.util.MapUtils;
import artoria.util.StringUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel tools and excel object.
 * @author Kahle
 */
public class Csv {

    public static Csv create(File file) throws IOException {
        return Csv.create(file, null);
    }

    public static Csv create(File file, String charset) throws IOException {
        Assert.notNull(file, "Parameter \"file\" must not null. ");
        Assert.state(file.exists(), "Parameter \"file\" must exist. ");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return Csv.create(in, charset);
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }

    public static Csv create(InputStream in, String charset) throws IOException {
        Assert.notNull(in, "Parameter \"in\" must not null. ");
        Assert.notBlank(charset, "Parameter \"charset\" must not blank. ");
        Reader reader = new InputStreamReader(in, charset);
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
        List<CSVRecord> records = parser.getRecords();

        List<List<String>> data = new ArrayList<>();
        for (CSVRecord record : records) {
            if (record == null) {
                data.add(new ArrayList<>());
            }
            else {
                List<String> row = new ArrayList<>();
                for (int i = 0; i < record.size(); i++) {
                    row.add(record.get(i));
                }
                data.add(row);
            }
        }

        return new Csv(data, charset);
    }

    public static <T> Csv create(List<T> beans, Map<String, String> ppTtlMap) {
        return Csv.create(null, Charset.defaultCharset().name(), beans, ppTtlMap, 0, 0);
    }

    public static <T> Csv create(Csv template, String charset, List<T> beans, Map<String, String> ppTtlMap) {
        return Csv.create(template, charset, beans, ppTtlMap, 0, 0);
    }

    /**
     *
     * @param template
     * @param beans
     * @param ppTtlMap Property title mapping
     * @param rowStart Begin 0
     * @param columnStart Begin 0
     * @param <T>
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Csv create(Csv template, String charset, List<T> beans, Map<String, String> ppTtlMap, int rowStart, int columnStart) {
        try {
            // Check rowStart and columnStart.
            if (rowStart < 0) { rowStart = 0; }
            if (columnStart < 0) { columnStart = 0; }
            // Check ppTtlMap not empty.
            boolean hasPpTtlMap = false;
            if (MapUtils.isNotEmpty(ppTtlMap)) { hasPpTtlMap = true; }
            // Check template and if null create default.
            List<List<String>> data = new ArrayList<>();
            Csv result = new Csv(data, charset);
            // Check beans has data.
            if (CollectionUtils.isEmpty(beans)) { return result; }
            T bean = CollectionUtils.takeFirstNotNullElement(beans);
            Assert.notNull(bean, "Elements in \"beans\" all is null. ");

            // Init some.
            List<String> rowContent = new ArrayList<String>();
            boolean isMap = bean instanceof Map;
            List<String> keys = new ArrayList<>();
            // Add blank cell.
            for (int z = 0; z < columnStart; z++) { rowContent.add(null); }
            rowContent.addAll(ppTtlMap.values());
            data.add(rowContent);

            // Fill beans data to excel.
            for (int i = 0, size = beans.size(); i < size; i++) {
                rowContent = new ArrayList<String>();
                // Add blank cell.
                for (int z = 0; z < columnStart; z++) { rowContent.add(null); }
                bean = beans.get(i);
                for (Map.Entry<String, String> entry : ppTtlMap.entrySet()) {
                    String name = entry.getKey();
                    Map beanMap = isMap ? (Map) bean : BeanUtils.createBeanMap(bean);
                    Object val = beanMap.get(name);
                    rowContent.add(val != null ? val.toString() : null);
                }
                data.add(rowContent);
            }
            // Return result.
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e, RuntimeException.class);
        }
    }

    private List<List<String>> data;
    private String charsetName;

    Csv(List<List<String>> data, String charsetName) {
        Assert.notNull(data, "Parameter \"workbook\" must not null. ");
        this.data = data;
        if (StringUtils.isNotBlank(charsetName)) {
            this.charsetName = charsetName;
        }
        else {
            this.charsetName = Charset.defaultCharset().name();
        }
    }

    public Csv write(OutputStream out) throws IOException {
        Assert.notNull(out, "Parameter \"out\" must not null. ");
        Writer writer = new OutputStreamWriter(out, charsetName);
        CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
        if (CollectionUtils.isEmpty(data)) { return this; }
        for (List<String> datum : data) {
            printer.printRecord(datum == null ? new ArrayList<>() : datum);
        }
        printer.close();
        out.flush();
        return this;
    }

    public void write(File file) throws IOException {
        Assert.notNull(file, "Parameter \"file\" must not null. ");
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            this.write(out);
        }
        finally {
            IOUtils.closeQuietly(out);
        }
    }

    public byte[] write() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.write(out);
        return out.toByteArray();
    }

    public <T> List<T> readToBeans(Class<T> clazz, int rowStart, int columnStart) {

        return this.readToBeans(clazz, null, rowStart, columnStart);
    }

    public <T> List<T> readToBeans(Class<T> clazz, Map<String, String> ttlPpMap) {

        return this.readToBeans(clazz, ttlPpMap, 0, 0);
    }

    /**
     *
     * @param clazz
     * @param ttlPpMap Title property mapping
     * @param rowStart Begin 0
     * @param columnStart Begin 0
     * @param <T>
     * @return
     */
    public <T> List<T> readToBeans(Class<T> clazz, Map<String, String> ttlPpMap, int rowStart, int columnStart) {
        try {
            Assert.notNull(clazz, "Parameter \"clazz\" must not null. ");
            boolean isMap = Map.class.isAssignableFrom(clazz);
            // Check rowStart and columnStart.
            if (rowStart < 0) { rowStart = 0; }
            if (columnStart < 0) { columnStart = 0; }
            // Check ttlPpMap not empty.
            boolean hasTtlPpMap = false;
            if (MapUtils.isNotEmpty(ttlPpMap)) { hasTtlPpMap = true; }
            // Check excel has row or has data.
            List<T> result = new ArrayList<T>();
            Integer lastRowNum = this.data.size();
            Assert.state(lastRowNum > rowStart, "Csv not has row or not has data. ");


            // Init some.
            Map<String, Method> writeMethods = ReflectUtils.findWriteMethods(clazz);
            List<Method> usefulWriteMethods = new ArrayList<Method>();
            List<String> keys = new ArrayList<>();



            // Find bean write methods.
            List<String> rowContent = this.data.get(rowStart);
            Assert.notNull(rowContent, "csv 的第一行是空白的");
            if (columnStart != 0) {
                rowContent = rowContent.subList(columnStart, this.data.size());
            }
            for (String name : rowContent) {
                if (StringUtils.isBlank(name)) {
                    throw new RuntimeException("Maybe parameter \"columnStart = " +
                            columnStart + "\" is error, because get table title include blank element. ");
                }
                if (hasTtlPpMap) {
                    String tmp = ttlPpMap.get(name);
                    name = StringUtils.isNotBlank(tmp) ? tmp : name;
                }
                keys.add(name);
            }


            // Fill data to bean.
            for (int i = rowStart + 1; i < lastRowNum; i++) {
                rowContent.clear();
                rowContent = this.data.get(i);
                if (CollectionUtils.isEmpty(rowContent)) { continue; }
                if (columnStart != 0) {
                    rowContent = rowContent.subList(columnStart, this.data.size());
                }

                Map beanMap;
                if (isMap) {
                    beanMap = new HashMap();
                    result.add((T) beanMap);
                }
                else {
                    T bean = clazz.newInstance();
                    beanMap = BeanUtils.createBeanMap(bean);
                    result.add(bean);
                }
                for (int j = 0, size = rowContent.size(); j < size; j++) {
                    Object val = rowContent.get(j);
                    if (val == null) { continue; }
                    if (j >= keys.size()) { continue; }
                    beanMap.put(keys.get(j), val);
                }
            }

            // Return result.
            return result;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e, RuntimeException.class);
        }
    }

    public List<Map<String, Object>> readToMapList(int rowStart, int columnStart) {

        return this.readToMapList(null, rowStart, columnStart);
    }

    public List<Map<String, Object>> readToMapList(Map<String, String> ttlKeyMap) {

        return this.readToMapList(ttlKeyMap, 0, 0);
    }

    /**
     *
     * @param ttlKeyMap Title key mapping
     * @param rowStart Begin 0
     * @param columnStart Begin 0
     * @return
     */
    public List<Map<String, Object>> readToMapList(Map<String, String> ttlKeyMap, int rowStart, int columnStart) {
        return (List) this.readToBeans(Map.class, ttlKeyMap, rowStart, columnStart);
    }

}
