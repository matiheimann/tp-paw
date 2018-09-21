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
public class SubscriptionJdbcDao implements SubscriptionDao{

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Integer> ROW_MAPPER = (rs, rowNum) ->
		new Integer(rs.getInt("count"));
	
	
	@Autowired
	public SubscriptionJdbcDao(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("subscriptions")
				.usingGeneratedKeyColumns("subscriptionid");
	}
	
	@Override
	public Number suscribe(User user, Group group) {
		final Map<String, Object> args = new HashMap<>();
		args.put("userid", user.getUserid());
		args.put("groupname", group.getName());
		return jdbcInsert.executeAndReturnKey(args);
	}

	@Override
	public int unsuscribe(User user, Group group) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int checkIfItsSuscribed(User user, Group group) {
		List<Integer> ls = jdbcTemplate.query("SELECT count(*) FROM SUBSCRIPTIONS WHERE userid = ? AND groupname = ?", 
				ROW_MAPPER, user.getUserid(), group.getName());
		return ls.get(0);
	}

}
