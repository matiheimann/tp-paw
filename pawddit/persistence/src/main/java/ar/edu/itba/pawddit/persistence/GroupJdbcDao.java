package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;


import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.services.UserService;

@Repository
public class GroupJdbcDao {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private static UserService us;
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Group> ROW_MAPPER = (rs, rowNum) ->
		new Group(rs.getString("name"), rs.getTimestamp("date"),
				rs.getString("description"), us.findById(rs.getInt("owner")).get());
	
	@Autowired
	public GroupJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("group");
	}
	
	public Optional<Group> findByName(String name){
		return jdbcTemplate.query("SELECT * FROM group WHERE name = ?", ROW_MAPPER, name).stream().findFirst();
	}
	
	public Group create(String name, Timestamp date, String description, long owner) {
		final Map<String, Object> args = new HashMap<>();
		args.put("name", name);
		args.put("date", date);
		args.put("description", description);
		args.put("owner", owner);
		jdbcInsert.executeAndReturnKey(args);
		return new Group(name, date, description, us.findById(owner).get()); 
	}
	
}
