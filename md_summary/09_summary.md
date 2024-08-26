### **서버(세션) 기반 인증**

서버 측에서 사용자들의 정보를 기억하고 있어야 함. 사용자들의 정보를 기억하기 위해 세션을 유지해야 하는데,

메모리나 디스크 또는 데이터 베이스 등을 통해 관리됨.

서버 기반의 인증 시스템은 , 클라이언트로부터 요청을 받으면 (서버) 클라이언트의 상태를 계속해서 유지하고 **이 정보를** 서비스에서 이용해야 함. (이 정보 == 세션)

이러한 서버를 sateful 서버라 함.


소규모 시스템에서는 많이 사용하더라도 ,규모가 커지면 서버 확장이 어려울 뿐만 아니라 데이터베이스에 무리를 줄수 있음.

확장성, 사용자가 늘어나게 되면 더 많은 트래픽을 처리하기 위해 여러 프로세스를 돌리거나 컴퓨터를 추가하는 등 서버를 확장해야함.

(트래픽 == 네트워크 내부에 일정 시간 동안 흐르는 데이터 양)

CORS(Cross-Origin-Resource Sharing)

웹 어플리케이션에서 세션을 관리할때 자주 사용되는 쿠키는 단일 도메인 및 서브 도메인에서만 작동하도록 설계되어있다.

그러니깐, CORS는 한 도메인 (서브 도메인만 작동하도록 설계 , 한 도메인 - 도메인 간의 요청을 가진 다른 도메인의 리소스에 엑세스를 할 수 있게 해주는)

### **토큰 기반 인증 시스템**

토큰 기반 인증 시스템이란, 인증받은 사용자들에게 토큰을 발급하고,서버에 요청을 할 떄 헤더에 토큰을 함께 보내도록 유효성 검사를 함.

시스템에서 더이상 사용자의 인증 정보를 서버나 세션에 유지하지 않고 클라이언트 측에서 들어오는 요청만으로 작업을 처리함.

1. **사용자가 아이디와 비밀번호로** 로그인을 한다
2. 서버 측에서 해당 정보 (사용자가 아이디 , 비밀번호)를 검증함.
3. 정보(사용자가 아이디와 비밀번호) 가 정확하다면 서버측에서 사용자에서 Signed 토큰을 발급 (Signed 토큰 : 해당 토큰이 서버에서 정상적으로 발급된 토큰)
4. **클라이언트 측에서 전달받은 토큰을 저장해두고, 서버에 요청을 할때마다 해당 토큰을 서버에 전달함. 이때 http 요청 헤더에 토큰을 포함시킴.
   : 무상태성**
   토큰은 클라이언트 측에 저장되기 때문에, 서버는 완전이 stateless
   서버 입장에서는 클라이언트의 인증 정보를 저장하거나 유지하지 않아도 되기때문에 효율적인 검증.
   5. 서버는 토큰이 유효한지 검증하고, 요청에 응답,

### **JWT 토큰**

---

JWT 토큰 : JWT 토큰(Json Web Token) Json 객체에 인증에 필요한 정보들을 담은 후 비밀 키로 서명한 토큰으로, 인터넷 표준 인증 방식.

공식적으로 인증(Authentication) & 권한허가(Authorization) 방식으로 사용됨.

### JWT 프로세스


1. 사용자가 아이디와 비밀번호 혹은 소셜로그인을 이용하여 서버에 로그인 요청을 보낸다.
2. 서버는 비밀키 를 사용해 Json 객체를 암호화한 JWT 토큰을 발급
3. JWT를 헤더에 담아 클라이언트에 보냄
4. 이 JWT를 로컬에 저장하고, API 호출을 할 때 마다 header에 JWT를 실어 보냄.
5. 서버는 헤더를 매번 확인하여 사용자가 신뢰할만 사용자인지 검증함
6. (5번과정 : 검증 이후) API 에 대한 응답

JWT를 헤더에 넣어서 보내야하는것은, HTTP 프로토콜 특성때문임.

### HTTP 특성

HTTP는 connectionless 하고, stateless 하다는 특징이 있음

<aside>
connectionless : 한번 통신이 이뤄지고 난 후에 연결이 바로 끊어짐
stateless : 이전 상태를 유지 / 기억하지 않는다.

</aside>

그러니깐, 한번 통신이 일어나고 나면 연결이 끊어진다는 것, 다시 연결해도 이전 상태를 유지하지 않아 과거에 어떤 정보를 보냈었는지 기억하지 못한다는 것.

즉, 화면이 바뀌고 새로운 API를 요청하면 다시 **신뢰할만한 사용자인지 인증하는 과정을 거쳐야 함.**

**매번 사용자가 인증하는 과정은** 귀찮을 뿐만 아니라 통신이 느려지는 문제가 될 수 있음. ( = 과부하..)

따라서, **인증된 사용자가 어느 정도 기간동안 재인증 하지 않아도 되도록 (로그인이 유지도록)하는게
Access Token**

### JWT의 구조

JWT는, Header, PayLoad, Signature

`xxxxx . yyyyy . zzzzz`

`Header  . Payload . Signature`

**Header**

- alg : Signature에서 사용하는 알고리즘
- typ : 토큰 타입

Signature에서 사용하는 알고리즘은 대표적으로 RS256(공개키 / 개인키) , HS256 (비밀키)가 있음

**Payload**

사용자 정보의 한 조각인 클레임(claim)이 들어있다.

- sub : 토큰 제목(subject)
- aud : 토큰 대상자(audience)
- iat : 토큰이 발급된 시각 (issued at)
- exp : 토큰의 만료 시각 (expired)

