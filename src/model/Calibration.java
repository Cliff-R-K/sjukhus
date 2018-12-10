package model;

import java.util.Date;

public class Calibration {

 private int id;
 private Date date;
 private Double mbq;
 
public Calibration(int id, Date date, Double mbq) {
	this.id = id;
	this.date = date;
	this.mbq = mbq;
}
public Calibration(Date date, Double mbq) {
	this.date = date;
	this.mbq = mbq;
}
public Calibration(int id) {
	this.id = id;
}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getMbq() {
		return mbq;
	}

	public void setMbq(Double mbq) {
		this.mbq = mbq;
	}

	public void print() {
		System.out.println("ID:	" + getId() + "\tDate:	" + getDate() + "\tMbq:	" + getMbq());

	}
}
