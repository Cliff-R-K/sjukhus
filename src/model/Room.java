package model;

public class Room {
 private String id;
 private String description;

 public Room(String id, String info) {
	this.id = id;
	this.description = info;
}


public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getDescription() {
	return description;
}

public void setDescription(String beskrivning) {
	this.description = beskrivning;
}

public void print() {
	System.out.println("ID: " + getId() + "\tDescriptions: " + getDescription());
}

 	
}
