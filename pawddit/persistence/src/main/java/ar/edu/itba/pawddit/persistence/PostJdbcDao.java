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

import ar.edu.itba.pawddit.model.Group;
import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.model.User;

@Repository
public class PostJdbcDao implements PostDao {

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<Post> ROW_MAPPER = (rs, rowNum) ->
	new Post(rs.getString("title"), rs.getString("content"), rs.getTimestamp("creationdate"), new Group(rs.getString("groupname"), null, null, null, 0), new User(rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getInt("score"), rs.getInt("userid")), rs.getInt("postid"));
	
	@Autowired
	public PostJdbcDao(final DataSource ds) {
			jdbcTemplate = new JdbcTemplate(ds);
			jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("posts")
					.usingGeneratedKeyColumns("postid");
	}

	@Override
	public Post create(final String title, final String content, final Timestamp date, final Group group, final User user) {
		final Map<String, Object> args = new HashMap<>();
		args.put("title", title); 
		args.put("content", content); 
		args.put("creationdate", date);
		args.put("groupname", group.getName());
		args.put("userid", user.getUserid());
		final Number postId = jdbcInsert.executeAndReturnKey(args);
		return new Post(title, content, date, group, user, postId.longValue());
	}
	
	@Override
	public List<Post> findAll() {
		return jdbcTemplate.query("SELECT * FROM posts JOIN users ON posts.userid = users.userid", ROW_MAPPER);
	}

	@Override
	public List<Post> findByGroup(final Group group) {
		return jdbcTemplate.query("SELECT * FROM posts JOIN users ON posts.userid = users.userid WHERE groupname = ?", ROW_MAPPER, group.getName());
	}

	@Override
	public List<Post> findByUser(final User user) {
		return jdbcTemplate.query("SELECT * FROM posts JOIN users ON posts.userid = ?", ROW_MAPPER, user.getUserid());
	}
	
	@Override
	public Optional<Post> findById(final long id) {
		return jdbcTemplate.query("SELECT * FROM posts JOIN users ON posts.userid = users.userid WHERE postid = ?", ROW_MAPPER, id).stream().findFirst();
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user) {
		return jdbcTemplate.query("SELECT * FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"WHERE EXISTS (SELECT postid from subscriptions WHERE userId = ? and posts.groupname LIKE subscriptions.groupname)", ROW_MAPPER, user.getUserid());
	}
	
}
