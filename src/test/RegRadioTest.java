package test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.MathController;
import dao.RegRadioDao;
import dao.UserDao;
import model.RegRadio;

class RegRadioTest {
	private String batchNumber;
	private int id;

	RegRadioDao regradiodao = new RegRadioDao();
	private String[] params = new String[1];
	MathController math = new MathController();

	@BeforeEach
	void setUp() throws Exception {
		batchNumber = "hittat";
		id = 1;
		params[0] = "calibration_activity";
		
	}

	@AfterEach
	void tearDown() throws Exception {
		batchNumber = null;
		id = 0;
		params = null;
	}

	@Test
	void test() {
		RegRadio regRadio = new RegRadioDao().get(1);
		assertEquals(id, regRadio.getId(),"fel id");
		int ids = 1;
		Double activity = math.execute(new RegRadioDao().get(ids));
		//assertEquals(batchNumber, regRadio.getBatchNumber(), "fel batchnumber");
		
		regradiodao.update(new RegRadio(ids, new RegRadioDao().get(ids).getStartActivity(),new RegRadioDao().get(ids).getStartDate(), 
				new RegRadioDao().get(ids).getArrivalDate(), "uppdaterat", new RegRadioDao().get(ids).getContaminationControll(),
				new RegRadioDao().get(ids).getRadiopharmaceutical(), new RegRadioDao().get(ids).getRoom(), new UserDao().get(ids), new RegRadioDao().get(ids).getCalibrationActivity() , new RegRadioDao().get(ids).getCalibrationDate()), params);
		/*assertEquals("uppdaterat", regradiodao.get(1).getBatchNumber()," fel batchnumber");
		regradiodao.update(new RegRadio(1, new RegRadioDao().get(1).getStartActivity(),new RegRadioDao().get(1).getStartDate(), 
				new RegRadioDao().get(1).getArrivalDate(), "hittat", new RegRadioDao().get(1).getContaminationControll(),
				new RegRadioDao().get(1).getRadiopharmaceutical(), new RegRadioDao().get(1).getRoom(), new UserDao().get(1)), params);
		assertEquals(batchNumber, regRadio.getBatchNumber(), "fel batchnumber");*/
		//regradiodao.save(new RegRadio(101.1, LocalDateTime.now(), new RegRadioDao().get(1).getArrivalDate(), "126371463", "OK",new RegRadioDao().get(1).getRadiopharmaceutical(), new RegRadioDao().get(1).getRoom(), new UserDao().get(1)));

	}
}