**Signature**

Signature는 헤더와 페이로드의 문자열을 합친 후에, **헤더에서 선언한 알고리즘과 key를 이용해 암호한 값**이다.

**Header, Payload 는 단순히 Base64url 로 인코딩 되어 있어, 쉽게 복호화가 가능한다. Signature 는 Key가 없으면 복호화를 못함. 보안성 안전 **

### Access Token 과 Refresh Token

JWT 토큰 인증방식은 비밀키(개인키 or 대칭키)로 암호화

탈취 당했을 때, 문제가 생길수 있는데 , 그렇기에 유호기간을 둬야함.

유효기간을 짧게 두면 사용자가 로그인을 자주 해야하므로, 사용자 경험적으로 좋지 않고, 유효기간을 길게두면 보안상 탈취 위험에서 벗어날 수 있음.

이때 유효기간을 2개로 두는데, 그게 Access Token / Refresh Token 임.

## 프로세스

기본적인 개념은 다음과 같다.

- `Access Token`의 **유효기간은** **짧다**. (ex. 60일([마이크로소프트](https://learn.microsoft.com/en-us/linkedin/shared/authentication/programmatic-refresh-tokens)), 1시간([아마존](https://developer.amazon.com/docs/login-with-amazon/access-token.html)))
- `Refresh Token`의 **유효기간은** **길다**. (ex. 1년 ([마이크로소프트](https://learn.microsoft.com/en-us/linkedin/shared/authentication/programmatic-refresh-tokens)))
- 평소에 API 통신할 때는 Access Token을 사용하고, Refresh Token은 Access Token이 만료되어 갱신될 때만 사용한다.

Access Token 으로 API 통신을 하고 Refresh Token 으로 만료되면 Access Token을 발급받는거

### 통신 방법

1. 로그인 인증에 성공한 클라이언트는 `Refresh Token`과 `Access Token` 두 개를 **서버로부터 받는다**.
2. 클라이언트는 `Refresh Token`과 `Access Token`을 **로컬**에 저장해놓는다.
3. 클라이언트는 **헤더**에 Access Token을 넣고 API 통신을 한다. **(Authorization)**
4. 일정 기간이 지나 `Access Token`의 **유효기간이 만료**되었다.4.1. Access Token은 이제 유효하지 않으므로 **권한이 없는 사용자**가 된다.4.2. 클라이언트로부터 유효기간이 지난 Access Token을 받은 서버는 [401 (Unauthorized)](https://www.rfc-editor.org/rfc/rfc6750#section-6.2.2) 에러 코드로 응답한다.4.3. `401`를 통해 클라이언트는 `invalid_token` (유효기간이 만료되었음)을 알 수 있다.
5. **헤더**에 Access Token 대신 `Refresh Token`을 넣어 **API를 재요청**한다.
6. Refresh Token으로 사용자의 권한을 확인한 서버는 **응답쿼리 헤더**에 **새로운 Access Token**을 넣어 응답한다.
7. 만약 `Refresh Token`도 **만료**되었다면 서버는 동일하게 **401 error code**를 보내고, 클라이언트는 **재로그인**해야한다.

### 토큰 필터

토큰 필터는, 요청이 오면 헤더값을 비교해서 토큰이 있는지 확인하고 유효 토큰이라면 **시큐리티 컨텍스트 홀더**에 인증 정보를 처리한다.

`요청 → 유효한토큰 (토큰이 있는지 확인) → 시큐리티 콘텍스트 홀더에 저장`

Security Context Holder

: 유효 토큰이라면, 시큐리티 컨텍스트 홀더에 인증 정보를 저장함. Security Context 란, 인증 객체가 저장되는 저장소. 인증 정보가 필요하다면, 꺼내 사용할 수 있는데. 이러한 Security Context 객체를 저장하는게 **Security Context Holder**

## CODE [Spring Security 핵심 설정]

```jsx

    @Bean
    //Spring Security Settings
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                //Disable
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/api/token")).permitAll() //permitAll (누구나 접근 가능)
                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated() //
                        .anyRequest().permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(oAuth2UserCustomService))
                        .successHandler(oAuth2SuccessHandler())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")
                        ))
                .build();
    }

```

### CSRF 보호 비활성화, 기본 HTTP 인증 비활성화

```jsx
.csrf(AbstractHttpConfigurer::disable)
.httpBasic(AbstractHttpConfigurer::disable)
.formLogin(AbstractHttpConfigurer::disable) //OAuth2와 JWT를 사용하여 인증처리, Form 기반 로그인 필요하지않음.
.logout(AbstractHttpConfigurer::disable)
```

CSRF(Cross-Site Request Forgery) 공격은 사용자가 의도하지 않은 요청을 전송하게 하는 공격

CSRF 보호는 **서버 세션 기반 인증**을 사용할 때 유효함.

하지만, 이 코드에서는 세션을 사용하지 않고, JWT를 사용하여 클라이언트 측에서 인증 정보를 관리하므로, CSRF 공격의 위험이 줄어듬. 그리고, JWT는 일반적으로 헤더에 포함되어 요청되기에, CSRF 보호가 필요하지 않음.

**기본 HTTP 인증 비활성화,**

기본 HTTP 인증은 브라우저가 **사용자 이름과 비밀번호를 요청 헤더**에 포함하여 서버에 전송하는 인증 방식.

이 방식은, 보안이 취약하고 유연성이 부족하므로, 이 애플리케이션에서는 JWT 기반 인증을 사용하여 보다 안전하고 유연한 인증 방식을 구현함. 따라서 기본 HTTP 인증을 비활성화.