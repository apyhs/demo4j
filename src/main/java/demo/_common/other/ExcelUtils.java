package demo._common.other;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static final ExcelType DEFAULT_EXCEL_TYPE = ExcelType.XLSX;

    public static Excel create() {
        return create(DEFAULT_EXCEL_TYPE);
    }

    public static Excel create(ExcelType excelType) {
        switch (excelType) {
            case XLS: return new Excel(new HSSFWorkbook(), excelType);
            case XLSX: return new Excel(new XSSFWorkbook(), excelType);
            default: throw new RuntimeException("Excel type is error. ");
        }
    }

    public static Excel create(InputStream in) throws IOException {
        return create(DEFAULT_EXCEL_TYPE, in);
    }

    public static Excel create(ExcelType excelType, InputStream in) throws IOException {
        switch (excelType) {
            case XLS: return new Excel(new HSSFWorkbook(in), excelType);
            case XLSX: return new Excel(new XSSFWorkbook(in), excelType);
            default: throw new RuntimeException("Excel type is error. ");
        }
    }

    public static Excel createByFile(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or file not exist. ");
        }
        String filePath = file.toString();
        String suffix = filePath.substring(
                filePath.lastIndexOf(".")).toLowerCase();
        InputStream in = null; Excel result = null;
        try {
            in = new FileInputStream(file);
            for (ExcelType type : ExcelType.values()) {
                if (type.getSuffix().equals(suffix)) {
                    result = create(type, in);
                }
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {

                }
            }
        }
        if (result != null) return result;
        else throw new RuntimeException("Suffix in file is not excel suffix. ");
    }

    public static <T> Excel createByBeans(List<T> beans) throws ReflectiveOperationException {
        return createByBeans(DEFAULT_EXCEL_TYPE, beans);
    }

    public static <T> Excel createByBeans(ExcelType excelType, List<T> beans) throws ReflectiveOperationException {
        Excel result = create(excelType).createSheet();
        if (CollectionUtils.isEmpty(beans)) {
            throw new IllegalArgumentException("Beans is empty. ");
        }

        List<Method> usefulGetMethods = new ArrayList<>();
        List<Object> rowContent = new ArrayList<>();

        Class<?> clazz = beans.get(0).getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String name = method.getName();
            if (method.isAccessible() && name.length() > 3 &&
                    name.indexOf("get") == 0) {
                usefulGetMethods.add(method);
                name = StringUtils.uncapitalize(name.substring(3));
                rowContent.add(name);
            }
        }
        result.writeRow(0, rowContent);

        for (int i = 0, len = beans.size(); i < len; i++) {
            rowContent.clear();
            for (Method method : usefulGetMethods) {
                rowContent.add(method.invoke(beans.get(i)));
            }
            result.writeRow(i + 1, rowContent);
        }

        return result;
    }

    public static <T> List<T> readToBeans(Excel excel, Class<T> clazz) throws ReflectiveOperationException {
        List<T> result = new ArrayList<>();
        Integer lastRowNum = excel.getLastRowNum();
        if (lastRowNum <= 0) {
            throw new IllegalArgumentException("Excel not have row or not have data. ");
        }

        List<Method> usefulSetMethods = new ArrayList<>();
        List<?> rowContent;

        Method[] methods = clazz.getMethods();
        rowContent = excel.readRow(0);
        for (Object data : rowContent) {
            String name = "set" + StringUtils.capitalize(data + "");
            int size = usefulSetMethods.size();
            for (Method method : methods) {
                if (StringUtils.equals(method.getName(), name)) usefulSetMethods.add(method);
            }
            if (size + 1 != usefulSetMethods.size()) {
                throw new RuntimeException("Method [" + name + "] not found in class [" + clazz.getName() + "].");
            }
        }

        for (int i = 1; i <= lastRowNum; i++) {
            rowContent.clear();
            rowContent = excel.readRow(i);
            T bean = clazz.newInstance();
            for (int j = 0, size = usefulSetMethods.size(); j < size; j++) {
                Object val = rowContent.get(j);
                Class<?> valType = usefulSetMethods.get(j).getParameterTypes()[0];
                if (!val.getClass().equals(valType)) {
                    val = ConvertUtils.convert(val, valType);
                }
                usefulSetMethods.get(j).invoke(bean, val);
            }
            result.add(bean);
        }

        return result;
    }

    public static byte[] saveToBytes(Excel excel) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excel.write(out);
        return out.toByteArray();
    }

    public static void saveToFile(Excel excel, File file) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            excel.write(out);
        } finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {

                }
            }
        }
    }

}
