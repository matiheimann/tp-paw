package ar.edu.itba.pawddit.persistence;

import ar.edu.itba.pawddit.model.User;

public interface UserDao {
	public User findById(int id);
}
