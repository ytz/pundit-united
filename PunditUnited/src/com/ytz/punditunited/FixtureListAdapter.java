package com.ytz.punditunited;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FixtureListAdapter extends ArrayAdapter<ParseObject> {

	Context context;
	List<ParseObject> ParseObjectList;
	DecimalFormat df = new DecimalFormat("#.00"); // display odds in 2decimal
													// place

	public FixtureListAdapter(Context context, List<ParseObject> ParseObjectList) {
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
		TextView tv_homeOdds;
		TextView tv_awayOdds;
		TextView tv_drawOdds;
		ImageView iv_home;
		ImageView iv_away;
		TextView tv_time;
		LinearLayout lo_home;
		LinearLayout lo_draw;
		LinearLayout lo_away;
		TextView tv_H;
		TextView tv_D;
		TextView tv_A;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fixture_list, null);
			holder = new ViewHolder();
			holder.tv_homeTeam = (TextView) convertView
					.findViewById(R.id.textView_home);
			holder.tv_awayTeam = (TextView) convertView
					.findViewById(R.id.textView_away);

			holder.tv_homeOdds = (TextView) convertView
					.findViewById(R.id.textView_homeOdds);
			holder.tv_awayOdds = (TextView) convertView
					.findViewById(R.id.textView_awayOdds);
			holder.tv_drawOdds = (TextView) convertView
					.findViewById(R.id.textView_drawOdds);

			holder.iv_home = (ImageView) convertView
					.findViewById(R.id.imageView_home);
			holder.iv_away = (ImageView) convertView
					.findViewById(R.id.imageView_away);

			holder.tv_time = (TextView) convertView
					.findViewById(R.id.textView_time);

			holder.lo_home = (LinearLayout) convertView
					.findViewById(R.id.layout_HomeFixture);
			holder.lo_draw = (LinearLayout) convertView
					.findViewById(R.id.layout_DrawFixture);
			holder.lo_away = (LinearLayout) convertView
					.findViewById(R.id.layout_AwayFixture);

			holder.tv_H = (TextView) convertView.findViewById(R.id.textView_H);
			holder.tv_D = (TextView) convertView.findViewById(R.id.textView_D);
			holder.tv_A = (TextView) convertView.findViewById(R.id.textView_A);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		// WORK STARTS HERE
		String home = ParseObjectList.get(position).getString("Home");
		String away = ParseObjectList.get(position).getString("Away");
		Date date = ParseObjectList.get(position).getDate("Date");

		Calendar matchday = Calendar.getInstance(TimeZone.getDefault());
		matchday.setTime(date);

		// SELECTION
		// int selection = ParseObjectList.get(position).getInt("Selection");
		SharedPreferences preference = context.getSharedPreferences(
				"Selection", 0);
		int selection = preference.getInt(ParseObjectList.get(position)
				.getObjectId(), -1);

		// TEAM NAMES
		holder.tv_homeTeam.setText(ClubHelper.getShortName(home));
		holder.tv_awayTeam.setText(ClubHelper.getShortName(away));

		// TEAM IMAGE
		holder.iv_home.setImageResource(ClubHelper.getImageResource(home));
		holder.iv_away.setImageResource(ClubHelper.getImageResource(away));

		// TIME
		String zone;
		int hour = matchday.get(Calendar.HOUR_OF_DAY);
		if (hour > 12) {
			hour -= 12;
			zone = "PM";
		} else if (hour == 0) {
			hour = 12;
			zone = "AM";
		} else
			zone = "AM";

		int minute = matchday.get(Calendar.MINUTE);
		String time;
		if (minute == 0) {
			time = "" + hour + "\n" + zone;
		} else
			time = "" + hour + "\n" + minute + "\n" + zone;

		holder.tv_time.setText(time);

		// IF THERE IS NO SCORE
		if (ParseObjectList.get(position).getNumber("H_Goal") == null) {

			// ODDS
			holder.tv_homeOdds.setText(""
					+ df.format(ParseObjectList.get(position).getNumber(
							"H_odds")));
			holder.tv_awayOdds.setText(""
					+ df.format(ParseObjectList.get(position).getNumber(
							"A_odds")));
			holder.tv_drawOdds.setText(""
					+ df.format(ParseObjectList.get(position).getNumber(
							"D_odds")));

			// HIGHLIGHT

			LinearLayout myLayout = null;
			switch (selection) {
			case -1:
				break;
			case 0:
				myLayout = holder.lo_home;
				myLayout.setBackgroundColor(Color.parseColor("#81ddff"));
				break;
			case 1:
				myLayout = holder.lo_draw;
				myLayout.setBackgroundColor(Color.parseColor("#81ddff"));
				break;
			case 2:
				myLayout = holder.lo_away;
				myLayout.setBackgroundColor(Color.parseColor("#81ddff"));
				break;
			}
		}

		// FORMAT WHEN THERE IS SCORE
		else {
			// CLEAR H,D,A
			holder.lo_home.removeView(holder.tv_H);
			holder.lo_draw.removeView(holder.tv_D);
			holder.lo_away.removeView(holder.tv_A);

			// SCORE
			int hGoal = ParseObjectList.get(position).getNumber("H_Goal")
					.intValue();
			int aGoal = ParseObjectList.get(position).getNumber("A_Goal")
					.intValue();
			holder.tv_homeOdds.setText("" + hGoal + ":" + aGoal);

			// SELECTION
			switch (selection) {
			case -1:
				break;
			case 0:
				holder.tv_drawOdds.setText("H");
				break;
			case 1:
				holder.tv_drawOdds.setText("D");
				break;
			case 2:
				holder.tv_drawOdds.setText("A");
				break;
			}
			
			/*
			// IF THERE IS A SELECTION
			if (selection != -1) {
				SharedPreferences amtWon = context.getSharedPreferences(
						"AmtWon", 0);
				int won = amtWon.getInt(ParseObjectList.get(position)
						.getObjectId(), -1);
				// IF NO RESULT IN PHONE DATA
				if (won == -1) {
					int result;
					if (hGoal > aGoal)
						result = 0;
					else if (hGoal < aGoal)
						result = 2;
					else
						result = 1;
					SharedPreferences amtBet = context.getSharedPreferences(
							"AmtBet", 0);
					if (selection == result) {
						switch (selection) {
						case 0:
							won = ParseObjectList.get(position)
									.getNumber("H_odds").intValue()
									* amtBet.getInt(
											ParseObjectList.get(position)
													.getObjectId(), -1);
							break;
						case 1:
							won = ParseObjectList.get(position)
									.getNumber("D_odds").intValue()
									* amtBet.getInt(
											ParseObjectList.get(position)
													.getObjectId(), -1);
							break;
						case 2:
							won = ParseObjectList.get(position)
									.getNumber("A_odds").intValue()
									* amtBet.getInt(
											ParseObjectList.get(position)
													.getObjectId(), -1);
							break;
						default:
							break;
						} // switch
					} // if
					else{
						won = -1 * amtBet.getInt(
								ParseObjectList.get(position)
								.getObjectId(), -1);
					}
					amtWon.edit().putInt(ParseObjectList.get(position)
								.getObjectId(), won);
					final int win = won;
					ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
					query.whereEqualTo("User", ParseUser.getCurrentUser());
					query.whereEqualTo("Match", ParseObjectList.get(position));
					query.findInBackground(new FindCallback<ParseObject>() {
					    @Override
						public void done(List<ParseObject> historyList, ParseException e) {
					        if (e == null) {
					            historyList.get(0).add("Check", true);
					            historyList.get(0).add("WinAmount", win);
					        } else {
					            Log.d("score", "Error: " + e.getMessage());
					        }
					    }
					});
				} // if
				holder.tv_awayOdds.setText("" + won);
			}*/

		}

		return convertView;
	}

	private int placedBet(int position) {
		// TODO Auto-generated method stub
		ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
		query.whereEqualTo("Match", ParseObjectList.get(position));
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		/*
		 * query.getFirstInBackground(new GetCallback<ParseObject>() { public
		 * void done(ParseObject object, ParseException e) { if (e == null) {
		 * selection = object.getInt("BetType"); // get selection to //
		 * highlight //layoutHighlight(selection); } else { //tempBoolean =
		 * false; selection = -1; } } });
		 */
		try {
			return query.getFirst().getInt("BetType");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	/*
	 * protected void layoutHighlight(int selection) { LinearLayout myLayout =
	 * null; switch (selection) { case 0: myLayout = holder.lo_home; break; case
	 * 1: myLayout = holder.lo_draw; break; case 2: myLayout = holder.lo_away;
	 * break; } myLayout.setBackgroundColor(Color.parseColor("#81ddff"));
	 * 
	 * }
	 */

}
