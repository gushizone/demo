package tk.gushizone.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.util.Date;

/**
 * @author gushizone@gmail.com
 * @date 2021/8/17 5:59 下午
 */
@Slf4j
public class JwtTest {

    /**
     * 密钥
     */
    public static final String SECRET = "secret";

    /**
     * 签发人
     */
    public static final String ISSUER = "issuer";

    public static final Integer USER_ID = 123;

    @Test
    public void create() {

        // 令牌算法：HMAC / RSA
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        // 生成token
        String token = JWT.create()
                .withIssuer(ISSUER) // 签发人
                .withIssuedAt(new Date())
                .withClaim("userId", USER_ID) // 自定义声明值，非必需
                .sign(algorithm);

        System.out.println(token);
        // eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpc3N1ZXIiLCJ1c2VySWQiOjEyM30.qCUPLLu-SFR5rb07n7rUsy5IEmKDElEVqUgmD9RAeK8
        // base64UrlEncode(header).base64UrlEncode(payload).[secret]

        String[] arr = token.split("\\.");
        System.out.println("header: " + new String(Base64Utils.decodeFromUrlSafeString(arr[0])));
        System.out.println("payload: " + new String(Base64Utils.decodeFromUrlSafeString(arr[1])));

//        header: {"typ":"JWT","alg":"HS256"}
//        payload: {"iss":"issuer","userId":123}
    }

    @Test
    public void verify() {

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpc3N1ZXIiLCJ1c2VySWQiOjEyM30.qCUPLLu-SFR5rb07n7rUsy5IEmKDElEVqUgmD9RAeK8";

        // 令牌算法：HMAC / RSA
        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER) // 签发人
                .withClaim("userId", USER_ID) // 自定义声明值，非必需
                .build();

        try {
            // 验证和解析，非法或过期则抛异常
            DecodedJWT decodedJWT = verifier.verify(token);

            System.out.println("header: " + decodedJWT.getHeader());
            System.out.println("payload: " + decodedJWT.getPayload());
            System.out.println("signature: " + decodedJWT.getSignature());

            System.out.println("issuer: " + decodedJWT.getIssuer());
            System.out.println("userId: " + decodedJWT.getClaim("userId"));

//            header: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
//            payload: eyJpc3MiOiJpc3N1ZXIiLCJ1c2VySWQiOjEyM30
//            signature: qCUPLLu-SFR5rb07n7rUsy5IEmKDElEVqUgmD9RAeK8
//            issuer: issuer
//            userId: 123
        } catch (JWTVerificationException e) {
            log.error("签名非法", e);
        }
    }

}
