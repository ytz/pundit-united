package com.ytz.punditunited;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SocialListAdapter extends ArrayAdapter<ParseObject> {

	
	private Context context;
	private List<ParseObject> ParseObjectList;
	private static final String BASE_URL = "http://graph.facebook.com/";
	private static final String PICTURE = "/picture?width=100&height=100";



	public SocialListAdapter(Context context, List<ParseObject> list) {
		super(context, R.layout.list_status, list);
		this.context = context;
		this.ParseObjectList = list;
	}

	@Override
	public int getCount() {
		return ParseObjectList.size();
	}

	@Override
	public ParseObject getItem(int arg0) {
		return ParseObjectList.get(arg0);
	}
	
	private class ViewHolder {
		//TextView tv_number;
		ImageView iv_profilePic;
		TextView tv_name;
		//TextView tv_rankBy;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_status, null);
			holder = new ViewHolder();
			holder.iv_profilePic = (ImageView) convertView
					.findViewById(R.id.imageView_statusProfilePic);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.textView_statusName);

		} else
			holder = (ViewHolder) convertView.getTag();
		
		ParseUser user = (ParseUser) ParseObjectList.get(position).get("User");
		
		holder.tv_name.setText(user.getString("Name"));
		
		String id = user.getString("fbId");
		String url = BASE_URL + id + PICTURE;
		ImageLoader.getInstance().displayImage(url, holder.iv_profilePic); // Default options will be used

		return convertView;

	}
}
