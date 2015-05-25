package com.way.chat.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.camera.CameraActivity;
import com.photoaibum.PhotoAlbumActivity;
import com.way.chat.common.util.Constants;

public class Photo extends Activity
{
	/** Called when the activity is first created. */
	private static String TAG = "HelloStranger";

	private Button cameraBtn = null;
	private Button mAlbum = null;
	private File   mPhotoFile = null;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		Log.e(TAG, "onCreate");
		mAlbum = (Button) findViewById(R.id.mypicture_btn);
		cameraBtn = (Button) findViewById(R.id.system_camera_btn);
		
		// 监听button的事件信息
		mAlbum.setOnClickListener(click);
		cameraBtn.setOnClickListener(click);
	}

	private View.OnClickListener click = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = null;
			switch (v.getId())
			{
			case R.id.mypicture_btn:
				gotoPhotoAlbumActivity();
				break;
			case R.id.system_camera_btn:
				
				intent = new Intent("android.media.action.IMAGE_CAPTURE");
				File file = new File(Constants.SAVEPATH);
				file.mkdirs();// 创建文件夹
				mPhotoFile = new File(Constants.SAVEPATH,getPhotoFileName());
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				// 把文件地址转换成Uri格式
				Uri uri = Uri.fromFile(mPhotoFile);
				// 设置系统相机拍摄照片完成后图片文件的存放地址
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, 1);
				break;
			}
		}
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		ContentResolver resolver = getContentResolver();
		
		switch (requestCode)
		{
		case 1:
			if (Activity.RESULT_OK == resultCode)
			{
				/*-----------即时刷新相册——>向相册插入图片---------*/
		    	ContentValues localContentValues = new ContentValues(); 
		    	localContentValues.put("_data", mPhotoFile.toString()); 
		    	localContentValues.put("description", "save image ---"); 
		    	localContentValues.put("mime_type", "image/jpeg");
		    	Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; 
		    	resolver.insert(localUri, localContentValues);
				/*----------------------------------------------*/
		    	gotoPhotoAlbumActivity();
			}
			break;
		}
	}
	private void gotoPhotoAlbumActivity()
	{
		Intent intent = null;
		intent = new Intent();  
        // 指定intent要启动的类  
        intent.setClass(Photo.this, PhotoAlbumActivity.class);   
        intent.putExtra("From", "Register");
        //启动一个新的Activity  
        startActivity(intent);  
        // 关闭当前的Activity   
        finish();
	}
	private String getPhotoFileName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	@Override
	public void onBackPressed() // 捕捉返回键
	{
		Intent intent = new Intent(); // 新建一个Intent对象
		intent.setClass(Photo.this, LoginActivity.class);// 指定intent要启动的类
		startActivity(intent); // 启动一个新的Activity
		finish(); // 关闭当前的Activity
	}

}