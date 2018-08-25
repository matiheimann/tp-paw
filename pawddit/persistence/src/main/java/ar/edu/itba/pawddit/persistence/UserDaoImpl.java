package ar.edu.itba.pawddit.persistence;

import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.persistence.UserDao;
import ar.edu.itba.pawddit.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
