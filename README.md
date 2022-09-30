
# Sample Spring Webflux Reactive Pattern Project
: 간단한 커뮤니티 구조의 백엔드 API 서버를 구현했습니다.(Webflux패턴 기반)

: Simple Social network stuff Structure based Back-end API server using Spring Webflux pattern.

## Background
- 개인 포트폴리오가 전부 상업용 프로젝트이기도 하고 백엔드 관련한 개인적인 repo가 충분하지 않았다.(My portfolio is full of commercial projects that cannot show to others. so tried to make public one)
- 개인적으로 만들고 있는 프로젝트(InteractiveERD)에서 백엔드를 뭘로 할지 고민을 하고 있었다.(Need to select proper backend language and frameworks to be used in my private projects which named 'InteractiveERD')
- 지인중 한 분이 본인 팀에서 Spring Webflux를 사용한다고 하길래 트랜드인가 싶어 익숙한 kotlin으로 공부해보면 좋겠다는 생각이 들었다.(옷은 트랜드를 안 따라가더라도 프로그래밍은 못참지)(Some of my X co-worker told me that Spring Webflux pattern used in IT major company in these days. so tried to study this pattern for catching up with IT trends for fun.)
- reactive 패턴은 이전에 rxdart로 질리도록 해봐서 재밌을거라 생각했다.(I get used to reactive pattern because used rxdart in other company for making flutter app.)

## Issues
: Found out some issues during making and learning from Webflux pattern project. 

### About Webflux `Reactor pattern`
1. Reactor pattern(using Mono, Flux) can reduce code readability.(callback hell like  before async/await pattern popped out in Node.js) 
  - Resolved by using `kotlin coroutine`.(persist non-blocking)

### About `R2DBC`
1. It's not an ORM(should write raw query and mapper in some cases)
2. We use R2DBC because of blocking issue when using JPA(cause blocking) instead.
   - Could not take advantage of confidence when using JPA.

## Introduction

