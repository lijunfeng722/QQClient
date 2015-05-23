package com.photoaibum;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.camera.CameraActivity;
import com.photoaibum.adapter.PhotoAdappter;
import com.photoaibum.entities.PhotoAibum;
import com.way.chat.activity.LoginActivity;
import com.way.chat.activity.R;

public class PhotoActivity extends Activity  {
	private GridView gv;
	private PhotoAibum aibum;
	private PhotoAdappter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photoalbum_gridview);
		aibum = (PhotoAibum)getIntent().getExtras().get("aibum");
		gv =(GridView)findViewById(R.id.photo_gridview);
		adapter = new PhotoAdappter(this,aibum);
		gv.setAdapter(adapter);
		gv.setOnItemClickListener(gvItemClickListener);
	}
	
	
	private OnItemClickListener gvItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(PhotoActivity.this,
					ChooseActivity.class);
			Bitmap bitmap = MediaStore.Images.Thumbnails.
					getThumbnail(PhotoActivity.this.getContentResolver(),  
							aibum.getBitList().get(position).getPhotoID(), 
							Thumbnails.MINI_KIND, null);
			System.out.println("before pic");
			intent.putExtra("picture", bitmap);
			startActivity(intent);	
			finish();
		}
	};
	@Override
	public void onBackPressed() // 捕捉返回键
	{
		Intent intent = new Intent(); // 新建一个Intent对象
		intent.setClass(PhotoActivity.this, PhotoAlbumActivity.class);// 指定intent要启动的类
		startActivity(intent); // 启动一个新的Activity
		finish(); // 关闭当前的Activity
	}
}
