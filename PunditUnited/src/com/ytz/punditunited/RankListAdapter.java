package com.ytz.punditunited;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

import com.parse.ParseUser;

public class RankListAdapter extends ArrayAdapter<ParseUser> {

	private Context context;
	private List<ParseUser> ParseUserList;
	private FacebookImageLoader mImageLoader;

	public RankListAdapter(Context context, List<ParseUser> list) {
		super(context, R.layout.rank_fragment, list);
		this.context = context;
		this.ParseUserList = list;
		mImageLoader = new FacebookImageLoader(context);
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
		} else
			holder = (ViewHolder) convertView.getTag();

		// TEXT
		int rank = position + 1;
		holder.tv_number.setText("" + rank);
		holder.tv_name.setText(ParseUserList.get(position).getString("Name"));
		
		// IMAGE
		String id = ParseUserList.get(position).getString("fbId");
		mImageLoader.load(id, holder.iv_profilePic);
		/*URL img_value = null;
		try {
			img_value = new URL("http://graph.facebook.com/" + id
					+ "/picture");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Bitmap mIcon1 = null;
		try {
			mIcon1 = BitmapFactory.decodeStream(img_value.openConnection()
					.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		holder.iv_profilePic.setImageBitmap(mIcon1);*/

		return convertView;
	}
}
