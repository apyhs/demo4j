package demo.util;

import demo.jdk.JKit;

import java.util.regex.Pattern;

public class MatchKit {
    public static Pattern numberPattern = Pattern.compile("^[0-9]+$");
    public static Pattern passwordPattern = Pattern.compile("^[a-zA-Z0-9]{6,22}$");
    public static Pattern phonePattern = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
    public static Pattern emailPattern = Pattern.compile("^(www\\.)?\\w+@\\w+(\\.\\w+)+$");
    public static Pattern qqPattern = Pattern.compile("^[1-9][0-9]{4,12}$");
    public static Pattern weixinPattern = Pattern.compile("^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$");

    public static boolean isNumber(String number){
        return JKit.match(numberPattern, number);
    }

    public static boolean notNumber(String number){
        return !isNumber(number);
    }

    public static boolean isPassword(String password){
        return JKit.match(passwordPattern, password);
    }

    public static boolean notPassword(String password){
        return !isPassword(password);
    }

    public static boolean isPhone(String phone) {
        return JKit.match(phonePattern, phone);
    }

    public static boolean notPhone(String phone) {
        return !isPhone(phone);
    }

    public static boolean isEmail(String email){
        return JKit.match(emailPattern, email);
    }

    public static boolean notEmail(String email){
        return !isEmail(email);
    }

    public static boolean isQQ(String qq){
        return JKit.match(qqPattern, qq);
    }

    public static boolean notQQ(String qq){
        return !isQQ(qq);
    }

    public static boolean isWeixin(String weixin){
        return JKit.match(weixinPattern, weixin);
    }

    public static boolean notWeixin(String weixin){
        return !isWeixin(weixin);
    }

}
