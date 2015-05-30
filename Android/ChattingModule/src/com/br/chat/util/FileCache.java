package com.br.chat.util;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
 
public class FileCache {
     
    private File cacheDir;
     
    public FileCache(Context context){
        //Find the dir to save cached images
    	
    	//final String cachePath =
//        Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
//        !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() :
//                context.getCacheDir().getPath();
    	
    	
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
//            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"TTImages_cache");
        	cacheDir=Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
        	        !isExternalStorageRemovable() ? getExternalCacheDir(context) :
                        context.getCacheDir();
        }else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    public File getCacheDir(){
    	return cacheDir;
    }
    
    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.toLowerCase().hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;
         
    }
     
    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
 
    @TargetApi(9)
    public static boolean isExternalStorageRemovable() {
       /* if (Utils.hasGingerbread()) {
            return Environment.isExternalStorageRemovable();
        }*/
        return true;
    }
    
    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {
      /*  if (Utils.hasFroyo()) {
            return context.getExternalCacheDir();
        }*/
        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }
}