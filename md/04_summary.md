테스트 코드 : 작성한 코드가 의도대로 잘 동작하고 예상치 못한 문제가 없는지 확인할 목적으로 작성하는 코드

테스트 코드 패턴 :

given - when - then

1) given : 테스트 실행을 준비하는 단계

2) when : 테스트를 진행하는 단계

3) then : 테스트 결과 검증

```jsx
@DisplayName("새로운 메뉴를 저장한다")
@Test
public void savaMenuTest() {
		//1)given:메뉴를 저장하기 위한 준비과정
		final String name = "아메리카노";
		final int price = 2000;
		final Menu americano = new Menu(name, price);
		
		//2)when:실제로 메뉴를 지정
		final long saveId = menuService.save(americano)
		
		//3)then: 메뉴가 잘 추가되었는지 검증
		final Menu saceId = menuService.findById(saveId).get();
		//검증
		assertThat(savedMenu.getName()).isEqualTo(name);
		assertThat(savedMenu.getPrice()).isEqualTo(price);
		}
```

Junit: 자바 언어를 위한 **단위 테스트 프레임워크**

단위테스트: 작성한 코드가 의도대로 작동하는지 작은 단위로 검증하는 것

Junit 특징

- 테스트 방식을 구분할 수 있는 Annotation을 제공함.
- @Test ⇒ 매서드를 호출할 때마다 새 인스턴스를 생성, 독립 테스트 가능
- 예상 결과를 검증하는 어셜션 매서드 제공
- 사용 방법이 단순, 테스트 코드 작성 시간이 적음

+_) Assert Method

```jsx
assert (조건) : "에러 메세지";
```

—> 조건을 만족하지 않는 경우, 에러를 던지고 로직을 종료한다.

Assert는 Exception과 달리 코드에 작성을 해도, .jar 파일을 실행하면  동작하지 않는다.

즉, assert 는 개발 및 디버깅을 할 때 사용할것 을 전제하로 만듬.

AssertJ

: 앞서 작성한 테스트 코드의 Assertion은 기댓값과 실제 비교값이 같은지 안같은지 확인

```jsx
assertThat(기댓값).매서드(실제비교값);
```

| 매서드이름 | 설명 |
| --- | --- |
| isEqualTo(A) | A 값과 같은지 검증 |
| isNotEqualTo(A) | A 값과 다른지 검증 |
| contains(A) | A 값을 포함하는지 검증 |
| doesNotContain(A) | A 값을 포함하지 않는지 검증 |
| startsWith(A) | 접두사가 A인지 검증 |
| endsWith(A) | 접미사가 A인지 검증 |
| isEmpty() | 비어있는 값인지 검증 |
| isNotEmpty() | 비어 있지 않은 값인지 검증 |
| isPositive() | 양수인지 검증 |