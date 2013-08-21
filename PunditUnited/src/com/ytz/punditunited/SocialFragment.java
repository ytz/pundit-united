package com.ytz.punditunited;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SocialFragment extends ListFragment {

	protected SocialListAdapter mAdapter;
	private PullToRefreshListView pullToRefreshView;

	// http://stackoverflow.com/questions/16493243/pulltorefresh-list-in-paged-fragment
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_social, container,
				false);
		ListView listView = (ListView) rootView
				.findViewById(android.R.id.list);

		// Set a listener to be invoked when the list should be refreshed.
		pullToRefreshView = new PullToRefreshListView(getActivity());
		pullToRefreshView.setLayoutParams(listView.getLayoutParams());
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// Do work to refresh the list here.
						getPost();
					}
				});

		return pullToRefreshView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.fragment_social);

		setHasOptionsMenu(true); // ActionBar buttons appear

		getPost();

	}

	private void getPost() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
		query.orderByDescending("CreatedAt");
		query.include("User");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					mAdapter = new SocialListAdapter(getActivity(), objects);
					pullToRefreshView.setAdapter(mAdapter);
					pullToRefreshView.onRefreshComplete();
				} else {
					System.out.println("Getting Post, Error: " + e.getMessage());
				}
			}
		});
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
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_profile:
			Intent intent = new Intent(getActivity(), ProfileActivity.class);
			intent.putExtra(MainActivity.MYUSERID, ParseUser.getCurrentUser()
					.getObjectId());
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
