package kr.brainyx.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import kr.brainyx.chat.dto.Member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {
	private JdbcTemplate jdbcTemplate;
	public void setDataSource(DataSource dataSource) {
	    this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings("rawtypes")
	private RowMapper userMapper = new RowMapper() {
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
			Member user = new Member();
			user.setUser_id(rs.getString("user_id"));
			user.setUser_name(rs.getString("user_name"));
			//user.set(rs.getString("user_phone"));
			return user;
		}
	};

	/**
	 * 사용자 등록
	 * @param user
	 */
	public void addUser(final Member user) {
		String query = "" +
				"INSERT INTO TODO.dbo.T_USER " +
				"	(user_id, user_pw, user_name, user_color, user_mobile, user_phone, user_email) " +
				"VALUES " +
				"	(?, ?, ?, ?, ?, ?, ?) ";
		this.jdbcTemplate.update(query, new Object[] {
			"",
			"",
			"",
		});
	}

	public void deleteUser(String user_id) {
		String query = "DELETE FROM TODO.dbo.T_USER WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { user_id });
	}

	public void updateUser(Member user) { 
		String query = "" + 
				"UPDATE TODO.dbo.T_USER SET " +
				"	user_id = ?, " +
				"	user_pw = ?, " +
				"	user_name = ?, " +
				"	user_color = ?, " +
				"	user_mobile = ?, " +
				"	user_phone = ?, " +
				"	user_email = ? " +
				"WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] {
			user.getUser_id(),
			user.getUser_name(),
			user.getUser_phone(),
		});
	}
	

	/**
	 * 비밀번호 변경
	 * @param userId
	 * @param userPw
	 */
	public void updateUserPassword(String userId, String userPw) { 
		String query = "" + 
				"UPDATE TODO.dbo.T_USER SET " +
				"	user_pw = ? " +
				"WHERE user_id = ? ";
		this.jdbcTemplate.update(query, new Object[] { userPw, userId });
	}
	
	
	/**
	 * 존재하는 아이디인지 여부
	 * @param loginId
	 * @return
	 */
	public boolean correctId(String loginId) {
		String query = "" +
				"SELECT COUNT(*) AS cnt " +
				"FROM TODO.dbo.V_USER " +
				"WHERE  user_id = ? ";
		return (this.jdbcTemplate.queryForInt(query, new Object[] { loginId }) == 1);
	}
	
	/**
	 * 로그인 확인
	 * @param loginId
	 * @param loginPw
	 * @return
	 */
	public boolean correctPw_old(String loginId, String loginPw) {
	    String query = "SELECT COUNT(*) AS CNT FROM TODO.dbo.V_USER WHERE user_id = ? and user_pw = ? ";
	    
	    return this.jdbcTemplate.queryForInt(query, new Object[] { loginId, loginPw }) == 1;
	}
	
	/**
	 * 로그인 확인(암호화)
	 * @param loginId
	 * @param loginPw
	 * @return
	 */
	public boolean correctPw(String loginId, String loginPw) { 
	    String query = "SELECT PWDCOMPARE(?, user_pw) AS CNT FROM TODO.dbo.V_USER WHERE user_id = ? ";
 	    return this.jdbcTemplate.queryForInt(query, new Object[] {loginPw, loginId }) > 0;
	}

	@SuppressWarnings("unchecked")
	public Member getUser(String user_id) {
		String query = "" + 
				"SELECT user_id, user_pw, user_name, user_color, user_mobile, user_phone, user_email " +
				"FROM TODO.dbo.V_USER " +
				"WHERE user_id = ? ";
		try {
			return (Member)this.jdbcTemplate.queryForObject(query, new Object[] { user_id }, this.userMapper);
		} catch (Exception e) {
			return new Member();
		}
	}

	/**
	 * 전체 사용자 목록
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getUserList() {
		String query = "" +
				"SELECT user_id, user_pw, user_name, user_color, user_mobile, user_phone, user_email " +
				"FROM TODO.dbo.V_USER " +
				"ORDER BY user_id DESC";
		return (List<Member>)this.jdbcTemplate.query(query, this.userMapper);
	}
	
	/**
	 * 전체 사용자 목록 : 페이징
	 * @param page
	 * @param itemCountPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getUserList(int page, int itemCountPerPage) {
		String query = "" +
				"SELECT TOP "+ itemCountPerPage +" user_id, user_pw, user_name, user_color, user_mobile, user_phone, user_email " +
				"FROM TODO.dbo.V_USER " +
				"WHERE user_id <= ( " +
				"	SELECT MIN(user_id) " +
				"	FROM ( " +
				"		SELECT TOP "+ ((page-1) * itemCountPerPage + 1) +" user_id " +
				"		FROM TODO.dbo.V_USER " +
				"		ORDER BY user_id DESC " +
				"	) AS A) " +
				"ORDER BY user_id DESC";
		return (List<Member>)this.jdbcTemplate.query(query, this.userMapper);
	}
	public int getCount() {
		String query = "SELECT COUNT(*) AS cnt FROM TODO.dbo.V_USER ";
		return this.jdbcTemplate.queryForInt(query);
	}
	
	/**
	 * 검색 사용자 목록 : 페이징
	 * @param keyword
	 * @param page
	 * @param itemCountPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getUserList(String keyword, int page, int itemCountPerPage) {
		String query = "" +
				"SELECT TOP "+ itemCountPerPage +" user_id, user_pw, user_name, user_color, user_mobile, user_phone, user_email " +
				"FROM TODO.dbo.V_USER " +
				"WHERE user_id <= ( " +
				"	SELECT MIN(user_id) " +
				"	FROM ( " +
				"		SELECT TOP "+ ((page-1) * itemCountPerPage + 1) +" user_id " +
				"		FROM TODO.dbo.V_USER " +
				"		WHERE user_id like ? OR user_name like ? " +
				"		ORDER BY user_id DESC " +
				"	) AS A) " +
				"	AND (user_id like ? OR user_name like ?) " +
				"ORDER BY user_id DESC";
		return (List<Member>)this.jdbcTemplate.query(query, new Object[] { "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%" }, this.userMapper);
	}
	public int getCount(String keyword) {
		String query = "SELECT COUNT(*) AS cnt FROM TODO.dbo.V_USER WHERE user_id like ? OR user_name like ? ";
		return this.jdbcTemplate.queryForInt(query, new Object[] { "%"+keyword+"%", "%"+keyword+"%" });
	}
	
	
}
