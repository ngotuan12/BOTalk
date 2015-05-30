package com.brainyxlib.image;

import java.io.File;
import java.util.ArrayList;

public class BoardImageFile {
	String filename_normal = ""; 
	String localfilename_normal = "";
	boolean needUpdate = false;
	public static final String SP_SUB = ":";
	public static final String SP_MAIN = ",";
	
	public String getFilename_normal() {
		return filename_normal;
	}
	public void setFilename_normal(String filename_normal) {
		this.filename_normal = filename_normal;
	}
	public String getLocalfilename_normal() {
		return localfilename_normal;
	}
	public void setLocalfilename_normal(String localfilename_normal) {
		this.localfilename_normal = localfilename_normal;
	}
	public boolean isNeedUpdate() {
		return needUpdate;
	}
	public void setNeedUpdate(boolean needUpdate) {
		this.needUpdate = needUpdate;
	}
	
	public static String getBoardImageFileStr(ArrayList<ImageVo> boardFiles) {
		String result = "";
		
		for(int i = 0 ; i < boardFiles.size(); i++){
			File file = new File(boardFiles.get(i).getImg_url());
			String PHOTO_FILENAME = file.getName();
			String tmpStr = PHOTO_FILENAME + SP_SUB +  "0" + SP_SUB + "0";
					
			if(result.equals("")) {
				result = tmpStr;	
			} else {
				result = result + SP_MAIN + tmpStr; 
			}	
			file = null;
		}
		return result;
	} 
	
	public static ArrayList<ImageVo> getImgList(String img,boolean flag){
		ArrayList<ImageVo>imglist = new ArrayList<ImageVo>();
		imglist.clear();
//		rjm89rbf20140203205323236.jpg:0:0   ,   myQRCode.jpg:0:0
		String[] imgs = img.split(",");
		ImageVo vo_;
		for(int i = 0 ; i < imgs.length; i ++){
			vo_ = new ImageVo();
			vo_.setImg_url(imgs[i].split(":")[0]);
			vo_.setImg_flag(flag);
			imglist.add(vo_);
		}
		
		return imglist;
	}
	

}
