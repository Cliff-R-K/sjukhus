package spike;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.CalibrationDao;
import model.Calibration;

public class CalibrationTest {
public static void main(String[] args) throws ParseException {
	CalibrationDao cd = new CalibrationDao();
	
//	String pattern = "yyyy-MM-dd HH:mm";
//	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//	Date date = simpleDateFormat.parse("2018-10-30 20:26");
//	
//	cd.save(new Calibration(date, 26.75));
	cd.delete(new Calibration(3));
}
}
