package io.github.derbejijing.adopt.storage;

import io.github.derbejijing.adopt.Main;

public class Grownup {

	String name;
	String owner_1;
	String owner_2;
	
	public Grownup(String name, String owner_1, String owner_2) {
		this.name = name;
		this.owner_1 = owner_1;
		this.owner_2 = owner_2;
		
		Main.log("construct grownup: " + name + " " + owner_1 + " " + owner_2);
	}
	
	public String getString() {
		return "GROWNUP " + name + " " + owner_1 + " " + owner_2;
	}
	
}
