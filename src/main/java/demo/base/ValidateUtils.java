package demo.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

    private static final Pattern QQ_PATTERN = Pattern.compile("^[1-9][0-9]{4,12}$");
    private static final Pattern WEIXIN_PATTERN = Pattern.compile("^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
    private static final Pattern URL_PATTERN = Pattern.compile("^[a-zA-z]+://[^\\s]*$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\s*?(.+)@(.+?)\\s*$");
    // private static final Pattern EMAIL_PATTERN = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
    private static final Pattern CHINESE_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
    private static final Pattern FLOAT_PATTERN = Pattern.compile("^-?[1-9]\\d*\\.?\\d*|-?0\\.?\\d*[1-9]\\d*$");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?[1-9]\\d*|0$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^-?\\d+\\.?\\d*$");

    public static boolean isQq(String qq) {
        return matches(QQ_PATTERN, qq);
    }

    public static boolean isNotQq(String qq) {
        return !isQq(qq);
    }

    public static boolean isWeixin(String weixin) {
        return matches(WEIXIN_PATTERN, weixin);
    }

    public static boolean isNotWeixin(String weixin) {
        return !isWeixin(weixin);
    }

    public static boolean isPhone(String phone) {
        return matches(PHONE_PATTERN, phone);
    }

    public static boolean isNotPhone(String phone) {
        return !isPhone(phone);
    }

    public static boolean isUrl(String url) {
        return matches(URL_PATTERN, url);
    }

    public static boolean isNotUrl(String url) {
        return !isUrl(url);
    }

    public static boolean isEmail(String email) {
        return matches(EMAIL_PATTERN, email);
    }

    public static boolean isNotEmail(String email) {
        return !isEmail(email);
    }

    public static boolean isChinese(String chinese) {
        return matches(CHINESE_PATTERN, chinese);
    }

    public static boolean isNotChinese(String chinese) {
        return !isChinese(chinese);
    }

    public static boolean isFloat(String floatNum) {
        return matches(FLOAT_PATTERN, floatNum);
    }

    public static boolean isNotFloat(String floatNum) {
        return !isFloat(floatNum);
    }

    public static boolean isInteger(String integer) {
        return matches(INTEGER_PATTERN, integer);
    }

    public static boolean isNotInteger(String integer) {
        return !isInteger(integer);
    }

    public static boolean isNumber(String number) {
        return matches(NUMBER_PATTERN, number);
    }

    public static boolean isNotNumber(String number) {
        return !isNumber(number);
    }

    public static boolean matches(Pattern pattern, CharSequence input) {
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    public static boolean matches(String regExp, CharSequence input) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
