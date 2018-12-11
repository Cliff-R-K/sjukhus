package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.RegRadioDao;
import model.RegRadio;

class RegRadioTest {
	private String batchNumber;
	private int id;
	

	@BeforeEach
	void setUp() throws Exception {
		batchNumber = "hittat";
		id = 2;
		
	}

	@AfterEach
	void tearDown() throws Exception {
		batchNumber = null;
		id = 0;
	}

	@Test
	void test() {
		RegRadio regRadio = new RegRadioDao().get(2);
		assertEquals(id, regRadio.getId(),"fel id");
		assertEquals(batchNumber, regRadio.getBatchNumber(), "fel batchnumber");
	}
}
