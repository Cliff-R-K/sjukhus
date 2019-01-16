package spike;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import controller.MathController;
import dao.RadiopharmaceuticalDao;
import dao.RegRadioDao;
import dao.RoomDao;
import dao.UserDao;
import model.RegRadio;

public class mathSpike {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");
		LocalDateTime startDate = LocalDateTime.parse("2019 01 08 10 20",formatter);
		String pattern = "yyyy-MM-dd HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date arrivalDate = simpleDateFormat.parse("2018-12-17 10:26");

		System.out.println("startDate: "+startDate);
		RegRadio radio = new RegRadio(1000.0, startDate, arrivalDate, "hittat", "OK", new RadiopharmaceuticalDao().get(1), new RoomDao().get(1),new UserDao().get(1), new RegRadioDao().get(1).getCalibrationActivity(),new RegRadioDao().get(1).getCalibrationDate()); 

		MathController mCon = new MathController();
		
		
		Double calcActivity;
		calcActivity=mCon.execute(radio);
		System.out.println("New activity: "+calcActivity);
		System.out.println("Old activity: "+radio.getStartActivity());
		System.out.println("startactivity: "+radio.getStartActivity());
		
		System.out.println("Halflife: "+radio.getRadiopharmaceutical().getSubstance().getHalfLife());
		System.out.println("Halflife: "+radio.getRadiopharmaceutical().getSubstance().getName());
	}

}
