package ar.edu.itba.pawddit.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.User;

@Repository
public class SubscriptionJdbcDao implements SubscriptionDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Integer> ROW_MAPPER = (rs, rowNum) ->
		new Integer(rs.getInt(1));
	
	
	@Autowired
	public SubscriptionJdbcDao(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("subscriptions");
	}
	
	@Override
	public void suscribe(final User user, final Group group) {
		final Map<String, Object> args = new HashMap<>();
		args.put("userid", user.getUserid());
		args.put("groupname", group.getName());
		jdbcInsert.execute(args);
	}

	@Override
	public void unsuscribe(final User user, final Group group) {
		String query = "DELETE FROM SUBSCRIPTIONS WHERE userid = ? AND groupName = ?";
		jdbcTemplate.update(query, user.getUserid(), group.getName());
	}

	@Override
	public Boolean isUserSub(final User user, final Group group) {
		List<Integer> ls = jdbcTemplate.query("SELECT count(*) FROM SUBSCRIPTIONS WHERE userid = ? AND groupname = ?", 
				ROW_MAPPER, user.getUserid(), group.getName());
		return (ls.get(0) == 1) ? true : false;
	}

	@Override
	public int countSuscribed(User user) {
		List<Integer> ls = jdbcTemplate.query("SELECT count(*) FROM SUBSCRIPTIONS WHERE userid = ?", ROW_MAPPER, user.getUserid());
		return ls.get(0);
	}

}
