package kr.brainyx.chat.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import kr.brainyx.chat.dto.MemberCategory;
import kr.brainyx.chat.dto.MemberPost;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MemberPostDao
{
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@SuppressWarnings("rawtypes")
	private RowMapper memberCategoryMapper1 = new RowMapper()
	{
		public MemberCategory mapRow(ResultSet rs, int rowNum)
				throws SQLException
		{
			MemberCategory memberCategory = new MemberCategory();

			memberCategory.setId(rs.getInt("id"));
			memberCategory.setUser_seq(rs.getInt("user_seq"));
			memberCategory.setName(rs.getString("name"));
			memberCategory.setCode(rs.getString("code"));
			memberCategory.setType(rs.getString("type"));
			memberCategory.setStatus(rs.getString("status"));
			memberCategory.setCreate_date(rs.getString("create_date"));

			return memberCategory;
		}
	};
	@SuppressWarnings("rawtypes")
	private RowMapper memberPostMapper1 = new RowMapper()
	{
		public MemberPost mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			MemberPost memberPost = new MemberPost();

			memberPost.setId(rs.getInt("id"));
			memberPost.setParent_id(rs.getInt("parent_id"));
			memberPost.setUser_seq(rs.getInt("user_seq"));
			memberPost.setCategory_id(rs.getInt("category_id"));
			memberPost.setStatus(rs.getString("status"));
			memberPost.setPrivacy(rs.getString("privacy"));
			memberPost.setType(rs.getString("type"));
			memberPost.setContent(rs.getString("content"));
			memberPost.setCreate_date(rs.getString("create_date"));
			memberPost.setTitle(rs.getString("title"));
			memberPost.setTitle_img(rs.getString("title_img"));
			memberPost.setTitle_description(rs.getString("title_description"));
			memberPost.setUrl(rs.getString("url"));
			memberPost.setNum_share(rs.getInt("num_share"));
			return memberPost;
		}
	};

	public boolean checkCategoryExists(int userSeq, int categoryId)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member_category "
				+ " WHERE user_seq = ? AND id = ?";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
				userSeq, categoryId
		}) == 1);
	}

	public int insertMemberCategory(int userSeq, String name, String code,
			String type, String status)
	{
		String strSQL = "INSERT INTO b_member_category(user_seq,name,code,type,status) "
				+ "VALUES(?,?,?,?,?)";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				userSeq, name, code, type, status
		});
	}

	public int updateMemberCategory(int categoryId, String name)
	{
		String strSQL = "" + " UPDATE b_member_category " + " SET name = ? "
				+ " WHERE id = ? ";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				name, categoryId
		});
	}

	public int deleteMemberCategory(int userSeq, int categoryId)
	{
		String strSQL = "DELETE FROM b_member_category"
				+ " WHERE user_seq = ? AND id = ? ";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				userSeq, categoryId
		});
	}

	@SuppressWarnings("unchecked")
	public List<MemberCategory> getMemberCategory(int userseq)
	{
		String strSQL = " SELECT id,user_seq,name,code,type,status,create_date "
				+ " FROM b_member_category "
				+ " WHERE (user_seq = ?) "
				+ " OR (type = '1') " + "ORDER BY type,name ";
		return (List<MemberCategory>) this.jdbcTemplate.query(strSQL,
				new Object[] {
					userseq
				}, this.memberCategoryMapper1);
	}

	public boolean checkPostExists(int userSeq, int postId)
	{
		String strSQL = "SELECT count(*) " + " FROM b_member_post "
				+ " WHERE user_seq = ? AND id = ?";
		return (this.jdbcTemplate.queryForInt(strSQL, new Object[] {
				userSeq, postId
		}) == 1);
	}

	public int insertMemberPost(String parentId, int userSeq, int categoryId,
			String status, String privacy, String type, String content,
			String url, String title, String titleImg, String tileDescription)
	{
		String strSQL = "INSERT INTO b_member_post"
				+ "(parent_id,user_seq,category_id,status,privacy,type,content,url,title,title_img,title_description) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				parentId, userSeq, categoryId, status, privacy, type, content,
				url, title, titleImg, tileDescription
		});
	}

	public int updateMemberPost(int postId, String content)
	{
		String strSQL = " UPDATE b_member_post " + " SET content = ? "
				+ " WHERE id = ? ";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				content, postId
		});
	}
	
	public int upNumShare(int postId)
	{
		String strSQL = " UPDATE b_member_post " + " SET num_share = num_share + 1 "
				+ " WHERE id = ? ";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				postId
		});
	}
	
	public int downNumShare(int postId)
	{
		String strSQL = " UPDATE b_member_post " + " SET num_share = num_share - 1 "
				+ " WHERE id = ? ";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				postId
		});
	}

	public int deleteMemberPost(int userSeq, int postId)
	{
		String strSQL = " DELETE FROM b_member_post "
				+ " WHERE user_seq = ? AND id = ? ";

		return this.jdbcTemplate.update(strSQL, new Object[] {
				userSeq, postId
		});
	}

	public int deleteMemberPostByParent(int parentId)
	{
		String strSQL = " DELETE FROM b_member_post " + " WHERE parent_id = ? ";

		return this.jdbcTemplate.update(strSQL, new Object[] {
			parentId
		});
	}

	@SuppressWarnings("unchecked")
	public List<MemberPost> getMemberPost(int userseq)
	{
		String strSQL = " SELECT id,parent_id,user_seq,category_id, "
				+ " status,privacy,type,content,create_date,title,title_img,title_description,num_share,url "
				+ " FROM b_member_post " + " WHERE user_seq = ? "
				+ " ORDER BY create_date desc ";
		return (List<MemberPost>) this.jdbcTemplate.query(strSQL, new Object[] {
			userseq
		}, this.memberPostMapper1);
	}

	@SuppressWarnings("unchecked")
	public MemberPost getPostById(int postId)
	{
		String strSQL = " SELECT id,parent_id,user_seq,category_id, "
				+ " status,privacy,type,content,create_date,title,title_img,title_description,num_share,url "
				+ " FROM b_member_post " + " WHERE id = ? ";
		return (MemberPost) this.jdbcTemplate.queryForObject(strSQL,
				new Object[] {
					postId
				}, this.memberPostMapper1);
	}

	@SuppressWarnings("unchecked")
	public List<MemberPost> getMemberPostByCategory(int cateId)
	{
		String strSQL = " SELECT id,parent_id,user_seq,category_id, "
				+ " status,privacy,type,content,create_date,title,title_img,title_description,num_share,url "
				+ " FROM b_member_post " + " WHERE category_id = ? "
				+ " ORDER BY create_date desc ";
		return (List<MemberPost>) this.jdbcTemplate.query(strSQL, new Object[] {
			cateId
		}, this.memberPostMapper1);
	}

	@SuppressWarnings("unchecked")
	public List<MemberPost> getAllMemberPost()
	{
		String strSQL = " SELECT id,parent_id,user_seq,category_id, "
				+ " status,privacy,type,content,create_date,title,title_img,title_description,num_share,url "
				+ " FROM b_member_post " + " ORDER BY create_date desc ";

		return (List<MemberPost>) this.jdbcTemplate.query(strSQL,
				new Object[] {}, this.memberPostMapper1);
	}
}
