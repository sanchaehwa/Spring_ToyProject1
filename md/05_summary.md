데이터 베이스 : 데이터를 매우 효율적으로 보관하고 꺼내볼 수 있는 곳

—> 데이터 베이스를 사용함으로써, 많은 사람들이 안전하게 데이터를 사용하고 관리할 수 있음.

데이터베이스를  관리하기 위한 소프트웨어 : DBMS(Database Management system) (eg. MySQL / oracle)

관리 특징에 따라 , 관계형 , 객체 관계형, 도큐먼트형, 비관계형 으로 분류되는 DBMS

~~>이중에서 가장 많이 사용하는게, 관계형 RDBMS

관계형 RDBMS (relational DBMS) :
테이블 형태로 이루어진 데이터 저장소

각 행은 고유의 키(ID값) 이메일 이름 ** 회원과 관련된 값들이 들어감

---

ORM : 자바의 객체와 데이터 베이스를 연결하는 프로그래밍 기법.

→ SQL에 대한 지식이 없더라도, 자바 언어로만 데이터 베이스에 접근해서 원하는 데이터를 받아올 수 있음. 즉, 객체와 데이터 베이스를 연결해 자바 언어로만 데이터베이스를 다룰 수 있게 하는 도구를 ORM이라함.

→ 객체 지향적으로 코드를 작성할 수 있기 때문에 개발자는, 비즈니스 로직 (서비스 처리..) 집중할 수 있음.

→ 데이터 베이스 시스템이 추상화 (MySQL → PostgreSQL 로 전환해도 추가 작업이 없음)
→ 매핑하는 정보가 명확하기 때문에 ERD에 대한 의존도를 낮출 수 있고 유지보수 할때 유리

ERD (An Entity Relationship Diagram)

시스템의 엔티티들이 무엇이 있고, 어떤 관계가 있는지를 나타내는 다이어그램

---

**JPA (Java Persistence API) :**

자바에서 객체를 데이터 베이스에 저장하고 관리하기 위한 인터페이스와 기능을 제공하는 API

JPA를 사용하면 객체와 관계형 데이터베이스 간의 매핑을 손쉽게 처리할수 있다.

데이터 베이스의 CRUD(Create Read Update Delete) 작업을 간편하게 수행할 수 있음.

**관계형 데이터베이스 (Relational Database (RDBMS))**
테이블 형태로 이루어져 있으며, 관계형 데이터 베이스는 데이터의 종속성을 관계로 표현하는 것이 특징임.

JPA 예제 코드

1. **엔티티 클래스 정의**

   엔티티: 데이터 베이스의 테이블과 매핑되는 객체 (데이터 베이스의 테이블과 직접 연결됨)

   엔티티 매니저 : 엔티티를 관리해 데이터베이스와 애플리케이션 사이에서 객체를 생성 수정 삭제하는 등의 역할을 함.

    ```jsx
    //엔터티 클래스 선언
    @Entity
    @Table(name = "users") //엔티티 클래스와 매핑될 데이터 베이스 테이블 이름을 지정함. 
    public class User {
    				@Id
    				@GeneratedValue(strategy = GenerationType.INDENTITY) 
    				// 엔티티의 식별자 값을 자동으로 생성
    				private Long id;
    				@Column(nullable = false, unique = true)
    				private String password;
    }
    }
    ```

2. Repository 인터페이스 정의

엔터티를 조작하기 위한 Repository 인터페이스 정의.

JpaRepository 인터페이스를 상속받으면, 기본적인 CRUD(Create Read Update Delete) 작업을 수행하는 매서드가 자동 제공됨.

JpaRepository만 상속받으면 따로 구현코드 없이 해당 매서드를 사용할 수 있다.

```jsx
//레퍼지토리 선언
@Repository 
public interface UserRepository extends JpaRepository<User, Long> {
			//username 필드 값과 일치한 User 엔티티 객체를 반환
			Optional<User> findByUsername(String username);
}

```

1. 엔티티 매니저 팩토리 설정

엔티티 매니저 : 엔티티를 관리해 데이터 베이스와 에플리케이션 사이에서 객체를 생성, 수정,삭제하는 등의 역할을 함.

엔터티 매니저를 만드는 곳 → 엔터티 매니저 팩토리

JPA의 인터페이스를 구현, 내부적으로는 JDBC API를 사용 → 하이버네이터

