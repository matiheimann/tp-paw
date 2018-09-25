package ar.edu.itba.pawddit.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.persistence.UserDao;
import ar.edu.itba.pawddit.model.User;

@Repository
public class UserJdbcDao implements UserDao {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) ->
		new User(
				rs.getString("username"),
				rs.getString("password"), 
				rs.getString("email"), 
				rs.getInt("score"), 
				rs.getInt("userid")
		);
	
	@Autowired
	public UserJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("users")
				.usingGeneratedKeyColumns("userid");
	}
	
	@Override
	public Optional<User> findById(final long id) {
		return jdbcTemplate.query("SELECT * FROM users WHERE userid = ?", ROW_MAPPER, id).stream().findFirst();
	}

	@Override
	public User create(final String username, final String password, final String email, int score) {
		final Map<String, Object> args = new HashMap<>();
		args.put("username", username); // la key es el nombre de la columna
		args.put("password", password);
		args.put("email", email);
		args.put("score", score);
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		return new User(username, password, email, score, userId.longValue());
	}

	@Override
	public Optional<User> findByUsername(final String username) {
		return jdbcTemplate.query("SELECT * FROM users WHERE username = ?", ROW_MAPPER, username).stream().findFirst();
	}

	@Override
	public Optional<User> findByEmail(final String email) {
		return jdbcTemplate.query("SELECT * FROM users WHERE email = ?", ROW_MAPPER, email).stream().findFirst();
	}
}
