package com.ytz.punditunited;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
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
		ImageView iv_profilePic;
		TextView tv_name;
		TextView tv_special;
		TextView tv_comment;
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
			holder.tv_special = (TextView) convertView
					.findViewById(R.id.textView_statusSpecial);
			holder.tv_comment = (TextView) convertView
					.findViewById(R.id.textView_statusComment);

		} else
			holder = (ViewHolder) convertView.getTag();

		ParseUser user = (ParseUser) ParseObjectList.get(position).get("User");

		holder.tv_name.setText(user.getString("Name"));

		String id = user.getString("fbId");
		String url = BASE_URL + id + PICTURE;
		// ImageSize targetSize = new ImageSize(50, 50);
		ImageLoader.getInstance().displayImage(url, holder.iv_profilePic); 

		// Special Status
		if (!ParseObjectList.get(position).getString("SpecialStatus").isEmpty()) {
			//holder.tv_special.setText(ParseObjectList.get(position).getString("SpecialStatus"));
			holder.tv_special.setText(Html.fromHtml(ParseObjectList.get(position).getString("SpecialStatus")));
			
		}
		
		// Status
		if (!ParseObjectList.get(position).getString("Status").isEmpty()) {
			holder.tv_comment.setText(ParseObjectList.get(position).getString("Status"));
		}

		return convertView;

	}
}
