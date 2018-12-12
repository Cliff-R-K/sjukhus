package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.UserDao;
import model.User;

class UserTest {
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetUser() {
		User user1 = new UserDao().get(1);
		User user2 = new UserDao().get(2);
		assertEquals("MB", user1.getSignature(), "Fel signar för MB");
		assertEquals("SO", user2.getSignature(), "Fel signarur från SO");
	}
}
