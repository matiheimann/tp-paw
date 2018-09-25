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
		new Post(
				rs.getString("title"), 
				rs.getString("content"), 
				rs.getTimestamp("creationdate"), 
				new Group(
						rs.getString("groupname"), 
						null, 
						null, 
						null, 
						0
				), 
				new User(
						rs.getString("username"), 
						rs.getString("email"), 
						rs.getString("password"), 
						rs.getInt("score"), 
						rs.getInt("userid")
				), 
				rs.getInt("postid"),
				rs.getInt("comments"),
				rs.getInt("votes")
		);
	
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
		return new Post(title, content, date, group, user, postId.longValue(), 0, 0);
	}
	
	@Override
	public List<Post> findAll() {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "users.score as score, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"GROUP BY posts.postid , users.score, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname", ROW_MAPPER);
	}

	@Override
	public List<Post> findByGroup(final Group group) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "users.score as score, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " + " WHERE groupname = ?" +
				"GROUP BY posts.postid , users.score, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname", ROW_MAPPER, group.getName());
	}

	@Override
	public List<Post> findByUser(final User user) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "users.score as score, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"WHERE users.userid = ? " +
				"GROUP BY posts.postid , users.score, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname", ROW_MAPPER, user.getUserid());
	}
	
	@Override
	public Optional<Post> findById(final Group group, final long id) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "users.score as score, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				" WHERE posts.postid = ? AND posts.groupname = ?" + 
				"GROUP BY posts.postid , users.score, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname", ROW_MAPPER, id, group.getName()).stream().findFirst();
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "users.score as score, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"WHERE EXISTS (SELECT posts.postid from subscriptions WHERE userId = ? and posts.groupname LIKE subscriptions.groupname) " +
				"GROUP BY posts.postid , users.score, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname", ROW_MAPPER, user.getUserid());
	}
	
}
