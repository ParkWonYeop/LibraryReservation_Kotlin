## HOMEWORK.md
### 멘토링을 진행하면서 공부해야할 내용을 정리하는 문서입니다.

#### 2024-05-07
- kotlin 코드의 장점은, 생산성이 아주 훌륭하다는 것. *
- 위 장점을 살릴 수 있도록 프로젝트 전체적으로 리팩토링 해볼 것. like AuthContorller *
- AuthService에서 생성자에 필요 디펜던시를 주입해주는것과, Autowired 어노테이션을 사용하는 것의 차이 스터디 후 포스팅
- wildcard import 하지 않도록 프로젝트 세팅할것. *
- ktlintformat 적용할 것. *
- entity를 data class로 선언하는 것과 class로 선언하는 것의 차이 스터디 후 포스팅 *
- N+1 쿼리 스터디 후 포스팅
- mutable, immutable 스터디 *
- optional과 nullable 차이 스터디 후 포스팅 *
- UserEntity::class.java / UserEntity::class 차이 스터디

#### 2024-05-13
- 생성자안에 들어가는 bean들 private 붙일것. *
- @Value 어노테이션도 생성자 안에서 선언할 것. *
- RuntimeException 은 진짜 예기치 못한 에러가 발생할 경우를 대비해서 따로 처리할것. *
- mac에서 ./gradlew 명령어 실행할 수 없는 현상 수정
- N+1 쿼리 다시 스터디, lazy loading 추가 스터디 *

