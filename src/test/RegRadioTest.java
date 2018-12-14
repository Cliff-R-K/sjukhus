package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dao.RegRadioDao;
import dao.UserDao;
import model.RegRadio;

class RegRadioTest {
	private String batchNumber;
	private int id;
	RegRadioDao regradiodao = new RegRadioDao();
	private String[] params = new String[1];

	@BeforeEach
	void setUp() throws Exception {
		batchNumber = "hittat";
		id = 1;
		params[0] = "batchnumber";
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
		assertEquals(batchNumber, regRadio.getBatchNumber(), "fel batchnumber");
		regradiodao.update(new RegRadio(1, new RegRadioDao().get(1).getStartActivity(),new RegRadioDao().get(1).getStartDate(), 
				new RegRadioDao().get(1).getArrivalDate(), "uppdaterat", new RegRadioDao().get(1).getContaminationControll(),
				new RegRadioDao().get(1).getRadiopharmaceutical(), new RegRadioDao().get(1).getRoom(), new UserDao().get(1), 
				new RegRadioDao().get(1).getCalibration()), params);
		assertEquals("uppdaterat", regradiodao.get(1).getBatchNumber()," fel batchnumber");
		regradiodao.update(new RegRadio(1, new RegRadioDao().get(1).getStartActivity(),new RegRadioDao().get(1).getStartDate(), 
				new RegRadioDao().get(1).getArrivalDate(), "hittat", new RegRadioDao().get(1).getContaminationControll(),
				new RegRadioDao().get(1).getRadiopharmaceutical(), new RegRadioDao().get(1).getRoom(), new UserDao().get(1), 
				new RegRadioDao().get(1).getCalibration()), params);
		assertEquals(batchNumber, regRadio.getBatchNumber(), "fel batchnumber");

	}
}
