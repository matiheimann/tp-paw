package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.HashMap;
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
public class GroupJdbcDao implements GroupDao{
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Group> ROW_MAPPER = (rs, rowNum) ->
		new Group(rs.getString("name"), rs.getTimestamp("creationDate"), rs.getString("description"), 
				new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getInt("score"), rs.getInt("userId")));
	
	@Autowired
	public GroupJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("groups");
	}
	
	@Override
	public Optional<Group> findByName(String name){
		return jdbcTemplate.query("SELECT * FROM groups INNER JOIN users ON groups.owner = users.userId WHERE name = ?", ROW_MAPPER, name).stream().findFirst();
	}
	
	@Override
	public Group create(String name, Timestamp date, String description, User owner) {
		final Map<String, Object> args = new HashMap<>();
		args.put("name", name);
		args.put("creationDate", date);
		args.put("description", description);
		args.put("owner", owner.getUserid());
		jdbcInsert.execute(args);
		return new Group(name, date, description, owner); 
	}
	
}
