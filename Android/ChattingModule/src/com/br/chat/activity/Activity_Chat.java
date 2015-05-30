package com.br.chat.activity;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.br.chat.ChatGlobal;
import com.br.chat.ChattingApplication;
import com.br.chat.adapter.AdapterChat;
import com.br.chat.adapter.AdapterChat.FaceViewClickListner;
import com.br.chat.adapter.AdapterChat.FailMsgSend;
import com.br.chat.adapter.AdapterChatDrawer;
import com.br.chat.adapter.ChatStatusType;
import com.br.chat.adapter.ChatType;
import com.br.chat.adapter.DrawerItemChatTop;
import com.br.chat.gallery.CustomGallery;
import com.br.chat.service.ChattingService;
import com.br.chat.socket.ConnetedSocket.ConnectedListner;
import com.br.chat.socket.SendHandlerQue;
import com.br.chat.util.CreateKey;
import com.br.chat.util.NetworkUtil;
import com.br.chat.util.WriteFileLog;
import com.br.chat.view.ImageButtonIconText;
import com.br.chat.vo.ChatVo;
import com.br.chat.vo.MemberVo;
import com.br.chat.vo.MessageVo;
import com.br.chat.vo.UserVo;
import com.brainyxlib.BaseGlobal;
import com.brainyxlib.BrUtilManager;
import com.brainyxlib.SharedManager;
import com.brainyxlib.image.BrImageUtilManager;
import com.brainyxlib.image.ImageVo;
import com.chattingmodule.R;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment.OnEmojiconClickedListener;
import com.rockerhieu.emojicon.EmojiconTextView;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.EmojiconsFragment.OnEmojiconBackspaceClickedListener;
import com.rockerhieu.emojicon.emoji.Emojicon;

