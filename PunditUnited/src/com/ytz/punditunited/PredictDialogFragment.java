package com.ytz.punditunited;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class PredictDialogFragment extends DialogFragment {

	private Spinner spinner;
	private String awayOdds;
	private String drawOdds;
	private String homeOdds;
	private int selection;
	private String away;
	private String home;
	private TextView seekText;
	private String matchID;
	private int gameweek;
	private EditText comment;
	private int totalPoints;
	private PredictFragment mListener;
	private ParseObject myLog;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		mListener = (PredictFragment) getTargetFragment();

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// Get the layout inflater
		LayoutInflater inflater = getActivity().getLayoutInflater();

		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(inflater.inflate(R.layout.dialog_predict, null));

		// Get information from previous fragment
		home = this.getArguments().getString(FixtureFragment.HOME);
		away = this.getArguments().getString(FixtureFragment.AWAY);

		selection = this.getArguments().getInt(PredictFragment.SELECTION);

		homeOdds = this.getArguments().getString(FixtureFragment.H_ODDS);
		drawOdds = this.getArguments().getString(FixtureFragment.D_ODDS);
		awayOdds = this.getArguments().getString(FixtureFragment.A_ODDS);

		matchID = this.getArguments().getString(FixtureFragment.MATCHID);
		gameweek = this.getArguments().getInt(FixtureFragment.GW);

		// Inflater
		View layout = inflater.inflate(R.layout.dialog_predict, null);
		// = (TextView) layout.findViewById(R.id.tag_error_message);
		spinner = (Spinner) layout.findViewById(R.id.spinner_dialog);
		addItemOnSpinner();
		// spinner.setOnItemSelectedListener(getActivity());
		// spinner.getSelectedItemPosition()

		totalPoints = ParseUser.getCurrentUser().getInt("Points");
		seekText = (TextView) layout.findViewById(R.id.textView_seekText);
		seekText.setText(""
				+ ((int) ((0 / 100.0) * (totalPoints - 5)) / 5 * 5 + 5));

		comment = (EditText) layout.findViewById(R.id.editText_dialogComment);

		SeekBar seekbar = (SeekBar) layout.findViewById(R.id.seekBar_dialog);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// positionSeekBar(progress);
				// seekText.setText("" + progress);
				int display = ((int) ((progress / 100.0) * (totalPoints - 5)) / 5 * 5 + 5);
				seekText.setText("" + display);
			}
		});

		builder.setView(layout); // impt!

		// Add the buttons
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
						placeBet(spinner.getSelectedItemPosition(), matchID,
								gameweek);
						mListener.updateFromDialog(spinner.getSelectedItemPosition());

					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});

		return builder.create();
	}

	private void placeBet(int type, String id, int gameweek) {
		System.out.println("IN PLACEBET!");
		ParseUser currentUser = ParseUser.getCurrentUser();
		myLog = new ParseObject("History");
		myLog.put("User", currentUser);
		myLog.put("Match", ParseObject.createWithoutData("Fixture", id));
		myLog.put("BetType", type);
		myLog.put("Check", false);
		myLog.put("GW", gameweek);
		myLog.put("BetAmount", Integer.parseInt(seekText.getText().toString()));
		myLog.put("Comment", comment.getText().toString());
		myLog.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				ParseObject match = ParseObject
						.createWithoutData("Fixture", matchID);
				ParseRelation<ParseObject> relation = match.getRelation("Bets");
				relation.add(myLog);
				match.saveInBackground();
			}
		});

		// openDialog(type);
	}

	/*
	 * protected void positionSeekBar(int progress) { String what_to_say =
	 * String.valueOf(how_many); fade_text.setText(what_to_say); int
	 * seek_label_pos = (((fade_seek.getRight() - fade_seek.getLeft()) *
	 * fade_seek.getProgress()) / fade_seek.getMax()) + fade_seek.getLeft(); if
	 * (how_many <=9) { fade_text.setX(seek_label_pos - 6); } else {
	 * fade_text.setX(seek_label_pos - 11); }
	 * 
	 * }
	 */

	/**
	 * Populate Spinner with the 3 items
	 */
	public void addItemOnSpinner() {
		List<String> list = new ArrayList<String>();
		list.add(home + " (" + homeOdds + ")");
		list.add("Draw (" + drawOdds + ")");
		list.add(away + " (" + awayOdds + ")");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		spinner.setSelection(selection);
	}

	/**
	 * Interface
	 */
	public interface PredictDialogFragmentListener {
		//void update(int selection);
		void updateFromDialog(int selection);
	}

	/*
	 * @Override public void onItemSelected(AdapterView<?> main, View view, int
	 * position, long Id) {
	 * 
	 * String item = main.getItemAtPosition(position).toString();
	 * 
	 * Toast.makeText(main.getContext(), "You selected Month is: " + item,
	 * Toast.LENGTH_LONG).show();
	 * 
	 * }
	 */
}
