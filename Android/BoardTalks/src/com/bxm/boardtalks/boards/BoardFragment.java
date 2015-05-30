package com.bxm.boardtalks.boards;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import kr.co.boardtalks.R;

import com.br.chat.activity.ActivityBoard;
import com.br.chat.activity.ActivityPost;
import com.bxm.boardtalks.main.BaseFragment;
import com.bxm.boardtalks.util.WriteFileLog;

public class BoardFragment extends BaseFragment {
	
	View view; 

	@Override
	public void OnReceiveMsgFromActivity(int msgId, Object param1, Object param2) {

	}

	@Override
	public void Onback() {

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			getActivity().setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
			if (view == null) {
				view = inflater.inflate(R.layout.fragment_board, container, false);
				ListView lvPosts = (ListView) view.findViewById(R.id.lv_posts);
				lvPosts.setAdapter(new PostAdapter(generateData(), getActivity()));
				
				lvPosts.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
						ActivityPost.link = generateData().get(position).link;
						startActivity(new Intent(getActivity(), ActivityPost.class));
						
					}
				});
				
			} else {
				ViewGroup parent = (ViewGroup) view.getParent();
				if (parent != null)
					parent.removeView(view);
			}
		} catch (Exception e) {
			WriteFileLog.writeException(e);
		}
		return view;
	}
	
	ArrayList<Post> generateData(){
		ArrayList<Post> data = new ArrayList<Post>();
		Post p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.3.15", "안재모", "http://blog.korea.net/?p=26605", "http://www.freelargeimages.com/wp-content/uploads/2015/04/Profile_Pictures_For_Facebook_04.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.3.15", "안재모", "http://blog.korea.net/?p=26605", "http://dantri21.vcmedia.vn/zoom/130_100/j2ss7Ldngmz50AuxF7FR95yiZNlVN/Image/2015/05/3/tram1-f0ccb.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.5.1", "안재모", "http://blog.korea.net/?p=23635", "http://dantri21.vcmedia.vn/zoom/130_100/qBmhJ8qjBodls0C2KN9J8PNJuaavPM/Image/2015/05/Bar-Real-b-5b091.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.3.15", "안재모", "http://blog.korea.net/?p=23635", "http://dantri21.vcmedia.vn/zoom/130_100/rAhYpSS0DLkFONJg2TQqcccccccccc/Image/2015/05/a-a7708.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.9.25", "안재모", "http://blog.korea.net/?p=26605", "http://dantri21.vcmedia.vn/zoom/130_100/qBmhJ8qjBodls0C2KN9J8PNJuaavPM/Image/2015/05/Bar-Real-b-5b091.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.3.15", "안재모", "http://blog.korea.net/?p=23635", "http://www.freelargeimages.com/wp-content/uploads/2015/04/Profile_Pictures_For_Facebook_04.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.9.25", "안재모", "http://blog.korea.net/?p=23635", "http://dantri21.vcmedia.vn/zoom/130_100/qBmhJ8qjBodls0C2KN9J8PNJuaavPM/Image/2015/05/Bar-Real-b-5b091.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.3.15", "안재모", "http://blog.korea.net/?p=26605", "http://dantri21.vcmedia.vn/zoom/130_100/rAhYpSS0DLkFONJg2TQqcccccccccc/Image/2015/05/a-a7708.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.3.15", "안재모", "http://blog.korea.net/?p=23635", "http://www.freelargeimages.com/wp-content/uploads/2015/04/Profile_Pictures_For_Facebook_04.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.9.25", "안재모", "http://blog.korea.net/?p=26470", "http://dantri21.vcmedia.vn/zoom/130_100/rAhYpSS0DLkFONJg2TQqcccccccccc/Image/2015/05/a-a7708.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.1.2", "안재모", "http://blog.korea.net/?p=23635", "http://dantri21.vcmedia.vn/zoom/130_100/qBmhJ8qjBodls0C2KN9J8PNJuaavPM/Image/2015/05/Bar-Real-b-5b091.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.3.15", "안재모", "http://blog.korea.net/?p=26470", "http://dantri21.vcmedia.vn/zoom/130_100/j2ss7Ldngmz50AuxF7FR95yiZNlVN/Image/2015/05/3/tram1-f0ccb.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.9.25", "안재모", "http://blog.korea.net/?p=26605", "http://www.freelargeimages.com/wp-content/uploads/2015/04/Profile_Pictures_For_Facebook_04.jpg");
		data.add(p);
		p = new Post("", "어린이 2 중국 의 인상적인 라인은 아버지 Playstore DC 기계 가 설치되어 있습니다. 설치 는 울창한 LUM 오류 를 엽니 다. 의사 결정 종료 취침 . 솔직히 , 그 안드로이드는 지금 k 개의 LUN 플레이 서비스를 치고 있다. 그래서 ROM 지원 및 검토 를 기다립니다. K 의 모든 나무 만 대 자신의 플레이 를 실행 에", "2015.3.15", "안재모", "http://blog.korea.net/?p=23635", "http://dantri21.vcmedia.vn/zoom/130_100/rAhYpSS0DLkFONJg2TQqcccccccccc/Image/2015/05/a-a7708.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.3.15", "안재모", "http://blog.korea.net/?p=26470", "http://dantri21.vcmedia.vn/zoom/130_100/qBmhJ8qjBodls0C2KN9J8PNJuaavPM/Image/2015/05/Bar-Real-b-5b091.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.9.25", "안재모","http://blog.korea.net/?p=26605", "http://www.freelargeimages.com/wp-content/uploads/2015/04/Profile_Pictures_For_Facebook_04.jpg");
		data.add(p);
		p = new Post("", "당신은 답답한 셔츠 폐쇄 세트 가 너무 지쳐 있다면 ? 당신은 여전히 ​​하나의 드레스 는 가벼운 할 뿐만 아니라, 매우 우아한 해야 🚬 📛So 짧은 소매 셔츠 는 당신 의 대답은 !", "2015.3.15", "안재모", "http://blog.korea.net/?p=26470", "http://dantri21.vcmedia.vn/zoom/130_100/j2ss7Ldngmz50AuxF7FR95yiZNlVN/Image/2015/05/3/tram1-f0ccb.jpg");
		data.add(p);
		
		return data;
	}

}
