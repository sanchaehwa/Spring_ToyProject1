package me.hwayeong.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.hwayeong.springbootdeveloper.config.jwt.TokenProvider;
import me.hwayeong.springbootdeveloper.domain.User;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public  class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token " );
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        //RefreshToken으로 사용자 찾음
        User user = userService.findById(userId);
//찾고, 엑세스토큰 발급
        return tokenProvider.generateToken(user, Duration.ofHours(2));

    }
}
