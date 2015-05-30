package kr.brainyx.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import kr.brainyx.chat.dto.Member;
import kr.brainyx.chat.dto.MemberPhoto;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MemberDao
{

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings("rawtypes")
	private RowMapper userPhotoMapper1 = new RowMapper()
	{
		public MemberPhoto mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			MemberPhoto userPhoto = new MemberPhoto();
			userPhoto.setId(rs.getInt("id"));
			userPhoto.setUser_seq(rs.getInt("user_seq"));
			userPhoto.setUrl(rs.getString("url"));
			userPhoto.setStatus(rs.getString("status"));
			userPhoto.setCategory_code(rs.getString("category_code"));
			userPhoto.setCreate_date(rs.getString("create_date"));
			return userPhoto;
		}
	};
	@SuppressWarnings("rawtypes")
	private RowMapper userMapper1 = new RowMapper()
	{
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Member user = new Member();

			user.setUser_seq(rs.getInt("user_seq"));
			user.setUser_id(rs.getString("user_id"));
			user.setUser_name(rs.getString("user_name"));
			// user.setUserNick(rs.getString("user_nick"));
			user.setUser_photo(rs.getString("user_photo"));
			user.setUser_stmsg(rs.getString("user_stmsg"));
			user.setUser_phone(rs.getString("user_phone"));
			user.setUser_regdate(rs.getString("user_regdate"));

			return user;

		}
	};

	@SuppressWarnings("rawtypes")
	private RowMapper userMapper2 = new RowMapper()
	{
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Member user = new Member();

			user.setUser_seq(rs.getInt("user_seq"));
			user.setUser_id(rs.getString("user_id"));
			user.setUser_name(rs.getString("user_name"));
			// user.setUserNick(rs.getString("user_nick"));
			user.setUser_photo(rs.getString("user_photo"));
			user.setUser_stmsg(rs.getString("user_stmsg"));
			user.setUser_phone(rs.getString("user_phone"));
			user.setUser_regdate(rs.getString("user_regdate"));
			user.setUser_os(rs.getString("user_os"));
			user.setUseR_pushkey(rs.getString("user_pushkey"));
			user.setUser_email(rs.getString("user_email"));
			user.setCountry_code(rs.getString("country_code"));
			// user.setPushAllow(rs.getString("push_allow"));

			return user;

		}
	};

	@SuppressWarnings("rawtypes")
	private RowMapper userMapper3 = new RowMapper()
	{
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Member user = new Member();

			user.setUser_os(rs.getString("user_os"));
			user.setUseR_pushkey(rs.getString("user_pushkey"));

			return user;

		}
	};

	private RowMapper<?> userMapper4 = new RowMapper<Object>()
	{
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			Member user = new Member();

			user.setUser_seq(rs.getInt("user_seq"));

			return user;

		}
	};

	/**
	 * Brainyx 멤버리스트 가져오기
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getBrainyxMemberList()
	{
		try
		{
			String query = ""
					+ "SELECT seq, user_id, user_name, user_nick, user_photo, user_stmsg, user_phone, reg_date  "
					+ "FROM BXMCHAT.dbo.V_MEMBER ";
			return (List<Member>) this.jdbcTemplate.query(query,
					this.userMapper1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 아이디체크
	 * 
	 * @param loginId
	 * @return
	 */
	public boolean correctId(String loginId)
	{
		String query = "SELECT COUNT(*) AS cnt FROM b_MEMBER WHERE  user_id = ? ";

		return (this.jdbcTemplate.queryForInt(query, new Object[] {
			loginId
		}) == 1);
	}

	/**
	 * 비밀번호체크
	 * 
	 * @param userId
	 * @param userPw
	 * @return
	 */
	public boolean correctPw(String userId, String userPw)
	{
		String query = "SELECT PWDCOMPARE('"
				+ userPw
				+ "', user_pw) AS CNT FROM BXMCHAT.dbo.V_MEMBER WHERE user_id = '"
				+ userId + "' ";

		return this.jdbcTemplate.queryForInt(query, new Object[] {}) > 0;
	}

	

	/**
	 * 푸시키가져오기SEQ
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Member getPushKeySeq(int userSeq)
	{
		String query = "" + "SELECT user_pushkey, user_os " + "FROM b_member "
				+ "WHERE user_seq = ? ";
		try
		{
			return (Member) this.jdbcTemplate.queryForObject(query,
					new Object[] {
						userSeq
					}, this.userMapper3);
		}
		catch (Exception e)
		{
			return new Member();
		}
	}

	/**
	 * 푸시키중복제거
	 * 
	 * @param pushKey
	 */
	public void updateOverRapPushkey(String pushKey, String userPhone)
	{
		String query = "" + "UPDATE b_member SET " + " user_pushkey = ? "
				+ "WHERE user_phone = ? ";
		this.jdbcTemplate.update(query, new Object[] {
				pushKey, userPhone
		});
	}

	/**
	 * 푸시키업데이트
	 * 
	 * @param loginId
	 * @param pushKey
	 * @param OS
	 * @return
	 */
	public int updatePushKey(String loginId, String pushKey, String OS,
			String userPhone)
	{
		int result = -1;
		try
		{
			String query = ""
					+ "UPDATE bxmAdmin.bxm.dbo.ADMIN_MEMBER SET "
					+ "push_key = ?, user_os = ?, last_login = getDate(), user_phone = ? "
					+ "WHERE user_Id = ? ";

			this.jdbcTemplate.update(query, new Object[] {
					pushKey, OS, userPhone, loginId
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 멤버포토업데이트
	 * 
	 * @param userPhoto
	 * @param userId
	 * @return
	 */
	public int setProfilePhoto(String userPhoto, int userSeq)
	{
		String query = "" + " UPDATE b_member " + " SET user_photo = ? "
				+ " WHERE user_seq = ? ";

		return this.jdbcTemplate.update(query, new Object[] {
				userPhoto, userSeq
		});
	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userPhoto
	 * @param userSeq
	 * @param categoryCode
	 */
	public void insertMemberPhoto(String userPhoto, int userSeq,
			String categoryCode)
	{
		String query = " INSERT INTO b_member_photo(user_seq,url,category_code) "
				+ " VALUES(?,?,?) ";

		this.jdbcTemplate.update(query, new Object[] {
				userSeq, userPhoto, categoryCode
		});
	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @param phoneNo
	 */
	public void insertMemberContact(int userSeq, String phoneNo)
	{
		String query = " INSERT INTO b_member_contact(user_seq,phone_no) "
				+ " VALUES(?,?) ";

		this.jdbcTemplate.update(query, new Object[] {
				userSeq, phoneNo
		});
	}

	/**
	 * @author TuanNA
	 * @since 13-05-2015
	 * @param userSeq
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getFriendFromContact(int userSeq, String userPhone)
	{
		String strSQL = "SELECT b.user_seq FROM b_member_contact a,b_member b "
				+ " WHERE a.user_seq = b.user_seq "
				+ " AND a.phone_no = ? "
				+ " AND EXISTS (SELECT phone_no FROM b_member_contact WHERE user_seq = ? AND phone_no = b.user_phone)";
		return (List<Member>) this.jdbcTemplate.query(strSQL, new Object[] {
				userPhone, userSeq
		}, userMapper4);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getUserFromContact(int userSeq)
	{
		String strSQL = "SELECT b.user_seq, b.user_id, b.user_name, "
				+ " b.user_photo, b.user_stmsg, b.user_phone, "
				+ " b.user_regdate, b.user_os, b.user_pushkey, b.user_email, b.country_code "
				+ " FROM b_member b "
				+ " WHERE "
				+ " EXISTS (SELECT phone_no FROM b_member_contact WHERE user_seq = ? AND phone_no = b.user_phone) ";
//				+ " AND NOT EXISTS (SELECT 1 FROM b_member_friend WHERE  user_seq = ? AND friend_seq = b.user_seq) ";
		return (List<Member>) this.jdbcTemplate.query(strSQL, new Object[] {
				userSeq
		}, userMapper2);
	}

	/**
	 * This function use check phone_no is exists
	 * 
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @param phoneNo
	 */
	public boolean isExistsContact(int userSeq, String phoneNo)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member_contact "
				+ " WHERE user_seq = ? AND phone_no = ? ";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
				userSeq, phoneNo
		}) == 1);
	}

	/**
	 * This function use check member is exists in friend list
	 * 
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @param friendSeq
	 */
	public boolean isExistsFriend(int userSeq, int friendSeq)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member_friend "
				+ " WHERE user_seq = ? AND friend_seq = ? ";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
				userSeq, friendSeq
		}) == 1);
	}

	/**
	 * This function use check ready friend of 2 member
	 * 
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @param friendSeq
	 */
	public boolean isExistsFriend(int userSeq, int friendSeq, String status)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member_friend "
				+ " WHERE user_seq = ? AND friend_seq = ? AND status = ? ";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
				userSeq, friendSeq, status
		}) == 1);
	}

	/**
	 * This function use check user exists by user sequence
	 * 
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 */
	public boolean isExistsMember(int userSeq)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member "
				+ " WHERE user_seq = ? ";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
			userSeq
		}) == 1);
	}

	/**
	 * This function use check user name exists
	 * 
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 */
	public boolean isExistsUserName(String userName)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member "
				+ " WHERE user_name = ? ";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
			userName
		}) == 1);
	}

	/**
	 * This function use check user name exists
	 * 
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 */
	public boolean isExistsEmail(String email)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member "
				+ " WHERE user_email = ? ";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
			email
		}) == 1);
	}

	/**
	 * This function use check password is correct
	 * 
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 */
	public boolean isCorrectPassWord(int userSeq, String pwd)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member "
				+ " WHERE user_seq = ? AND user_pw = md5(?) ";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
				userSeq, pwd
		}) == 1);
	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> getListMemberFriend(int userSeq, String status)
	{
		String strSQL = "SELECT b.user_seq, b.user_id, b.user_name, "
				+ " b.user_photo, b.user_stmsg, b.user_phone, "
				+ " b.user_regdate, b.user_os, b.user_pushkey, b.user_email, b.country_code "
				+ " FROM b_member_friend a, b_member b "
				+ " WHERE a.friend_seq = b.user_seq " + " AND a.user_seq = ? "
				+ " AND a.status = ? ";
		return (List<Member>) this.jdbcTemplate.query(strSQL, new Object[] {
				userSeq, status
		}, userMapper2);
	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Member> searchMember(String searchKey)
	{
		String strSearch = "%" + searchKey + "%";
		String strSQL = "SELECT b.user_seq, b.user_id, b.user_name, "
				+ " b.user_photo, b.user_stmsg, b.user_phone, "
				+ " b.user_regdate, b.user_os, b.user_pushkey, b.user_email, b.country_code  "
				+ " FROM b_member b "
				+ " WHERE b.user_phone LIKE ? OR b.user_name LIKE ? ";
		return (List<Member>) this.jdbcTemplate.query(strSQL, new Object[] {
				strSearch, strSearch
		}, userMapper2);
	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<MemberPhoto> getMemberPhoto(int userSeq)
	{
		String strSQL = " SELECT id,user_seq,url,status,category_code, "
				+ " date_format(create_date,'%Y-%m-%d %H:%i:%s') create_date "
				+ " FROM b_member_photo " + " WHERE user_seq = ? ";
		return (List<MemberPhoto>) this.jdbcTemplate.query(strSQL,
				new Object[] {
					userSeq
				}, userPhotoMapper1);
	}

	/**
	 * This function use insert new friend
	 * 
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @param friendSeq
	 * @return
	 */
	public void insertMemberFriend(int userSeq, int friendSeq, String status)
	{
		String strSQL = " INSERT INTO b_member_friend(user_seq,friend_seq,status) "
				+ " VALUES(?,?,?) ";
		this.jdbcTemplate.update(strSQL, new Object[] {
				userSeq, friendSeq, status
		});
	}

	/**
	 * This function use update friend
	 * 
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @param friendSeq
	 * @param status
	 * @return
	 */
	public void updateMemberFriend(int userSeq, int friendSeq, String status)
	{
		String strSQL = " UPDATE b_member_friend " + " SET status = ? "
				+ " WHERE user_seq = ? AND friend_seq = ? ";
		this.jdbcTemplate.update(strSQL, new Object[] {
				status, userSeq, friendSeq
		});
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 유저 회원가입
	 * 
	 * @param userId
	 * @param userPw
	 * @param userName
	 * @param userPushkey
	 * @param userPhone
	 * @param userOs
	 */
	public boolean InsertMember(String userPushkey, String userPhone,
			String userOs)
	{
		String query = "" + "INSERT INTO b_member "
				+ "	(user_phone,user_pushkey,user_os,user_regdate,user_pw) "
				+ "VALUES " + "	(  ?, ?, ?,now(),md5('') ) ";
		return this.jdbcTemplate.update(query, new Object[] {
				userPhone, userPushkey, userOs
		}) > 0;
	}

	/**
	 * 내정보가져오기
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Member getMyInfo(String userPhone)
	{
		String query = ""
				+ "SELECT user_seq, user_id, user_name, user_photo, user_stmsg, user_phone, user_regdate, user_os, user_pushkey, user_email, country_code "
				+ "FROM b_member " + "WHERE user_phone = ? ";
		try
		{
			return (Member) this.jdbcTemplate.queryForObject(query,
					new Object[] {
						userPhone
					}, this.userMapper2);
		}
		catch (Exception e)
		{
			return new Member();
		}
	}

	/**
	 * 전화번호부기반으로 친구목록 만들기
	 * 
	 * @param JsonData
	 *            23
	 * @return
	 */
	public List<Member> getContactList(String JsonData, String myphone)
	{
		List<Member> list = new ArrayList<Member>();
		try
		{
			JSONArray array = new JSONArray(JsonData);
			for (int i = 0; i < array.length(); i++)
			{
				JSONObject obj = array.getJSONObject(i);
				System.out.println(obj.toString());
				String phone = obj.getString("contact");

				Member user = getContactUser(phone, myphone);
				if (user != null)
				{
					list.add(user);
				}
			}
			return list;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 내정보가져오기
	 * 
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Member getContactUser(String userPhone, String myphone)
	{
		String query = ""
				+ "SELECT user_seq, user_id, user_name,  user_photo, user_stmsg, user_phone, user_regdate, user_os, user_pushkey, user_email, country_code "
				+ "FROM b_member "
				+ "WHERE user_phone = ? and user_phone != ? and user_name is not null";
		try
		{
			return (Member) this.jdbcTemplate.queryForObject(query,
					new Object[] {
							userPhone, myphone
					}, this.userMapper2);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userseq
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Member getUserInfo(int userseq)
	{
		String query = ""
				+ " SELECT user_seq, user_id, user_name,  user_photo, user_stmsg, user_phone, user_regdate, "
				+ " user_os, user_pushkey, user_email, country_code "
				+ " FROM b_member " + " WHERE user_seq = ? ";
		try
		{
			return (Member) this.jdbcTemplate.queryForObject(query,
					new Object[] {
						userseq
					}, this.userMapper2);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userEmail
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Member getUserInfoByEmail(String userEmail)
	{
		String query = ""
				+ " SELECT user_seq, user_id, user_name,  user_photo, user_stmsg, user_phone, user_regdate, user_os, user_pushkey, user_email, country_code "
				+ " FROM b_member " + " WHERE user_email = ? ";
		try
		{
			return (Member) this.jdbcTemplate.queryForObject(query,
					new Object[] {
						userEmail
					}, this.userMapper2);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * 멤버상태메세지업데이트
	 * 
	 * @param userStmsg
	 * @param userId
	 * @return
	 */
	public int setProfileStmsg(String userStmsg, int userSeq)
	{
		String query = "" + " UPDATE b_member " + " SET user_stmsg = ? "
				+ " WHERE user_seq = ? ";

		return this.jdbcTemplate.update(query, new Object[] {
				userStmsg, userSeq
		});
	}

	/**
	 * Update user name
	 * 
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userName
	 * @param userSeq
	 * @return
	 */
	public int setProfileUserName(String userName, int userSeq)
	{
		String query = "" + " UPDATE b_member " + " SET user_name = ? "
				+ " WHERE user_seq = ? ";

		return this.jdbcTemplate.update(query, new Object[] {
				userName, userSeq
		});
	}

	/**
	 * Update user email
	 * 
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userName
	 * @param userSeq
	 * @return
	 */
	public int setProfileEmail(String email, int userSeq)
	{
		String query = "" + " UPDATE b_member " + " SET user_email = ? "
				+ " WHERE user_seq = ? ";

		return this.jdbcTemplate.update(query, new Object[] {
				email, userSeq
		});
	}

	/**
	 * Update pass word
	 * 
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userName
	 * @param userSeq
	 * @return
	 */
	public int setProfilePwd(String pwd, int userSeq)
	{
		String query = "" + " UPDATE b_member " + " SET user_pw = md5(?) "
				+ " WHERE user_seq = ? ";

		return this.jdbcTemplate.update(query, new Object[] {
				pwd, userSeq
		});
	}

	/**
	 * 휴대폰 번호체크
	 * 
	 * @param loginId
	 * @return
	 */
	public boolean isuserPhone(String phone)
	{
		String query = "" + "SELECT COUNT(*) AS cnt " + "FROM b_member "
				+ "WHERE  user_phone = ? ";
		return (this.jdbcTemplate.queryForInt(query, new Object[] {
			phone
		}) == 1);
	}

	public boolean UpdateUserProfile(String userName, String userPhoto,
			String userPhone)
	{
		String query = "UPDATE b_member SET user_name = ? , user_photo = ? WHERE user_phone = ?";
		return this.jdbcTemplate.update(query, new Object[] {
				userName, userPhoto, userPhone
		}) > 0;
	}

	/**
	 * This function use register
	 * 
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 */
	public void registerEmail(int userSeq, String email, String pwd)
	{
		String query = "UPDATE b_member SET user_email = ? , user_pw = md5(?) WHERE user_seq = ?";
		this.jdbcTemplate.update(query, new Object[] {
				email, pwd, userSeq
		});
	}

	/**
	 * 멤버수가져오기
	 * 
	 * @return
	 */
	public int getMemberCount()
	{
		String query = "" + "SELECT count(*) " + "FROM b_member ";
		try
		{
			return (Integer) this.jdbcTemplate.queryForInt(query);
		}
		catch (Exception e)
		{
			return -1;
		}
	}

	/**
	 * @author TuanNA
	 * @since 05-18-2015
	 * @param phoneNo
	 * @return
	 */
	public String normalizedPhone(String phoneNo, String prefixCode)
	{
		String strNormalizedPhone = "";
		if (phoneNo.startsWith("00"))
		{
			strNormalizedPhone = phoneNo.substring(2);
		}
		else if (phoneNo.startsWith("0"))
		{
			strNormalizedPhone = phoneNo.substring(1);
			strNormalizedPhone = prefixCode + strNormalizedPhone;
		}
		else if (phoneNo.startsWith("++"))
		{
			strNormalizedPhone = phoneNo.substring(2);
		}
		else if (phoneNo.startsWith("+"))
		{
			strNormalizedPhone = phoneNo.substring(1);
		}
		else if (phoneNo.startsWith("82"))
		{
			strNormalizedPhone = phoneNo;
		}
		else if (phoneNo.startsWith("84"))
		{
			strNormalizedPhone = phoneNo;
		}
		else 
			strNormalizedPhone = prefixCode + phoneNo;
		return strNormalizedPhone;
	}

}
