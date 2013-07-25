package com.ytz.punditunited;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

public class HistoryListAdapter extends ArrayAdapter<ParseObject> {

	private Context context;
	private List<ParseObject> ParseObjectList;

	public HistoryListAdapter(Context context, List<ParseObject> ParseObjectList) {
		super(context, R.layout.fixture_fragment, ParseObjectList);
		this.context = context;
		this.ParseObjectList = ParseObjectList;
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
		TextView tv_homeTeam;
		TextView tv_awayTeam;
		TextView tv_gameweek;
		TextView tv_score;
		TextView tv_points;
		ImageView iv_home;
		ImageView iv_away;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.history_list, null);
			holder = new ViewHolder();
			holder.tv_homeTeam = (TextView) convertView
					.findViewById(R.id.textView_historyHome);
			holder.tv_awayTeam = (TextView) convertView
					.findViewById(R.id.textView_historyAway);

			holder.tv_gameweek = (TextView) convertView
					.findViewById(R.id.textView_gw);
			holder.tv_score = (TextView) convertView
					.findViewById(R.id.textView_historyScore);
			holder.tv_points = (TextView) convertView
					.findViewById(R.id.textView_historyPoints);

			holder.iv_home = (ImageView) convertView
					.findViewById(R.id.imageView_historyHome);
			holder.iv_away = (ImageView) convertView
					.findViewById(R.id.imageView_historyAway);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		String home = ParseObjectList.get(position).getParseObject("Match")
				.getString("Home");
		String away = ParseObjectList.get(position).getParseObject("Match")
				.getString("Away");

		holder.tv_gameweek.setText("GW "
				+ ParseObjectList.get(position).getNumber("GW"));
		holder.tv_homeTeam.setText("" + ClubHelper.getShortName(home));
		holder.tv_awayTeam.setText("" + ClubHelper.getShortName(away));
		// holder.tv_score.setText();
		// holder.tv_points.setText();

		holder.iv_home.setImageResource(ClubHelper.getImageResource(home));
		holder.iv_away.setImageResource(ClubHelper.getImageResource(away));

		return convertView;
	}
}
