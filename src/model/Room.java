package model;

public class Room {
 private int id;
 private String description;
 private String roomCode;

 public Room(String info, String roomCode) {
	this.roomCode = roomCode;
	this.description = info;
	
}
 
public Room(int id, String info, String roomCode) {
	this.id = id;
	this.roomCode = roomCode;
	this.description = info;
} 

public int getId() {
	return id;
}
 
public String getRoomCode() {
	return roomCode;
}

public void setRoomCode(String roomCode) {
	this.roomCode = roomCode;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public void print() {
	System.out.println("ID: " + getId() + "\tRoomCode: " + getRoomCode() + "\tDescriptions: " + getDescription());
}
@Override
	public String toString() {
		return this.description;
	}
 	
}
