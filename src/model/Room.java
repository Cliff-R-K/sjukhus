package model;

public class Room {
 private int id;
 private String description;
 private String roomCode;

 public Room(String roomCode, String info) {
	this.roomCode = roomCode;
	this.description = info;
}
 
public Room(int id, String roomCode, String info) {
	this.id = id;
	this.roomCode = roomCode;
	this.description = info;
} 

public int getId() {
	return id;
}
 
public String getRoomId() {
	return roomCode;
}

public void setRoomId(String roomCode) {
	this.roomCode = roomCode;
}

public String getDescription() {
	return description;
}

public void setDescription(String beskrivning) {
	this.description = beskrivning;
}

public void print() {
	System.out.println("ID: " + getId() + "\tRoomCode: " + getRoomId() + "\tDescriptions: " + getDescription());
}

 	
}
