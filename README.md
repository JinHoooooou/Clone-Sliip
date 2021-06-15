# 6. 회원 가입 기능 추가

`signUpForm.html`에서 form 태그를 이용하여 input들을 받았었다. input에 정보를 입력하고 제출 버튼을 누르면 form 태그의 `action` attribute를 통해 url로 전송할 수 있다.

* 

  ```html
  <!-- resources/templates/signUpForm.html -->
  <!-- 윗부분 생략-->
  <h1>회원 가입 페이지</h1>
  <div class="container">
    <form action="/create">
      <div class="mb-3">
        <label for="sign-up-form-userId" class="form-label">사용자 아이디</label>
        <input type="text" class="form-control" id="sign-up-form-userId" name="userId">
      </div>
      <div class="mb-3">
        <label for="sign-up-form-password" class="form-label">Password</label>
        <input type="password" class="form-control" id="sign-up-form-password" name="password">
      </div>
      <div class="mb-3">
        <label for="sign-up-form-name" class="form-label">이름</label>
        <input type="password" class="form-control" id="sign-up-form-name" name="name">
      </div>
      <div class="mb-3">
        <label for="sign-up-form-email" class="form-label">이메일</label>
        <input type="password" class="form-control" id="sign-up-form-email" name="email">
      </div>
      <button type="submit" class="btn btn-primary">Submit</button>
    </form>
  <!-- 아래쪽 생략 -->
  ```

  * form 태그 안에 input 태그가 있다. input에 해당하는 데이터들을 입력하고 submit 버튼을 누르면 `localhost:8080/create` url로 해당 데이터를 전달한다.

url을 매핑하는 메서드를 Controller에서 만들고, 그 메서드 안에서 전달받은 데이터들을 출력해보자.

* ```java
  // HomeController.java
  @Controller
  public class HomeController {
    // 위쪽 생략
    
    @GetMapping("/create")
    public String printUserData(String userId, String password, String name, String email) {
      System.out.println(userId);
      System.out.println(password);
      System.out.println(name);
      System.out.println(email);
      return "index";
    }
  }
  ```

* ![image-20210611170145788](README.assets/image-20210611170145788.png)

* ![image-20210611170205003](README.assets/image-20210611170205003.png)

  * 참고로 url (`localhost:8080/create`)로 전달되는 input 태그들의 이름은 name attribute에 의해 결정된다. url과 매핑되는 메서드의 인자들 이름과 name 값과 같아야 전달 받은 데이터를 처리할 수 있다.
  * 예) name 값이 userId인데 String temp이면 안된다는 뜻.

데이터(이름, 아이디, 패스워드 등)은 전달이 됐는데 String으로 하나씩 관리하지 않고 User라는 Object를 만들어 관리하자.

* ```java
  // User.java
  @Data
  public class User {
  
    private String userId;
    private String password;
    private String name;
    private String email;
  }
  ```

회원 가입을 하면 회원 정보를 계속 가지고 있어야 로그인도 하고, 로그인 이후에 회원 정보 수정 등을 할 수 있다. 입력한 데이터를 저장하기 위해 데이터베이스에 저장을 해야한다.

1. User 데이터를 가공하고 처리하는(business logic) 클래스인 UserService 클래스를 생성한다.

   * ```java
     // UserService.java
     @Service
     public class UserService {
     
       @Autowired
       private UserRepository userRepository;
     
       public void create(User newUser) {
         userRepository.save(newUser);
       }
     }
     ```

     * 보통 UserService를 interface로 만들고 구현부인 UserServiceImpl를 새로 만들어서 처리한다고 하는데 간단한 서비스 구현이므로 불필요 하다고 판단하여 class로 만들어 사용

2. UserService 클래스에서 JPA를 이용하여 미리 연결해놓은 mysql에 user를 저장한다.

   1. JPA를 사용하기위해 UserRepository 인터페이스를 생성한다.

      * ```java
        // UserRepository.java
        import org.springframework.data.repository.CrudRepository;
        
        public interface UserRepository extends CrudRepository<User, Long> {
        
        }
        ```

        * CrudRepository의 crud만 사용할것이기 때문에 따로 메서드는 만들지 않는다.

   2. User를 데이터베이스에 저장하기위해 User 클래스를 조금 수정한다.

      * ```java
        // User.java
        @Data
        @Entity
        public class User {
        
          @Id
          @GeneratedValue
          private Long id;
        
          @Column(nullable = false, length = 15)
          private String userId;
          @Column(nullable = false, length = 10)
          private String password;
          @Column(nullable = false)
          private String name;
          private String email;
        }
        
        ```

        * PrimaryKey를 위해 id가 필요하다.
      
   3. 마지막으로 HomeController에서 UserService의 `create()`메서드를 호출한다.
   
      * ```java
        // HomeController
        @Controller
        public class HomeController {
        
          @Autowired
          private UserService userService;
        
        	// 중간 생략
        
          @GetMapping("/create")
          public String create(User user) {
            userService.create(user);
            return "index";
          }
        }
        
        ```

이제 다시 실행 후, 회원가입 페이지에서 정보를 입력하고 제출버튼을 누르면 데이터베이스에 저장이된다. 근데 에러가 발생한다. 왜냐? 미리 연결해놓은 sliip 데이터베이스에 user라는 table이 없기 때문이다. 그러므로 table을 생성한다.

* mysql에서 직접 쿼리를 작성하여 table을 생성

* 혹은 `application.yml`에서 설정

  * 
    
    ```yaml
    # application.yml
    spring:
    	jpa:
    		hibernate:
    			ddl-auto: create-drop
    ```
    
    * 어플리케이션을 실행할때마다 table을 삭제하고 다시 생성한다.
  
* ![image-20210615140100357](README.assets/image-20210615140100357.png)

  * 데이터베이스에 저장된 

이제 진짜_최종, 회원가입 페이지에서 정보를 입력하고 제출버튼을 누르면 데이터베이스에도 저장이 됐다. 근데 또 문제가 있다. 뭐냐고? 제출 버튼을 누르면  HomeController의 `create()`메서드에서 회원정보를 데이터베이스에 저장하고, `index.html`페이지로 가도록 한다.

* ![image-20210611170205003](README.assets/image-20210611170205003.png)
  * url을 보면 개인정보 노출이된다. 특히 password는 치명적임

위와 같이 url에 key - value 값이 같이 나오는 이유는 GET Method를 이용했기 때문이다. 새로운 리소스를 생성할때는 주로 POST Method를 사용한다.

* GET Method
  * 주로 데이터를 읽거나 검색할 때 사용되며, 요청할때 파라미터는 url을통해 전달된다.
* POST Method
  * 주로 새로운 리소스를 생성할 때 사용되며, 요청할때 전달할 정보들을 Request Body에 담아서 전달한다.

그러므로 `signUpForm.html`의 form 태그의 method attribute를 post로 바꾸고, HomeController의 `create()`메서드를 PostMapping으로 바꾼다.

* ![image-20210615142934245](README.assets/image-20210615142934245.png)
  * POST Method 사용 후 url



