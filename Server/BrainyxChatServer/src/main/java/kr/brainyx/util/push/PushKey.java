package kr.brainyx.util.push;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PushKey {
	String userId = "";
	String pushKey = "";
	String os = "";
	
	public String getPushKey() {
		return pushKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPushKey(String pushKey) {
		this.pushKey = pushKey;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	
}
