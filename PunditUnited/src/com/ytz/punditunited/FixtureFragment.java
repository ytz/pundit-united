package com.ytz.punditunited;

import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FixtureFragment extends ListFragment{
	
	private List<ParseObject> list;
	private int gameweek;
	private FixtureListAdapter mAdapter;
	private ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflat and return the layout
		//return inflater.inflate(R.layout.fixture_fragment, container, false);
		
		View rootView = inflater.inflate(R.layout.fixture_fragment,
                container, false);
        listView = (ListView) rootView.findViewById(android.R.id.list);

        return rootView;
	}
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
                
        getGameweek();
        //getFixtureList();	
        
        //mAdapter = new FixtureListAdapter(getActivity(), list);
        //listView.setAdapter(mAdapter);
    }
	
	private void getGameweek(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Control");
		//query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
		/*try {
			gameweek = query.find().get(0).getInt("GW");
			System.out.println("GW IS " + gameweek);
			getFixtureList();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		query.getInBackground("2oFu0OiS0b", new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) {
		      gameweek = object.getInt("GW");
		      getFixtureList();
		    } else {
		      // something went wrong
		    	System.out.println("Getting gameweek, Error: " + e.getMessage());
		    }
		  }
		});
	}
	
	private void getFixtureList(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Fixture");
		query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        query.whereEqualTo("GW", gameweek);
        query.orderByAscending("Date");
        query.findInBackground(new FindCallback<ParseObject>() {
           	@Override
			public void done(List<ParseObject> objects, ParseException e) {
           	 if (e == null) {
                 list = objects;
                 mAdapter = new FixtureListAdapter(getActivity(), list);
                 listView.setAdapter(mAdapter);
             } else {
            	 System.out.println("Getting fixturelist, Error: " + e.getMessage());
             }
				
			}
        });
	}

}