```jsx
# H2 인-메모리 데이터베이스를 사용하기 위한 데이터 소스 설정(application.yml)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA를 사용하기 위한 설정
spring.jpa**.hibernate.**ddl-auto=create # 애플리케이션 실행 시 엔티티를 대상으로 DDL 실행
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect # H2 데이터베이스 방언 지정
```

1. (엔터티 활용) 서비스 클래스 정의

엔터티를 사용하는 비즈니스 로직을 구현하는 서비스 클래스

```jsx
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 새로운 사용자를 생성하고, 생성된 사용자를 반환.
     * param : user 새로 생성할 사용자 정보
     * return : 생성된 사용자 정보
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 주어진 사용자명(username)에 해당하는 사용자 정보를 조회.
     * param : username 조회할 사용자명
     * return 사용자 정보가 존재하는 경우 해당 정보를, 그렇지 않은 경우 null을 반환.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // other methods
}

```

1. 컨트롤러 클래스 정의

HTTP 요청을 처리하는 컨틀롤러 클래스 정의

```jsx
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 새로운 사용자를 생성하고, 생성된 사용자 정보를 반환.
     * param : user 생성할 사용자 정보
     * return : 생성된 사용자 정보
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * 주어진 사용자명(username)에 해당하는 사용자 정보를 조회.
     * param : username 조회할 사용자명
     * return : 사용자 정보가 존재하는 경우 해당 정보를, 그렇지 않은 경우 null을 반환.
     */
    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException(username);
        }
    }

    // other methods
}
```

**영속성 컨텍스트**

영속성 컨텍스트란, jpa에서 엔티티를 db에 반영하기 전에 영속화하는 공간.

서버단에 존재하는 가상의 DB 역할을 하는 일종의 매모리 공간이며 엔터티의 변화를 감지하여 DB에 반영전 객체를 우선적으로 작업하는 공간.



1차 캐시 공간 에서 엔티티를 관리하니, **내가 사용하는 엔티티와 동일한 엔터티가** 캐시(1차캐시)공간에 있다면, DB에 접근하지 않고 영속성 컨텍스트에 있는 엔터티를 사용할 수 있는것.

[ 내가 사용하는 엔터티와 1차 캐시 공간에서 엔터티 동일 여부 확인]

식별자 (객체의 id, 테이블에서 pk)로 판단

**클라이언트의 요청부터 DB까지 반영되는 과정**

클라이언트의 요청이 서버에 들어오게 되면, 요청 작업을

쓰레드에게 EntityFactory가 EntityManager를 트랜잭션이 시작될 때 할당.

[사용자가 DB에 엔터티 쓰기 작업]

해당 엔티티의 식별자값을 가져와 탐색 (해당 엔터티를 찾고)

헤당 엔터티와 사용자가 가져온 엔터티를 비교 (변화된 필드 감지) ⇒ 더티체킹

더티체킹하여 변화가 일어났다면, 업데이트 (쿼리 만들고 SQL 저장소 쿼리 저장)

트랜잭션이 끝나면 한번에 실행되는 과정 ⇒ 쓰기 지연

한번에 실행하니깐, 서버와 DB가 쿼리를 실행할 떄마다 접근하지 않아서, 성능과 속도면에서 큰 효율을 얻을 수 있음.

영속성 컨텍스트의 SQL 저장소에 있는 쿼리 는 DB에 반영된 이후, (=트랜잭션 종료) 영속성 컨텍스트 파기

**Entity의 생명주기**

비영속(new/transient)

- 엔터티 객체를 생성하였지만 아직 영속성 컨텍스트에 저장하지 않은 상태

영속(managed)

- 영속성 컨텍스트에 저장된 상태
- 엔티티가 영속성 컨텍스트에 의해 관리된다.
- 영속 상태가 되었다고 바로 DB에 값이 저장되지 않고 트렌젝션의 커밋 시점에 영속성 컨텍스트에 있는 정보들을 DB에 쿼리로 날리게 된다.

```jsx
@Autowired
private EntityManager entityManager;
// Class내에 Autowired로 EntityManager추가

    //객체만 생성한 비영속상태 
    User user = new User();
    
    // 객체를 저장한 영속상태
    entityManager.persist(user);
```

준영속(detached)

- 영속성 컨텍스트에 저장되었다가 분리된 상태
- 1차 캐시, 쓰기 지연, 변경 감지, 지연 로딩 을 포함한 영속성 컨텍스트가 제공하는 어떠한 기능도 동작하지 않음x