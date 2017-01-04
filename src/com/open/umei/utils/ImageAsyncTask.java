/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-4上午10:18:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.umei.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.open.umei.R;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-1-4上午10:18:49
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class ImageAsyncTask extends AsyncTask<Bitmap, Void, String> {  
    Context mContext;  
    ImageView mImageView;  
    String srcUrl;
    
    public ImageAsyncTask( Context mContext, ImageView imageView,String srcUrl) {  
        this.mImageView = imageView;  
        this.mContext = mContext;  
        this.srcUrl = srcUrl; 
    }  
  
    @Override  
    protected String doInBackground(Bitmap... params) {  
        String result = mContext.getResources().getString(R.string.save_picture_failed);  
        try {  
            String sdcard = Environment.getExternalStorageDirectory().toString();  
            File file = new File(sdcard + "/"+mContext.getPackageName()+"/");  
            if (!file.exists()) {  
                file.mkdirs();  
            }  
            //http://i1.umei.cc/uploads/tu/201609/33/mimn5ha5dfo.jpg
            File imageFile = new File(file.getAbsolutePath(),  URLEncoder.encode(srcUrl.replace(".jpg", ""),"UTF-8")+".jpg");  
            imageFile.deleteOnExit();
            imageFile.createNewFile();
            FileOutputStream outStream = null;  
            outStream = new FileOutputStream(imageFile);  
            Bitmap image = params[0];  
            image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);  
            outStream.flush();  
            outStream.close();  
            result = mContext.getResources().getString(R.string.save_picture_success, file.getAbsolutePath());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
  
    @Override  
    protected void onPostExecute(String result) {  
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();  
        mImageView.setDrawingCacheEnabled(false);  
    }  
} 
