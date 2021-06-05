# 정리

1. 개발 환경
   * Java8
   * IntelliJ 2020.01
   * Spring Boot 2.5.0
   * MySQL
   * Thymeleaf

2. MySql 연동

프로젝트 생성 후 실행하면 다음과 같은 에러 메시지가 나온다.

 * ![image-20210605165008470](README.assets/image-20210605165008470.png)

H2 데이터베이스를 사용할 경우 자동으로 설정을 잡아주기 때문에 별도로 설정을 입력할 필요가 없지만 MySQL을 사용할 경우  `src/main/resources`의 `application.properties` 또는 `application.yml` 에 설정을 입력해야한다.

* 

  ``` yaml
  spring:
  	datasource:
  		driver-class-name: com.mysql.cj.jdbc.Driver
  		url: jdbc:mysql://localhost:3306/{db_name}?serverTimezone=UTC&characterEncoding=UTF-8
  		username: {db_user_name}
  		password: {db_user_password}
  ```

  * driver-class-name : `com.mysql.jdbc.Driver`와 `com.mysql.cj.jdbc.Driver`가 있는데 전자는 deprecated라고 해서 후자를 사용
  * url
    * {db_name}은 mysql에 생성한 database 이름을 작성
    * serverTimezone=UTC를 작성해야 에러가 발생하지 않는다.
  * username / password : mysql 계정이름과 비밀번호를 작성한다.

설정 입력후 다시 실행을 하면 정상적으로 실행이된다.