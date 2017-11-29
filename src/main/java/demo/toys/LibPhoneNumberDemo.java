package demo.toys;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import org.junit.Test;

import java.util.Locale;

/**
 * 根据 谷歌 的 libphonenumber 包 来查询手机号归属地
 */
public class LibPhoneNumberDemo {

    @Test
    public void test() {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();

        String language ="CN";
        Phonenumber.PhoneNumber referencePhonenumber = null;
//        String phoneNum = "1872171XXXX";
        String phoneNum = "1526838XXXX";
        try {
            referencePhonenumber = phoneUtil.parse(phoneNum, language);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }

        // 手机号码归属城市 city
        String city = phoneNumberOfflineGeocoder.getDescriptionForNumber(referencePhonenumber, Locale.CHINA);
        System.out.println(city);
    }

}
