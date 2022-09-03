package io.github.derbejijing.adopt.storage;

import java.util.Calendar;
import java.util.Date;

import io.github.derbejijing.adopt.Main;

public class ChildRaisePhase {

	String name;
	String owner_1;
	String owner_2;
	String date_time_grownup;
	long start;
	long end;
	Date grownup;
	
	
	public ChildRaisePhase(String child, String owner_1, String owner_2) {
		this.name = child;
		this.owner_1 = owner_1;
		this.owner_2 = owner_2;
		
		Date date_now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date_now);
		calendar.add(Calendar.SECOND, 60);
		grownup = calendar.getTime();
		
		start = date_now.getTime();
		end = grownup.getTime();
		
		this.date_time_grownup = Main.getData().sdf.format(calendar.getTime());
		
		Main.log("construct crp: " + child + " " + owner_1 + " " + owner_2 + " " + start + " " + end);
	}
	
	
	public ChildRaisePhase(String child, String owner_1, String owner_2, String start, String end) {
		this.name = child;
		this.owner_1 = owner_1;
		this.owner_2 = owner_2;
		this.start = Long.parseLong(start);
		this.end = Long.parseLong(end);
		this.grownup = new Date(this.end);
		this.date_time_grownup = Main.getData().sdf.format(this.grownup);
		
		Main.log("construct crp: " + child + " " + owner_1 + " " + owner_2 + " " + start + " " + end);
	}
	
	
	public String getString() {
		return "RAISE " + this.name + " " + this.owner_1 + " " + this.owner_2 + " " + this.start + " " + this.end;
	}
	
	
	public Date getGrownupDate() {
		return this.grownup;
	}
	
	
	public double getProgress() {
		double progress = ((double)new Date().getTime() - this.start) / ((double)this.end - this.start);
		return (progress <= 1) ? progress : 1;
	}
	
	
	public boolean grownup() {
		Date date_now = new Date();
		if(date_now.compareTo(this.grownup) > 0) return true;		
		return false;
	}
	
}
