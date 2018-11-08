package ar.edu.itba.pawddit.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	@Column(name = "username", length = 100, nullable = false, unique = true)
	private String username;
	
	@Column(name = "password", length = 100, nullable = false)
	private String password;
	
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(name = "admin", nullable = false)
	private boolean isAdmin;
	
	@Column(name = "enabled", nullable = false)
	private boolean enabled;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_userid_seq")
	@SequenceGenerator(sequenceName = "users_userid_seq", name = "users_userid_seq", allocationSize = 1)
	@Column(name = "userid")
	private long userid;

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "owner")
	private Set<Group> createdGroups = new HashSet<Group>();
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "owner")
	private Set<Post> createdPosts = new HashSet<Post>();
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "owner")
	private Set<Comment> createdComments = new HashSet<Comment>();
	
	@OneToOne(fetch = FetchType.LAZY, optional = false, mappedBy = "user")
	private VerificationToken verificationToken;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinTable(
        name = "subscriptions", 
        joinColumns = { @JoinColumn(name = "userid") },
        inverseJoinColumns = { @JoinColumn(name = "groupname") }
    )
	private Set<Group> subscribedGroups = new HashSet<Group>();
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "pk.user")
	private Set<VotePost> votesPosts = new HashSet<VotePost>();
	
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "pk.user")
	private Set<VoteComment> votesComments = new HashSet<VoteComment>();
	
	/* package */ User() {
		// Just for Hibernate, we love you!
	}
	
	public User(final String username, final String password, final String email, final boolean isAdmin, final boolean enabled) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.isAdmin = isAdmin;
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public Set<Group> getCreatedGroups() {
		return createdGroups;
	}

	public void setCreatedGroups(Set<Group> createdGroups) {
		this.createdGroups = createdGroups;
	}

	public Set<Post> getCreatedPosts() {
		return createdPosts;
	}

	public void setCreatedPosts(Set<Post> createdPosts) {
		this.createdPosts = createdPosts;
	}

	public Set<Comment> getCreatedComments() {
		return createdComments;
	}

	public void setCreatedComments(Set<Comment> createdComments) {
		this.createdComments = createdComments;
	}

	public VerificationToken getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}

	public Set<Group> getSubscribedGroups() {
		return subscribedGroups;
	}

	public void setSubscribedGroups(Set<Group> subscribedGroups) {
		this.subscribedGroups = subscribedGroups;
	}

	public Set<VotePost> getVotesPosts() {
		return votesPosts;
	}

	public void setVotesPosts(Set<VotePost> votesPosts) {
		this.votesPosts = votesPosts;
	}

	public Set<VoteComment> getVotesComments() {
		return votesComments;
	}

	public void setVotesComments(Set<VoteComment> votesComments) {
		this.votesComments = votesComments;
	}
	
	public void addSubscribedGroup(Group group) {
		this.subscribedGroups.add(group);
	}
	
	public void removeSubscribedGroup(Group group) {
		this.subscribedGroups.remove(group);
	}
	
}
