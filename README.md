
# Sample Spring Webflux Reactive Pattern Project
: 간단한 커뮤니티 구조의 백엔드 API 서버를 구현했습니다.(Webflux패턴 기반)

참고로 완성본이 아니고 아직 작업할게 많이 남아있습니다.(수정중)

## Background
- 포트폴리오가 전부 상업용 프로젝트이기도 하고 백엔드 관련한 개인적인 repo가 충분하지 않았다.
- 개인적으로 만들고 있는 프로젝트(InteractiveERD)에서 백엔드를 뭘로 할지 고민을 하고 있었다.
- 지인중 한 분이 본인 팀에서 Spring Webflux를 사용한다고 하길래 트랜드인가 싶어 익숙한 kotlin으로 공부해보면 좋겠다는 생각이 들었다.(옷은 트랜드를 안 따라가더라도 프로그래밍은 못참지)

## Introduction

### ERD 
![image](https://user-images.githubusercontent.com/37768791/192204542-0954ebb6-f386-4acf-9fd1-19338bbfc79b.png)
### Description
- `Kotlin` 언어 사용
- `Webflux` 패턴 채택
- `함수형` 프로그래밍 적용
- `Postgresql` 드라이버 사용
- 논블로킹을 위해 JPA가 아닌 `R2DBC` 채택
- 클라우드는 `GCP`를 사용(연동하는 코드는 없으니 굳이 필요없는 정보이긴함.)



## References

#### R2DBC 사용법 파악 및 아키텍처 파악
- URL : https://github.com/piomin/sample-spring-data-webflux
- URL : https://www.vinsguru.com/spring-data-r2dbc/
- URL : https://github.com/hantsy/spring-r2dbc-sample
- URL : https://medium.com/bliblidotcom-techblog/reactive-spring-boot-application-with-r2dbc-and-postgresql-849fc7811135
- URL : https://github.com/kakaohairshop/spring-r2dbc-study

#### application.yaml 사용법
- URL : https://docs.spring.io/spring-boot/docs/1.5.6.RELEASE/reference/html/boot-features-external-config.html

#### profile별 빌드 및 실행방법
- URL : https://1minute-before6pm.tistory.com/12

#### Route 코드 리팩토링
- URL : https://github.com/spring-projects/spring-framework/issues/28603

#### Webflux 에러 핸들링 
- URL : https://www.baeldung.com/spring-webflux-errors

#### Webflux 폴더구조 참고용
- URL : https://github.com/kamalhm/spring-boot-r2dbc/tree/master/src/main/java/com/khm/reactivepostgres

