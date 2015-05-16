package com.way.chat.activity;
import com.way.chat.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
public class ActivityChoose extends Activity {
    /** Called when the activity is first created. */
	private static String TAG="HelloStranger";
    @Override
    //���򴴽�
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      
        setContentView(R.layout.friendbef);
        Log.e(TAG,"onCreate");
        Button button1 = (Button) findViewById(R.id.lookotherbtn);  
        // ����button���¼���Ϣ   
        button1.setOnClickListener(new Button.OnClickListener() {  
            public void onClick(View v)  
            {  
                // �½�һ��Intent����   
                Intent intent = new Intent();  
                // ָ��intentҪ��������  
                intent.setClass(ActivityChoose.this, ActivityMap.class);  
                //����һ���µ�Activity  
                startActivity(intent);  
                // �رյ�ǰ��Activity   
                ActivityChoose.this.finish();  
            }  
        });  
        Button button2 = (Button) findViewById(R.id.decodeStrangerbtn);  
        // ����button���¼���Ϣ  
        button2.setOnClickListener(new Button.OnClickListener() {  
            public void onClick(View v)  
            {  
            	// �½�һ��Intent����   
                Intent intent = new Intent();  
                intent.putExtra("ID", ActivityChoose.this.getIntent().getStringExtra("ID"));
                // ָ��intentҪ��������  
//                intent.setClass(ActivityChoose.this, ActivityAddStranger.class);
                intent.setClass(ActivityChoose.this, PinTuActy.class);
                //����һ���µ�Activity  
                startActivity(intent);  
                // �رյ�ǰ��Activity   
                ActivityChoose.this.finish();  
            }  
        });  
    }  
    //����ʼ
    public void onStart()
    {
    	super.onStart();
    	Log.e(TAG,"onStart");
    }
    //����ָ�
    public void onResume()  
    {  
        super.onResume();  
        Log.v(TAG, "onResume");  
    }
    //������ͣ
    public void onPause()  
    {  
        super.onPause();  
        Log.v(TAG, "onPause");  
    }    
    //����ֹͣ
    public void onStop()  
    {  
        super.onStop();  
        Log.v(TAG, "onStop");  
    }  
    //�������
    public void onDestroy()  
    {  
        super.onDestroy();  
        Log.v(TAG, "onDestroy");  
    }  
    //��������
    public void onRestart()  
    {  
        super.onRestart();  
        Log.v(TAG, "onReStart");  
    }  
}