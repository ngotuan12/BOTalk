package kr.brainyx.util.push;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PushMapper {
	
	public static RowMapper pushMapper = new RowMapper() {
		public PushKey mapRow(ResultSet rs, int rowNum) throws SQLException {
			PushKey pushKey = new PushKey();
			pushKey.setUserId(rs.getString("user_id"));
			pushKey.setPushKey(rs.getString("push_key"));
			pushKey.setOs(rs.getString("os"));
			return pushKey;
		}
	};

}
