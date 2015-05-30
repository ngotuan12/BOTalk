package kr.brainyx.chat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import kr.brainyx.chat.dao.UserDao;
import kr.brainyx.chat.dto.Member;
import kr.brainyx.util.Response;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

@Controller
public class UserController {

	@Autowired private UserDao userDao;

	// 페이지당 아이템 갯수
	@Value("#{config['page.itemCountPerPage']}")
	int ITEM_COUNT_PER_PAGE;
	
	// 페이징당 페이지 갯수
	@Value("#{config['page.pageCountPerPaging']}")
	int PAGE_COUNT_PER_PAGING;
	
	// 파일 루트
	@Value("#{config['file.root']}")
	String FILE_ROOT;
	
	// 파일 최대크기(Mb)
	@Value("#{config['file.maxSize']}")
	int FILE_MAX_SIZE;
	
	String FILE_PATH = "";
	String FILE_LOCAL_PATH = "";

	@RequestMapping("/user_info.go")
	public String getUserInfoController(
			@RequestParam(value="userId", required=false, defaultValue="") String userId,
			Model model
		) {
		
		Member user = userDao.getUser(userId);
		
		model.addAttribute("user", user);
		return "user_info";
	}

	@RequestMapping("/user_list.go")
	public String getUserListController(
			@RequestParam(value="page", required=false, defaultValue="1") int page,
			@RequestParam(value="keyword", required=false, defaultValue="") String keyword,
			HttpServletResponse res
		) {
		
		List<?> userList = null;
		if (page < 1) {
			userList = userDao.getUserList();
		} else if (keyword.equals("")) {
			userList = userDao.getUserList(page, ITEM_COUNT_PER_PAGE);
		} else {
			userList = userDao.getUserList(keyword, page, ITEM_COUNT_PER_PAGE);
		}
		

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("message", "사용자 목록입니다.");
		map.put("userList", userList);
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		Gson gson = new Gson();
	    String outputstr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputstr);

		return null;
	}

	
}
