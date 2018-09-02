package ar.edu.itba.pawddit.model;

import java.sql.Timestamp;

public class Group {
	
	private User owner;
	private String name;
	private Timestamp date;
	private String description;
	
	public Group(String name, Timestamp date, String description, User owner) {
		this.name = name;
		this.date = date;
		this.description = description;
		this.owner = owner;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Timestamp getDate() {
		return this.date;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
