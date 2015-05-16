package com.way.chat.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MyDialog extends Dialog{

private GestureDetector mGestureDetector;
private Bitmap bm, bmTemp;
 
private static final float SMALL_SCALE = 0.8f;
private static final float BIG_SCALE = 1.25f;
private int startX = 0, startY = 0;
private int imageWidth, imageHeight;
private float scaleWidth = 1, scaleHeight = 1;
private int displayWidth, displayHeight;
private ImageView imageView;
        private Button imageSmall, imageBig, imageClose;
    
    
public MyDialog(Context c,Bitmap bm) {
           super(c,R.style.dialog);
           this.bm = bm;
           this.bmTemp = bm;
           this.mGestureDetector = new GestureDetector( new ViewGestureListener());
}


protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.img);
           imageView = (ImageView) findViewById(R.id.myImageView);
           imageSmall = (Button) findViewById(R.id.image_small);
           imageBig = (Button) findViewById(R.id.image_big);
           imageClose = (Button) findViewById(R.id.image_close);
        
           init();
           writeImage();
           bindListener();
}
private void init() {
           DisplayMetrics dm = new DisplayMetrics();
           getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
           displayWidth = dm.widthPixels;
           displayHeight = dm.heightPixels;
           imageWidth = bm.getWidth();
           imageHeight = bm.getHeight();
}
private void writeImage() {
           int w = (w = bmTemp.getWidth()) > displayWidth ? displayWidth : w;
           int h = (h = bmTemp.getHeight()) > displayHeight ? displayHeight : h;
           if (startX + w <= bmTemp.getWidth() && startY + h <= bmTemp.getHeight()) {
               Bitmap bmOrg = Bitmap.createBitmap(bmTemp, startX, startY, w, h);
               imageView.setImageDrawable(new BitmapDrawable(bmOrg));
           } 
}
private void buttonShow() {
           imageSmall.setVisibility(View.VISIBLE);
           imageBig.setVisibility(View.VISIBLE);
           imageClose.setVisibility(View.VISIBLE);
}
private void bindListener() {
           Button.OnClickListener imageButtonListener = new Button.OnClickListener() {
               public void onClick(View v) {
                   switch (v.getId()) {
                       case R.id.image_small:
                           resize(SMALL_SCALE);
                           break;
                       case R.id.image_big:
                           resize(BIG_SCALE);
                           break;
                       case R.id.image_close:
                           MyDialog.this.dismiss();
                           break;
                       default:
                           break;
                   }
               }
           };
           imageSmall.setOnClickListener(imageButtonListener);
           imageBig.setOnClickListener(imageButtonListener);
           imageClose.setOnClickListener(imageButtonListener);
}
public boolean onTouchEvent(MotionEvent event) {
           buttonShow();
           return mGestureDetector.onTouchEvent(event);
}


private void resize(float scale) {
       Matrix matrix = new Matrix();
       scaleWidth = scaleWidth * scale;
       scaleHeight = scaleHeight * scale;
       matrix.postScale(scaleWidth, scaleHeight);
       
       bmTemp = Bitmap.createBitmap(bm, 0, 0, imageWidth, imageHeight, matrix, true);
       writeImage();
       
       // 小于图片实际大小就不让缩小
       if (scaleWidth * scale * imageWidth < imageWidth || scaleHeight * scale * imageHeight < imageHeight) {
            imageSmall.setEnabled(false);
            imageSmall.setTextColor(Color.GRAY);
       } else {
            imageSmall.setEnabled(true);
            imageSmall.setTextColor(Color.MAGENTA);
       }
       // 超过屏幕大小的5倍就不让放大
       if (scaleWidth * scale * imageWidth > displayWidth * 5 || scaleHeight * scale * imageHeight > displayHeight * 5) {
            imageBig.setEnabled(false);
            imageBig.setTextColor(Color.GRAY);
       } else {
            imageBig.setEnabled(true);
            imageBig.setTextColor(Color.MAGENTA);
       }
       
   }

class ViewGestureListener implements OnGestureListener{
           public boolean onDown(MotionEvent e) {
               return false;
           }
           public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
               return false;
           }
           public void onLongPress(MotionEvent e) {
           }
           public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
               startX = (startX + distanceX + displayWidth) > bmTemp.getWidth() ? startX : (int) (startX + distanceX);
               startY = (startY + distanceY + displayHeight) > bmTemp.getHeight() ? startY : (int) (startY + distanceY);
               startX = startX <= 0 ? 0 : startX;
               startY = startY <= 0 ? 0 : startY;
               writeImage();
               return false;
           }
           public void onShowPress(MotionEvent e) {
           }
           public boolean onSingleTapUp(MotionEvent e) {
               return false;
           }
}

}