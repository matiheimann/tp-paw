package ar.edu.itba.pawddit.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.persistence.UserDao;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.model.VerificationToken;

@Repository
public class UserJdbcDao implements UserDao {
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsertUsers;
	private final SimpleJdbcInsert jdbcInsertTokens;
	private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) ->
		new User(
				rs.getString("username"),
				rs.getString("password"), 
				rs.getString("email"), 
				rs.getInt("score"), 
				rs.getBoolean("enabled"),
				rs.getInt("userid")
		);
	private final static RowMapper<VerificationToken> ROW_MAPPER_TOKEN = (rs, rowNum) ->
		new VerificationToken(
				rs.getString("token"),
				new User(
					rs.getString("username"),
					rs.getString("password"), 
					rs.getString("email"), 
					rs.getInt("score"), 
					rs.getBoolean("enabled"),
					rs.getInt("userid")
				)
		);
	
	@Autowired
	public UserJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsertUsers = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("users")
				.usingGeneratedKeyColumns("userid");
		jdbcInsertTokens = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("verificationtokens")
				.usingGeneratedKeyColumns("tokenid");
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
		args.put("enabled", false);
		final Number userId = jdbcInsertUsers.executeAndReturnKey(args);
		return new User(username, password, email, score, false, userId.longValue());
	}

	@Override
	public Optional<User> findByUsername(final String username) {
		return jdbcTemplate.query("SELECT * FROM users WHERE username = ?", ROW_MAPPER, username).stream().findFirst();
	}

	@Override
	public Optional<User> findByEmail(final String email) {
		return jdbcTemplate.query("SELECT * FROM users WHERE email = ?", ROW_MAPPER, email).stream().findFirst();
	}

	@Override
	public VerificationToken createToken(final User user) {
		final String token = UUID.randomUUID().toString();
		final Map<String, Object> args = new HashMap<>();
		args.put("userid", user.getUserid());
		args.put("token", token);
		jdbcInsertTokens.execute(args);
		return new VerificationToken(token, user);
	}

	@Override
	public Optional<VerificationToken> findToken(final String token) {
		return jdbcTemplate.query("SELECT * FROM verificationtokens JOIN users ON users.userid = verificationtokens.userid WHERE token = ?", ROW_MAPPER_TOKEN, token).stream().findFirst();
	}

	@Override
	public int enableUser(final User user) {
		return jdbcTemplate.update("UPDATE users SET enabled = true WHERE userid = ?", user.getUserid());
	}
}
