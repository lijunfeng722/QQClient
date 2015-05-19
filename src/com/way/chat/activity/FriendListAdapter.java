package com.way.chat.activity;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.way.chat.common.bean.User;
import com.way.chat.common.util.BitmapUtil;
import com.way.util.GroupFriend;

public class FriendListAdapter extends BaseAdapter
{

	private Bitmap imgs;
	private Context context;
	private List<User> friendList;// 传递过来的经过处理的总数据
	


	public FriendListAdapter(Context context, List<User> friendList)
	{
		super();
		this.context = context;
		this.friendList = friendList;
	}

	@Override
	public int getCount()
	{
		return friendList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return friendList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.item, null);
		}
		final TextView title = (TextView) convertView
				.findViewById(R.id.name_item);// 显示用户名
		final TextView title2 = (TextView) convertView
				.findViewById(R.id.id_item);// 显示用户id
		ImageView icon = (ImageView) convertView
				.findViewById(R.id.imageView_item);// 显示用户头像

		final String name = friendList.get(position).getName();
		final String id = friendList.get(position).getId() + "";
		final String email = friendList.get(position).getEmail();
		imgs = BitmapUtil.toRoundCorner((Bitmap) friendList.get(position)
				.getImg(), 3);
		title.setText(name);// 大标题
		title2.setText(id);// 小标题
		Drawable drawable = new BitmapDrawable(imgs);
		icon.setImageDrawable(drawable);
		icon.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// 下面是切换到聊天界面处理
				User u = new User();
				u.setName(name);
				u.setId(Integer.parseInt(id));
				u.setImg(imgs);
				u.setEmail(email);
				Intent intent = new Intent(context, FriendMsg.class);
				intent.putExtra("user", u);
				((FriendListActivity)context).startActivityForResult(intent, 1);
				// Toast.makeText(Tab2.this, "开始聊天", 0).show();

			}
		});
		convertView.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// 下面是切换到聊天界面处理
				User u = new User();
				u.setName(name);
				u.setId(Integer.parseInt(id));
				u.setImg(imgs);
				u.setEmail(email);
				Intent intent = new Intent(context, ChatActivity.class);
				intent.putExtra("user", u);
				context.startActivity(intent);

			}
		});
		return convertView;
	}

	public void updata(List<User> friendList)
	{
		this.friendList = null;
		this.friendList = friendList;
	}
}
