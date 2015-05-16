package com.way.chat.activity;

import com.way.chat.common.bean.User;
import com.way.chat.common.tran.bean.TranObject;
import com.way.chat.common.tran.bean.TranObjectType;
import com.way.chat.common.util.Constants;
import com.way.client.Client;
import com.way.client.ClientOutputThread;
import com.way.util.DialogFactory;
import com.way.util.SharePreferenceUtil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FriendMsg extends MyActivity {
	private Button BtnAdd;
	private Button BtnCancel;
	private TextView nameView;
	private TextView idView;
	private TextView emailView;
	private ImageView ImgView;
	private TranObject msg;
	private MyApplication application;
	private SharePreferenceUtil util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_msg);
		application = (MyApplication) this.getApplicationContext();
		BtnAdd = (Button) findViewById(R.id.button1);
		BtnCancel = (Button) findViewById(R.id.button2);
		nameView = (TextView) findViewById(R.id.nameView);
		idView = (TextView) findViewById(R.id.idView);
		emailView = (TextView) findViewById(R.id.emailView);
		
		String ID =FriendMsg.this.getIntent().getStringExtra("ID");//获得陌生人ID
		
		msg = (TranObject) getIntent().getSerializableExtra(Constants.MSGKEY);
		User i=(User)msg.getObject();
		nameView.setText(i.getName());
		idView.setText(i.getId()+" ");
		emailView.setText(i.getEmail());
		
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		BtnAdd.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				submit();
				Intent intent = new Intent();  
                // 指定intent要启动的类  
                intent.setClass(FriendMsg.this, FriendListActivity.class);   
                startActivity(intent);
			}

		});
	
	}
	
	private Dialog mDialog = null;
	
	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在添加...");
		mDialog.show();
	}
	
	private void submit() {
		showRequestDialog();
			// 通过Socket验证信息
		Client client = application.getClient();
		ClientOutputThread out = client.getClientOutputThread();
//				System.out.println("fdfgdfjh");
		TranObject<User> o = new TranObject<User>(TranObjectType.FriendAdd);
		User u = new User();
		User v = (User)msg.getObject();
		u.setId(v.getId());
		o.setFromUser(Integer.parseInt(util.getId()));
		System.out.println(util.getId());
	    o.setObject(u);
		out.setMsg(o);
		System.out.println(o);
		Toast.makeText(getApplicationContext(), "添加成功", 0).show();
	}

	@Override
	public void getMessage(TranObject msg) {
		Toast.makeText(getApplicationContext(), "添加成功", 0).show();
		
	}
}
