# 정리

## 1. 개발 환경
   * Java8
   * IntelliJ 2020.01
   * Spring Boot 2.5.0
   * MySQL
   * Thymeleaf

## 2. MySql 연동

프로젝트 생성 후 실행하면 다음과 같은 에러 메시지가 나온다.

 * ![image-20210605165008470](README.assets/image-20210605165008470.png)

H2 데이터베이스를 사용할 경우 자동으로 설정을 잡아주기 때문에 별도로 설정을 입력할 필요가 없지만 MySQL을 사용할 경우  `src/main/resources`의 `application.properties` 또는 `application.yml` 에 설정을 입력해야한다.

* ``` yaml
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



## 3. Hello World

`src/main/resources/static`에 `index.html`을 생성한다.

* ```html
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Main Page</title>
  </head>
  <body>
  <p> Hello World!</p>
  </body>
  </html>
  ```

실행하고 `https://localhost:8080`에 접속하면 다음과 같이 `index.html`페이지가 실행된다.

* ![image-20210605172030955](README.assets/image-20210605172030955.png)



## 4. static & templates, Controller, Thymeleaf

spring boot에서 프로젝트를 생성하면 기본적으로 `src/main/resources`에 두개의 directory가 있다. `static`과 `templates`

* `static` css나 js등 정적 컨텐츠를 담는다.
* `templates`에는 thymeleaf관련 컨텐츠를 담는다.
* [공식 문서 참고](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-spring-mvc-static-content)

`static ` directory 기본 설정에서 url이 없으면 index.html으로 인식한다. 그래서 `localhost:8080`이나 `localhost:8080/index.html`둘 다 같은 page를 반환한다.

* ![image-20210605172020071](README.assets/image-20210605172020071.png)
* ![image-20210605172030955](README.assets/image-20210605172030955.png)
  * 둘 다 같은 page임!



정적 페이지가 아닌 동적 페이지를 출력하기 위해서는 `templates`에서 작성해야한다. 그리고 spring boot에서 `templates`에 있는 html파일을 출력하기 위해 Controller가 필요하다.

* ```java
  // HomeController
  
  @Controller
  public class HomeController {
    
    public String home() {
      return "index";
    }
  }
  ```

* ```html
  <!-- src/resources/templates/index.html -->
  <!DOCTYPE html>
  <html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>Main Page</title>
  </head>
  <body>
  <h1> I'm in templates!!</h1>
  </body>
  </html>
  ```

* ![image-20210605182246595](README.assets/image-20210605182246595.png)

사용자(browser)에서 요청한 주소 (`localhost:8080/{url}`) 👉 Spring Boot의 Controller에서 `{url}`에 매핑되는 메서드를 찾는다. 👉 메서드에서 반환하는 String이 `templates`에 있는 html파일명 👉 해당 파일을 사용자가 볼 수 있게 전달한다.

* ```java
  // HomeController
  @Controller
  public class HomeController {
  
    @GetMapping("/home")
    public String home() {
      return "index";
    }
  }
  ```

  

* ![image-20210605180325917](README.assets/image-20210605180325917.png)

  * `localhost:8080/home`의 `home`에 매핑되는 메서드 `String home()` 👉 `home()`이 반환하는 `index`를 `src/resources/templates`에서 찾아 사용자에게 보여준다.

`static`과 `templates`차이를 하나 더 보면 `static`의 경우 url에 `.html`까지 붙여야하지만 `templates`은 기본적으로 html에 매핑되기때문에 붙이지않는다.

