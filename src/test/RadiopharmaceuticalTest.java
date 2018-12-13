package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.RadiopharmaceuticalDao;
import model.Radiopharmaceutical;

class RadiopharmaceuticalTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		Radiopharmaceutical radio = new RadiopharmaceuticalDao().get(8);
		assertEquals("Xofigo", radio.getRadiopharmaceuticalName(), "Fel Läkemedel");
	}
}
