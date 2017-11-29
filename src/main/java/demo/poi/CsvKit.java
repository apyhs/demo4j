package demo.poi;

import demo.StrKit;
import org.apache.commons.beanutils.ConvertUtils;

import java.util.List;

public class CsvKit {

    public static StringBuffer make(List<List<String>> data){
        StringBuffer buffer = new StringBuffer();
        for (List<?> list : data) {
            for (Object obj : list) {
                buffer.append(ConvertUtils.convert(obj, String.class)).append(",");
            } buffer.append(StrKit.LSEP);
        } return buffer;
    }
}
