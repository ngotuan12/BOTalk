package kr.brainyx.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.brainyx.chat.dao.MemberPostDao;
import kr.brainyx.chat.dto.MemberCategory;
import kr.brainyx.chat.dto.MemberPost;
import kr.brainyx.util.AppException;
import kr.brainyx.util.Response;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberPostController
{

	@Autowired
	private MemberPostDao postDao;

	@Value("#{config['file.maxSize']}")
	int FILE_MAX_SIZE;

	@Value("#{config['file.root']}")
	String FILE_ROOT;

	String FILE_LOCAL_PATH = "";

	String FILE_PATH = "";

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_get_all_member_post.go")
	public String mGetAllMemberPost(
	// @RequestParam(value = "user_seq", required = true, defaultValue = "") int
	// userSeq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_005";
		String msg_str = "Get posts success!";
		boolean result = true;
		List<MemberPost> posts = null;
		try
		{
			posts = postDao.getAllMemberPost();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			responseMessage.put("posts", posts);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_get_mem_post_by_category.go")
	public String mGetMemberPostByCategory(
			@RequestParam(value = "category_id", required = true, defaultValue = "") int categoryId,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_005";
		String msg_str = "Get posts success!";
		boolean result = true;
		List<MemberPost> posts = null;
		try
		{
			posts = postDao.getMemberPost(categoryId);
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			responseMessage.put("posts", posts);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_get_member_post.go")
	public String mGetMemberPost(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_005";
		String msg_str = "Get posts success!";
		boolean result = true;
		List<MemberPost> posts = null;
		try
		{
			posts = postDao.getMemberPost(userSeq);
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			responseMessage.put("posts", posts);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	public String RemoveTag(String html)
	{
		html = html.replaceAll("\\<.*?>", "");
		html = html.replaceAll("&", "");
		return html;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param category_id
	 * @param content
	 * @return
	 */
	@RequestMapping("/m_get_charset.go")
	public String mGetCharset(HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		responseMessage.put("file.encoding",
				System.getProperty("file.encoding"));
		responseMessage.put("defaultCharset", Charset.defaultCharset().name());
		
		JSONObject jsonObject = JSONObject.fromObject(responseMessage);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param category_id
	 * @param content
	 * @return
	 */
	@RequestMapping("/m_add_member_post.go")
	public String mAddMemberPost(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "url", required = true, defaultValue = "") String strUrl,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_006";
		String msg_str = "Add member post success!";
		boolean result = true;

		try
		{
			String strTitle = "";
			String img = "";
			String description = "";
			try
			{
				URL url = new URL(strUrl);
				URLConnection urlConnection = url.openConnection();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));

				String html = "";
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					html += inputLine;
				in.close();
				Document doc = Jsoup.parse(html);
				doc.outputSettings().charset("utf-8");
				strTitle = doc.title();
				img = doc.select("meta[property=og:image]").first()
						.attr("content");
				description = doc.select("meta[name=description]").first()
						.attr("content");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			int resultCode = postDao.insertMemberPost(null, userSeq, 1, "1",
					"1", "1", "", strUrl, strTitle, img, description);
			if (resultCode == -1) throw new AppException("SYS_018",
					"Can't add post!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param category_id
	 * @param content
	 * @return
	 */
	@RequestMapping("/m_share_member_post.go")
	public String mShareMemberPost(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "post_id", required = true, defaultValue = "0") String postId,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_006";
		String msg_str = "Share post success!";
		boolean result = true;

		try
		{
			MemberPost post = postDao.getPostById(Integer.parseInt(postId));
			Integer i = post.getId();
			if (post.getType().equals("2"))
			{
				post = postDao.getPostById(post.getParent_id());
				i = post.getId();
			}

			int resultCode = postDao.insertMemberPost(i.toString(), userSeq, 1,
					"1", "1", "2", "", post.getUrl(), post.getTitle(),
					post.getTitle_img(), post.getTitle_description());

			postDao.upNumShare(i);

			if (resultCode == -1) throw new AppException("SYS_025",
					"Can't share post!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param category_id
	 * @param content
	 * @return
	 */
	@RequestMapping("/m_update_member_post.go")
	public String mUpdateMemberPost(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "post_id", required = true, defaultValue = "") int postId,
			@RequestParam(value = "content", required = true, defaultValue = "") String content,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_007";
		String msg_str = "Update member post success!";
		boolean result = true;

		try
		{
			if (!postDao.checkPostExists(userSeq, postId)) throw new AppException(
					"SYS_021", "Post does not exists!");
			int resultCode = postDao.updateMemberPost(postId, content);
			if (resultCode == -1) throw new AppException("SYS_022",
					"Can't update post!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param category_id
	 * @param content
	 * @return
	 */
	@RequestMapping("/m_delete_member_post.go")
	public String mDeleteMemberPost(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "post_id", required = true, defaultValue = "") int postId,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_008";
		String msg_str = "Delete member post success!";
		boolean result = true;

		try
		{
			if (!postDao.checkPostExists(userSeq, postId)) throw new AppException(
					"SYS_021", "Post does not exists!");
			MemberPost post = postDao.getPostById(postId);
			if (post.getType().equals("1")) postDao
					.deleteMemberPostByParent(postId);
			else postDao.downNumShare(post.getParent_id());
			int resultCode = postDao.deleteMemberPost(userSeq, postId);
			if (resultCode == -1) throw new AppException("SYS_024",
					"Can't delete post!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_get_member_category.go")
	public String mGetMemberCategory(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_001";
		String msg_str = "Get categories success!";
		boolean result = true;
		List<MemberCategory> categories = null;
		try
		{
			categories = postDao.getMemberCategory(userSeq);
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			responseMessage.put("categories", categories);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param categoryName
	 * @return
	 */
	@RequestMapping("/m_add_member_category.go")
	public String mAddMemberCategory(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "category_name", required = true, defaultValue = "") String categoryName,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_002";
		String msg_str = "Add member category success!";
		boolean result = true;

		try
		{
			int resultCode = postDao.insertMemberCategory(userSeq,
					categoryName, "", "2", "1");
			if (resultCode == -1) throw new AppException("SYS_017",
					"Can't add category!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param category_id
	 * @param content
	 * @return
	 */
	@RequestMapping("/m_update_member_category.go")
	public String mUpdateMemberCategory(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "category_id", required = true, defaultValue = "") int categoryId,
			@RequestParam(value = "category_name", required = true, defaultValue = "") String name,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_003";
		String msg_str = "Update member category success!";
		boolean result = true;
		try
		{
			if (!postDao.checkCategoryExists(userSeq, categoryId)) throw new AppException(
					"SYS_019", "Category does not exists!");
			int resultCode = postDao.updateMemberCategory(categoryId, name);
			if (resultCode == -1) throw new AppException("SYS_020",
					"Can't update category!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param userSeq
	 * @param category_id
	 * @param content
	 * @return
	 */
	@RequestMapping("/m_delete_member_category.go")
	public String mDeleteMemberCategory(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "category_id", required = true, defaultValue = "") int categoryId,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_POST_004";
		String msg_str = "Delete member category success!";
		boolean result = true;
		try
		{
			if (!postDao.checkCategoryExists(userSeq, categoryId)) throw new AppException(
					"SYS_019", "Category does not exists!");
			int resultCode = postDao.deleteMemberCategory(userSeq, categoryId);
			if (resultCode == -1) throw new AppException("SYS_023",
					"Can't delete category!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		finally
		{
			// response variable
			responseMessage.put("result", result);
			responseMessage.put("msg_code", msg_code);
			responseMessage.put("msg_str", msg_str);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}
}
