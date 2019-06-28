package demo.bank;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import org.junit.Test;

public class BankInfoUtils1Test {
    private static Logger log = LoggerFactory.getLogger(BankInfoUtils1Test.class);

    @Test
    public void test1() {
//        String carnum = "622600687501042806";
//        String carnum = "6230960288002899254";
//        String carnum = "6217994000264606028";
        String carnum = "6230666046001759766";
        if (BankCardUtils1.checkBankCard(carnum)) {
            char[] ss = carnum.toCharArray();
            String bank = BankCardUtils1.getNameOfBank(ss, 0);
            log.info("bank={}", bank);
        }
    }

}
