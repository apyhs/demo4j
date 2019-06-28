package demo.bank;

import artoria.logging.Logger;
import artoria.logging.LoggerFactory;
import org.junit.Test;

public class BankInfoUtilsTest {
    private static Logger log = LoggerFactory.getLogger(BankInfoUtilsTest.class);

    @Test
    public void test1() {
        BankInfoUtils bankInfoUtils = new BankInfoUtils("622700187301032701");
//        BankInfoUtils bankInfoUtils = new BankInfoUtils("6230910299001891252");
        String bankId = bankInfoUtils.getBankId();
        String bankName = bankInfoUtils.getBankName();
        String cardType = bankInfoUtils.getCardType();
        String reg = bankInfoUtils.getReg();
        log.info("bankId={}, bankName={}, cardType={}, reg={}", bankId, bankName, cardType, reg);
    }

}
