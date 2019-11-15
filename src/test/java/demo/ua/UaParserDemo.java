package demo.ua;

import org.junit.Test;
import ua_parser.Client;
import ua_parser.Parser;

public class UaParserDemo {

    @Test
    public void test1() throws Exception {
        String uaString = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_1_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B206 Safari/7534.48.3";

        Parser uaParser = new Parser();
        Client client = uaParser.parse(uaString);

        System.out.println(client.userAgent.family); // => "Mobile Safari"
        System.out.println(client.userAgent.major);  // => "5"
        System.out.println(client.userAgent.minor);  // => "1"

        System.out.println(client.os.family);        // => "iOS"
        System.out.println(client.os.major);         // => "5"
        System.out.println(client.os.minor);         // => "1"

        System.out.println(client.device.family);    // => "iPhone"
    }

}
