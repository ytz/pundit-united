package com.ytz.punditunited;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class HistoryFragment extends ListFragment {

	private ListView listView;
	private HistoryListAdapter mAdapter;
	private String userID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.history_fragment, container,
				false);
		listView = (ListView) rootView.findViewById(android.R.id.list);
		
		userID = getActivity().getIntent().getExtras()
		.getString(MainActivity.MYUSERID);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getHistory();
	}

	private void getHistory() {
		ParseUser currentUser = null;
		try {
			currentUser = ParseUser.getQuery().get(userID);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//ParseUser currentUser = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("History");
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		query.whereEqualTo("User", currentUser);
		query.include("Match");
		query.include("User");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					mAdapter = new HistoryListAdapter(getActivity(), objects);
					listView.setAdapter(mAdapter);
				} else {
					// something went wrong
					System.out.println("Getting History, Error: "
							+ e.getMessage());
				}
			}

		});

	}

}
