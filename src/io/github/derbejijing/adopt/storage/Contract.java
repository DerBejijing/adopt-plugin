package io.github.derbejijing.adopt.storage;

public class Contract {

	String name;
	String owner_1;
	String owner_2;
	
	public Contract(String name, String owner_1, String owner_2) {
		this.name = name;
		this.owner_1 = owner_1;
		this.owner_2 = owner_2;
	}
	
	public String getString() {
		return "CONTRACT " + name + " " + owner_1 + " " + owner_2;
	}
	
}
