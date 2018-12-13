package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import model.User;

class UserTest {
	UserDao userdao = new UserDao();
	private String[] params = new String[1];
	@BeforeEach
	void setUp() throws Exception {
		params[0]="signature";
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetUser() {
		User user1 = new UserDao().get(1);
		assertEquals("MB", user1.getSignature(), "Fel signar för MB");
		userdao.update(new User(1, "KS"), params);
		user1 = userdao.get(1);
		assertEquals("KS", user1.getSignature(), "Fel signar för KS");
		userdao.update(new User(1, "MB"), params);
		user1 = userdao.get(1);
		assertEquals("MB", user1.getSignature(), "Fel signar för MB");
	}
}
