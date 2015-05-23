package com.way.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.way.chat.common.bean.User;
import com.way.chat.common.tran.bean.TranObject;
import com.way.chat.common.tran.bean.TranObjectType;
import com.way.chat.common.util.Constants;
import com.way.client.Client;
import com.way.client.ClientOutputThread;
import com.way.util.DialogFactory;

public class AddIdFriend extends MyActivity {
	private EditText mAccounts;
	private Button addBtn;
	private MyApplication application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_add);
		application = (MyApplication) this.getApplicationContext();
		mAccounts = (EditText) findViewById(R.id.addIdTvInFADD);
		addBtn = (Button) findViewById(R.id.addBtnInFADD);
		addBtn.setOnClickListener(new Button.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				submit();
			}

		});
		
	}
	
	private Dialog mDialog = null;
	
	private void showRequestDialog() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		mDialog = DialogFactory.creatRequestDialog(this, "正在查询...");
		mDialog.show();
	}
	
	private void submit() {
		String accounts = mAccounts.getText().toString();
		if (accounts.length() == 0) {
			DialogFactory.ToastDialog(this, "添加好友", "亲！帐号不能为空哦");
		} else {
			showRequestDialog();
			// 通过Socket验证信息
				Client client = application.getClient();
				ClientOutputThread out = client.getClientOutputThread();
//				System.out.println("fdfgdfjh");
				TranObject<User> o = new TranObject<User>(TranObjectType.FriendCheck);
				User u = new User();
				u.setId(Integer.parseInt(accounts));
				o.setObject(u);
				out.setMsg(o);
		}
	}
	
	@Override
	public void getMessage(TranObject msg) {
	//	System.out.println("fdfgdfjh");
		if (msg != null) {
			System.out.println("Result:" + msg);
			switch (msg.getType()) {
			case FriendCheck:// LoginActivity只处理登录的消息
				User friend = (User) msg.getObject();
				System.out.println(friend);
				if(friend.getId()==0) {
					DialogFactory.ToastDialog(AddIdFriend.this, "查询结果",
							"亲！您输入的账号不存在哦");
					if (mDialog.isShowing())
						mDialog.dismiss();
				}
				else {
					// 保存用户信息
					/*SharePreferenceUtil util = new SharePreferenceUtil(
							LoginActivity.this, Constants.SAVE_USER);
					util.setId(mAccounts.getText().toString());
					util.setPasswd(mPassword.getText().toString());
					util.setEmail(list.get(0).getEmail());
					util.setName(list.get(0).getName());
					util.setImg(list.get(0).getImg());

					UserDB db = new UserDB(LoginActivity.this);
					db.addUser(list);*/

					
					Intent intent = new Intent();  
	                // 指定intent要启动的类  
	                intent.setClass(AddIdFriend.this, StrangerMsg.class);  
	                intent.putExtra(Constants.MSGKEY, msg);
	                //启动一个新的Activity  
	                startActivity(intent); 
	                finish();
					
				} 
				break;
			default:
				break;
			}
		}
	}
}
