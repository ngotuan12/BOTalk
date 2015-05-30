package com.brainyxlib.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.ImageButton;

public class CameraActivity extends Activity  {

	private static final int PICK_FROM_CAMERA = 0;
	private static final int PICK_FROM_ALBUM = 1;
	private static final int CROP_FROM_CAMERA = 2;
	public static Uri mImageCaptureUri;
	private static Uri m_ImageUri;
	private ImageButton m_photo_taking, m_photo_album;
	private String m_resultUri;
	private String dir = "";
	File outFilePath;
	Bitmap photo;
	int count;
	String activity = "";
	String type = "";
	private final int SELECT_MOVIE = 3;
	private int select = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		dir = Environment.getExternalStorageDirectory() + "/linkceo";

		File path1 = new File(Environment.getExternalStorageDirectory() + "/linkceo/");
		if (!path1.isDirectory()) {
			path1.mkdir();
		}
		Intent intent = getIntent();
		select = intent.getIntExtra("what", 0);
			if(select == 1){
				doTakeAlbumAction();
			}else if (select == 2){
				doTakePhotoAction();
			}
		

	}

	private void doTakePhotoAction() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 임시로 사용할 파일의 경로를 생성
		String url = "tmp_" + String.valueOf(System.currentTimeMillis())
				+ ".jpg";
		mImageCaptureUri = Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), url));

		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
				mImageCaptureUri);
		intent.putExtra("return-data", true);
		intent.putExtra("passok", "ok");
		startActivityForResult(intent, PICK_FROM_CAMERA);

		// finish();
	}

	private void doTakeAlbumAction() {
		// 앨범 호출
		Intent intent = new Intent(Intent.ACTION_PICK);
		// MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		// intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
		// intent.putExtra("passok", "ok");
		startActivityForResult(intent, PICK_FROM_ALBUM);

		// finish();
	}

	private void doSelectMovie() {
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.setType("video/*");
		try {
			startActivityForResult(i, SELECT_MOVIE);
			// finish();
		} catch (android.content.ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

/*	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.photo_taking:

			doTakePhotoAction();

			break;

		case R.id.photo_album:
			doTakeAlbumAction();

			break;
		}

	}*/

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			finish();

			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("thumbnail", "0");
		this.setResult(RESULT_CANCELED, intent);
		finish();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			finish();
		} else {
			switch (requestCode) {
			case CROP_FROM_CAMERA: {
				// 크롭이 된 이후의 이미지를 넘겨 받습니다. 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
				// 임시 파일을 삭제합니다.

				if (m_ImageUri != null) {

					m_resultUri = m_ImageUri.getPath();
					// String path = getPath(m_ImageUri);

					/*
					 * String repath = path.substring(1);
					 * 
					 * String name = getName(m_ImageUri); String currentFileName
					 * = path.substring(path.lastIndexOf("/"), path.length());
					 * String urlname = repath.substring(0,
					 * repath.lastIndexOf("/")); String jpg =
					 * name.substring(name.lastIndexOf("."), name.length());
					 * String rename2 = jpg.substring(1);
					 * 
					 * String rename = "/tmp_" +
					 * String.valueOf(System.currentTimeMillis()) + jpg; File
					 * file = new File(path); File file2 = new File(urlname +
					 * "/" + rename);
					 * 
					 * String uriId = getUriId(m_ImageUri); Log.e("###",
					 * "실제경로 : " + path + "\n파일명 : " + name + "\nuri : " +
					 * m_ImageUri.toString() + "\nuri id : " + uriId);
					 * 
					 * 
					 * 
					 * boolean renameto = myapp.copyFile(repath, urlname +
					 * rename); m_resultUri = rename;
					 */

					/*
					 * final Bundle extras = data.getExtras(); photo =
					 * extras.getParcelable("data"); if (photo != null) try {
					 * FileOutputStream fos = new FileOutputStream(
					 * outFilePath); photo.compress(CompressFormat.JPEG, 100,
					 * fos); // 이미지 // 저장 fos.flush(); fos.close(); } catch
					 * (Exception e) { Log.e("photo", "" + requestCode + " : " +
					 * e.toString()); } else { Log.e("photo", "Bitmap is null");
					 * // 에러처리 return;
					 * 
					 * }
					 */// 이건 BUNDEL로 받을때 쓰는거

					try {
						photo = Images.Media.getBitmap(getContentResolver(),
								m_ImageUri);
						/*
						 * ByteArrayOutputStream byteArr = new
						 * ByteArrayOutputStream(); Bitmap curThumb = photo;
						 * curThumb.compress(CompressFormat.PNG, 100, byteArr);
						 * byte[] b = byteArr.toByteArray();
						 * intent.putExtra("img",b);
						 */
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
					Bitmap curThumb = photo;
					curThumb.compress(CompressFormat.JPEG, 70, byteArr);
					byte[] b = byteArr.toByteArray();
					
					Intent intent = new Intent();
					//intent.putExtra("thumbnail", b);
					intent.putExtra("thumbnailurl", m_resultUri);
					this.setResult(RESULT_OK, intent);
					finish();

					break;
				}

			}

			case PICK_FROM_ALBUM: {
				// 이후의 처리가 카메라와 같으므로 일단 break없이 진행합니다.
		/*		try {
					photo = Images.Media.getBitmap(getContentResolver(),
							data.getData());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
				Bitmap curThumb = photo;
				curThumb.compress(CompressFormat.JPEG, 70, byteArr);
				byte[] b = byteArr.toByteArray();
				 Intent intent = new Intent();
				intent.putExtra("thumbnail", b);
				 setResult(112, intent);
				 finish();*/
				 
				 Cursor c = getContentResolver().query(data.getData(), null,null,null,null);
				 c.moveToNext();
				 String path = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
				 Intent intent = new Intent();
				 intent.putExtra("thumbnailurl", path);
				 setResult(RESULT_OK, intent);
				 finish();
				
				break;
			}

			case PICK_FROM_CAMERA: {
				// 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
				// 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
				outFilePath = new File(dir + "/tmp_"
						+ System.currentTimeMillis() + ".jpg");

				m_ImageUri = Uri.fromFile(outFilePath);
				// m_ImageUri = mImageCaptureUri;
				// m_resultUri = outFilePath;

				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(mImageCaptureUri, "image/*");

				/*
				 * intent.putExtra("outputX", 180); intent.putExtra("outputY",
				 * 120); intent.putExtra("aspectX", 1);
				 * intent.putExtra("aspectY", 1);
				 */
				intent.putExtra("outputX", 480);
				intent.putExtra("outputY", 480);
			/*	intent.putExtra("aspectX", 5);
				intent.putExtra("aspectY", 10);*/
				intent.putExtra("scale", true);
				intent.putExtra("outputFormat", "JPEG");
				intent.putExtra("output", m_ImageUri); // 파일로 저장
				// intent.putExtra("scale", true);
				// intent.putExtra("return-data", true); //bundle로 저장
				try {
					startActivityForResult(intent, CROP_FROM_CAMERA);
				} catch (Exception e) {
					// TODO: handle exception
					e.getMessage();
				}

				break;
			}
			/*
			 * case SELECT_MOVIE: {
			 * 
			 * Uri uri = data.getData(); String path = getPath(uri);
			 * 
			 * String repath = path.substring(1);
			 * 
			 * String name = getName(uri); String currentFileName =
			 * path.substring(path.lastIndexOf("/"), path.length()); String
			 * urlname = repath.substring(0, repath.lastIndexOf("/")); String
			 * mp4 = name.substring(name.lastIndexOf("."), name.length());
			 * String rename2 = mp4.substring(1);
			 * 
			 * String rename = "/tmp_" +
			 * String.valueOf(System.currentTimeMillis()) + mp4; File file = new
			 * File(path); File file2 = new File(urlname + "/" + rename);
			 * 
			 * String uriId = getUriId(uri); Log.e("###", "실제경로 : " + path +
			 * "\n파일명 : " + name + "\nuri : " + uri.toString() + "\nuri id : " +
			 * uriId);
			 * 
			 * String dir = "/sdcard/wagle";
			 * 
			 * boolean renameto = myapp.fileinstance.copyFile(repath, urlname +
			 * rename);
			 * 
			 * String realpath = urlname + rename; Intent intent = new Intent();
			 * intent.putExtra("thumbnail", realpath); this.setResult(RESULT_OK,
			 * intent); finish();
			 * 
			 * break; }
			 */
			}
		}
	}

	private File makeDirectory(String dir_path) {
		File dir = new File(dir_path);
		if (!dir.exists()) {
			dir.mkdirs();
		} else {
		}

		return dir;
	}

	public static void fileCopy(String inFileName, String outFileName) {
		try {
			FileInputStream fis = new FileInputStream(inFileName);
			FileOutputStream fos = new FileOutputStream(outFileName);

			int data = 0;
			while ((data = fis.read()) != -1) {
				fos.write(data);
			}
			fis.close();
			fos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void copy(String source, String target) {
		// 복사 대상이 되는 파일 생성
		File sourceFile = new File(source);
		// 스트림, 채널 선언
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		try {
			// 스트림 생성
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(target);
			// 채널 생성
			fcin = inputStream.getChannel();
			fcout = outputStream.getChannel();

			// 채널을 통한 스트림 전송
			long size = fcin.size();
			fcin.transferTo(0, size, fcout);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 해제
			try {
				fcout.close();
			} catch (IOException ioe) {
			}
			try {
				fcin.close();
			} catch (IOException ioe) {
			}
			try {
				outputStream.close();
			} catch (IOException ioe) {
			}
			try {
				inputStream.close();
			} catch (IOException ioe) {
			}
		}
	}

	private boolean copyFile(File file, String save_file) {
		boolean result;
		if (file != null) {
			try {
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream newfos = new FileOutputStream(save_file);
				int readcount = 0;
				byte[] buffer = new byte[1024];
				while ((readcount = fis.read(buffer, 0, 1024)) != -1) {
					newfos.write(buffer, 0, readcount);
				}
				newfos.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	// 실제 경로 찾기
	private String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	// 파일명 찾기
	private String getName(Uri uri) {
		String[] projection = { MediaStore.Images.ImageColumns.DISPLAY_NAME };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	// uri 아이디 찾기
	private String getUriId(Uri uri) {
		String[] projection = { MediaStore.Images.ImageColumns._ID };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

}
