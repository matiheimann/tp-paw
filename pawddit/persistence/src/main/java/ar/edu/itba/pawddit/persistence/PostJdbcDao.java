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

import ar.edu.itba.pawddit.model.Post;
import ar.edu.itba.pawddit.services.UserService;

@Repository
public class PostJdbcDao implements PostDao{

	@Autowired
	private static UserService us;
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Post> ROW_MAPPER = (rs, rowNum) ->
	new Post(rs.getString("content"), rs.getTimestamp("creationDate"), rs.getString("groupName"), 
			us.findById(rs.getInt("user")).get(), rs.getInt("postId"));
	
	@Autowired
	public PostJdbcDao(final DataSource ds) {
			jdbcTemplate = new JdbcTemplate(ds);
			jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("post")
					.usingGeneratedKeyColumns("id");
	}
	
	@Override
	public Optional<Post> findById(long id) {
		return jdbcTemplate.query("SELECT * FROM post WHERE id = ?", ROW_MAPPER, id).stream().findFirst();
	}

	@Override
	public Post create(String content, Timestamp date, String group, long user) {
		final Map<String, Object> args = new HashMap<>();
		args.put("content", content); // la key es el nombre de la columna
		args.put("creationDate", date);
		args.put("groupName", group);
		args.put("user", user);
		final Number postId = jdbcInsert.executeAndReturnKey(args);
		return new Post(content, date, group, us.findById(user).get(), postId.longValue());
	}


	
}
