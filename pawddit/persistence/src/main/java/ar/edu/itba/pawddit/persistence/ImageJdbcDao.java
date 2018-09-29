package ar.edu.itba.pawddit.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ImageJdbcDao implements ImageDao {
	
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	private final static RowMapper<byte[]> ROW_MAPPER = (rs, rowNum) -> rs.getBytes("bytearray");
	
	@Autowired
	public ImageJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("images");
	}
	
	@Override
	public String saveImage(final byte[] image, final String token) {
		final Map<String, Object> args = new HashMap<>();
		args.put("token", token);
		args.put("bytearray", image);
		jdbcInsert.execute(args);
		return token;
	}

	@Override
	public Optional<byte[]> findByToken(final String token) {
		return jdbcTemplate.query("SELECT * FROM images WHERE token = ?", ROW_MAPPER, token).stream().findFirst();
	}

}