public class Activity_Chat extends ActionBarActivity implements
		OnEmojiconClickedListener, OnEmojiconBackspaceClickedListener
{

	private static Activity_Chat mInstance;

	public static Activity_Chat getInstance()
	{
		return mInstance;
	}

	public String getRoomSeq()
	{
		return chatvo.chatRoomSeq;
	}

	public void setRoomSeq(String roomseq)
	{
		chatvo.chatRoomSeq = roomseq;
	}

	public void setRoomMember(String[] members)
	{
		chatvo.chatRoomMember = members;
	}

	private DrawerLayout mDrawer;
	private StickyListHeadersListView mStickyListHeadersListView;
	// private ListView mStickyListHeadersListView;
	private ImageButtonIconText mImageButtonIconText_send;
	private EmojiconEditText mEditText_message;
	private LinearLayout mLinearLayout_root;
	private LinearLayout mLinearLayout_drawer;
	private ListView mDrawerList;
	private ChatVo chatvo;
	public ArrayList<MessageVo> messg = new ArrayList<MessageVo>();
	private Handler mHandler = new Handler();
	private AdapterChat mAdapterChat;
	private String sendname = "";
	public ArrayList<Object> mArrDrawerItem;
	public ArrayList<UserVo> arrVHUser = new ArrayList<UserVo>();
	SendHandlerQue messageque = null;
	ArrayList<ImageVo> filekey = new ArrayList<ImageVo>();
	ArrayList<String> mSelectImg = new ArrayList<String>();
	public int mPage = 1;
	public boolean mLock = true, mIsBottomOfList = false;
	LinearLayout newmsglayout;

	EmojiconTextView newmsgtextview;
	// public int mSelection = 20;

	LinearLayout addfile_layout;

	private int keyboardHeight;

	private RelativeLayout slideDownView;
	private View handle;

	private WebView webView;

	private EditText edtUrl;

	private FrameLayout popUpView;
	private LinearLayout emoticonsCover;
	private PopupWindow popupWindow;
	private LinearLayout parentLayout;

	private boolean isKeyBoardVisible;

	private static final int CONTENT_VIEW_ID = 10101010;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		setContentView(R.layout.activity_chat);
		mInstance = this;
		sendname = SharedManager.getInstance().getString(Activity_Chat.this,
				BaseGlobal.User_name);
		ChattingApplication.getInstance().clearNotifyMsg();
		init();
		final float popUpheight = getResources().getDimension(
				R.dimen.keyboard_height);
		changeKeyboardHeight((int) popUpheight);
		popupWindow = new PopupWindow(popUpView, LayoutParams.MATCH_PARENT,
				keyboardHeight, false);
		// setEmojiconFragment(false);
		checkKeyboardHeight(parentLayout);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// ChattingApplication.getInstance().clearNotifyMsg();
		if (ChattingApplication.getInstance().socket != null
				&& !ChattingApplication.getInstance().socket.getSocket()
						.isClosed())
		{
			Log.e("", "재접속 시도1");
			ChattingApplication.getInstance().socket.StartSocket(
					chatvo.chatRoomMember, CreateKey.getFileKey(),
					chatvo.chatRoomSeq, chatvo.chatRoomOwner, sendname,
					chatvo.chatRoomType, chatvo.chatRoomRevName,
					chatvo.chatRoomMemberName);
		}
		else
		{
			Log.e("", "재접속 시도2");
			ChattingApplication.getInstance().ConnectSocket(
					new ConnectedListner()
					{

						@Override
						public void setConnectedListner()
						{
							ChattingApplication.getInstance().socket
									.StartSocket(chatvo.chatRoomMember,
											CreateKey.getFileKey(),
											chatvo.chatRoomSeq,
											chatvo.chatRoomOwner, sendname,
											chatvo.chatRoomType,
											chatvo.chatRoomRevName,
											chatvo.chatRoomMemberName);
						}
					});

		}

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mInstance = null;
	}

	@Override
	public void onBackPressed()
	{
		if (mDrawer.isDrawerOpen(GravityCompat.END)) mDrawer
				.closeDrawer(mLinearLayout_drawer);
		else finish();
	}

	private void init()
	{

		parentLayout = (LinearLayout) findViewById(R.id.activtyChat_LinearLayout_root);
		emoticonsCover = (LinearLayout) findViewById(R.id.footer_for_emoticons);
		popUpView = (FrameLayout) getLayoutInflater().inflate(
				R.layout.emoicon_popup, null);
		popUpView.setId(CONTENT_VIEW_ID);

		edtUrl = (EditText) findViewById(R.id.edt_url);
		edtUrl.setOnEditorActionListener(new OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_DONE)
				{
					String url = v.getText().toString();
					if (!url.startsWith("http")) url = "http://" + url;
					webView.loadUrl(url);
					return true;
				}
				return false;
			}
		});

		mStickyListHeadersListView = (StickyListHeadersListView) this
				.findViewById(R.id.activtyChat_StickyListHeadersListView);
		// View header = findViewById(R.id.webViewHolder);
		// header = getLayoutInflater().inflate(R.layout.webview, null);

		slideDownView = (RelativeLayout) findViewById(R.id.slide_down_view);
		handle = findViewById(R.id.handle);

		handle.setOnTouchListener(new OnTouchListener()
		{

			/* Starting Y point (where touch started). */
			float yStart = 0;

			/* Default height when in the open state. */
			float closedHeight = 600;

			/* Default height when in the closed state. */
			float openHeight = 700;

			/* The height during the transition (changed on ACTION_MOVE). */
			float currentHeight;

			/*
			 * The last y touch that occurred. This is used to determine if the
			 * view should snap up or down on release. Used in conjunction with
			 * directionDown boolean.
			 */
			float lastY = 0;
			boolean directionDown = false;

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{

				Log.e("onTouch", "kk");

				switch (event.getAction())
				{

				/* User tapped down on screen. */
				case MotionEvent.ACTION_DOWN:
					// User has tapped the screen
					yStart = event.getRawY();
					lastY = event.getRawY();
					currentHeight = slideDownView.getHeight();
					break;

				/* User is dragging finger. */
				case MotionEvent.ACTION_MOVE:

					// Calculate the total height change thus far.
					float totalHeightDiff = event.getRawY() - yStart;

					// Adjust the slide down height immediately with touch
					// movements.
					LayoutParams params = slideDownView.getLayoutParams();
					params.height = (int) (currentHeight + totalHeightDiff);
					slideDownView.setLayoutParams(params);

					// Check and set which direction drag is moving.
					if (event.getRawY() > lastY)
					{
						directionDown = true;
					}
					else
					{
						directionDown = false;
					}

					// Set the lastY for comparison in the next ACTION_MOVE
					// event.
					lastY = event.getRawY();
					break;

				/* User lifted up finger. */
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:

					break;

				}
				return true;

			}
		});

		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				edtUrl.setText(url);
				return false;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{

				edtUrl.setText(url);

				super.onPageStarted(view, url, favicon);
			}

		});

		webView.loadUrl("https://www.google.com");

		try
		{
			messageque = new SendHandlerQue(this);
			this.findViewById(R.id.activityChat_ImageButton_close)
					.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							Log.e("", "");
							finish();
							// Receiver_refresh refresh = new
							// Receiver_refresh(ActivityChat.this, null);
							// refresh.sendBroadCast();
							// finish();
						}
					});

			this.findViewById(R.id.activityChat_ImageButton_toggleDrawer)
					.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							if (mDrawer.isDrawerOpen(GravityCompat.END)) mDrawer
									.closeDrawer(mLinearLayout_drawer);
							else mDrawer.openDrawer(mLinearLayout_drawer);
						}
					});

			this.findViewById(R.id.activityChat_LinearLayout_leaveBox)
					.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							mDrawer.closeDrawer(mLinearLayout_drawer);
							BrUtilManager.getInstance().ShowDialog2btn(
									Activity_Chat.this, null,
									getString(R.string.alert_msg_leave),
									new BrUtilManager.dialogclick()
									{
										@Override
										public void setondialogokclick()
										{
											SendMessageVo(ChatType.leave,
													chatvo.chatRoomOwner,
													chatvo.chatRoomSeq,
													chatvo.chatRoomRevName);
										}

										@Override
										public void setondialocancelkclick()
										{
										}
									});
						}
					});

			findViewById(R.id.activityChat_ImageView_pic).setOnClickListener(
					new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							try
							{
								if (addfile_layout.getVisibility() == View.VISIBLE)
								{
									addfile_layout.setVisibility(View.GONE);
								}
								else
								{
									addfile_layout.setVisibility(View.VISIBLE);
								}

								/*
								 * String[] array = {"앨범에서가져오기", "사진찍기"};
								 * BrUtilManager
								 * .getInstance().ShowArrayDialog(Activity_Chat
								 * .this, null, array, -1, new
								 * BrUtilManager.setOnItemChoice() {
								 * 
								 * @Override public void setOnItemClick(int
								 * arg0) { try { switch(arg0){ case 0: Intent
								 * intent = new
								 * Intent(Activity_Chat.this,CustomGallery
								 * .class); intent.putExtra("size",
								 * ChatGlobal.IMGTOTALCOUNT -
								 * filekey.size());//선택가능한갯수
								 * startActivityForResult(intent,
								 * ChatGlobal.CAMERA_ALBUM_REQUEST_CODE); break;
								 * case 1:
								 * 
								 * intent = new
								 * Intent(Activity_Chat.this,CameraActivity
								 * .class) ; intent.putExtra("what", 2);
								 * startActivityForResult(intent,
								 * ChatGlobal.TABLET_CAMERA_ALBUM_REQUEST_CODE);
								 * if
								 * (ChattingApplication.getInstance().isTablet.
								 * equals("phone")){
								 * BxmIntent.goCameraForResult(
								 * Activity_Chat.this,
								 * ChatGlobal.CAMERA_CROP_REQUEST_CODE, null);
								 * }else{ intent = new
								 * Intent(Activity_Chat.this,
								 * CameraActivity.class) ;
								 * intent.putExtra("what", 2);
								 * startActivityForResult(intent,
								 * ChatGlobal.TABLET_CAMERA_ALBUM_REQUEST_CODE);
								 * } break; } } catch (Exception e) {
								 * WriteFileLog.writeException(e); } } });
								 */
							}
							catch (Exception e)
							{
								WriteFileLog.writeException(e);
							}
						}
					});

			// mStickyListHeadersListView = (StickyListHeadersListView)
			// this.findViewById(R.id.activtyChat_StickyListHeadersListView);

			mEditText_message = (EmojiconEditText) this
					.findViewById(R.id.activityChat_EditText_message);
			mImageButtonIconText_send = (ImageButtonIconText) this
					.findViewById(R.id.activityChat_ImageButtonIconText_send);
			mLinearLayout_root = (LinearLayout) this
					.findViewById(R.id.activtyChat_LinearLayout_root);

			// mStickyListHeadersListView.setAreHeadersSticky(true);
			mStickyListHeadersListView.setDivider(null);
			mStickyListHeadersListView.setOnScrollListener(onscroll);
			mStickyListHeadersListView.setOnItemClickListener(mListListener);
			// mStickyListHeadersListView.setStackFromBottom(true);
			mDrawer = (DrawerLayout) findViewById(R.id.activityChat_DrawerLayout);
			mDrawer.setDrawerShadow(R.drawable.drawer_shadow,
					GravityCompat.START);
			mDrawerList = (ListView) findViewById(R.id.activityChat_ListView_drawer);
			mLinearLayout_drawer = (LinearLayout) findViewById(R.id.activtyChat_LinearLayout_drawer);
			mDrawerList.setDivider(null);
			newmsglayout = (LinearLayout) findViewById(R.id.newmsglayout);
			newmsgtextview = (EmojiconTextView) findViewById(R.id.newmsgtextview);
			newmsglayout.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					mStickyListHeadersListView.smoothScrollToPosition(messg
							.size());
				}
			});
			mAdapterChat = new AdapterChat(Activity_Chat.this, messg);
			mStickyListHeadersListView.setAdapter(mAdapterChat);
			Intent intent = getIntent();
			try
			{
				chatvo = (ChatVo) intent
						.getSerializableExtra(ChatGlobal.ChatRoomObj);
			}
			catch (Exception e)
			{
				WriteFileLog.writeException(e);
			}
			mAdapterChat.setFaceViewClickListner(new FaceViewClickListner()
			{
				@Override
				public void setOnFaceViewClickListner(Intent intent)
				{
					intent.putExtra(ChatGlobal.ProfileIntentChatType,
							chatvo.chatRoomType);
					startActivityForResult(intent, ChatGlobal.OnChatReQustCode);
				}
			});
			mAdapterChat.setOnFailMsgSendListner(new FailMsgSend()
			{
				@Override
				public void setOnFailMsgSendListner(String key)
				{
					try
					{
						MessageVo msgvo = messageque.getFailMessage(key);
						ChattingApplication.getInstance().socket.SendMessage(
								msgvo.getMemberseq(),
								msgvo.getMsgkey(),
								msgvo.getSendseq(),
								msgvo.getMsg(),
								SharedManager.getInstance().getString(
										Activity_Chat.this,
										BaseGlobal.User_name),
								msgvo.getMsgtype(), msgvo.getRoomseq(),
								msgvo.getRoomtype(), msgvo.getRevname(),
								msgvo.getMembername());
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			// 키보드를 띄운다.
			imm.showSoftInputFromInputMethod(
					mEditText_message.getWindowToken(),
					InputMethodManager.SHOW_FORCED);
			getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

			addfile_layout = (LinearLayout) findViewById(R.id.addfile_layout);

			findViewById(R.id.addphoto_layout).setOnClickListener(
					new OnClickListener()
					{

						@Override
						public void onClick(View v)
						{
							addfile_layout.setVisibility(View.GONE);
							Intent intent = new Intent(Activity_Chat.this,
									CustomGallery.class);
							intent.putExtra("size", ChatGlobal.IMGTOTALCOUNT
									- filekey.size());// 선택가능한갯수
							startActivityForResult(intent,
									ChatGlobal.CAMERA_ALBUM_REQUEST_CODE);
						}
					});

			findViewById(R.id.addcamera_layout).setOnClickListener(
					new OnClickListener()
					{

						@Override
						public void onClick(View v)
						{
							addfile_layout.setVisibility(View.GONE);
							Intent intent = new Intent(Activity_Chat.this,
									CameraActivity.class);
							intent.putExtra("what", 2);
							startActivityForResult(intent,
									ChatGlobal.TABLET_CAMERA_ALBUM_REQUEST_CODE);
							/*
							 * if(ChattingApplication.getInstance().isTablet.equals
							 * ("phone")){
							 * BxmIntent.goCameraForResult(Activity_Chat.this,
							 * ChatGlobal.CAMERA_CROP_REQUEST_CODE, null);
							 * }else{ intent = new
							 * Intent(Activity_Chat.this,CameraActivity.class) ;
							 * intent.putExtra("what", 2);
							 * startActivityForResult(intent,
							 * ChatGlobal.TABLET_CAMERA_ALBUM_REQUEST_CODE); }
							 */
						}
					});

			findViewById(R.id.addvoice_layout).setOnClickListener(
					new OnClickListener()
					{

						@Override
						public void onClick(View v)
						{

						}
					});
			/*
			 * final SoftKeyboardDectectorView softKeyboardDecector = new
			 * SoftKeyboardDectectorView(this);
			 * addContentView(softKeyboardDecector, new
			 * FrameLayout.LayoutParams(-1, -1));
			 * softKeyboardDecector.setOnShownKeyboard(new
			 * OnShownKeyboardListener() {
			 * 
			 * @Override public void onShowSoftKeyboard() { //키보드 등장할 때
			 * if(mIsBottomOfList)
			 * mStickyListHeadersListView.setSelection(messg.size()); } });
			 * 
			 * softKeyboardDecector.setOnHiddenKeyboard(new
			 * OnHiddenKeyboardListener() {
			 * 
			 * @Override public void onHiddenSoftKeyboard() { // 키보드 사라질 때
			 * //if(mIsBottomOfList)
			 * //mStickyListHeadersListView.setSelection(messg.size()); } });
			 */

			TextWatcher mTextWatcher = new TextWatcher()
			{
				public void afterTextChanged(Editable s)
				{
					if (s == null || s.toString().length() <= 0)
					{
						mImageButtonIconText_send.setClickable(false);
					}
					else
					{
						mImageButtonIconText_send.setClickable(true);
					}
				}

				public void beforeTextChanged(CharSequence s, int start,
						int count, int after)
				{
				}

				public void onTextChanged(CharSequence s, int start,
						int before, int count)
				{
					// ifAtBottomStayAtBottom();
				}
			};

		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}

		setTitle();
		/*
		 * if(ChattingService.getInstance().getRoomCheck(chatvo.chatRoomSeq,chatvo
		 * .chatRoomType)){ // 방이있으면
		 * //ChattingService.getInstance().getRoomMessage
		 * (chatvo.chatRoomSeq,mPage); }else{ //방이없으면
		 * //ChattingService.getInstance
		 * ().CreateRoom(chatvo.chatRoomMember,chatvo.chatRoomSeq,
		 * chatvo.chatRoomOwner, chatvo.chatRoomMember, chatvo.chatRoomType, 0,
		 * ""); }
		 */

		// 소켓접속
		// socket = new ConnetedSocket(this);
		// 방 접속 메세지 전달
		// ChattingApplication.getInstance().socket.StartSocket(chatvo.chatRoomMember,
		// CreateKey.getFileKey(),chatvo.chatRoomSeq,chatvo.chatRoomOwner,
		// sendname,chatvo.chatRoomType);
		// ChattingService.getInstance().SocketConnect(chatvo.chatRoomMember,
		// chatvo.chatRoomOwner, chatvo.chatRoomName, chatvo.chatRoomType);

		mImageButtonIconText_send.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mEditText_message.getText().toString().length() == 0)
				{
					return;
				}
				if (ChattingApplication.getInstance().socket != null
						&& !ChattingApplication.getInstance().socket
								.getSocket().isClosed())
				{
					SendMessageVo(ChatType.text, mEditText_message.getText()
							.toString(), chatvo.chatRoomSeq,
							chatvo.chatRoomRevName);
				}
				else
				{
					ChattingApplication.getInstance().ConnectSocket(
							new ConnectedListner()
							{
								@Override
								public void setConnectedListner()
								{
									SendMessageVo(ChatType.text,
											mEditText_message.getText()
													.toString(),
											chatvo.chatRoomSeq,
											chatvo.chatRoomRevName);
								}
							});

				}
			}
		});
		setarrVHUser();
		startDrawerList();
		// mSelection = messg.size();

	}

	public void SendMessageVo(int type, String message, String roomseq,
			String revname)
	{
		try
		{
			final MessageVo vo = new MessageVo();
			vo.setMsg(message);
			vo.setMsgkey(CreateKey.getFileKey());
			vo.setMemberseq(chatvo.chatRoomMember);
			vo.setRoomseq(roomseq);
			// vo.setRecevseq(chatvo.chatRoomSeq);
			vo.setSendname(SharedManager.getInstance().getString(
					Activity_Chat.this, BaseGlobal.User_name));
			vo.setRevname(revname);
			vo.setSendseq(chatvo.chatRoomOwner);
			// vo.setMemberseq(chatvo.chatRoomMember);
			vo.setMsgtype(type);
			vo.setRoomtype(chatvo.chatRoomType);
			vo.setMembername(chatvo.chatRoomMemberName);
			if (!ChattingApplication.getInstance().socket.getSocket()
					.isConnected()
					|| NetworkUtil.getConnectivityStatus(Activity_Chat.this) == 0)
			{
				vo.setStatus(ChatStatusType.SendFail);
			}
			else
			{
				vo.setStatus(ChatStatusType.SendIng);
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");
			Date date = new Date();
			vo.setMsgregdate(dateFormat.format(date));
			messg.add(vo);
			if (type != ChatType.file)
			{
				ChattingApplication.getInstance().socket.SendMessage(
						vo.getMemberseq(),
						vo.getMsgkey(),
						vo.getSendseq(),
						vo.getMsg(),
						SharedManager.getInstance().getString(
								Activity_Chat.this, BaseGlobal.User_name),
						type, vo.getRoomseq(), vo.getRoomtype(),
						vo.getRevname(), vo.getMembername());
			}
			else
			{
				vo.setStatus(ChatStatusType.Send);
				/*
				 * ChattingApplication.getInstance().socket.SendFileMessage(vo.
				 * getMemberseq(),vo.getMsgkey(), vo.getSendseq(), vo.getMsg(),
				 * SharedManager.getInstance().getString(this,
				 * BaseGlobal.User_name
				 * ),ChatType.file,vo.getRoomseq(),vo.getRoomtype
				 * (),vo.getRevname(),vo.getMembername(),"");
				 */
			}
			mHandler.post(new Runnable()
			{
				public void run()
				{
					// mAdapterChat.notifyDataSetChanged();
					mAdapterChat.updateview();
					mStickyListHeadersListView.setSelection(messg.size());
					mEditText_message.setText("");
					messageque.SendMessage(Activity_Chat.this, vo);
				}
			});
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	public OnScrollListener onscroll = new OnScrollListener()
	{
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState)
		{
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount)
		{
			if (totalItemCount > 0
					&& (firstVisibleItem + visibleItemCount) == totalItemCount)
			{
				mHandlerKeyBoard.removeCallbacks(setIsBottomFalse);
				mHandlerKeyBoard.post(setIsBottomTrue);
			}
			else
			{
				mHandlerKeyBoard.removeCallbacks(setIsBottomTrue);
				mHandlerKeyBoard.post(setIsBottomFalse);
			}

			if (totalItemCount > 0 && firstVisibleItem == 0
					&& messg.size() == (ChatGlobal.ChatSelection * mPage)
					&& !mLock)
			{ // 보이는 뷰아이템 포지션이 0 이고, 채팅글갯수가 10*페이지라면 처음글
				// mSelection = 20;
				mLock = true;
				mPage++;
				ChattingService.getInstance().getRoomMessage(
						chatvo.chatRoomSeq, mPage, false);
			}
		}
	};

	private void setarrVHUser()
	{
		try
		{
			arrVHUser.clear();
			for (int i = 0; i < chatvo.chatRoomMember.length; i++)
			{
				UserVo vo = new UserVo();

				// MemberVo memvo =
				// (MemberVo)ChattingApplication.getInstance().memberMap.get(chatvo.chatRoomMember[i]);
				vo.UserId = chatvo.chatRoomMemberName[i];
				vo.UserSeq = chatvo.chatRoomMember[i];

				arrVHUser.add(vo);
			}

		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	public void leaveChatRoom()
	{
		try
		{
			finish();
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	private Handler mHandlerKeyBoard = new Handler();
	public Runnable setIsBottomFalse = new Runnable()
	{
		public void run()
		{
			mIsBottomOfList = false;
		}
	};
	public Runnable setIsBottomTrue = new Runnable()
	{
		public void run()
		{
			mIsBottomOfList = true;
			newmsglayout.setVisibility(View.GONE);
		}
	};

	public void UpdateView(final boolean msgflag)
	{
		try
		{
			mHandler.post(new Runnable()
			{
				public void run()
				{
					// mAdapterChat.notifyDataSetChanged();
					/*
					 * if(messagevo != null){
					 * mAdapterChat.updateview(messagevo.getMsgkey()); }else{
					 */
					mAdapterChat.updateview();
					// }

					// mStickyListHeadersListView.get
					if (mIsBottomOfList) mStickyListHeadersListView
							.setSelection(messg.size());
					//
					if (mLock && !mIsBottomOfList)
					{
						if (messg.size() == ChatGlobal.ChatSelection * mPage)
						{
							mLock = false;
							mStickyListHeadersListView
									.setSelection(ChatGlobal.ChatSelection);
						}
					}
					if (msgflag && !mIsBottomOfList)
					{ // true면 새로받은메세지
						if (!messg
								.get(messg.size() - 1)
								.getSendseq()
								.equals(SharedManager.getInstance()
										.getString(Activity_Chat.this,
												BaseGlobal.User_Seq)))
						{
							newmsglayout.setVisibility(View.VISIBLE);
							newmsgtextview.setText(messg.get(messg.size() - 1)
									.getMsg());
						}
					}
					setarrVHUser();
					startDrawerList();
					// mHandlerKeyBoard.postDelayed(setIsBottomFalse, 200);
					//
				}
			});
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	private void setTitle()
	{
		try
		{
			if (chatvo.chatRoomType == ChatGlobal.chatTypeSingle)
			{
				((TextView) findViewById(R.id.activityChat_TextView_mainTitle))
						.setText(chatvo.chatRoomRevName);
				/*
				 * MemberVo memvo =
				 * (MemberVo)ChattingApplication.getInstance().memberMap
				 * .get(chatvo.chatRoomSeq);
				 * ((TextView)findViewById(R.id.activityChat_TextView_mainTitle
				 * )).setText(memvo.getUsername());
				 */
			}
			else if (chatvo.chatRoomType == ChatGlobal.chatTypeGroup)
			{
				((TextView) findViewById(R.id.activityChat_TextView_mainTitle))
						.setText("그룹채팅");
			}

		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	private HashMap<String, UserVo> mRoomUserHashMap;
	private AdapterChatDrawer mAdapterChatDrawer;

	private void startDrawerList()
	{
		try
		{
			mDrawerList.setAdapter(null);
			mAdapterChatDrawer = null;
			mArrDrawerItem = new ArrayList<Object>();

			// 상단버튼(연락처리스트) 추가
			mArrDrawerItem.add(new DrawerItemChatTop());
			mRoomUserHashMap = new HashMap<String, UserVo>();
			for (UserVo user : arrVHUser)
			{
				mArrDrawerItem.add(user);
				mRoomUserHashMap.put(String.valueOf(user.UserSeq), user);
			}
			mAdapterChatDrawer = new AdapterChatDrawer(this, mArrDrawerItem,
					messg);
			mDrawerList.setAdapter(mAdapterChatDrawer);
			mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		}
		catch (Exception e)
		{
			WriteFileLog.writeException(e);
		}
	}

	/*
	 * private Handler mHandlerKeyBoard = new Handler(); private void
	 * ifAtBottomStayAtBottom(){ Mylog.e(TAG, "====== 진입:시작");
	 * mHandlerKeyBoard.postDelayed(setBlockKeyboardDetector_true, 0);
	 * if(mIsBottomOfList && mArrChatUI!=null && mArrChatUI.size()>0){
	 * Mylog.e(TAG, "====== 진입:변경");
	 * mStickyListHeadersListView.setSelection(mArrChatUI.size()-1); } }
	 * 
	 * public Runnable setBlockKeyboardDetector_true = new Runnable() { public
	 * void run() { mBlockKeyboardDetector = true;
	 * mHandlerKeyBoard.postDelayed(setBlockKeyboardDetector_false, 500); } };
	 * 
	 * public Runnable setBlockKeyboardDetector_false = new Runnable() { public
	 * void run() { mBlockKeyboardDetector = false;
	 * 
	 * } };
	 */

	/*
	 * Drawer 아이템 클릭 이벤트
	 */

	public void AddMember()
	{
		Intent i = new Intent("action_activityFriendPickerBt");
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		i.addCategory(Intent.CATEGORY_DEFAULT);
		// i.putExtra(ActivityChat.ChatRoomObject, swChatRoom);
		startActivityForResult(i, ActivityFriendPicker.RESPONSE_CODE);
		mDrawer.closeDrawer(mLinearLayout_drawer);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			mDrawerList.setItemChecked(position, true);

			if (mArrDrawerItem.get(position) instanceof MemberVo)
			{

			}
			else
			{
				Intent i = new Intent("action_activityFriendPickerBt");
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_SINGLE_TOP);
				i.addCategory(Intent.CATEGORY_DEFAULT);
				// i.putExtra(ActivityChat.ChatRoomObject, swChatRoom);
				startActivityForResult(i, ActivityFriendPicker.RESPONSE_CODE);
			}

			// mBaseFragment = selectFragment(position);
			// mSelectedFragment = position;
			//
			// if (mBaseFragment != null)
			// changeFragment(mBaseFragment);
			mDrawer.closeDrawer(mLinearLayout_drawer);
		}
	}

	private OnItemClickListener mListListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView parent, View view,
				final int position, long id)
		{
			// getDialogForFailedMessage(position);
		}
	};

	public void newGroupRoom(String roomseq, String[] member)
	{
		try
		{
			Intent intent = new Intent(this, Activity_Chat.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			ChatVo chatvo = new ChatVo();
			String my = SharedManager.getInstance().getString(this,
					BaseGlobal.User_name); // 내아이디
			String myseq = SharedManager.getInstance().getString(this,
					BaseGlobal.User_Seq); // 내seq
			chatvo.chatRoomOwner = myseq; // 내seq
			chatvo.chatRoomSeq = roomseq;
			chatvo.chatRoomMember = member; // 멤버들
			chatvo.chatRoomType = ChatGlobal.chatTypeGroup; // 싱글
			chatvo.chatRoomRevName = ChatGlobal.ChatRoupName;
			// chatvo.chatRoomName = mSelectUserName; // 상대이름
			intent.putExtra(ChatGlobal.ChatRoomObj, chatvo); // 방정보
			// intent.putExtra(ChatGlobal.ChatRoomUser, mSelectUser); //상대정보
			SendMessageVo(ChatType.add, ChatVo.setRoomMember(member), roomseq,
					chatvo.chatRoomRevName);
			startActivity(intent);
			finish();
		}
		catch (Exception e)
		{
		}
	}

	public void SaveImage(final String filepath, final boolean album,
			final String filename, final Bitmap bitmap)
	{
		AsyncTask<Void, Void, String> getDataWorker = new AsyncTask<Void, Void, String>()
		{
			@Override
			protected void onPreExecute()
			{
				try
				{
				}
				catch (Exception e)
				{
				}
				super.onPreExecute();
			}

			@Override
			protected String doInBackground(Void... params)
			{
				String realpath = "";
				try
				{
					realpath = SaveImg(filepath, album, filename, bitmap);
				}
				catch (Exception e)
				{
				}
				return realpath;
			}

			@Override
			protected void onPostExecute(String resultvalue)
			{
				try
				{
					SendMessageVo(ChatType.file, resultvalue,
							chatvo.chatRoomSeq, chatvo.chatRoomRevName);
				}
				catch (Exception e)
				{
				}
				finally
				{

				}
				super.onPostExecute(resultvalue);
			}
		};
		getDataWorker.execute();
	}

	public String SaveImg(String filepath, boolean album, String filename,
			Bitmap bitmap)
	{
		bitmap = BrImageUtilManager.getInstance().getBitmap(filepath, album,
				800, 600);
		File testfile = new File(ChattingApplication.getInstance()
				.getFileDir_Ex() + filename + ".jpg");
		boolean flag = BrImageUtilManager.getInstance().saveOutput(this,
				bitmap, getImageUri(testfile.getAbsolutePath()));
		if (!flag) return "";

		return testfile.getAbsolutePath();
	}

	private Uri getImageUri(String path)
	{
		return Uri.fromFile(new File(path));
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent intent)
	{
		super.onActivityResult(arg0, arg1, intent);
		if (arg1 == RESULT_OK)
		{
			switch (arg0)
			{
			case ChatGlobal.CAMERA_CROP_REQUEST_CODE:
				if (intent == null) return;

				String filename = intent
						.getStringExtra(BxmIntent.KEY_FULL_PATH);

				Log.e("", "" + filename);
				// Bitmap bitmap = null;
				// SaveImage(filename, true, new File(filename).getName(),
				// bitmap);
				// filename = SaveImg(filename, true, new
				// File(filename).getName(), bitmap);
				SendMessageVo(ChatType.file, filename, chatvo.chatRoomSeq,
						chatvo.chatRoomRevName);
				break;

			case ChatGlobal.CAMERA_ALBUM_REQUEST_CODE:
				if (intent == null) return;

				mSelectImg.clear();
				mSelectImg = intent.getStringArrayListExtra("simg");
				intent = new Intent(Activity_Chat.this, ActivityPhotoTool.class);
				intent.putExtra(BxmIntent.KEY_TEMP_IMAGE_FILEPATH,
						mSelectImg.get(0));
				intent.putExtra("activity", true);
				startActivityForResult(intent,
						ChatGlobal.CAMERA_CROP_REQUEST_CODE);
				// Bitmap bitmap2 = null;
				// SaveImage(mSelectImg.get(0), true, new
				// File(mSelectImg.get(0)).getName(), bitmap2);
				// SendMessageVo(ChatType.file,
				// mSelectImg.get(0),chatvo.chatRoomSeq,chatvo.chatRoomRevName);
				break;

			case ActivityFriendPicker.RESPONSE_CODE:
				if (intent == null) return;
				Serializable list = intent
						.getSerializableExtra(ActivityFriendPicker.VHUSERS);
				ArrayList<MemberVo> contactUsers = (ArrayList<MemberVo>) list;

				// 선택안됬을때
				if (contactUsers == null || contactUsers.size() <= 0) return;

				// 중복유저는 제외
				ArrayList<UserVo> newUsers = new ArrayList<UserVo>();
				String myseq = SharedManager.getInstance().getString(
						Activity_Chat.this, BaseGlobal.User_Seq);
				for (MemberVo user : contactUsers)
				{
					// 자신 제외
					if (myseq.equals(String.valueOf(user.getSeq()))) continue;
					// if((new
					// VHAccountManager()).getLoginUser(this).itemName.equals(user.itemName))

					if (mRoomUserHashMap.get(String.valueOf(user.getSeq())) == null)
					{
						UserVo vo = new UserVo();
						vo.UserId = user.getUsername();
						vo.UserSeq = String.valueOf(user.getSeq());

						arrVHUser.add(vo);
						newUsers.add(vo);

					}
				}
				// 새로운 유저가 없을때, 실행안함
				if (newUsers.size() <= 0)
				{
					return;
				}

				String[] chatmember = new String[arrVHUser.size()];
				String[] chatmember2 = new String[newUsers.size()];
				String[] chatmembername = new String[arrVHUser.size()];
				String[] chatmembername2 = new String[newUsers.size()];
				for (int i = 0; i < arrVHUser.size(); i++)
				{
					chatmember[i] = arrVHUser.get(i).UserSeq;
					chatmembername[i] = arrVHUser.get(i).UserId;
				}
				for (int i = 0; i < newUsers.size(); i++)
				{
					chatmember2[i] = newUsers.get(i).UserSeq;
					chatmembername2[i] = newUsers.get(i).UserId;
				}

				if (chatvo.chatRoomType == ChatGlobal.chatTypeSingle)
				{
					chatvo.chatRoomMember = chatmember;
					String groupseq = CreateKey.getFileKey();
					// chatvo.chatRoomSeq = groupseq;
					chatvo.chatRoomType = ChatGlobal.chatTypeGroup;
					chatvo.chatRoomRevName = ChatGlobal.ChatRoupName;
					chatvo.chatRoomMemberName = chatmembername;
					// newGroupRoom(groupseq, chatmember2);
					// chatvo.chatRoomSeq);
					SendMessageVo(
							ChatType.add,
							ChatVo.setRoomMember(chatvo.chatRoomMember)
									+ ","
									+ ChatVo.setRoomMember(chatvo.chatRoomMemberName),
							groupseq, chatvo.chatRoomRevName);
				}
				else if (chatvo.chatRoomType == ChatGlobal.chatTypeGroup)
				{
					chatvo.chatRoomMember = chatmember;
					chatvo.chatRoomMemberName = chatmembername;
					SendMessageVo(
							ChatType.add,
							ChatVo.setRoomMember(chatmember2) + ","
									+ ChatVo.setRoomMember(chatmembername2),
							chatvo.chatRoomSeq, chatvo.chatRoomRevName);
				}

				setTitle();
				setarrVHUser();
				startDrawerList();

				break;
			case ChatGlobal.OnChatReQustCode:

				BrUtilManager.getInstance().showToast(mInstance, "추가진행예정");

				break;
			case ChatGlobal.TABLET_CAMERA_ALBUM_REQUEST_CODE:
				if (intent == null) return;

				filename = intent.getStringExtra("thumbnailurl");
				intent = new Intent(Activity_Chat.this, ActivityPhotoTool.class);
				intent.putExtra(BxmIntent.KEY_TEMP_IMAGE_FILEPATH, filename);
				intent.putExtra("activity", true);
				startActivityForResult(intent,
						ChatGlobal.CAMERA_CROP_REQUEST_CODE);
				break;
			}
		}
	}

	public void webViewBack(View v)
	{
		webView.goBack();
		edtUrl.setText(webView.getUrl());
	}

	public void webViewNext(View v)
	{
		webView.goForward();
		edtUrl.setText(webView.getUrl());
	}

	public void webViewRefresh(View v)
	{
		webView.reload();
	}

	public void showEmoicon(View v)
	{

		if (!popupWindow.isShowing())
		{

			popupWindow.setHeight((int) (keyboardHeight));

			if (isKeyBoardVisible)
			{
				emoticonsCover.setVisibility(LinearLayout.GONE);
			}
			else
			{
				emoticonsCover.setVisibility(LinearLayout.VISIBLE);
			}
			popupWindow.showAtLocation(parentLayout, Gravity.BOTTOM, 0, 0);
		}
		else
		{
			popupWindow.dismiss();
		}

	}

	private void setEmojiconFragment(boolean useSystemDefault)
	{
		getSupportFragmentManager()
				.beginTransaction()
				.add(CONTENT_VIEW_ID,
						EmojiconsFragment.newInstance(useSystemDefault))
				// .replace(popUpView.getId(),
				// EmojiconsFragment.newInstance(useSystemDefault))
				.commit();

	}

	@Override
	public void onEmojiconBackspaceClicked(View v)
	{
		EmojiconsFragment.backspace(mEditText_message);
	}

	@Override
	public void onEmojiconClicked(Emojicon emojicon)
	{
		EmojiconsFragment.input(mEditText_message, emojicon);
	}

	/**
	 * Checking keyboard height and keyboard visibility
	 */

	int previousHeightDiffrence = 0;

	private void checkKeyboardHeight(final View parentLayout)
	{

		parentLayout.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener()
				{

					@Override
					public void onGlobalLayout()
					{

						Rect r = new Rect();
						parentLayout.getWindowVisibleDisplayFrame(r);

						int screenHeight = parentLayout.getRootView()
								.getHeight();
						int heightDifference = screenHeight - (r.bottom);

						if (previousHeightDiffrence - heightDifference > 50)
						{
							popupWindow.dismiss();
						}

						previousHeightDiffrence = heightDifference;
						if (heightDifference > 100)
						{

							isKeyBoardVisible = true;
							changeKeyboardHeight(heightDifference);

						}
						else
						{

							isKeyBoardVisible = false;

						}

					}
				});

	}

	/**
	 * change height of emoticons keyboard according to height of actual
	 * keyboard
	 * 
	 * @param height
	 *            minimum height by which we can make sure actual keyboard is
	 *            open or not
	 */
	private void changeKeyboardHeight(int height)
	{

		if (height > 100)
		{
			keyboardHeight = height;
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, keyboardHeight);
			emoticonsCover.setLayoutParams(params);
		}

	}

}
