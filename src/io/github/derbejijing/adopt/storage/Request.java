package io.github.derbejijing.adopt.storage;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import io.github.derbejijing.adopt.Main;

public class Request {
	
	String player_from;
	String player_to;
	String player_to_adopt;
	String date_time_invalid;
	
	public Request(String player_from, String player_to, String player_to_adopt) {
		this.player_from = player_from;
		this.player_to = player_to;
		this.player_to_adopt = player_to_adopt;
		
		Date date_now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date_now);
		calendar.add(Calendar.MINUTE, 2);
		this.date_time_invalid = Main.getData().sdf.format(calendar.getTime());
		
		try {
			Main.log("construct request: " + player_from + " " + player_to + " " + player_to_adopt + " " + Main.getData().sdf.parse(this.date_time_invalid).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
	public Request(String player_from, String player_to, String player_to_adopt, String time) {
		this.player_from = player_from;
		this.player_to = player_to;
		this.player_to_adopt = player_to_adopt;
		this.date_time_invalid = Main.getData().sdf.format(new Date(Long.parseLong(time)));
		Main.log("construct request: " + player_from + " " + player_to + " " + player_to_adopt + " " + time);
	}
	
	
	public String getString() {
		try {
			return "REQUEST " + this.player_from + " " + this.player_to + " " + this.player_to_adopt + " " + Main.getData().sdf.parse(this.date_time_invalid).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public boolean timeout_reached() {
		try {
			Date date_now = new Date();
			Date invalid = Main.getData().sdf.parse(this.date_time_invalid);
			if(date_now.compareTo(invalid) > 0) return true;	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
