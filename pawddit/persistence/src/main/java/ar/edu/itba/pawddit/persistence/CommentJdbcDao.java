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

import ar.edu.itba.pawddit.model.Comment;
import ar.edu.itba.pawddit.model.User;
import ar.edu.itba.pawddit.services.CommentService;
import ar.edu.itba.pawddit.services.PostService;
import ar.edu.itba.pawddit.services.UserService;

@Repository
public class CommentJdbcDao implements CommentDao{
	
	@Autowired
	private static UserService us;
	
	@Autowired
	private static PostService ps;
	
	@Autowired
	private static CommentService cs;
	
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	private final static RowMapper<Comment> ROW_MAPPER = (rs, rowNum) -> 
		new Comment(rs.getString("comment"), ps.getPostById(rs.getInt("postId")).get(), cs.getCommentById(rs.getInt("replyTo")).get(), us.findById(rs.getInt("userId")).get(), 
				rs.getTimestamp("creationDate"), rs.getInt("commentId"));
	
	@Autowired
	public CommentJdbcDao(DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("comments")
				.usingGeneratedKeyColumns("commentId");
	}
	
	@Override
	public Optional<Comment> findById(long id) {
		return jdbcTemplate.query("SELECT * FROM comments WHERE commentId = ?", ROW_MAPPER, id).stream().findFirst();
	}

	@Override
	public Comment create(int replyTo, Timestamp creationDate, String comment, long idPost, long user) {
		final Map<String, Object> args = new HashMap<>();
		args.put("replyTo", replyTo); // la key es el nombre de la columna
		args.put("comment", comment);
		args.put("postId", idPost);
		args.put("userId", user);
		args.put("creationDate", creationDate);
		final Number commentId = jdbcInsert.executeAndReturnKey(args);
		return new Comment(comment, ps.getPostById(idPost).get(), cs.getCommentById(replyTo).get(), us.findById(user).get(), creationDate, commentId.longValue());
	}

}
