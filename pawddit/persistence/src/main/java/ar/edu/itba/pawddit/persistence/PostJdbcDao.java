package ar.edu.itba.pawddit.persistence;

import java.sql.Timestamp;
import java.util.HashMap;
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
public class PostJdbcDao implements PostDao{

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Post> ROW_MAPPER = (rs, rowNum) ->
	new Post(rs.getString("content"), rs.getTimestamp("creationDate"), 
			new Group(rs.getString("name"), rs.getTimestamp("groups.creationDate"), rs.getString("description"), new User(rs.getString("users2.username"), rs.getString("users2.password"), rs.getString("users2.email"), rs.getInt("users2.score"), rs.getInt("users2.userId"))), 
			new User(rs.getString("users1.username"), rs.getString("users1.password"), rs.getString("users1.email"), rs.getInt("users1.score"), rs.getInt("users1.userId")), 
			rs.getInt("postId"));
	
	@Autowired
	public PostJdbcDao(final DataSource ds) {
			jdbcTemplate = new JdbcTemplate(ds);
			jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("posts")
					.usingGeneratedKeyColumns("postId");
	}
	
	@Override
	public Optional<Post> findById(long id) {
		return jdbcTemplate.query("SELECT * FROM posts" + 
				" INNER JOIN users users1 ON users1.idUser = posts.idUser" + 
				" INNER JOIN groups ON posts.groupName = groups.name" + 
				" INNER JOIN users users2 ON groups.userId = users2.userId WHERE idPost = ?"
				, ROW_MAPPER, id).stream().findFirst();
	}

	@Override
	public Post create(String content, Timestamp date, Group group, User user) {
		final Map<String, Object> args = new HashMap<>();
		args.put("content", content); 
		args.put("creationDate", date);
		args.put("groupName", group.getName());
		args.put("userId", user.getUserid());
		final Number postId = jdbcInsert.executeAndReturnKey(args);
		return new Post(content, date, group, user, postId.longValue());
	}


	
}
