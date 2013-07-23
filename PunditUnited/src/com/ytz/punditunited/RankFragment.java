package com.ytz.punditunited;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RankFragment extends Fragment {

	private ListView listView;
	protected List<ParseUser> list;
	protected RankListAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		View rootView = inflater.inflate(R.layout.rank_fragment, container,
				false);
		listView = (ListView) rootView.findViewById(android.R.id.list);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getUserList();
	}

	private void getUserList() {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					list = objects;
					
					System.out.println("Number of users = " + objects.size());

					mAdapter = new RankListAdapter(getActivity(), list);
					listView.setAdapter(mAdapter);
				} else {
					System.out.println("Getting fixturelist, Error: "
							+ e.getMessage());
				}

			}
		});

	}
}
