package com.photoaibum;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.way.chat.activity.R;

public class PhotoGridItem extends RelativeLayout  {
	private Context mContext;
	private ImageView mImageView;
	
	public PhotoGridItem(Context context) {
		this(context, null, 0);
	}
	
	public PhotoGridItem(Context context, AttributeSet attrs) {  
        this(context, attrs, 0);  
    }

	public PhotoGridItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		LayoutInflater.from(mContext).inflate(R.layout.photo_item, this);
		mImageView = (ImageView)findViewById(R.id.photo_img_view);
	}

	public void setImgResID(int id){
		if(mImageView != null){
			mImageView.setBackgroundResource(id);
		}
	}
	
	public void SetBitmap(Bitmap bit){
		if(mImageView != null){
			mImageView.setImageBitmap(bit);
		}
	}
	
	
}
