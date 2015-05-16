package com.way.chat.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 消息ListView的Adapter
 * 
 * @author way
 */
public class ChatMsgViewAdapter extends BaseAdapter
{
	/*private int[] imgs =
	{ R.drawable.icon, R.drawable.f1, R.drawable.f2, R.drawable.f3,
			R.drawable.f4, R.drawable.f5, R.drawable.f6, R.drawable.f7,
			R.drawable.f8, R.drawable.f9 };*/
	private Bitmap imgs;

	public static interface IMsgViewType
	{
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	private static final int ITEMCOUNT = 2;// 消息类型的总数
	private List<ChatMsgEntity> coll;// 消息对象数组
	private LayoutInflater mInflater;
	private Context context;

	public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> coll)
	{
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	public int getCount()
	{
		return coll.size();
	}

	public Object getItem(int position)
	{
		return coll.get(position);
	}

	public long getItemId(int position)
	{
		return position;
	}

	/**
	 * 得到Item的类型，是对方发过来的消息，还是自己发送出去的
	 */
	public int getItemViewType(int position)
	{
		ChatMsgEntity entity = coll.get(position);

		if (entity.isComMsg())
		{// 收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else
		{// 自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item类型的总数
	 */
	public int getViewTypeCount()
	{
		return ITEMCOUNT;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{

		final ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.isComMsg();

		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			if (isComMsg)
			{
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else
			{
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			}

			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			viewHolder.isComMsg = isComMsg;

			
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvContent.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (entity.getMsgType() == ChatMsgEntity.MSG_TYPE_VIOCE)
				{
					MediaPlayer mPlayer = MediaPlayer.create(context,Uri.parse(entity.getPath()));
					System.out.println("tvContent onclick getPath="+entity.getPath());
					mPlayer.start();
				}
				else if (entity.getMsgType() == ChatMsgEntity.MSG_TYPE_IMAGE)
				{
					Bitmap bmp=entity.getImageByte();
					System.out.println(bmp);
					System.out.println("tvContent onclick getPath="+entity.getPath());
					MyDialog dialog = new MyDialog(context,bmp);
					dialog.show();
					
				}
				else
					Toast.makeText(context, entity.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
		viewHolder.tvSendTime.setText(entity.getDate());
		viewHolder.tvUserName.setText(entity.getName());
		int type = entity.getMsgType();
		if (type == 0)
			viewHolder.tvContent.setText(entity.getMessage());
		else if(type==1)
		{
			MediaPlayer mp = MediaPlayer.create(context,
					Uri.parse(entity.getPath()));
			System.out.println("entity.getPath=" + entity.getPath());
			int duration = mp.getDuration();
			viewHolder.tvContent.setText(")))" + duration);
		}
		else{
			Drawable drawable = new BitmapDrawable(entity.getImageByte());
			viewHolder.tvContent.setText("点我有惊喜");
		}
		Drawable drawable = new BitmapDrawable(imgs); 
		viewHolder.icon.setImageDrawable(drawable);
		return convertView;
	}

	static class ViewHolder
	{
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public ImageView image;
		public ImageView icon;
		public boolean isComMsg = true;
	}

}
