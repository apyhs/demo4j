package demo.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Demo {

    @Test
    public void test1() throws Exception {
        String secret = "123456";
        String jwt = JWT.create()
                .withClaim("userId", "123")
                .withExpiresAt(new Date())
                .sign(Algorithm.HMAC256(secret));
        System.out.println(jwt);

        Thread.sleep(1000);

//        DecodedJWT decodedJWT = JWT.decode(jwt);
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
        System.out.println("Token : " + decodedJWT.getToken());
        System.out.println("Header : " + decodedJWT.getHeader());
        System.out.println("Payload : " + decodedJWT.getPayload());
        System.out.println("Signature : " + decodedJWT.getSignature());
        System.out.println("----------------------");
        System.out.println(decodedJWT.toString());
        System.out.println("Algorithm : " + decodedJWT.getAlgorithm());
        List<String> audience = decodedJWT.getAudience();
        System.out.println("Audience : " + audience);
        String contentType = decodedJWT.getContentType();
        System.out.println("ContentType : " + contentType);
        Date expiresAt = decodedJWT.getExpiresAt();
        System.out.println("ExpiresAt : " + expiresAt);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println("Claims : " + claims);
        String id = decodedJWT.getId();
        System.out.println("Id : " + id);
        Date issuedAt = decodedJWT.getIssuedAt();
        System.out.println("IssuedAt : " + issuedAt);
        String issuer = decodedJWT.getIssuer();
        System.out.println("Issuer : " + issuer);
        String subject = decodedJWT.getSubject();
        System.out.println("Subject : " + subject);
        String type = decodedJWT.getType();
        System.out.println("Type : " + type);
    }


}
