package com.ytz.punditunited;

import java.text.DecimalFormat;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
	DecimalFormat df = new DecimalFormat("#.00"); // display odds in 2decimal

	// place

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

		// GW
		holder.tv_gameweek.setText("GW "
				+ ParseObjectList.get(position).getNumber("GW"));

		// Club short name
		holder.tv_homeTeam.setText("" + ClubHelper.getShortName(home));
		holder.tv_awayTeam.setText("" + ClubHelper.getShortName(away));
		
		// BetType
		int selection = ParseObjectList.get(position).getInt("BetType");
		String betType = null;
		switch (selection) {
		case 0:
			betType = "H";
			break;
		case 1:
			betType = "D";
			break;
		case 2:
			betType = "A";
			break;
		}

		// Score
		// If there is a score
		if (ParseObjectList.get(position).getParseObject("Match")
				.getNumber("H_Goal") != null) {
			int homeGoal = ParseObjectList.get(position)
					.getParseObject("Match").getNumber("H_Goal").intValue();
			int awayGoal = ParseObjectList.get(position)
					.getParseObject("Match").getNumber("A_Goal").intValue();
			
			holder.tv_score.setText(homeGoal + " : " + awayGoal + "   " + betType);

			// Points
			final SharedPreferences amtWon = context.getSharedPreferences(
					"AmtWon", 0);
			float amount = amtWon.getFloat(ParseObjectList.get(position)
					.getParseObject("Match").getObjectId(), -1);
			if (amount != -1) {
				String sign = "";
				if (amount > 0)
					sign = "+";
				holder.tv_points.setText(sign + df.format(amount));
			}
		}

		// If there is no score
		else{
			holder.tv_score.setText(betType); // show selection
			holder.tv_points.setText("" + ParseObjectList.get(position).getInt("BetAmount")); // show amt bet
		}

		// Club Logo
		holder.iv_home.setImageResource(ClubHelper.getImageResource(home));
		holder.iv_away.setImageResource(ClubHelper.getImageResource(away));

		return convertView;
	}
}
