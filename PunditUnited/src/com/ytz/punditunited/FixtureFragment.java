package com.ytz.punditunited;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FixtureFragment extends ListFragment {

	private List<ParseObject> list;

	private int gameweek;
	private FixtureListAdapter mAdapter;
	private ListView listView;
	public static final String MATCHID = "com.ytz.punditunited.MATCHID";
	public static final String HOME = "com.ytz.punditunited.HOME";
	public static final String AWAY = "com.ytz.punditunited.AWAY";
	public static final String H_ODDS = "com.ytz.punditunited.H_ODDS";
	public static final String D_ODDS = "com.ytz.punditunited.D_ODDS";
	public static final String A_ODDS = "com.ytz.punditunited.A_ODDS";
	public static final String GW = "com.ytz.punditunited.GW";
	public static final String DATE = "com.ytz.punditunited.DATE";
	private SeparatedListAdapter adapter;
	// protected int count;

	private int index;

	private int top;

	/**
	 * When list is clicked, send match information and open MatchActivity.java
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(getActivity(), MatchActivity.class);

		intent.putExtra(MATCHID,
				((ParseObject) adapter.getItem(position)).getObjectId());
		intent.putExtra(HOME,
				((ParseObject) adapter.getItem(position)).getString("Home"));
		intent.putExtra(AWAY,
				((ParseObject) adapter.getItem(position)).getString("Away"));
		intent.putExtra(H_ODDS,
				((ParseObject) adapter.getItem(position)).getDouble("H_odds"));
		intent.putExtra(D_ODDS,
				((ParseObject) adapter.getItem(position)).getDouble("D_odds"));
		intent.putExtra(A_ODDS,
				((ParseObject) adapter.getItem(position)).getDouble("A_odds"));
		intent.putExtra(GW,
				((ParseObject) adapter.getItem(position)).getInt("GW"));
		intent.putExtra(DATE, ((ParseObject) adapter.getItem(position))
				.getDate("Date").getTime());

		startActivity(intent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fixture_fragment, container,
				false);
		listView = (ListView) rootView.findViewById(android.R.id.list);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// getGameweek();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getGameweek();
	}

	private void getGameweek() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Control");
		query.getInBackground("2oFu0OiS0b", new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					gameweek = object.getInt("GW");
					getFixtureList();
				} else {
					// something went wrong
					System.out.println("Getting gameweek, Error: "
							+ e.getMessage());
				}
			}
		});
	}

	private void getFixtureList() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Fixture");
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		query.whereEqualTo("GW", gameweek);
		query.orderByAscending("Date");
		// query.include("Bets");
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					list = objects;

					System.out.println("list size " + list.size());
					// count = 0;
					getSelection(0);

					/*
					 * // get selection for (a = 0; a < list.size(); a++) {
					 * System.out.println("a = " + a); temp = list.get(a);
					 * ParseRelation<ParseObject> relation = list.get(a)
					 * .getRelation("Bets"); ParseQuery<ParseObject> query =
					 * relation.getQuery(); query.whereEqualTo("User",
					 * ParseUser.getCurrentUser());
					 * query.getFirstInBackground(new GetCallback<ParseObject>()
					 * {
					 * 
					 * @Override public void done(ParseObject object,
					 * ParseException e) { if (e != null) {
					 * System.out.println("FOUND"); list.get(a).put("Selection",
					 * object.getInt("BetType")); // temp.put("Selection", //
					 * object.getInt("BetType")); } else {
					 * System.out.println("NOT FOUND");
					 * list.get(a).put("Selection", -1); //
					 * temp.put("Selection", -1); } } }); }
					 */

					/*
					 * ArrayList<List<ParseObject>> arrayList =
					 * separateListWithDate(list); adapter = new
					 * SeparatedListAdapter(getActivity()); for (int i = 0; i <
					 * arrayList.size(); i++) { adapter.addSection(
					 * getDateHeader(arrayList.get(i)), new
					 * FixtureListAdapter(getActivity(), arrayList .get(i))); }
					 * // mAdapter = new FixtureListAdapter(getActivity(),
					 * list); // listView.setAdapter(mAdapter);
					 * listView.setAdapter(adapter);
					 */
				} else {
					System.out.println("Getting fixturelist, Error: "
							+ e.getMessage());
				}
			}
		});
	}

	protected void getSelection(int temp) {
		final int count = temp;
		if (count == list.size() && PredictFragment.CHANGE == 0) {
			System.out.println("Go to setupadapter");
			setUpAdapter();
		}
		else if (count >= list.size() && PredictFragment.CHANGE == 1) {
			PredictFragment.CHANGE = 0;	
			adapter.notifyDataSetChanged();
			listView.setSelectionFromTop(index, top);
			return;
		}else {
			if (count >= list.size())
				return;
			ParseRelation<ParseObject> relation = list.get(count).getRelation(
					"Bets");
			ParseQuery<ParseObject> query = relation.getQuery();
			query.whereEqualTo("User", ParseUser.getCurrentUser());
			query.getFirstInBackground(new GetCallback<ParseObject>() {
				@Override
				public void done(ParseObject object, ParseException e) {
					if (count < list.size()){
					if (object != null) {
						System.out.println("FOUND " + count);
						if (list == null)
							System.out.println("List is null");
						if (object == null)
							System.out.println("Object is null");
						list.get(count).put("Selection",
								object.getInt("BetType"));
						getSelection(count+1);
						// temp.put("Selection",
						// object.getInt("BetType"));
					} else {
						System.out.println("NOT FOUND " + count);
						//System.out.println(e.getMessage());
						list.get(count).put("Selection", -1);
						getSelection(count+1);
						// temp.put("Selection", -1);
					}
				}
				}
			});
		} // else

	}

	private void setUpAdapter() {
		ArrayList<List<ParseObject>> arrayList = separateListWithDate(list);
		adapter = new SeparatedListAdapter(getActivity());
		for (int i = 0; i < arrayList.size(); i++) {
			adapter.addSection(getDateHeader(arrayList.get(i)),
					new FixtureListAdapter(getActivity(), arrayList.get(i)));
		}
		// mAdapter = new FixtureListAdapter(getActivity(), list);
		// listView.setAdapter(mAdapter);
		listView.setAdapter(adapter);

	}

	/**
	 * Fixtures in a list = same date
	 * 
	 * @param list
	 * @return
	 */
	private ArrayList<List<ParseObject>> separateListWithDate(
			List<ParseObject> list) {
		int cutoff = 0;
		Date currDate = list.get(0).getDate("Date");
		Calendar tempCal = Calendar.getInstance();
		Calendar currCal = Calendar.getInstance();
		tempCal.setTime(currDate);
		ArrayList<List<ParseObject>> myList = new ArrayList<List<ParseObject>>();
		for (int i = 0; i < list.size(); i++) {
			currDate = list.get(i).getDate("Date");
			currCal.setTime(currDate);
			if (currCal.get(Calendar.DAY_OF_MONTH) != tempCal
					.get(Calendar.DAY_OF_MONTH)
					|| currCal.get(Calendar.MONTH) != tempCal
							.get(Calendar.MONTH)) {
				myList.add(list.subList(cutoff, i));
				cutoff = i;
				tempCal.setTime(currCal.getTime());
				--i;
			}

			// Odd Last case problem
			if (i == list.size() - 1) {
				myList.add(list.subList(list.size() - 1, list.size()));
			}
		}
		return myList;
	}

	/**
	 * Format date with SimpleDateFormat
	 * 
	 * @param list
	 * @return
	 */
	private String getDateHeader(List<ParseObject> list) {
		Date currDate = list.get(0).getDate("Date");
		SimpleDateFormat sf = new SimpleDateFormat("E, d MMM yy");
		return sf.format(currDate);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (PredictFragment.CHANGE == 1) {
			System.out.println("START");
			// int index = listView.getFirstVisiblePosition();
			// View v = listView.getChildAt(0);
			// int top = (v == null) ? 0 : v.getTop();
			// System.out.println("index = " + index + " ,top = " + top);
			// adapter.notifyDataSetChanged();
			// getGameweek();
			getSelection(0);
			// PredictFragment.CHANGE = 0;
			// adapter.notifyDataSetChanged();
			// listView.setSelectionFromTop(index, 0);
			System.out.println("END");
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		index = listView.getFirstVisiblePosition();
		View v = listView.getChildAt(0);
		top = (v == null) ? 0 : v.getTop();
		// store index using shared preferences
	}

}
