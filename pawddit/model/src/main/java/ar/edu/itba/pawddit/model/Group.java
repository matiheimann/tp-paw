package ar.edu.itba.pawddit.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "groups")
public class Group {
	
	@Id
	@Column(name = "name", length = 32, nullable = false)
	private String name;
	
	@Column(name = "creationdate", nullable = false)
	private LocalDateTime date;
	
	@Column(name = "description", length = 1000, nullable = false)
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "owner")
	private User owner;
	
	@Transient
	private int suscriptors;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "group")
	private Set<Post> posts = new HashSet<Post>();
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinTable(
	        name = "subscriptions", 
	        joinColumns = { @JoinColumn(name = "groupname") },
	        inverseJoinColumns = { @JoinColumn(name = "userid") }
	    )
	private Set<User> subscribedUsers = new HashSet<User>();
	
	/* package */ Group() {
		// Just for Hibernate, we love you!
	}
	
	public Group(final String name, final LocalDateTime date, final String description, final User owner) {
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
	
	public LocalDateTime getDate() {
		return this.date;
	}
	
	public void setDate(LocalDateTime date) {
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

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<User> getSubscribedUsers() {
		return subscribedUsers;
	}

	public void setSubscribedUsers(Set<User> subscribedUsers) {
		this.subscribedUsers = subscribedUsers;
	}

}
