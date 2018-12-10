package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.CalibrationDao;
import model.Calibration;

class CalibrationTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetCalibration() {
		Calibration calibration1 = new CalibrationDao().get(1);
		//assertEquals(27, calibration1.getMbq(), "Fel calibration");
		fail("Not yet implemented");
	}

}
