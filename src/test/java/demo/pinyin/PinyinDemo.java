package demo.pinyin;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

public class PinyinDemo {
    private static Logger log = LoggerFactory.getLogger(PinyinDemo.class);

    @Test
    public void test1() {
        String data = "你好，世界！";
        log.info("{}", toFirstChar(data));
        log.info("{}", toPinyin(data));
    }

    public static String toFirstChar(String chinese){
        String pinyinStr = "";
        //转为单个字符
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    String[] strings = PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat);
                    if (ArrayUtils.isEmpty(strings)) { continue; }
                    pinyinStr += strings[0].charAt(0);
                }
                catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
            else {
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }


    public static String toPinyin(String chinese){
        String pinyinStr = "";
        char[] newChar = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < newChar.length; i++) {
            if (newChar[i] > 128) {
                try {
                    String[] strings = PinyinHelper.toHanyuPinyinStringArray(newChar[i], defaultFormat);
                    if (ArrayUtils.isEmpty(strings)) { continue; }
                    pinyinStr += strings[0];
                }
                catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }
            else {
                pinyinStr += newChar[i];
            }
        }
        return pinyinStr;
    }

}
