package kr.brainyx.chat;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.brainyx.chat.dao.MemberDao;
import kr.brainyx.chat.dto.Member;
import kr.brainyx.chat.dto.MemberPhoto;
import kr.brainyx.util.AmazonFileUpload;
import kr.brainyx.util.AppException;
import kr.brainyx.util.Response;
import net.sf.json.JSONObject;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberController
{

	@Autowired
	private MemberDao memberDao;

	@Value("#{config['file.maxSize']}")
	int FILE_MAX_SIZE;

	@Value("#{config['file.root']}")
	String FILE_ROOT;

	String FILE_LOCAL_PATH = "";

	String FILE_PATH = "";
	
	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param user_email
	 * @param user_pwd
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_login_email.go")
	public String mLoginEmail(
			@RequestParam(value = "user_email", required = true, defaultValue = "") String userEmail,
			@RequestParam(value = "user_pwd", required = true, defaultValue = "") String userPwd,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_LOGIN_001";
		String msg_str = "Login by email success!";
		boolean result = true;
		Member user = null;
		try
		{
			// if check email does not exists
			if(!memberDao.isExistsEmail(userEmail))
				throw new AppException("SYS-016", "Email does not register in system!");
			// info
			user = memberDao.getUserInfoByEmail(userEmail);
			// check pass word
			if(!memberDao.isCorrectPassWord(user.getUser_seq(), userPwd))
				throw new AppException("SYS-007", "Pass word does not correct!");
		}
		catch (AppException aex)
		{
			result = false;
			msg_code = aex.getMsgCode();
			msg_str = aex.getMessage();
			user = null;
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
			responseMessage.put("info", user);
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
	 * @param user_seq
	 * @param user_email
	 * @param user_pwd
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_register_email.go")
	public String mRegisterEmail(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "user_email", required = true, defaultValue = "") String userEmail,
			@RequestParam(value = "user_pwd", required = true, defaultValue = "") String userPwd,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_REGISTER_001";
		String msg_str = "Register success!";
		boolean result = true;
		Member info = null;
		try
		{
			//if member does not exists
			if(!memberDao.isExistsMember(userSeq))
				throw new AppException("SYS-002", "User does not exists!");
			// if check email exists
			if(memberDao.isExistsEmail(userEmail))
				throw new AppException("SYS-009", "Email already exists!");
			memberDao.registerEmail(userSeq, userEmail, userPwd);
			
			info = memberDao.getUserInfo(userSeq);
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
			responseMessage.put("info", info);
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
	 * @param friend_seq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_cancel_friend_confirm.go")
	public String mCancelFriendConfirm(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "friend_seq", required = true, defaultValue = "") int friend_seq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_007";
		String msg_str = "Cancel friend confirm success!";
		boolean result = true;
		try
		{
			// if not exists request
			if(!memberDao.isExistsFriend(userSeq,friend_seq,"3"))
				throw new AppException("SYS_014", "Not found friend confirm request!");
			// cancel request
			if(memberDao.isExistsFriend(userSeq, friend_seq))
			{
				memberDao.updateMemberFriend(userSeq, friend_seq, "0");
			}
			else
			{
				memberDao.insertMemberFriend(userSeq, friend_seq, "0");
			}
			// member send request
			if(memberDao.isExistsFriend(friend_seq,userSeq))
			{
				memberDao.updateMemberFriend(friend_seq,userSeq, "0");
			}
			else
			{
				memberDao.insertMemberFriend(friend_seq,userSeq, "0");
			}
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
	 * @param friend_seq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_cancel_friend_request.go")
	public String mCancelFriendRequest(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "friend_seq", required = true, defaultValue = "") int friend_seq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_006";
		String msg_str = "Cancel friend request success!";
		boolean result = true;
		try
		{
			// if not exists request
			if(!memberDao.isExistsFriend(userSeq,friend_seq,"2"))
				throw new AppException("SYS_015", "Not found friend request!");
			// cancel request
			if(memberDao.isExistsFriend(userSeq, friend_seq))
			{
				memberDao.updateMemberFriend(userSeq, friend_seq, "0");
			}
			else
			{
				memberDao.insertMemberFriend(userSeq, friend_seq, "0");
			}
			// member send request
			if(memberDao.isExistsFriend(friend_seq,userSeq))
			{
				memberDao.updateMemberFriend(friend_seq,userSeq, "0");
			}
			else
			{
				memberDao.insertMemberFriend(friend_seq,userSeq, "0");
			}
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
	 * @param friend_seq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_confirm_friend_request.go")
	public String mComfirmFriendRequest(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "friend_seq", required = true, defaultValue = "") int friend_seq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_005";
		String msg_str = "Confirm friend success!";
		boolean result = true;
		try
		{
			//if ready friend
			if(memberDao.isExistsFriend(userSeq,friend_seq,"1"))
				throw new AppException("SYS_011", "Member is already friend!");
			if(!memberDao.isExistsFriend(userSeq,friend_seq,"3"))
				throw new AppException("SYS_014", "Not found friend confirm request!");
			// confirm request
			if(memberDao.isExistsFriend(userSeq, friend_seq))
			{
				memberDao.updateMemberFriend(userSeq, friend_seq, "1");
			}
			else
			{
				memberDao.insertMemberFriend(userSeq, friend_seq, "1");
			}
			// member send request
			if(memberDao.isExistsFriend(friend_seq,userSeq))
			{
				memberDao.updateMemberFriend(friend_seq,userSeq, "1");
			}
			else
			{
				memberDao.insertMemberFriend(friend_seq,userSeq, "1");
			}
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
	 * @param friend_seq
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_friend_request.go")
	public String mFriendRequest(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "friend_seq", required = true, defaultValue = "") int friend_seq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_002";
		String msg_str = "Friend request success!";
		boolean result = true;
		try
		{
			//if ready friend
			if(memberDao.isExistsFriend(userSeq,friend_seq,"1"))
				throw new AppException("SYS_011", "Member is already friend!");
			if(memberDao.isExistsFriend(userSeq,friend_seq,"2"))
				throw new AppException("SYS_012", "Friend request has sended!");
			if(memberDao.isExistsFriend(friend_seq,userSeq,"3"))
				throw new AppException("SYS_013", "Friend is in comfirm list!");
			// member send request
			if(memberDao.isExistsFriend(userSeq, friend_seq))
			{
				memberDao.updateMemberFriend(userSeq, friend_seq, "2");
			}
			else
			{
				memberDao.insertMemberFriend(userSeq, friend_seq, "2");
			}
			// member confirm request
			if(memberDao.isExistsFriend(friend_seq,userSeq))
			{
				memberDao.updateMemberFriend(friend_seq,userSeq, "3");
			}
			else
			{
				memberDao.insertMemberFriend(friend_seq,userSeq, "3");
			}
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
	 * @since 05-13-2015
	 * @param user_seq
	 * @param session
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_get_friend_request_list.go")
	public String mGetFriendRequestList(
			@RequestParam(value = "user_seq", required = true) int user_seq,
			HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_003";
		String msg_str = "Get friend request list success!";
		boolean result = true;
		List<Member> friends = null;
		try
		{
			friends = memberDao.getListMemberFriend(user_seq,"2");
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		// response variable
		responseMessage.put("result", result);
		responseMessage.put("msg_code", msg_code);
		responseMessage.put("msg_str", msg_str);
		responseMessage.put("friends", friends);
		// response
		JSONObject jsonObject = JSONObject.fromObject(responseMessage);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);
		return null;
	}
	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param user_seq
	 * @param session
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_get_friend_confirm_list.go")
	public String mGetFriendConfirmList(
			@RequestParam(value = "user_seq", required = true) int user_seq,
			HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_004";
		String msg_str = "Get friend confirm list success!";
		boolean result = true;
		List<Member> friends = null;
		try
		{
			friends = memberDao.getListMemberFriend(user_seq,"3");
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		// response variable
		responseMessage.put("result", result);
		responseMessage.put("msg_code", msg_code);
		responseMessage.put("msg_str", msg_str);
		responseMessage.put("friends", friends);
		// response
		JSONObject jsonObject = JSONObject.fromObject(responseMessage);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);
		return null;
	}
	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param search_key
	 * @param session
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_get_photo_library.go")
	public String mGetPhotoLibrary(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_PHOTO_001";
		String msg_str = "Get photos success!";
		boolean result = true;
		List<MemberPhoto> photos = null;
		try
		{
			photos = memberDao.getMemberPhoto(userSeq);
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
			responseMessage.put("photos", photos);
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
	 * @since 05-13-2015
	 * @param search_key
	 * @param session
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_search_member.go")
	public String mSearchMember(
			@RequestParam(value = "search_key", required = true, defaultValue = "") String search_key,
			HttpSession session, HttpServletResponse res)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_001";
		String msg_str = "Search member succesfull!";
		boolean result = true;
		List<Member> members = null;
		try
		{
			members = memberDao.searchMember(search_key);
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
			responseMessage.put("members", members);
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
	 * @since 05-13-2015
	 * @param user_seq
	 * @param session
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_get_friend_list.go")
	public String mGetFriendList(
			@RequestParam(value = "user_seq", required = true) int user_seq,
			HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_FRIEND_001";
		String msg_str = "Success!";
		boolean result = true;
		List<Member> friends = null;
		try
		{
			friends = memberDao.getListMemberFriend(user_seq,"1");
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		// response variable
		responseMessage.put("result", result);
		responseMessage.put("msg_code", msg_code);
		responseMessage.put("msg_str", msg_str);
		responseMessage.put("friends", friends);
		// response
		JSONObject jsonObject = JSONObject.fromObject(responseMessage);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);
		return null;
	}
	@RequestMapping("/m_get_user_from_contact.go")
	public String mGetUserFromContact(
			@RequestParam(value = "user_seq", required = true) int user_seq,
			HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_CONTACT_002";
		String msg_str = "Get users from contact success!";
		boolean result = true;
		List<Member> users = null;
		try
		{
			users = memberDao.getUserFromContact(user_seq);
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		// response variable
		responseMessage.put("result", result);
		responseMessage.put("msg_code", msg_code);
		responseMessage.put("msg_str", msg_str);
		responseMessage.put("users", users);
		// response
		JSONObject jsonObject = JSONObject.fromObject(responseMessage);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);
		return null;
	}
	/**
	 * @author TuanNA
	 * @since 13-05-2015
	 * @param user_seq
	 * @param contacts
	 * @param session
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_update_contact.go")
	public String mUpdateContact(
			@RequestParam(value = "user_seq", required = true) int user_seq,
			@RequestParam(value = "user_phone", required = true) String user_phone,
			@RequestParam(value = "contacts", required = true) String contacts,
			HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "MEMBER_CONTACT_001";
		String msg_str = "Update success!";
		boolean result = true;
		try
		{
			Member info = memberDao.getUserInfo(user_seq);
			String countryCode = info.getCountry_code();
			// analysis contacts
			JSONArray arrContact = new JSONArray(contacts);
			for (int i = 0; i < arrContact.length(); i++)
			{
				String strPhoneNo = memberDao.normalizedPhone(arrContact.getString(i), countryCode) ;
				// if contact not exists
				if (!memberDao.isExistsContact(user_seq, strPhoneNo))
				{
					// insert new contact
					memberDao.insertMemberContact(user_seq, strPhoneNo);
				}
			}
			// analysis friend from contacts
//			List<Member> friends = memberDao.getFriendFromContact(user_seq,
//					user_phone);
//			for (Member friend : friends)
//			{
//				int friendSeq = friend.getUser_seq();
//				if (!memberDao.isExistsFriend(user_seq, friendSeq))
//				{
//					memberDao.insertMemberFriend(user_seq, friendSeq,"1");
//				}
//
//				if (!memberDao.isExistsFriend(friendSeq, user_seq))
//				{
//					memberDao.insertMemberFriend(friendSeq, user_seq,"1");
//				}
//			}
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "SYS-001";
			msg_str = ex.getMessage();
			ex.printStackTrace();
		}
		// response variable
		responseMessage.put("result", result);
		responseMessage.put("msg_code", msg_code);
		responseMessage.put("msg_str", msg_str);
		// response
		JSONObject jsonObject = JSONObject.fromObject(responseMessage);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);
		return null;
	}

	@RequestMapping("/m_update_member_email.go")
	public String mUpdateMemberEmail()
	{
		return null;
	}

	/**
	 * This function use: <br/>
	 * upload and save profile picture to S3 amazon server <br/>
	 * update profile picture
	 * 
	 * @author TuanNA
	 * @since 05-08-2015
	 * @param session
	 * @param req
	 * @param res
	 * @param model
	 * @return null
	 */
	@RequestMapping("/m_update_profile_photo.go")
	public String mUpdateProfilePhoto(
			@RequestParam(value = "user_seq", required = true) int user_seq,
			HttpSession session, HttpServletRequest req,
			HttpServletResponse res, Model model)
	{

		Map<String, Object> responseMessage = new HashMap<String, Object>();
		boolean result = true;
		String msg_code = "USER_PROFILE_001";
		String msg_str = "Update profile photo success!";
		String profile_img = "";
		try
		{
			// variable
			String noticePath = FILE_ROOT + FILE_PATH;
			;
			int fileMaxBiteSize = FILE_MAX_SIZE * 1024 * 1024;
			File file = null;
			// Analysis file
			MultipartRequest multi = new MultipartRequest(req, noticePath,
					fileMaxBiteSize, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration<?> files = multi.getFileNames();
			while (files.hasMoreElements())
			{
				String elementName = (String) files.nextElement();
				file = multi.getFile(elementName);
				if (file != null)
				{
					// upload file
					Map<String, Object> map = AmazonFileUpload.s3Upload(file,
							"profile/" + user_seq + "/");
					// set profile photo
					profile_img = map.get("filePath").toString();
					memberDao.setProfilePhoto(profile_img, user_seq);
					// insert member_photo
					memberDao.insertMemberPhoto(profile_img, user_seq,
							"PROFILE");
					// delete cache file
					file.delete();
				}
			}
		}
		catch (Exception ex)
		{
			result = false;
			msg_code = "USER_PROFILE_002";
			msg_str = "Update profile photo false!";
			ex.printStackTrace();
		}
		// response variable
		responseMessage.put("result", result);
		responseMessage.put("msg_code", msg_code);
		responseMessage.put("msg_str", msg_str);
		responseMessage.put("profile_img", profile_img);
		// response
		JSONObject jsonObject = JSONObject.fromObject(responseMessage);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);
		// return
		return null;
	}

	/**
	 * 유저 회원가입
	 * 
	 * @param loginId
	 * @param loginPw
	 * @param pushKey
	 * @param OS
	 * @param userPhone
	 * @param userName
	 * @param session
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_join.go")
	public String mJoinDoController(
			/*
			 * @RequestParam(value="loginId", required=false, defaultValue="")
			 * String loginId,
			 * 
			 * @RequestParam(value="loginPw", required=false, defaultValue="")
			 * String loginPw,
			 */
			@RequestParam(value = "pushKey", required = false, defaultValue = "") String pushKey,
			@RequestParam(value = "OS", required = false, defaultValue = "ANDROID") String OS,
			@RequestParam(value = "userPhone", required = false, defaultValue = "") String struserPhone,
			@RequestParam(value = "countryCode", required = false, defaultValue = "82") String countryCode,
			/*
			 * @RequestParam(value="userName", required=false, defaultValue="")
			 * String userName,
			 */
			HttpSession session, HttpServletResponse res)
	{
		if(countryCode==null||countryCode.trim().equals(""))
		{
			countryCode = "82";
		}
		String userPhone = memberDao.normalizedPhone(struserPhone, countryCode); 
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isCorrectId = memberDao.isuserPhone(userPhone); // 휴대폰 번호 체크
		String message = "";
		boolean result = false;
		Member user = new Member();

		if (isCorrectId)
		{ // 현재 가입된 아이디가 있음
			result = true;
			memberDao.updateOverRapPushkey(pushKey, userPhone);
			user = memberDao.getMyInfo(userPhone); // 내정보 던짐
			message = "회원가입이 완료되었습니다.";
		}
		else
		{ // 현재 가입된 아이디가 없음.
			result = memberDao.InsertMember(pushKey, userPhone, OS);
			user = memberDao.getMyInfo(userPhone); // 내정보 던짐
			message = "회원가입이 완료되었습니다.";

		}

		map.put("result", result);
		map.put("message", message);
		map.put("data", user);
		map.put("is_member", isCorrectId);

		JSONObject jsonObject = JSONObject.fromObject(map);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);

		return null;

	}

	/**
	 * @author TuanNA
	 * @since 05-13-2015
	 * @param userSeq
	 * @param session
	 * @param res
	 * @return
	 */
	@RequestMapping("/m_get_user_info.go")
	public String mGetUserInfo(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			HttpSession session, HttpServletResponse res)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String msg_code = "USER_PROFILE_003";
		String msg_str = "Get user info sucess!";
		Member user = memberDao.getUserInfo(userSeq);
		if (user == null)
		{
			result = false;
			msg_code = "SYS_002";
			msg_str = "User does not exists!";
		}
		map.put("result", result);
		map.put("msg_code", msg_code);
		map.put("msg_str", msg_str);
		map.put("info", user);
		JSONObject jsonObject = JSONObject.fromObject(map);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);

		return null;
	}

	@RequestMapping("/m_updateprofile.go")
	public String mUpdateProfile(
			@RequestParam(value = "userName", required = false, defaultValue = "") String userName,
			@RequestParam(value = "userPhone", required = false, defaultValue = "") String userPhone,
			@RequestParam(value = "userPhoto", required = false, defaultValue = "") String userPhoto,
			HttpSession session, HttpServletResponse res)
	{

		Map<String, Object> map = new HashMap<String, Object>();

		boolean result = memberDao.UpdateUserProfile(userName, userPhoto,
				userPhone);
		map.put("result", result);

		JSONObject jsonObject = JSONObject.fromObject(map);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);

		return null;
	}

	@SuppressWarnings("unused")
	@RequestMapping("/m_contact.go")
	public String mContactListController(
			@RequestParam(value = "userPhone", required = false, defaultValue = "") String userPhone,
			@RequestParam(value = "contact", required = false, defaultValue = "") String contact,
			HttpSession session, HttpServletResponse res)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		String message = "";
		boolean result = false;
		Member user = new Member();
		user = memberDao.getMyInfo(userPhone); // 내정보 던짐
		List<Member> list = memberDao.getContactList(contact, userPhone);

		// map.put("result", result);
		// map.put("message", message);
		map.put("data", list);
		map.put("myinfo", user);
		JSONObject jsonObject = JSONObject.fromObject(map);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);

		return null;
	}

	/**
	 * 로그인 처리
	 * 
	 * @param loginId
	 * @param loginPw
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_login.go")
	public void mLoginDoController(
			@RequestParam(value = "loginId", required = false, defaultValue = "") String loginId,
			@RequestParam(value = "loginPw", required = false, defaultValue = "") String loginPw,
			@RequestParam(value = "pushKey", required = false, defaultValue = "") String pushKey,
			@RequestParam(value = "OS", required = false, defaultValue = "ANDROID") String OS,
			@RequestParam(value = "userPhone", required = false, defaultValue = "") String userPhone,
			HttpSession session, HttpServletResponse res)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		String message = "";
		boolean isCorrectId = false;
		boolean isCorrectPassword = false;
		Member user = new Member();

		isCorrectId = memberDao.correctId(loginId);
		if (isCorrectId)
		{
			isCorrectPassword = memberDao.correctPw(loginId, loginPw);
			if (isCorrectPassword)
			{
				user = memberDao.getUserInfo(Integer.parseInt(loginId) );
			}
			else
			{
				message = "비밀번호가 올바르지 않습니다.";
			}
		}
		else
		{
			message = "존재하지 않는 아이디 입니다.";
		}

		if (isCorrectId && isCorrectPassword)
		{

			// 중복 푸시키 제거
			memberDao.updateOverRapPushkey(pushKey, loginId);

			// 푸시정보 등록
			memberDao.updatePushKey(loginId, pushKey, OS, userPhone);

			result = true;
			message = "로그인 성공";
			map.put("user", user);

		}

		map.put("result", result);
		map.put("message", message);

		JSONObject jsonObject = JSONObject.fromObject(map);

		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);

	}

	/**
	 * 푸시키,os 가져오기
	 * 
	 * @param userSeq
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/m_get_pushkey.go")
	public String getPushKeyController(
			@RequestParam(value = "userSeq", required = false, defaultValue = "0") int userSeq,
			HttpSession session, HttpServletResponse res, Model model)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";
		Member user = new Member();

		user = memberDao.getPushKeySeq(userSeq);

		map.put("result", result);
		map.put("message", userSeq);
		map.put("userOs", user.getUser_os());
		map.put("pushKey", user.getUseR_pushkey());

		JSONObject jsonObject = JSONObject.fromObject(map);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);

		Response.responseWrite(res, outputStr);

		return null;
	}

	/**
	 * Brainyx 멤버리스트 가져오기
	 * 
	 * @param userId
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_get_member_list.go")
	public String getMemberListController(
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			HttpSession session, HttpServletResponse res, Model model)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "Brainyx 멤버리스트";

		List<Member> userList = memberDao.getBrainyxMemberList();

		Member userNew = new Member();
		List<Member> userNewList = new ArrayList<Member>();

		for (Member user : userList)
		{
			if (user.getUser_id().toString().equals(userId))
			{
				userNew = user;
			}
			else
			{
				userNewList.add(user);
			}
		}

		map.put("result", result);
		map.put("message", message);
		map.put("data", userNewList);
		map.put("myinfo", userNew);

		JSONObject jsonObject = JSONObject.fromObject(map);

		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputStr);

		return null;
	}

	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @param userSeq
	 * @param userPhoto
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_set_profile_photo.go")
	public String mSetProfilePhoto(
			@RequestParam(value = "user_seq", required = false, defaultValue = "") int userSeq,
			@RequestParam(value = "user_photo", required = false, defaultValue = "") String userPhoto,
			HttpSession session, HttpServletResponse res, Model model)
	{

		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "USER_PROFILE_004";
		String msg_str = "Set profile photo succesfull!";
		boolean result = true;
		Member user = null;
		try
		{
			// check user exists
			if (memberDao.isExistsMember(userSeq))
			{
				// set profile photo
				int resultCode = memberDao.setProfilePhoto(userPhoto, userSeq);
				if (resultCode != -1)
				{
					result = true;
					// message = "업데이트 되었습니다.";
					user = memberDao.getUserInfo(userSeq);
				}
				else throw new AppException("SYS-003",
						"Can't set profile photo!");
			}
			else
			{
				throw new AppException("SYS-002", "User does not exists!");
			}

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
			responseMessage.put("info", user);
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
	 * @param userStmsg
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_set_profile_stmsg.go")
	public String mSetProfileStmsg(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "user_stmsg", required = true, defaultValue = "") String userStmsg,
			HttpSession session, HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "USER_PROFILE_005";
		String msg_str = "Set profile status message success!";
		boolean result = true;
		Member user = null;
		try
		{
			// check user exists
			if (memberDao.isExistsMember(userSeq))
			{
				// set status message
				int resultCode = memberDao.setProfileStmsg(userStmsg, userSeq);
				if (resultCode != -1)
				{
					result = true;
					// message = "업데이트 되었습니다.";
					user = memberDao.getUserInfo(userSeq);
				}
				else throw new AppException("SYS-004",
						"Can't set profile status!");
			}
			else
			{
				throw new AppException("SYS-002", "User does not exists!");
			}

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
			responseMessage.put("info", user);
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
	 * @param userName
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_set_profile_user_name.go")
	public String mSetProfileUserName(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "user_name", required = true, defaultValue = "") String userName,
			HttpSession session, HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "USER_PROFILE_006";
		String msg_str = "Set profile user name success!";
		boolean result = true;
		Member user = null;
		try
		{
			// check user exists
			if (memberDao.isExistsMember(userSeq))
			{
				// check user name exists
				if (memberDao.isExistsUserName(userName)) throw new AppException(
						"SYS-005", "User name already exists!");
				// set user name
				int resultCode = memberDao
						.setProfileUserName(userName, userSeq);
				if (resultCode != -1)
				{
					result = true;
					// message = "업데이트 되었습니다.";
					user = memberDao.getUserInfo(userSeq);
				}
				else throw new AppException("SYS-006",
						"Can't set profile user name!");
			}
			else
			{
				throw new AppException("SYS-002", "User does not exists!");
			}

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
			responseMessage.put("info", user);
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
	 * @param userEmail
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_set_profile_email.go")
	public String mSetProfileEmail(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "user_email", required = true, defaultValue = "") String userEmail,
			HttpSession session, HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "USER_PROFILE_008";
		String msg_str = "Set profile email success!";
		boolean result = true;
		Member user = null;
		try
		{
			// check user exists
			if (memberDao.isExistsMember(userSeq))
			{
				// check user name exists
				if (memberDao.isExistsEmail(userEmail)) throw new AppException(
						"SYS-009", "Email already exists!");
				// set user name
				int resultCode = memberDao
						.setProfileEmail(userEmail, userSeq);
				if (resultCode != -1)
				{
					result = true;
					// message = "업데이트 되었습니다.";
					user = memberDao.getUserInfo(userSeq);
				}
				else throw new AppException("SYS-010",
						"Can't set profile email!");
			}
			else
			{
				throw new AppException("SYS-002", "User does not exists!");
			}

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
			responseMessage.put("info", user);
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
	 * @param cur_pwd
	 * @param new_pwd
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_set_profile_pwd.go")
	public String mSetProfilePwd(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "cur_pwd", required = true, defaultValue = "") String curPwd,
			@RequestParam(value = "new_pwd", required = true, defaultValue = "") String newPwd,
			HttpSession session, HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "USER_PROFILE_007";
		String msg_str = "Set profile pass word success!";
		boolean result = true;
		Member user = null;
		try
		{
			// check user exists
			if (memberDao.isExistsMember(userSeq))
			{
				// check current password
				if (!memberDao.isCorrectPassWord(userSeq, curPwd)) throw new AppException(
						"SYS-007", "Pass word does not correct!");
				// set profile photo
				int resultCode = memberDao.setProfilePwd(newPwd, userSeq);
				if (resultCode != -1)
				{
					result = true;
					// message = "업데이트 되었습니다.";
					user = memberDao.getUserInfo(userSeq);
				}
				else throw new AppException("SYS-008",
						"Can't set new pass word!");
			}
			else
			{
				throw new AppException("SYS-002", "User does not exists!");
			}

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
			responseMessage.put("info", user);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}

	/**
	 * 베이스
	 * 
	 * @param userSeq
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/m_base.go")
	public String BaseController(
			@RequestParam(value = "userSeq", required = false, defaultValue = "0") int userSeq,
			HttpSession session, HttpServletResponse res, Model model)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "";
		Member user = new Member();

		user = memberDao.getPushKeySeq(userSeq);

		map.put("result", result);
		map.put("message", userSeq);
		map.put("userOs", user.getUser_os());
		map.put("pushKey", user.getUseR_pushkey());

		JSONObject jsonObject = JSONObject.fromObject(map);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);

		Response.responseWrite(res, outputStr);

		return null;
	}

	/**
	 * 멤버수가져오기
	 * 
	 * @param session
	 * @param res
	 * @param model
	 * @return
	 */
	@RequestMapping("/m_get_botalks_member_count.go")
	public String getMemberCountController(HttpSession session,
			HttpServletResponse res, Model model)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = true;
		String message = "보톡스가입인원수";
		int data = 0;

		data = memberDao.getMemberCount();

		map.put("result", result);
		map.put("message", message);
		map.put("data", data);

		JSONObject jsonObject = JSONObject.fromObject(map);
		Gson gson = new Gson();
		String outputStr = gson.toJson(jsonObject);

		Response.responseWrite(res, outputStr);

		return null;
	}
	/**
	 * @author TuanNA
	 * @since 05-14-2015
	 * @return
	 */
	@RequestMapping("/m_update_member_url.go")
	public String mUpdateMemberUrl(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			@RequestParam(value = "url", required = true, defaultValue = "") String url,
			HttpSession session, HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "USER_PROFILE_009";
		String msg_str = "Update Member url success!";
		boolean result = true;
		try
		{
			memberDao.updateMemberUrl(userSeq, url);
			
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
	 * @return
	 */
	@RequestMapping("/m_get_member_url.go")
	public String mGetMemberUrl(
			@RequestParam(value = "user_seq", required = true, defaultValue = "") int userSeq,
			HttpSession session, HttpServletResponse res, Model model)
	{
		Map<String, Object> responseMessage = new HashMap<String, Object>();
		String msg_code = "USER_PROFILE_010";
		String msg_str = "Get Member url success!";
		boolean result = true;
		String url = "";
		try
		{
			url = memberDao.getMemberUrl(userSeq);
			
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
			responseMessage.put("url", url);
			// response
			JSONObject jsonObject = JSONObject.fromObject(responseMessage);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			Response.responseWrite(res, outputStr);
		}
		return null;
	}
}
