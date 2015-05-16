package com.way.chat.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Photo extends Activity
{
	/** Called when the activity is first created. */
	private static String TAG = "HelloStranger";
	private static final int CAMERA = 1;
	private static final int ALBUM = 2;
	private static final String FILE_PATH = "/sdcard/syscamera.jpg";

	private Button cameraBtn = null;
	private Button certain = null;
	private Button mAlbum = null;
	private File mPhotoFile = null;
	private byte[] mContent;
	private Bitmap myBitmap;
	private ImageView cameraImgv = null;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		Log.e(TAG, "onCreate");
		mAlbum = (Button) findViewById(R.id.mypicture_btn);
		cameraBtn = (Button) findViewById(R.id.system_camera_btn);
		certain = (Button) findViewById(R.id.yes_btn);
		cameraImgv = (ImageView) findViewById(R.id.imageview);
		// 监听button的事件信息
		mAlbum.setOnClickListener(click);
		certain.setOnClickListener(click);
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
				intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("image/jpeg");
				startActivityForResult(intent, ALBUM);
				break;
			case R.id.system_camera_btn:
				intent = new Intent("android.media.action.IMAGE_CAPTURE");
				mPhotoFile = new File("sdcard/DCIM/", getPhotoFileName());
				mPhotoFile.mkdir();
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				// 把文件地址转换成Uri格式
				Uri uri = Uri.fromFile(mPhotoFile);
				// 设置系统相机拍摄照片完成后图片文件的存放地址
				intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent, CAMERA);
				break;
			case R.id.yes_btn:
				// 新建一个Intent对象
				intent = new Intent();
				// 指定intent要启动的类
				intent.setClass(Photo.this, RegisterActivity.class); // ActivityAdress.class);
				// 启动一个新的Activity
				startActivity(intent);
				// 关闭当前的Activity
				Photo.this.finish();

			}
		}
	};

	@Override
	public void onBackPressed() // 捕捉返回键
	{
		Intent intent = new Intent(); // 新建一个Intent对象
		intent.setClass(Photo.this, LoginActivity.class);// 指定intent要启动的类
		startActivity(intent); // 启动一个新的Activity
		finish(); // 关闭当前的Activity
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		ContentResolver resolver = getContentResolver();
		System.out.println("requestCode="+requestCode);
		switch (requestCode)
		{
		case CAMERA:
			if (Activity.RESULT_OK == resultCode)
			{
				Uri fileUri = Uri.fromFile(mPhotoFile);
				cameraImgv.setImageURI(fileUri);
			}
			break;
		case ALBUM:
			if (Activity.RESULT_OK == resultCode)
			{
				try
				{
					// 获得图片的uri
					Uri originalUri = data.getData();
					// 将图片内容解析成字节数组
					mContent = readStream(resolver.openInputStream(Uri
							.parse(originalUri.toString())));
					// 将字节数组转换为ImageView可调用的Bitmap对象
					myBitmap = getPicFromBytes(mContent, null);
					// //把得到的图片绑定在控件上显示
					cameraImgv.setImageBitmap(myBitmap);
				} catch (Exception e)
				{
					System.out.println(e.getMessage());
					return;
				}
			}
			break;
		}
	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts)
	{
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	public static byte[] readStream(InputStream inStream) throws Exception
	{
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1)
		{
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;

	}

	private String getPhotoFileName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	// 程序开始
	@Override
	public void onStart()
	{
		super.onStart();
		Log.e(TAG, "onStart");
	}

	// 程序恢复
	@Override
	public void onResume()
	{
		super.onResume();
		Log.v(TAG, "onResume");
	}

	// 程序暂停
	@Override
	public void onPause()
	{
		super.onPause();
		Log.v(TAG, "onPause");
	}

	// 程序停止
	@Override
	public void onStop()
	{
		super.onStop();
		Log.v(TAG, "onStop");
	}

	// 程序销毁
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.v(TAG, "onDestroy");
	}

	// 程序重启
	@Override
	public void onRestart()
	{
		super.onRestart();
		Log.v(TAG, "onReStart");
	}
}