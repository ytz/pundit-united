package com.ytz.punditunited;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
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

		// Inflater
		View layout = inflater.inflate(R.layout.dialog_predict, null);
		// = (TextView) layout.findViewById(R.id.tag_error_message);
		spinner = (Spinner) layout.findViewById(R.id.spinner_dialog);
		addItemOnSpinner();
		//spinner.setOnItemSelectedListener(getActivity());
		
		seekText = (TextView) layout.findViewById(R.id.textView_seekText);
		
		SeekBar seekbar = (SeekBar) layout.findViewById(R.id.seekBar_dialog);
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {       
	        @Override       
	        public void onStopTrackingTouch(SeekBar seekBar) {            
	        }       
	        @Override       
	        public void onStartTrackingTouch(SeekBar seekBar) {     
	        }       
	        @Override       
	        public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
	            //positionSeekBar(progress);
	        	seekText.setText("" + progress);
	        }
	    });
		
		builder.setView(layout); //impt!

		// Add the buttons
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
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

	/*protected void positionSeekBar(int progress) {
		String what_to_say = String.valueOf(how_many);
	    fade_text.setText(what_to_say);
	    int seek_label_pos = (((fade_seek.getRight() - fade_seek.getLeft()) * fade_seek.getProgress()) / fade_seek.getMax()) + fade_seek.getLeft();
	    if (how_many <=9)
	        {
	            fade_text.setX(seek_label_pos - 6);
	        }
	    else
	        {
	            fade_text.setX(seek_label_pos - 11);
	        }
		
	}*/

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
	
	/*@Override
    public void onItemSelected(AdapterView<?> main, View view, int position,
            long Id) {
 
        String item = main.getItemAtPosition(position).toString();
 
        Toast.makeText(main.getContext(), "You selected Month is: " + item,
                Toast.LENGTH_LONG).show();
 
    }*/
}
