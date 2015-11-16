package hello;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;


import user.dao.UserDAO;
import user.model.User;

@RestController
public class UserController {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module.xml");
	private static final UserDAO userDAO = (UserDAO) context.getBean("userDAO");
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST) 
	public ResponseEntity<User> loginUser(
			@RequestParam("username") String username,
			@RequestParam("password") String password) 
	{
		User user = userDAO.findByUsername(username);
		HttpHeaders responseHeaders = new HttpHeaders();
		
		if(user != null) {
			if (user.getPassword().equals(password)) // 로그인 성공
				return new ResponseEntity<User>(user, responseHeaders, HttpStatus.OK);
			else // 로그인 실패 (입력한 password 다) 
				return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
		} else { // 로그인 실패 (입력한 username을 가진 사용자없음)
			return new ResponseEntity<User>(null, responseHeaders, HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/user/signup", method = RequestMethod.POST)
	public User signupUser(
			@RequestParam("username") String username,
			@RequestParam("password") String password)
	{
		User user = userDAO.signupUser(new User(0, username, password, 0));
		return user;
	}
}
