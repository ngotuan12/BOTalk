package kr.brainyx.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@RequestMapping("/welcome.go")
	public String home(
			@RequestParam (value="name", required=false, defaultValue="YOU") String name,
			Model model
		) {
		
		HashMap<String, String> hm = new HashMap<String, String>();
		List<HashMap<String, String>> hiList = new ArrayList<HashMap<String, String>>();
		hm.put("name", "hanju");
		hiList.add(hm);

		HashMap<String, String> hm2 = new HashMap<String, String>();
		hm2.put("name", "an");
		hiList.add(hm2);
		
		HashMap<String, String> hm3 = new HashMap<String, String>();
		hm3.put("name", "kim");
		hiList.add(hm3);
		

		model.addAttribute("hiList", hiList);
		model.addAttribute("username", name);
		return "index";
	}
	
}
