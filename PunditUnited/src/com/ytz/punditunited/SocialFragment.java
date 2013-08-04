package com.ytz.punditunited;

import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SocialFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflat and return the layout
		return inflater.inflate(R.layout.social_fragment, container, false);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true); // ActionBar buttons appear

	}
	
	/**
	 * Action Bar Buttons
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
	}
	
	/**
	 * Action Bar - My Profile
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	   switch (item.getItemId()) 
	   {
	     case R.id.menu_profile:
	        Intent intent = new Intent(getActivity(), ProfileActivity.class);
	        intent.putExtra(MainActivity.MYUSERID, ParseUser.getCurrentUser().getObjectId());
	        startActivity(intent);
	        return true;
	     default:
	        return super.onOptionsItemSelected(item);
	   }
	}

	

}
