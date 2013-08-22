package com.ytz.punditunited;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.text.Spannable;
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
		TextView tv_title;
		TextView tv_time;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_status, null);
			holder = new ViewHolder();
			holder.iv_profilePic = (ImageView) convertView
					.findViewById(R.id.imageView_statusProfilePic);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.textView_statusName);
			if (type == 1){
			holder.tv_special = (TextView) convertView
					.findViewById(R.id.textView_statusSpecial);
			}
			holder.tv_comment = (TextView) convertView
					.findViewById(R.id.textView_statusComment);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.textView_statusTitle);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.textView_statusTime);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// Name
		ParseUser user = (ParseUser) ParseObjectList.get(position).get("User");
		if (holder.tv_name == null)
			System.out.println("HOLDER NULL!");
		if (user == null)
			System.out.println("USER NULL!");
		holder.tv_name.setText(user.getString("Name"));

		// Profile Pic
		String id = user.getString("fbId");
		String url = BASE_URL + id + PICTURE;
		ImageLoader.getInstance().displayImage(url, holder.iv_profilePic);

		// Special Status
		if (ParseObjectList.get(position).getString("SpecialStatus") != null) {
			Spannable s = ClubHelper.getClubEmoteText(getContext(),
					ParseObjectList.get(position).getString("SpecialStatus"));
			holder.tv_special.setText(s);
		}

		// Status
		if (ParseObjectList.get(position).getString("Status") != null) {
			holder.tv_comment.setText(ParseObjectList.get(position).getString(
					"Status"));
		}

		// Title
		if (user.getString("Title") != null) {
			holder.tv_title.setText(user.getString("Title"));
		}

		// Time
		Date postDate = ParseObjectList.get(position).getCreatedAt();
		long diff = Calendar.getInstance().getTime().getTime()
				- postDate.getTime();
		String time = "";

		if (diff / (60 * 60 * 1000) >= 24) {
			SimpleDateFormat sf = new SimpleDateFormat("d MMM");
			time = sf.format(postDate);
		} else if (diff / (60 * 60 * 1000) > 0)
			time = "" + (diff / (60 * 60 * 1000)) + "h";
		else if (diff / (60 * 1000) > 0)
			time = "" + (diff / (60 * 1000)) + "m";
		else
			time = "" + (diff / 1000) + "s";
		holder.tv_time.setText("" + time);

		return convertView;

	}
	
	@Override
	public int getItemViewType(int position){
		// Have Special - 1, No Special - 0
		if (ParseObjectList.get(position).getString("SpecialStatus") != null)
			return 1;
		else return 0;
	}
	
	@Override
	public int getViewTypeCount(){
		return 2;
	}
}
