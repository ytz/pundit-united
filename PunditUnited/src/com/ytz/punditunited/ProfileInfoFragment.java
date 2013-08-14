package com.ytz.punditunited;

import java.text.DecimalFormat;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileInfoFragment extends Fragment {
	
	private static final String PICTURE = "/picture?width=100&height=100";
	private static final String BASE_URL = "http://graph.facebook.com/";
	DecimalFormat df = new DecimalFormat("#.00"); // display odds in 2decimal
	// place


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_profile_info, container, false);
		
		ParseUser user = ParseUser.getCurrentUser();
		
		// FB PROFILE PIC
		ImageView fbPic = (ImageView) view.findViewById(R.id.iv_fbProfileInfo);
		String id = ParseUser.getCurrentUser().getString("fbId");
		String url = BASE_URL + id + PICTURE;
		ImageLoader.getInstance().displayImage(url, fbPic);
		
		// NAME
		TextView name = (TextView) view.findViewById(R.id.tv_nameProfileInfo);
		name.setText(user.getString("Name"));
		
		// Points
		TextView points = (TextView) view.findViewById(R.id.tv_pointsProfileInfo);
		points.setText("" + user.getInt("Points"));
		
		// GAMES
		int game = user.getInt("Games");
		TextView g = (TextView) view.findViewById(R.id.tv_gamesProfileInfo);
		g.setText("" + game);
		
		// WIN RATE
		TextView wr = (TextView) view.findViewById(R.id.tv_winRateProfileInfo);
		if (game == 0){
			wr.setText("0%");
		}
		else{
			int win = user.getInt("Win");
			float winRate = (win/game) * 100;
			wr.setText("" + df.format(winRate) + "%");		
		}
		
		return view;
	}

}
