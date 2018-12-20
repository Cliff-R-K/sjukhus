package spike;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import dao.RadiopharmaceuticalDao;
import dao.RoomDao;
import dao.SubstanceDao;
import dao.SupplierDao;
import dao.UserDao;
import model.User;
import view.Main_method;

public class mainTest {
	public static void main(String[] args) throws ParseException {

		Main_method mm = new Main_method();
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date startdate = simpleDateFormat.parse("2018-10-30 20:26");
		Date enddate = simpleDateFormat.parse("2019-10-30 20:26");
		Date arrivalDate = simpleDateFormat.parse("2019-11-02 10:26");
		String[] params = new String[1];
		params[0]="Knark";
		//mm.updateUser(new User(1,"MB"), new String[] {"signature"});
		//mm.addRadiopharmaceutical("Knark", "Tabletter", new SubstanceDao().get(1), new SupplierDao().get(1));
		//mm.addRoom("04-02-303", "NM Förråd");
		/*mm.addRadiopharmaceutical("I-131 Standard källa", 192.96, startdate, "Lösning", 
				arrivalDate, "påhittad", "OK", new SubstanceDao().get(7), new UserDao().get(1), new CalibrationDao().get(2), 
				new RoomDao().get(1), new SupplierDao().get(5));*/
		//mm.getAllRadiopharmaceutical();
		//mm.getRadiopharmaceutical(8);
		//mm.getCalibration(1);
		//mm.getRoom(1);
//		mm.addRegRadio(1000.0, startdate, arrivalDate, "hittat", "OK", new RadiopharmaceuticalDao().get(2), new RoomDao().get(1),new UserDao().get(1),new CalibrationDao().get(1)); 
//				new RoomDao().get(1), new UserDao().get(1), new CalibrationDao().get(1));
//		mm.getAllRegRadio();
//		mm.getRegRadio(4).getSupplier().print();
	}
}
