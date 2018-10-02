package ar.edu.itba.pawddit.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@Repository
public class PostVoteJdbcDao implements PostVoteDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Integer> ROW_MAPPER = (rs, rowNum) ->
	new Integer(rs.getInt(1));
	
	@Autowired
	public PostVoteJdbcDao(final DataSource ds) {
			jdbcTemplate = new JdbcTemplate(ds);
			jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("voteposts");
	}

	@Override
	public int changeVote(final User user, final Post post) {
		String query = "UPDATE voteposts SET valuevote = valuevote * -1 WHERE userid = ? AND postid = ?";
		return jdbcTemplate.update(query, user.getUserid(), post.getPostid());
	}

	@Override
	public int cancelVote(final User user, final Post post) {
		String query = "DELETE FROM voteposts WHERE userid = ? AND postid = ?";
		return jdbcTemplate.update(query, user.getUserid(), post.getPostid());
	}

	@Override
	public Boolean votePost(final User user, final Post post, final Integer value) {
		Map<String, Object> args = new HashMap<>();
		args.put("userid", user.getUserid());
		args.put("postid", post.getPostid());
		args.put("valuevote", value);
		if(checkVote(user, post) != 0) {
			return false;
		}
		jdbcInsert.execute(args);
		return true;
	}

	@Override
	public Integer checkVote(final User user, final Post post) {
		return jdbcTemplate.query("SELECT COALESCE(sum(valuevote), 0) FROM voteposts WHERE userid = ? AND postid = ?", ROW_MAPPER, 
				user.getUserid(), post.getPostid()).get(0);
	}

}
