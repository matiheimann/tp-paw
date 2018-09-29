package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@Repository
public class CommentJdbcDao implements CommentDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<Comment> ROW_MAPPER = (rs, rowNum) ->
		new Comment(
				rs.getString("content"),
				new Post(
						null, 
						null, 
						null, 
						null, 
						null, 
						rs.getInt("postid"),
						0,
						0,
						null
				),
				null,
				new User(
						rs.getString("username"), 
						rs.getString("email"), 
						rs.getString("password"), 
						rs.getInt("score"), 
						rs.getBoolean("enabled"),
						rs.getInt("userid")
				),
				rs.getTimestamp("creationdate"),
				rs.getInt("commentid")
		);
		
	private final static RowMapper<Integer> COUNT_MAPPER = (rs, rowNum) -> rs.getInt(1);
	
	@Autowired
	public CommentJdbcDao(final DataSource ds) {
			jdbcTemplate = new JdbcTemplate(ds);
			jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("comments")
					.usingGeneratedKeyColumns("commentid");
	}

	@Override
	public Comment create(final String content, final Post post, final Comment replyTo, final User user, final Timestamp creationDate) {
		final Map<String, Object> args = new HashMap<>();
		args.put("content", content); 
		args.put("replyTo", null);
		args.put("postid", post.getPostid());
		args.put("userid", user.getUserid());
		args.put("creationdate", creationDate);
		final Number commentId = jdbcInsert.executeAndReturnKey(args);
		return new Comment(content, post, replyTo, user, creationDate, commentId.longValue());
	}

	@Override
	public List<Comment> findByUser(final User user, final int limit, final int offset) {
		return jdbcTemplate.query("SELECT * FROM comments JOIN users ON comments.userid = ? ORDER BY comments.creationdate DESC LIMIT ? OFFSET ?", ROW_MAPPER, user.getUserid(), limit, offset);
	}

	@Override
	public List<Comment> findByPost(final Post post, final int limit, final int offset) {
		return jdbcTemplate.query("SELECT * FROM comments JOIN users ON comments.userid = users.userid WHERE postid = ? ORDER BY comments.creationdate DESC LIMIT ? OFFSET ?", ROW_MAPPER, post.getPostid(), limit, offset);
	}

	@Override
	public Optional<Comment> findById(final long id) {
		return jdbcTemplate.query("SELECT * FROM comments JOIN users ON comments.userid = users.userid WHERE commentid = ?", ROW_MAPPER, id).stream().findFirst();
	}

	@Override
	public int findByUserCount(User user) {
		return jdbcTemplate.query("SELECT count(1) FROM comments WHERE userid = ?", COUNT_MAPPER, user.getUserid()).get(0);
	}

	@Override
	public int findByPostCount(Post post) {
		return jdbcTemplate.query("SELECT count(1) FROM comments WHERE postid = ?", COUNT_MAPPER, post.getPostid()).get(0);
	}
	
	
	
}
