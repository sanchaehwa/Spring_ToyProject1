package me.hwayeong.springbootdeveloper.config.jwt;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
//(사용자 값) 해당 값을 변수로 접근
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
