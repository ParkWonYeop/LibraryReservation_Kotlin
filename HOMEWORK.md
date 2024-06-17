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


#### 2024-05-22
- 페이징 처리 스터디, 적용 및 포스팅 *
- Long, Int 같은 Primitve 타입은 디폴트값이 존재. 따라서, NotNull 어노테이션을 붙여도 기본값이 들어가서 validation을 건너뛰는 이슈가 있는데 이걸 수정해보기.
- 구조 분해 선언과 component 함수 스터디, 포스팅 *
- Kotlin ENUM에 ';'을 찍는 이유 스터디, 포스팅b *
- JWTFilter 50-54 line 리팩토링. return 도 하게끔. *
- database reader/writer 분리 스터디, 적용 및 포스팅 *
- ~Test에서 AuthController 의존성을 가져오지 말고, session 세팅을 fixture등으로 해결할 것. *


#### 2024-05-27
- PageRequest 를 사용해서 페이징 요청을 받도록 개선하고 CustomPageRequest를 직접 만들어서 적용해볼것. *
    - CustomPageRequest란?
        - sort를 id, name, email 등등으로 지정할 수 있을텐데 클라이언트에서 별도로 지정하지 않아도 default 값으로 세팅되도록.
        - pageNumber, size 모두 위와 같이 작업.
- Primitive 타입은 defualut value가 적용되서 nullable 처리하면 service layer logic 지저분해진다. 이를 해결할 수 있는 방법을 찾아볼것. *
- componentN 몇까지 생성되는지 알아보기 *
- apply, let, run, also, with 차이 스터디 후 포스팅 *
- drop table, truncate 차이를 성능에 근거하여 포스팅 *
- 매 테스트 코드가 돌 때 마다 sql 돌 때 auto_increment 초기화하는 방법 강구해보기. *
- 코드 통일성 지킬것. 꼼꼼함을 기를것.

#### 2024-06-03
- jpa.hibernate.ddl-auto 옵션들 스터디 후 포스팅 *
- NotBlank 어노테이션을 걸었는데, 기본값으로 ""(empty) 세팅하는 로직 리팩토링할것. *
- BaseEntity 를 만들고, 모든 entity들이 BaseEntity를 상속받게끔 수정 *
    - BaseEntity 는 createdAt, updatedAt, deletedAt 을 넣기.
- soft delete / hard delete 스터디 후 포스팅 *
- RuntimeExceptionHandler 리팩토링 *
- 프로젝트 전수 검사 후 리팩토링 이어서 진행할것. *
- JWTUtil 예외처리 로직쪽 반드시 리팩토링할것. *

#### 2024-06-10
- @PreUpdate 공부해보기. *
- baseEntity.kt 네이밍 수정할것. *
- main에 jpa 관련 어노테이션 붙여놓은거 제거할것. *
- 다음 프로젝트 난이도 높아 보이는것 고민해보기.
    - spring boot + kotlin + gradle + jpa(query DSL)
