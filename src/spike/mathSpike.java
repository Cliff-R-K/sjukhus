package spike;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import controller.MathController;
import dao.CalibrationDao;
import dao.RadiopharmaceuticalDao;
import dao.RoomDao;
import dao.UserDao;
import model.RegRadio;

public class mathSpike {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");
		LocalDateTime startDate = LocalDateTime.parse("2018 12 14 19 30",formatter);

		System.out.println("startDate: "+startDate);

		RegRadio radio = new RegRadio(1000.0, startDate, null, "hittat", "OK", new RadiopharmaceuticalDao().get(2), new RoomDao().get(1),new UserDao().get(1),new CalibrationDao().get(1)); 

		MathController mCon = new MathController();
		
		
		System.out.println("New activity: "+mCon.execute(radio));
		System.out.println("Old activity: "+radio.getStartActivity());
		System.out.println("startactivity: "+radio.getStartActivity());
		
		System.out.println("Halflife: "+radio.getRadiopharmaceutical().getSubstance().getHalfLife());
		System.out.println("Halflife: "+radio.getRadiopharmaceutical().getSubstance().getName());
	}

}
