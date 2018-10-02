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
						rs.getBoolean("admin"),
						rs.getBoolean("enabled"),
						rs.getInt("userid")
				), 
				rs.getInt("postid"),
				rs.getInt("comments"),
				rs.getInt("votes"),
				rs.getString("imageid")
		);
		
	private final static RowMapper<Integer> COUNT_MAPPER = (rs, rowNum) -> rs.getInt(1);

	
	@Autowired
	public PostJdbcDao(final DataSource ds) {
			jdbcTemplate = new JdbcTemplate(ds);
			jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("posts")
					.usingGeneratedKeyColumns("postid");
	}

	@Override
	public Post create(final String title, final String content, final Timestamp date, final Group group, final User user, final String imageId) {
		final Map<String, Object> args = new HashMap<>();
		args.put("title", title); 
		args.put("content", content); 
		args.put("creationdate", date);
		args.put("groupname", group.getName());
		args.put("userid", user.getUserid());
		args.put("imageid", imageId);
		final Number postId = jdbcInsert.executeAndReturnKey(args);
		return new Post(title, content, date, group, user, postId.longValue(), 0, 0, imageId);
	}
	
	@Override
	public List<Post> findAll(final int limit, final int offset, final String sort) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, coalesce(ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)), 0) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "imageid, enabled, users.admin as admin, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"GROUP BY posts.postid , enabled, users.admin, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname " +
				getOrderBySort(sort) +
				"LIMIT ? OFFSET ? ", ROW_MAPPER, limit, offset);
	}

	@Override
	public List<Post> findByGroup(final Group group, final int limit, final int offset, final String sort) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, coalesce(ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)), 0) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "imageid, enabled, users.admin as admin, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " + " WHERE groupname = ?" +
				"GROUP BY posts.postid , enabled, users.admin, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname " +
				getOrderBySort(sort) +
				"LIMIT ? OFFSET ?", ROW_MAPPER, group.getName(), limit, offset);
	}

	@Override
	public List<Post> findByUser(final User user, final int limit, final int offset, final String sort) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, coalesce(ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)), 0) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "imageid, enabled, users.admin as admin, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"WHERE users.userid = ? " +
				"GROUP BY posts.postid , enabled, users.admin, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname " +
				getOrderBySort(sort) +
				"LIMIT ? OFFSET ?", ROW_MAPPER, user.getUserid(), limit, offset);
	}
	
	@Override
	public Optional<Post> findById(final Group group, final long id) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, coalesce(ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)), 0) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "imageid, enabled, users.admin as admin, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				" WHERE posts.postid = ? AND posts.groupname = ?" + 
				"GROUP BY posts.postid , enabled, users.admin, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname", ROW_MAPPER, id, group.getName()).stream().findFirst();
	}
	
	@Override
	public List<Post> findBySubscriptions(final User user, final int limit, final int offset, final String sort) {
		return jdbcTemplate.query("SELECT count(DISTINCT commentId) AS comments, coalesce(ceil(cast(sum(valuevote) AS decimal)/cast((CASE WHEN count(DISTINCT commentId) <> 0 THEN count(DISTINCT commentId) ELSE 1 END) AS decimal)), 0) AS votes, title, posts.content AS content, posts.creationdate as creationdate, groupname, posts.postid as postid, "
				+ "imageid, enabled, users.admin as admin, password, email, username, users.userid as userid FROM posts " + 
				"INNER JOIN users ON users.userId = posts.userId " + 
				"INNER JOIN groups ON groups.name = posts.groupname " + 
				"FULL OUTER JOIN comments ON comments.postid = posts.postid " +
				"FULL OUTER JOIN voteposts ON voteposts.postid = posts.postid " +
				"WHERE EXISTS (SELECT posts.postid from subscriptions WHERE userId = ? and posts.groupname LIKE subscriptions.groupname) " +
				"GROUP BY posts.postid , enabled, users.admin, users.password, users.userid, username, email, title, posts.content, posts.creationdate, groupname " +
				getOrderBySort(sort) +
				"LIMIT ? OFFSET ?", ROW_MAPPER, user.getUserid(), limit, offset);
	}

	@Override
	public int findAllCount() {
		return jdbcTemplate.query("SELECT count(1) FROM posts", COUNT_MAPPER).get(0);
	}

	@Override
	public int findByGroupCount(final Group group) {
		return jdbcTemplate.query("SELECT count(1) FROM posts WHERE groupname = ?", COUNT_MAPPER, group.getName()).get(0);
	}

	@Override
	public int findByUserCount(final User user) {
		return jdbcTemplate.query("SELECT count(1) FROM posts WHERE users.userid = ?", COUNT_MAPPER, user.getUserid()).get(0);
	}

	@Override
	public int findBySubscriptionsCount(final User user) {
		return jdbcTemplate.query("SELECT count(1) FROM posts WHERE EXISTS (SELECT posts.postid from subscriptions WHERE userId = ? and posts.groupname LIKE subscriptions.groupname)", COUNT_MAPPER, user.getUserid()).get(0);
	}
	
	private String getOrderBySort(final String sort) {
		if (sort == null)
			return "ORDER BY posts.creationdate DESC ";
		
		if (sort.equals("top"))
			return "ORDER BY votes DESC ";
		
		return "ORDER BY posts.creationdate DESC ";
	}

	@Override
	public int deleteById(final Group group, final long id) {
		return jdbcTemplate.update("DELETE FROM posts WHERE groupname = ? AND postid = ?", group.getName(), id);
	}
	
}
