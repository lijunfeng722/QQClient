package com.photoaibum;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.way.chat.activity.ChatActivity;
import com.way.chat.activity.Photo;
import com.way.chat.activity.R;
import com.way.chat.activity.RegisterActivity;

public class ChooseActivity extends Activity
{
	private Button yesBtn = null;
	private Button backBtn = null;
	private Bitmap myBitmap = null;
	private ImageView cameraImgv = null;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choose);

		yesBtn = (Button) findViewById(R.id.choose_yesBtn);
		backBtn = (Button) findViewById(R.id.choose_back_btn);
		cameraImgv = (ImageView) findViewById(R.id.choose_imgview);

		if (null != getIntent().getExtras())
		{
			myBitmap = (Bitmap) getIntent().getExtras().get("picture");
		}
		yesBtn.setOnClickListener(click);
		backBtn.setOnClickListener(click);
		System.out.println("before pic");
		cameraImgv.setImageBitmap(myBitmap);// 将图片显示在ImageView里
		System.out.println("before take");
	}

	private View.OnClickListener click = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = null;
			switch (v.getId())
			{
			case R.id.choose_yesBtn:
				intent = new Intent();
				// 指定intent要启动的类
				intent.setClass(ChooseActivity.this, RegisterActivity.class);

				intent.putExtra("bitmap", myBitmap);

				// 启动一个新的Activity
				startActivity(intent);
				// 关闭当前的Activity
				finish();
				break;
			case R.id.choose_back_btn:
				intent = new Intent();
				// 指定intent要启动的类
				intent.setClass(ChooseActivity.this, Photo.class);
				// 启动一个新的Activity
				startActivity(intent);
				// 关闭当前的Activity
				finish();
				break;
			}
		}
	};

	@Override
	public void onBackPressed() // 捕捉返回键
	{
		Intent intent = new Intent(); // 新建一个Intent对象
		intent.setClass(ChooseActivity.this, Photo.class);// 指定intent要启动的类
		startActivity(intent); // 启动一个新的Activity
		finish(); // 关闭当前的Activity
	}

}
