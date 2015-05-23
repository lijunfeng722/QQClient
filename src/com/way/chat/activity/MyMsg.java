package com.way.chat.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.way.chat.common.bean.User;
import com.way.chat.common.util.Constants;
import com.way.util.SharePreferenceUtil;

public class MyMsg extends Activity {
	private TextView myName; 
	private TextView myId;
	private TextView myEmail;
	private Button btn;
	private SharePreferenceUtil util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_msg);
		myName = (TextView) findViewById(R.id.nameTvInMMSG);
		myId = (TextView) findViewById(R.id.idTvInMMSG);
		myEmail = (TextView) findViewById(R.id.emailTvInMMSG);
		btn = (Button) findViewById(R.id.editBtnInMMSG);
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		myName.setText(util.getName());
		myId.setText(util.getId());
		myEmail.setText(util.getEmail());
		btn.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MyMsg.this, SetMyMsg.class);
				startActivity(intent);
			}

		});
	}
}
