package controller;

import java.time.LocalDateTime;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import model.RegRadio;

public class MathController {


	private long timeDifference;
	private Double startActivity;
	private Double halfLife;
	private Double result;
	private long seconds;
	private LocalDateTime startDate;


	public MathController() {


	}

	public Double execute(RegRadio radio) {


		this.startActivity=radio.getStartActivity();
		this.halfLife=radio.getRadiopharmaceutical().getSubstance().getHalfLife();

		this.startDate=radio.getStartDate();

		timeDifference=getDifferenceInDates();
		System.out.println("time difference: " +timeDifference);
		result=calculate(timeDifference);
		return result;
	}

	private long getDifferenceInDates() {


		TimeZone tz = TimeZone.getTimeZone("Europe/Rome");
		TimeZone.setDefault(tz);



		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm");

		LocalDateTime currentDate = (LocalDateTime.now());
		String formatCurrentDate = currentDate.format(formatter);


		String formatStartDate = startDate.format(formatter);

		final LocalDateTime firstDate = LocalDateTime.parse(formatCurrentDate, formatter);
		final LocalDateTime secondDate = LocalDateTime.parse(formatStartDate, formatter);
		System.out.println(firstDate);
		System.out.println(secondDate);
		seconds = (ChronoUnit.SECONDS.between(firstDate, secondDate));
		final long minutes = (ChronoUnit.MINUTES.between(firstDate, secondDate));
		final long hours = (ChronoUnit.HOURS.between(firstDate, secondDate));
		final long days = (ChronoUnit.DAYS.between(firstDate, secondDate));

		System.out.println("Seconds between: " + seconds);
		System.out.println("minutes between: " + minutes);
		System.out.println("hours between: " + hours);
		System.out.println("days between: " + days);

		return minutes;
		
		//y=1000*e^((ln(2)/66)*-x)
	}
	private double calculate(long time) {

		double activity;
		activity = startActivity*(Math.exp(((Math.log(2)/halfLife)*(time/60))));

		//((Math.log(2)/halfLife)*Math.abs(time))
		System.out.println(activity);
		return activity;
	}
}
