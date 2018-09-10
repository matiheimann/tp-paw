package ar.edu.itba.pawddit.model;

import java.sql.Timestamp;

public class Group {
	
	private User owner;
	private String name;
	private Timestamp date;
	private String description;
	private int suscriptors;
	
	public Group(String name, Timestamp date, String description, User owner, int suscriptors) {
		this.name = name;
		this.date = date;
		this.description = description;
		this.owner = owner;
		this.suscriptors = suscriptors;
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

	public int getSuscriptors() {
		return suscriptors;
	}

	public void setSuscriptors(int suscriptors) {
		this.suscriptors = suscriptors;
	}
}
