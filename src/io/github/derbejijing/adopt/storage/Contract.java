package io.github.derbejijing.adopt.storage;

import io.github.derbejijing.adopt.Main;

public class Contract {

	String name;
	String owner_1;
	String owner_2;
	
	public Contract(String name, String owner_1, String owner_2) {
		this.name = name;
		this.owner_1 = owner_1;
		this.owner_2 = owner_2;
		
		Main.log("construct contract: " + name + " " + owner_1 + " " + owner_2);
	}
	
	public String getString() {
		return "CONTRACT " + name + " " + owner_1 + " " + owner_2;
	}
	
}
