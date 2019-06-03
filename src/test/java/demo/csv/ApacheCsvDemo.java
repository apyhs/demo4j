//package demo4j.csv;
//
//import artoria.beans.BeanUtils;
//import artoria.io.IOUtils;
//import artoria.random.RandomUtils;
//import artoria.util.CollectionUtils;
//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVParser;
//import org.apache.commons.csv.CSVPrinter;
//import org.apache.commons.csv.CSVRecord;
//import org.junit.Test;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class ApacheCsvDemo {
//
//    @Test
//    public void test1() throws IOException {
//        List<User> userList = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            userList.add(RandomUtils.nextObject(User.class));
//        }
//
//        CSVPrinter printer = null;
//        try {
//            FileWriter fileWriter = new FileWriter("f:\\test1.csv");
//            printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT);
//            if (CollectionUtils.isEmpty(userList)) { return; }
//
//            User user = CollectionUtils.getFirstNotNullElement(userList);
//            Map map = BeanUtils.createBeanMap(user);
//            printer.printRecord(map.keySet());
//
//            for (User userObj : userList) {
//                if (userObj == null) { continue; }
//                Map data = BeanUtils.createBeanMap(user);
//                printer.printRecord(data.values());
//            }
//        }
//        finally {
//            IOUtils.closeQuietly(printer);
//        }
//
//    }
//
//
//    // CSV文件分隔符
//    private static String NEW_LINE_SEPARATOR="\n";
//
//    public static void writeCsv(List<String[]> data, String filePath) throws IOException {
//        //初始化csvformat
////        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
//        CSVFormat formator = CSVFormat.DEFAULT;
//        //创建FileWriter对象
//        FileWriter fileWriter = new FileWriter(filePath);
//        //创建CSVPrinter对象
//        CSVPrinter printer = new CSVPrinter(fileWriter, formator);
//        if (null != data) {
//            //循环写入数据
//            for(String[] lineData : data) {
//                printer.printRecord(lineData);
//            }
//        }
//        System.out.println("CSV文件创建成功,文件路径:"+filePath);
//    }
//
//    public static List<CSVRecord> readCSV(String filePath, String[] headers) throws IOException{
//        //创建CSVFormat
//        CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
//        FileReader fileReader=new FileReader(filePath);
//        //创建CSVParser对象
//        CSVParser parser=new CSVParser(fileReader,formator);
//        List<CSVRecord> records=parser.getRecords();
//        parser.close();
//        fileReader.close();
//        return records;
//    }
//
//}
