package spike;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.CalibrationDao;
import dao.RoomDao;
import dao.SubstanceDao;
import dao.SupplierDao;
import dao.UserDao;
import view.Main_method;

public class mainTest {
	public static void main(String[] args) throws ParseException {

		Main_method mm = new Main_method();
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date startdate = simpleDateFormat.parse("2018-10-30 20:26");
		Date enddate = simpleDateFormat.parse("2019-10-30 20:26");
		Date arrivalDate = simpleDateFormat.parse("2019-11-02 10:26"); 
		/*mm.addRadiopharmaceutical("Ultra-Technekow (Tc-99m Generator", 68.0, startdate, enddate, "övrigt", 
				arrivalDate, "12345", "OK", new SubstanceDao().get(9), new UserDao().get(1), new CalibrationDao().get(2), 
				new RoomDao().get(1), new SupplierDao().get(1));*/
		mm.getRadiopharmaceutical(1);
		//mm.getCalibration(1);
		//mm.getRoom(1);
	}
}
