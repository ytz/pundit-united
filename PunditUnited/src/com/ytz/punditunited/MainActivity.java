package com.ytz.punditunited;

import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivity extends FragmentActivity {

	static final int NUM_ITEMS = 4;
	MyAdapter mAdapter;
	ViewPager mPager;
	public static String MYUSERID = "com.ytz.punditunited.MainActivity.MYUSERID";

	// http://stackoverflow.com/questions/16091704/android-scrollable-tabs-swipe-state-when-swiping
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// PARSE SETUP
		Parse.initialize(this, "gEPGfVTzUnO1j0Z2XdWuFfnrAkJ21DI5cW3X6vJp",
				"Bjpe0LDwNNUfmOwuiXrbylHeSSOSUAXgRrBudu24");
		ParseAnalytics.trackAppOpened(getIntent()); // track statistics

		// PARSE FB
		ParseFacebookUtils.initialize(getString(R.string.app_id));

		ParseFacebookUtils.logIn(this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				if (user == null) {
					Log.d("MyApp",
							"Uh oh. The user cancelled the Facebook login.");
				} else if (user.isNew()) {
					Log.d("MyApp",
							"User signed up and logged in through Facebook!");
					getFacebookIdInBackground();
				} else {
					Log.d("MyApp", "User logged in through Facebook!");
				}
			}
		});

		// Get Selection
		getSelection();

		// the page adapter contains all the fragment registrations
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		// set the contents of the ViewPager
		mPager.setAdapter(mAdapter);

		// Cache page?
		mPager.setOffscreenPageLimit(NUM_ITEMS);

		// Cause indicator to change when swiped to next tab
		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between pages, select the
				// corresponding tab.
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		setupABar();
	}

	private void getSelection() {
		final SharedPreferences selection = getSharedPreferences("Selection", 0);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		if (selection.getAll().size() != 0)
			query.whereEqualTo("inPhoneData", false);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					storeSelection(objects, selection);
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}

			}
		});
	}

	private void storeSelection(List<ParseObject> objects,
			SharedPreferences selection) {
		SharedPreferences.Editor editor = selection.edit();

		for (int i = 0; i < objects.size(); i++) {
			editor.putInt(
					((ParseObject) objects.get(i).get("Match")).getObjectId(),
					objects.get(i).getInt("BetType"));
		}
		// Commit the edits!
		editor.commit();
		
		System.out.println("test + " + selection.getInt("whCjIdy0kW", -1));
	}

	/**
	 * PARSE ===== For Facebook's "Single sign-on"
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment;
			switch (position) {
			case 0:
				fragment = new SocialFragment();
				break;
			case 1:
				fragment = new FixtureFragment();
				break;
			case 2:
				fragment = new RankFragment();
				break;
			case 3:
				fragment = new MeFragment();
				break;
			default:
				fragment = null;
				break;
			}
			return fragment;
		}
	}

	// http://developer.android.com/training/implementing-navigation/lateral.html#swipe-tabs
	// http://ucla.jamesyxu.com/?p=278
	// w/o this, no tab
	@SuppressLint("NewApi")
	private void setupABar() {
		// obtain the action bar
		final ActionBar aBar = getActionBar();
		// set it to tab mode
		aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// add a tab listeners to handle changing the current fragment (next
		// fragment when tab pressed)
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				mPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {

			}

			@Override
			public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
				// TODO Auto-generated method stub

			}
		};
		// add the tabs, register the event handler for the tabs
		aBar.addTab(aBar.newTab().setText("Social").setTabListener(tabListener));
		aBar.addTab(aBar.newTab().setText("Fixture")
				.setTabListener(tabListener));
		aBar.addTab(aBar.newTab().setText("Rank").setTabListener(tabListener));
		aBar.addTab(aBar.newTab().setText("Me").setTabListener(tabListener));

	}

	/**
	 * Action Bar Buttons
	 */
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
	 */

	/**
	 * Action Bar - My Profile
	 */
	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { switch
	 * (item.getItemId()) { case R.id.menu_profile: Intent intent = new
	 * Intent(this, ProfileActivity.class); intent.putExtra(MYUSERID,
	 * ParseUser.getCurrentUser().getObjectId()); startActivity(intent); return
	 * true; default: return super.onOptionsItemSelected(item); } }
	 */

	/**
	 * Stores fbId and Name to Parse's database when user uses the app for the
	 * first time
	 */
	private static void getFacebookIdInBackground() {
		Request.executeMeRequestAsync(ParseFacebookUtils.getSession(),
				new Request.GraphUserCallback() {
					@Override
					public void onCompleted(GraphUser user, Response response) {
						if (user != null) {
							ParseUser.getCurrentUser()
									.put("fbId", user.getId());
							ParseUser.getCurrentUser().put("Name",
									user.getName());
							ParseUser.getCurrentUser().put("Points", 100); // initial
																			// points
							ParseUser.getCurrentUser().saveInBackground();
						}
					}
				});
	}

}