### ERD 
![image](https://user-images.githubusercontent.com/37768791/192972062-0a38529b-c326-4b6f-9917-4b6a547b481c.png)
### Tech Stacks
- `Kotlin` 언어 사용(이전에 안드로이드 개발을 kotlin으로도 했고 꽤나 열심히 언어공부를 했던 언어이기에 선택함.)
- `gradle` 빌드 방식 적용(안드로이드에 익숙함)
- `Webflux` 패턴 채택
- `함수형(Functional)` 프로그래밍 적용
- `Postgresql` driver 사용(원래는 mysql이 익숙한데 tokenizing때문에 postgresql을 공부하기로 맘먹음)
- 논블로킹을 위해 JPA가 아닌 `R2DBC` 채택
- 클라우드는 `GCP`를 사용 for file upload

### Description(feat.예정된 작업)
- [x] 관계 테이블 커스텀 쿼리 적용
- [x] kotlin coroutine 적용(코드 가독성을 위해)~~
- [ ] R2DBC OneToMany, OneToOne, ManyToMany mapping 적용
- [x] 로깅 적용(logging)
- [x] 클라우드 저장소 이미지 파일 업로드
  - [ ] ISSUE > 8MB 이상 파일 업로드시 inputStream 데이터 비는 현상 개선
- [x] 서버 에러 핸들링(error handling)
- [ ] Authguard 적용(간단한 정도의 인증/인가 미들웨어 구현) (apply auth middleware)
- [ ] 테스트 코드 작성(test code)
- [ ] 배포 자동화 CI/CD (profile 환경별 빌드 및 배포, build and deploy by each profile)
- ~~(microservice는 손이 너무 많이 가서 일단 보류...)~~

## Get Started!
### Environment(yml) Setup
: `application.yaml` 파일을 .gitignore 처리를 했지만 아래와 같이 적으면 문제 없이 실행할 수 있습니다. 혹시나 다른 데이터베이스 드라이버를 사용하시는 경우라면 datasource파일 및 gradle을 수정할 필요가 있습니다.
```yml
# src/main/resources/application.yaml
spring:
  config:
    activate:
      on-profile: local
  server:
    port: 8000
  r2dbc:
    protocol: r2dbc:postgresql
    host: localhost
    port: 5432
    username: postgres
    password: postgres
    database: postgres
  storage:
    cred-path: <YOUR CONFIG CREDENTIAL FILE PATH>
    project-id: <YOUR GCP STORAGE PROJECT ID>
    bucket-name: <YOUR GCP STORAGE BUCKET NAME>
```

## References
: 공부하면서 참고한 링크는 아래에 모두 기록할 예정입니다. 프로젝트 코드보다 개념이 필요하신분들은 아래 링크들에 잘 기록되어있을테니 참고하시면 되겠습니다.(주의 : 개념이 없는 링크들도 있을수도 있음.)

(Warning: it's almost Korean blog links. )

#### R2DBC 사용법 파악 및 아키텍처 파악(R2DBC use-cases and architectures)
- URL : https://github.com/piomin/sample-spring-data-webflux
- URL : https://www.vinsguru.com/spring-data-r2dbc/
- URL : https://github.com/hantsy/spring-r2dbc-sample
- URL : https://medium.com/bliblidotcom-techblog/reactive-spring-boot-application-with-r2dbc-and-postgresql-849fc7811135
- URL : https://github.com/kakaohairshop/spring-r2dbc-study

#### application.yaml 사용법 (application.yaml use-cases)
- URL : https://docs.spring.io/spring-boot/docs/1.5.6.RELEASE/reference/html/boot-features-external-config.html

#### profile별 빌드 및 실행방법 (How to set up profile for building and running spring boot project)
- URL : https://1minute-before6pm.tistory.com/12

#### Route 코드 리팩토링 (RouteConfig refactoring)
- URL : https://github.com/spring-projects/spring-framework/issues/28603

#### Webflux 에러 핸들링 (Webflux server error handling conventions)
- URL : https://www.baeldung.com/spring-webflux-errors

#### Webflux 폴더구조 참고용 (Webflux projects directory structure)
- URL : https://github.com/kamalhm/spring-boot-r2dbc/tree/master/src/main/java/com/khm/reactivepostgres

#### R2DBC에서 관계 테이블 구현 (Implements relations when using R2DBC)
(공식적으로 r2dbc는 관계relation를 지원하지 않는다.)

지원하고 있지 않다는 이슈
- URL : https://github.com/spring-projects/spring-data-r2dbc/issues/99
- URL : https://github.com/spring-projects/spring-data-r2dbc/issues/352

관계 매핑 구현방법
- URL : https://tolkiana.com/introduction-to-spring-data-r2dbc-with-kotlin/
- URL : https://heesutory.tistory.com/33
- URL : https://heesutory.tistory.com/35
  - Lombok의 @Builder 대신 kotlin으로 해결(URL : https://levelup.gitconnected.com/kotlin-makes-lombok-obsolete-9ed3318596cb, https://stackoverflow.com/questions/36140791/how-to-implement-builder-pattern-in-kotlin)
- URL : https://binux.tistory.com/156

#### 코루틴 적용하는 법(코루틴 도입배경) (Apply Kotlin coroutine in spring server)
- URL : https://techblog.woowahan.com/7349/
- URL : https://appleg1226.tistory.com/16
- URL(coRouter 구현) : https://veluxer62.github.io/tutorials/spring-web-flux-functional-endpoint-api/
- URL : https://umbum.dev/1047

#### Repository DatabaseClient Transaction 적용 
- URL : https://spring.io/blog/2019/05/16/reactive-transactions-with-spring

#### Logger Setup
- URL : https://jsonobject.tistory.com/500
- URL : https://www.reddit.com/r/Kotlin/comments/8gbiul/slf4j_loggers_in_3_ways/