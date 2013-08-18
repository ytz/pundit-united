package com.ytz.punditunited;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.ParseUser;

public class RankListAdapter extends ArrayAdapter<ParseUser> {

	private Context context;
	private List<ParseUser> ParseUserList;
	private FacebookImageLoader mImageLoader;
	DecimalFormat df = new DecimalFormat("0.00"); // display odds in 2decimal
													// place
	private static final String BASE_URL = "http://graph.facebook.com/";
	// http://stackoverflow.com/questions/11743768/how-to-get-facebook-profile-large-square-picture
	private static final String PICTURE = "/picture?width=100&height=100";

	public RankListAdapter(Context context, List<ParseUser> list) {
		super(context, R.layout.rank_fragment, list);
		this.context = context;
		this.ParseUserList = list;
		//mImageLoader = new FacebookImageLoader(context);
	}

	@Override
	public int getCount() {
		return ParseUserList.size();
	}

	@Override
	public ParseUser getItem(int arg0) {
		return ParseUserList.get(arg0);
	}

	private class ViewHolder {
		TextView tv_number;
		ImageView iv_profilePic;
		TextView tv_name;
		TextView tv_rankBy;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.rank_list, null);
			holder = new ViewHolder();
			holder.tv_number = (TextView) convertView
					.findViewById(R.id.textView_rankNumber);
			holder.iv_profilePic = (ImageView) convertView
					.findViewById(R.id.imageView_profilePic);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.textView_name);
			holder.tv_rankBy = (TextView) convertView
					.findViewById(R.id.textView_rankBy);
		} else
			holder = (ViewHolder) convertView.getTag();

		// TEXT (Rank, Name, Points)
		int rank = position + 1;
		holder.tv_number.setText("" + rank);
		holder.tv_name.setText(ParseUserList.get(position).getString("Name"));
		holder.tv_rankBy.setText(""
				+ df.format(ParseUserList.get(position).getNumber("Points")
						.floatValue())); // by points first

		// IMAGE
		String id = ParseUserList.get(position).getString("fbId");
		String url = BASE_URL + id + PICTURE;
		// mImageLoader.load(id, holder.iv_profilePic);
		ImageLoader.getInstance().displayImage(url, holder.iv_profilePic); // Default options will be used

		return convertView;
	}
}
