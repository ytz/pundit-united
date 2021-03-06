package com.ytz.punditunited;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PredictFragment extends Fragment implements
		PredictDialogFragment.PredictDialogFragmentListener {

	private String matchID;
	private DecimalFormat df = new DecimalFormat("#.##");
	private int gameweek;
	public static String SELECTION = "com.ytz.punditunited.SELECTION";

	private String home;
	private String away;
	private String homeOdds;
	private String drawOdds;
	private String awayOdds;
	private Date date;
	private LinearLayout buttonH;
	private LinearLayout buttonD;
	private LinearLayout buttonA;
	// private int selection;
	private View view;
	private boolean tempBoolean;
	protected int mySelection;
	public static int CHANGE = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		// return inflater.inflate(R.layout.predict_fragment, container, false);
		view = inflater.inflate(R.layout.predict_fragment, container, false);

		date = new Date(getActivity().getIntent().getExtras()
				.getLong(FixtureFragment.DATE));

		home = getActivity().getIntent().getExtras()
				.getString(FixtureFragment.HOME);
		away = getActivity().getIntent().getExtras()
				.getString(FixtureFragment.AWAY);
		matchID = getActivity().getIntent().getExtras()
				.getString(FixtureFragment.MATCHID);

		// TEXT
		TextView tv_home = (TextView) view
				.findViewById(R.id.textView_HfullName);
		TextView tv_away = (TextView) view
				.findViewById(R.id.textView_AfullName);

		TextView tv_hOdds = (TextView) view
				.findViewById(R.id.textView_pHomeOdds);
		TextView tv_dOdds = (TextView) view
				.findViewById(R.id.textView_pDrawOdds);
		TextView tv_aOdds = (TextView) view
				.findViewById(R.id.textView_pAwayOdds);

		// SET TEXT
		tv_home.setText(home);
		tv_away.setText(away);

		homeOdds = String.valueOf(df.format(getActivity().getIntent()
				.getExtras().getDouble(FixtureFragment.H_ODDS)));
		drawOdds = String.valueOf(df.format(getActivity().getIntent()
				.getExtras().getDouble(FixtureFragment.D_ODDS)));
		awayOdds = String.valueOf((df.format(getActivity().getIntent()
				.getExtras().getDouble(FixtureFragment.A_ODDS))));

		tv_hOdds.setText(String.valueOf(df.format(getActivity().getIntent()
				.getExtras().getDouble(FixtureFragment.H_ODDS))));
		tv_dOdds.setText(String.valueOf(df.format(getActivity().getIntent()
				.getExtras().getDouble(FixtureFragment.D_ODDS))));
		tv_aOdds.setText(String.valueOf((df.format(getActivity().getIntent()
				.getExtras().getDouble(FixtureFragment.A_ODDS)))));

		// IMAGE
		ImageView iv_home = (ImageView) view.findViewById(R.id.imageView_pHome);
		ImageView iv_away = (ImageView) view.findViewById(R.id.imageView_pAway);

		// SET IMAGE
		iv_home.setImageResource(ClubHelper.getImageResource(home));
		iv_away.setImageResource(ClubHelper.getImageResource(away));

		// Percent
		TextView tv_H_percent = (TextView) view.findViewById(R.id.tv_hPercent);
		TextView tv_D_percent = (TextView) view.findViewById(R.id.tv_dPercent);
		TextView tv_A_percent = (TextView) view.findViewById(R.id.tv_aPercent);

		tv_H_percent.setText(""
				+ df.format(getActivity().getIntent().getExtras()
						.getFloat(FixtureFragment.H_PERCENT)) + "%");
		tv_D_percent.setText(""
				+ df.format(getActivity().getIntent().getExtras()
						.getFloat(FixtureFragment.D_PERCENT)) + "%");
		tv_A_percent.setText(""
				+ df.format(getActivity().getIntent().getExtras()
						.getFloat(FixtureFragment.A_PERCENT)) + "%");

		// BUTTONS
		// BET HOME
		gameweek = getActivity().getIntent().getExtras()
				.getInt(FixtureFragment.GW);

		buttonH = (LinearLayout) view.findViewById(R.id.linearlayout_Hbutton);
		buttonH.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openDialog(0);
			}
		});

		// BET DRAW
		buttonD = (LinearLayout) view.findViewById(R.id.linearlayout_Dbutton);
		buttonD.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openDialog(1);
			}
		});

		// BET AWAY
		buttonA = (LinearLayout) view.findViewById(R.id.linearlayout_Abutton);
		buttonA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openDialog(2);
			}
		});

		// placedBet();
		checkStatus();

		return view;
	}

	private void checkStatus() {
		// if (tempBoolean || matchExpire())
		// update(mySelection);
		if (checkSelection() || matchExpire())
			update(mySelection);

	}

	private boolean matchExpire() {
		// TODO Auto-generated method stub
		if (Calendar.getInstance().getTime().after(date))
			return true;
		return false;
	}

	/**
	 * NOT IN USE
	 */
	private void placedBet() {
		// TODO Auto-generated method stub
		ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
		query.whereEqualTo("Match",
				ParseObject.createWithoutData("Fixture", matchID));
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		query.whereEqualTo("Check", true);
		query.getFirstInBackground(new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					int selection = object.getInt("BetType"); // get selection
																// to
																// highlight
					System.out.println("selection " + selection);
					tempBoolean = true;
					mySelection = selection;
					checkStatus();
				} else {
					tempBoolean = false;
					checkStatus();
				}
			}
		});
	}

	private boolean checkSelection() {
		// SELECTION
		SharedPreferences preference = getActivity().getSharedPreferences(
				"Selection", 0);
		mySelection = preference.getInt(matchID, -1);
		if (mySelection != -1)
			return true;
		else
			return false;
	}

	/**
	 * Send information and open dialog
	 * 
	 * @param type
	 */
	protected void openDialog(int type) {
		// TODO Auto-generated method stub
		DialogFragment predictDialog = new PredictDialogFragment();
		predictDialog.setTargetFragment(this, 0);
		Intent intent = new Intent();
		intent.putExtra(SELECTION, type);
		intent.putExtra(FixtureFragment.HOME, home);
		intent.putExtra(FixtureFragment.AWAY, away);

		intent.putExtra(FixtureFragment.H_ODDS, homeOdds);
		intent.putExtra(FixtureFragment.D_ODDS, drawOdds);
		intent.putExtra(FixtureFragment.A_ODDS, awayOdds);

		intent.putExtra(FixtureFragment.GW, gameweek);
		intent.putExtra(FixtureFragment.MATCHID, matchID);

		predictDialog.setArguments(intent.getExtras());
		predictDialog.show(getActivity().getSupportFragmentManager(),
				"predictDialog");
	}

	/**
	 * UPdate from Dialog, which redirects to update
	 */
	@Override
	public void updateFromDialog(int selection) {
		CHANGE = 1;
		update(selection);
	}

	/**
	 * Disable buttons, highlight selection
	 * 
	 * @param selection
	 */
	public void update(int selection) {
		buttonH.setEnabled(false);
		buttonD.setEnabled(false);
		buttonA.setEnabled(false);
		highlightLayout(selection);
	}

	private void highlightLayout(int selection) {
		LinearLayout myLayout = null;
		switch (selection) {
		case -1: return;
		case 0:
			myLayout = (LinearLayout) view
					.findViewById(R.id.layout_HomePredict);
			break;
		case 1:
			myLayout = (LinearLayout) view
					.findViewById(R.id.layout_DrawPredict);
			break;
		case 2:
			myLayout = (LinearLayout) view
					.findViewById(R.id.layout_AwayPredict);
			break;
		}
		myLayout.setBackgroundColor(Color.parseColor("#81ddff"));
	}

}
