package com.ytz.punditunited;

import java.text.DecimalFormat;

import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PredictFragment extends Fragment {
	
	private String matchID;
	private DecimalFormat df = new DecimalFormat("#.00");
	private int gameweek;
	public static String SELECTION = "com.ytz.punditunited.SELECTION";
	
	private String home;
	private String away;
	private String homeOdds;
	private String drawOdds;
	private String awayOdds;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		// return inflater.inflate(R.layout.predict_fragment, container, false);

		View view = inflater.inflate(R.layout.predict_fragment, container,
				false);

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
		
		homeOdds = String.valueOf(df.format(getActivity().getIntent().getExtras()
				.getDouble(FixtureFragment.H_ODDS)));
		drawOdds = String.valueOf(df.format(getActivity().getIntent().getExtras()
				.getDouble(FixtureFragment.D_ODDS)));
		awayOdds = String.valueOf((df.format(getActivity().getIntent().getExtras()
				.getDouble(FixtureFragment.A_ODDS))));
				

		tv_hOdds.setText(String.valueOf(df.format(getActivity().getIntent().getExtras()
						.getDouble(FixtureFragment.H_ODDS))));
		tv_dOdds.setText(String.valueOf(df.format(getActivity().getIntent().getExtras()
						.getDouble(FixtureFragment.D_ODDS))));
		tv_aOdds.setText(String.valueOf((df.format(getActivity().getIntent().getExtras()
						.getDouble(FixtureFragment.A_ODDS)))));

		// IMAGE
		ImageView iv_home = (ImageView) view.findViewById(R.id.imageView_pHome);
		ImageView iv_away = (ImageView) view.findViewById(R.id.imageView_pAway);

		// SET IMAGE
		iv_home.setImageResource(ClubHelper.getImageResource(home));
		iv_away.setImageResource(ClubHelper.getImageResource(away));

		// BUTTONS
		// BET HOME
		gameweek = getActivity().getIntent().getExtras()
				.getInt(FixtureFragment.GW);
		LinearLayout buttonH = (LinearLayout) view
				.findViewById(R.id.linearlayout_Hbutton);
		buttonH.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// stuff
				placeBet(0, matchID, gameweek);
			}
		});

		// BET DRAW
		LinearLayout buttonD = (LinearLayout) view
				.findViewById(R.id.linearlayout_Dbutton);
		buttonD.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				placeBet(1, matchID, gameweek);
			}
		});

		// BET AWAY
		LinearLayout buttonA = (LinearLayout) view
				.findViewById(R.id.linearlayout_Abutton);
		buttonA.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				placeBet(2, matchID, gameweek);
			}
		});

		return view;
	}
	
	protected void openDialog(int type) {
		// TODO Auto-generated method stub
		DialogFragment predictDialog = new PredictDialogFragment();
		Intent intent = new Intent();
		intent.putExtra(SELECTION, type);
		intent.putExtra(FixtureFragment.HOME, home);
		intent.putExtra(FixtureFragment.AWAY, away);
		
		intent.putExtra(FixtureFragment.H_ODDS, homeOdds);
		intent.putExtra(FixtureFragment.D_ODDS, drawOdds);
		intent.putExtra(FixtureFragment.A_ODDS, awayOdds);

		predictDialog.setArguments(intent.getExtras());
		predictDialog.show(getActivity().getSupportFragmentManager(), "predictDialog");
	}

	private void placeBet(int type, String id, int gameweek){
		System.out.println("IN PLACEBET!");
		ParseUser currentUser = ParseUser.getCurrentUser();
		ParseObject myLog = new ParseObject("History");
		//myLog.put("UserID", currentUser );
		myLog.put("User", currentUser );
		//myLog.put("parent", ParseObject.createWithoutData("Control", "2oFu0OiS0b"));
		//myLog.put("MatchID", id);
		myLog.put("Match", ParseObject.createWithoutData("Fixture", id));
		myLog.put("Bet", type);
		myLog.put("Check", false);
		myLog.put("GW", gameweek);
		myLog.saveInBackground();
		openDialog(type);
	}
}
