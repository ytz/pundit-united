package com.ytz.punditunited;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PredictFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflat and return the layout
		//return inflater.inflate(R.layout.predict_fragment, container, false);
		
		View view = inflater.inflate(R.layout.predict_fragment, container, false);
		
		TextView tv_home = (TextView) view.findViewById(R.id.textView_HfullName);
		TextView tv_away = (TextView) view.findViewById(R.id.textView_AfullName);
		
		TextView tv_hOdds = (TextView) view.findViewById(R.id.textView_pHomeOdds);
		TextView tv_dOdds = (TextView) view.findViewById(R.id.textView_pDrawOdds);
		TextView tv_aOdds = (TextView) view.findViewById(R.id.textView_pAwayOdds);
		
		tv_home.setText(getActivity().getIntent().getExtras().getString(FixtureFragment.HOME));
		tv_away.setText(getActivity().getIntent().getExtras().getString(FixtureFragment.AWAY));
		
		tv_hOdds.setText("" + getActivity().getIntent().getExtras().getString(FixtureFragment.H_ODDS));
		tv_dOdds.setText("" + getActivity().getIntent().getExtras().getString(FixtureFragment.D_ODDS));
		tv_aOdds.setText("" + getActivity().getIntent().getExtras().getString(FixtureFragment.A_ODDS));
		
		return view;
	}
}
