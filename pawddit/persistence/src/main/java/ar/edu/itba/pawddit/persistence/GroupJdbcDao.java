package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;


import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

@Repository
public class GroupJdbcDao implements GroupDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Group> ROW_MAPPER = (rs, rowNum) ->
		new Group(
				rs.getString("name"),
				rs.getTimestamp("creationdate"),
				rs.getString("description"), 
				new User(
						rs.getString("username"), 
						rs.getString("email"), 
						rs.getString("password"), 
						rs.getInt("score"),
						rs.getBoolean("enabled"),
						rs.getInt("owner")
				), 
				rs.getInt("followers")
		);
		
	private final static RowMapper<Group> SEARCH_MAPPER = (rs, rowNum) ->
		new Group(
				rs.getString("name"),
				null,
				null, 
				null,
				null
		);
		
		private final static RowMapper<Group> INTEREST_MAPPER = (rs, rowNum) ->
		new Group(
				rs.getString("name"),
				null,
				null, 
				null,
				rs.getInt("subs")
		);
	
	@Autowired
	public GroupJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("groups");
	}
	
	@Override
	public Optional<Group> findByName(final String name){
		return jdbcTemplate.query("SELECT name, username, email, password, score, enabled, creationdate, description, owner, count(DISTINCT subscriptions.userid) as followers FROM groups JOIN users ON groups.owner = users.userid FULL OUTER JOIN subscriptions ON groups.name = subscriptions.groupname" +  
				" WHERE name = ? GROUP BY name, username, email, password, score, enabled", ROW_MAPPER, name).stream().findFirst();
	}
	
	@Override
	public Group create(final String name, final Timestamp date, final String description, final User owner) {
		final Map<String, Object> args = new HashMap<>();
		args.put("name", name);
		args.put("creationdate", date);
		args.put("description", description);
		args.put("owner", owner.getUserid());
		jdbcInsert.execute(args);
		return new Group(name, date, description, owner, 0); 
	}
	
	@Override
	public List<Group> findAll() {
		return jdbcTemplate.query("SELECT name, username, email, password, score, enabled, creationdate, description, owner, count(DISTINCT subscriptions.userid) as followers FROM groups JOIN users ON groups.owner = users.userid FULL OUTER JOIN subscriptions ON groups.name = subscriptions.groupname " + 
				"GROUP BY name, username, email, password, score, enabled", ROW_MAPPER);
	}

	@Override
	public List<Group> getSuscribed(User user) {
		return jdbcTemplate.query("SELECT name, username, email, password, score, enabled, creationdate, description, owner, count(DISTINCT subscriptions.userid) as followers FROM groups JOIN users ON groups.owner = users.userid FULL OUTER JOIN subscriptions ON groups.name = subscriptions.groupname " + 
				" WHERE subscriptions.userid = ? "
				+ "GROUP BY name, username, email, password, score, enabled", ROW_MAPPER, user.getUserid());
	}

	@Override
	public int deleteByName(String name) {
		return jdbcTemplate.update("DELETE FROM groups WHERE name = ?", name);
	}

	@Override
	public List<Group> searchByName(String name) {
		return jdbcTemplate.query("SELECT name FROM groups WHERE upper(name) LIKE upper(?) LIMIT 5", SEARCH_MAPPER, "%" + name + "%");
	}

	@Override
	public List<Group> searchByInterest(User user) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT count(DISTINCT userid) AS subs, name FROM groups ")
			.append("INNER JOIN subscriptions ON groups.name = subscriptions.groupname ")
			.append("WHERE name NOT IN (select groupname from subscriptions where userid = ?) ")
			.append("AND userid IN (select userid from subscriptions where userid <> ? AND groupname IN (select groupname from subscriptions where userid = ?)) ")
			.append("GROUP BY name ")
			.append("ORDER BY count(DISTINCT userid) DESC ");
		return jdbcTemplate.query(query.toString(), INTEREST_MAPPER, user.getUserid(), user.getUserid(), user.getUserid());
	}
	
	
	
}
