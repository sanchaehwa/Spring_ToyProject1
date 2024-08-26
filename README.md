
#### [스프링 부트3 백엔드 개발자 되기] 책을 참고하여 진행한, 토이 프로젝트
##### [개발 환경]

<img src="https://skillicons.dev/icons?i=spring,mysql&perline="/>

#### File Structure (= Overall project process)

```jsx
+--------------------------------+
|        Spring_ToyProject       |
|  +--------------------------+  |
|  |       Controller          | |
|  | (BlogApiController,       | |
|  |  UserApiController,       | |
|  |  TokenApiController)      | |
|  +------------+-------------+  |
|               |                |
|  +------------v-------------+  |
|  |         Service           | |
|  |  (BlogService,            | |
|  |   UserService,            | |
|  |   TokenService)           | |
|  +------------+-------------+  |
|               |                |
|  +------------v-------------+  |
|  |        Repository         | |
|  |  (BlogRepository,         | |
|  |   UserRepository,         | |
|  |   RefreshTokenRepository) | |
|  +------------+-------------+  |
|               |                |
|  +------------v-------------+  |
|  |        Database           | |
|  |   (Article, User,         | |
|  |    RefreshToken)          | |
|  +--------------------------+  |
+--------------------------------+
```
