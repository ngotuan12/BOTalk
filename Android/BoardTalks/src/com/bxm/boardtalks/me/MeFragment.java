package com.bxm.boardtalks.me;

import kr.co.boardtalks.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.chat.ChatGlobal;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.SharedManager;
import com.bxm.boardtalks.main.BaseFragment;
import com.bxm.boardtalks.util.WriteFileLog;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MeFragment extends BaseFragment implements OnClickListener {

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
			getActivity()
					.setTheme(
							android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
			if (view == null) {
				view = inflater.inflate(R.layout.fragment_me, container, false);
				ImageView imgViewAvatar = (ImageView) view
						.findViewById(R.id.imgViewAvatar);
				TextView tvPhoneNumber = (TextView) view
						.findViewById(R.id.tv_phone_number);
				String user_phone = SharedManager.getInstance().getString(
						getActivity(), BaseGlobal.User_Phone);
				String user_seq = SharedManager.getInstance().getString(
						getActivity(), BaseGlobal.User_Seq);
				user_phone = "+" + user_phone;
				tvPhoneNumber.setText(user_phone);
				ImageLoader.getInstance().displayImage(
						ChatGlobal.getMemberFaceThumURL(user_seq),
						imgViewAvatar);
				
				imgViewAvatar.setOnClickListener(this);
				view.findViewById(R.id.span_user_name).setOnClickListener(this);
				view.findViewById(R.id.span_status).setOnClickListener(this);
				view.findViewById(R.id.span_my_sns).setOnClickListener(this);
				view.findViewById(R.id.span_her).setOnClickListener(this);
				view.findViewById(R.id.span_setting).setOnClickListener(this);
				
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

	public void changeAvatar(View v) {

	}

	public void ChangeUserName(View v) {

	}

	public void changeStatus(View v) {

	}

	public void setUpSnSAccount(View v) {

	}

	public void setUpHer(View v) {

	}

	public void setting(View v) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgViewAvatar :
			
			changeAvatar(v);
			
			break;
		case R.id.span_user_name:
			
			ChangeUserName(v);
			
			break;
			case R.id.span_her:
				setUpHer(v);
				break;
			case R.id.span_status:
				changeStatus(v);
				break;
			case R.id.span_my_sns:
				setUpSnSAccount(v);
				break;
			case R.id.span_setting:
				
				setting(v);
				
				break;
				
		default:
			break;
		}
	}

}
