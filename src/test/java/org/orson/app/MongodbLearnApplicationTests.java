package org.orson.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.orson.app.controller.LoginController;
import org.orson.app.dao.UserDao;
import org.orson.app.dao.UserDaoImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbLearnApplicationTests {

	private UserDao dao;

	@Before
	public void init() {
		dao  = mock(UserDao.class);
	}

	@Test
	public void test_login_failed() {
		LoginController controller = new LoginController(this.dao);


		when(dao.auth(anyString(), anyString())).thenReturn(Boolean.FALSE);

		String result = controller.login("orson", "123456");

		System.out.println("---: " + result);

		Assert.assertEquals("expect logon failed", "authentication failed.", result);

	}

	@Test
	public void test_login_success() {
		LoginController controller = new LoginController(this.dao);

		when(dao.auth("orson", "12345")).thenReturn(Boolean.TRUE);

		String result = controller.login("orson", "12345");

		System.out.println("---: " + result);
		Assert.assertEquals("expect logon success", "login successfully.", result);
	}

	@Test
	public void test_register() {
		UserDaoImpl dao = new UserDaoImpl();
		Boolean result = dao.register("orson", "123456");

		Boolean auth = dao.auth("orson", "123456");

		Assert.assertTrue("register failed", auth);

	}

}

