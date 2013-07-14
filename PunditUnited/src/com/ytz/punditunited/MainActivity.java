package com.ytz.punditunited;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity {

	static final int NUM_ITEMS = 4;

	MyAdapter mAdapter;

	ViewPager mPager;

	

	// http://stackoverflow.com/questions/16091704/android-scrollable-tabs-swipe-state-when-swiping
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// the page adapter contains all the fragment registrations
		mAdapter = new MyAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		// set the contents of the ViewPager
		mPager.setAdapter(mAdapter);
		
		// Cause indicator to change when swiped to next tab
		mPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    // When swiping between pages, select the
	                    // corresponding tab.
	                    getActionBar().setSelectedNavigationItem(position);
	                }
	            });
		
		setupABar();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//http://developer.android.com/training/implementing-navigation/lateral.html#swipe-tabs
	//http://ucla.jamesyxu.com/?p=278
	// w/o this, no tab
	@SuppressLint("NewApi") private void setupABar() {
		// obtain the action bar
		final ActionBar aBar = getActionBar();
                // set it to tab mode
		aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// add a tab listeners to handle changing the current fragment (next fragment when tab pressed)
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
		aBar.addTab(aBar.newTab().setText("Fixture").setTabListener(tabListener));
		aBar.addTab(aBar.newTab().setText("Rank").setTabListener(tabListener));
		aBar.addTab(aBar.newTab().setText("Me").setTabListener(tabListener));

	}
	

}
