package com.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.way.chat.activity.LoginActivity;
import com.way.chat.activity.Photo;
import com.way.chat.activity.R;
import com.way.chat.activity.RegisterActivity;
import com.way.chat.common.util.Constants;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends Activity 
{
	private Button yesBtn     = null;
	private Button backBtn    = null;
	private File   mPhotoFile = null;
	private Bitmap myBitmap   = null;
	private byte[] mContent;
	private ImageView cameraImgv = null;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);
		System.out.println("after camera");
		yesBtn = (Button) findViewById(R.id.ca_yesBtn);
		backBtn = (Button) findViewById(R.id.ca_back_btn);
		cameraImgv = (ImageView) findViewById(R.id.imgview);
		yesBtn.setOnClickListener(click);
		backBtn.setOnClickListener(click);
		
		takePhotos();
	}
	private View.OnClickListener click = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			Intent intent = null;
			switch (v.getId())
			{
			case R.id.ca_yesBtn:
				intent = new Intent();  
                // 指定intent要启动的类  
                intent.setClass(CameraActivity.this, RegisterActivity.class); 
                System.out.println("before put");
                intent.putExtra("bitmap", mPhotoFile);
                //启动一个新的Activity  
                
                startActivity(intent);  
                // 关闭当前的Activity   
                finish();
				break;
			case R.id.ca_back_btn:
				intent = new Intent();  
                // 指定intent要启动的类  
                intent.setClass(CameraActivity.this, Photo.class);   
                //启动一个新的Activity  
                startActivity(intent);  
                // 关闭当前的Activity   
                finish();
				break;
			}
		}
	};
	private void takePhotos()
	{
		Intent intent = null;
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
	}
	
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
				Uri fileUri =null;
				fileUri=Uri.fromFile(mPhotoFile);
				if(fileUri!=null)
				{
					try
					{
						mContent = readStream(resolver.openInputStream(Uri
							.parse(fileUri.toString())));
					// 将字节数组转换为ImageView可调用的Bitmap对象
						myBitmap = getPicFromBytes(mContent, null);
						// //把得到的图片绑定在控件上显示
						cameraImgv.setImageBitmap(myBitmap);
					} catch(Exception e)
					{
						System.out.println(e.getMessage());
						return;
					}
				}
			
				cameraImgv.setImageBitmap(myBitmap);// 将图片显示在ImageView里
				/*-----------即时刷新相册——>向相册插入图片---------*/
		    	ContentValues localContentValues = new ContentValues(); 
		    	localContentValues.put("_data", mPhotoFile.toString()); 
		    	localContentValues.put("description", "save image ---"); 
		    	localContentValues.put("mime_type", "image/jpeg");
		    	Uri localUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; 
		    	resolver.insert(localUri, localContentValues);
				/*----------------------------------------------*/
			}
			break;
		}
	}
	public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) 
	{

		int w = bitmap.getWidth();

		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();

		float scaleWidth = ((float) width / w);

		float scaleHeight = ((float) height / h);

		matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出

		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

		return newbmp;
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

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, 
            double newHeight) { 
    // 获取这个图片的宽和高 
    float width = bgimage.getWidth(); 
    float height = bgimage.getHeight(); 
    // 创建操作图片用的matrix对象 
    Matrix matrix = new Matrix(); 
    // 计算宽高缩放率 
    float scaleWidth = ((float) newWidth) / width; 
    float scaleHeight = ((float) newHeight) / height; 
    // 缩放图片动作 
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, 
                    (int) height, matrix, true); 
    return bitmap; 
	}
	
	@Override
	public void onBackPressed() // 捕捉返回键
	{
		Intent intent = new Intent(); // 新建一个Intent对象
		intent.setClass(CameraActivity.this, RegisterActivity.class);// 指定intent要启动的类
		double maxSize =35.00; 
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）   
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); 
        byte[] b = baos.toByteArray(); 
        //将字节换成KB 
        double mid = b.length/1024; 
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩 
        if (mid > maxSize) { 
                //获取bitmap大小 是允许最大大小的多少倍 
                double i = mid / maxSize; 
                //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小） 
                myBitmap = zoomImage(myBitmap, myBitmap.getWidth() / Math.sqrt(i), 
                		myBitmap.getHeight() / Math.sqrt(i)); 
        } 
		intent.putExtra("bitmap", myBitmap);
		startActivity(intent); // 启动一个新的Activity
		finish(); // 关闭当前的Activity
	}
	private String getPhotoFileName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
}