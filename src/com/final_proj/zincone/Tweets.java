package com.final_proj.zincone;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tweetbeats.R;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshBaseListFragment;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class Tweets extends PullToRefreshBaseListFragment<PullToRefreshListView>
{
	
	//declare variables
	 private LinkedList<String> mListItems;
	 private ArrayAdapter<String> mAdapter;
	 private PullToRefreshListFragment mPullRefreshListFragment;
	 Context context;
	 private PullToRefreshListView mPullToRefreshListView;
	 
	 private final static String CONSUMER_KEY = "bDfcAsCzCAyTtKFmdamZw";
	 
	     private final static String CONSUMER_KEY_SECRET = "7QHC23uNVWa8U2soN2sXSAzKlEKDBLMfEOTpRp7Yc4";
	  

	ArrayList<HashMap<String, String>> list;
		
	SimpleAdapter adapter;
	View v;
	
	ConfigurationBuilder cb;
	TwitterFactory tf;
	Twitter twitter;
	ArrayList<String> genres;

@Override
protected PullToRefreshListView onCreatePullToRefreshListView(
		LayoutInflater inflater, Bundle savedInstanceState) {
	
		
	
		list = new ArrayList<HashMap<String,String>>();

		genres = new ArrayList<String>();
		
		//set up genres to be read for hashtags
		genres.add("Country");
		genres.add("EDM");
		genres.add("Folk");
		genres.add("Rap");
		genres.add("House");
		genres.add("Indie");
		genres.add("Jazz");
		genres.add("Latin");
		genres.add("Punk");
		genres.add("Reggae");
		genres.add("Rock");
		
		


	
	
	context = this.getActivity();
	
	
	//initalize simpleadapter to handle rows of content
	adapter = new SimpleAdapter(context, list, R.layout.row,
			new String[] { "username", "tweet", "pic"}, new int[] {
					R.id.tweeter, R.id.tweet, R.id.userImage }) {
		public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.row, null);
		
		TextView username = (TextView) v.findViewById(R.id.tweeter);
		
		username.setText(list.get(position).get("username"));
		
		TextView tweet = (TextView) v.findViewById(R.id.tweet);
		
		tweet.setText(list.get(position).get("tweet"));
		
		ImageView profilePic = (ImageView)v.findViewById(R.id.userImage);
		
		ImgDownload imgDL = new ImgDownload(list.get(position).get("pic"),profilePic);
		
		imgDL.execute();
		
		return v;
		}
	};
	
	mPullToRefreshListView = new PullToRefreshListView(context);
	/*
	mListItems = new LinkedList<String>();
	mListItems.addAll(Arrays.asList(mStrings));
	mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mListItems);
	*/
	
	//refresh the listview
	new GetDataTask().execute();
	
	mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

		@Override
		public void onRefresh(PullToRefreshBase refreshView) {
			new GetDataTask().execute();
		}
		
	});
	
	mPullToRefreshListView.setOnFocusChangeListener(new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			//test onfocuschange
		}
		
	});
	// You can also just use setListAdapter(mAdapter) or
	// mPullRefreshListView.setAdapter(mAdapter)
	mPullToRefreshListView.setAdapter(adapter);
	

	
	
	return mPullToRefreshListView;
}



@Override
public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);

    if (isVisibleToUser == true) { 
    	//used to check fragment visibility
        Log.v("MZ", "this fragment is now visible");
        new GetDataTask().execute();
    }

    else if (isVisibleToUser == false) {  
        Log.v("MZ", "this fragment is now invisible");

    }
}


public void onStart() {
	super.onStart();
	this.setListShown(true);
}

private class ImgDownload extends AsyncTask {
    private String requestUrl;
    private ImageView view;
    private Bitmap pic;

    //imgdownload constructor. reads in URL and imageview
    private ImgDownload(String requestUrl, ImageView view) {
        this.requestUrl = requestUrl;
        this.view = view;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
        	URL url = new URL(requestUrl);
        	
            URLConnection conn = url.openConnection();
            pic = BitmapFactory.decodeStream(conn.getInputStream());
  
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
    	//update UI
        view.setImageBitmap(pic);
    }
}



private class GetDataTask extends AsyncTask<Void, Void, Void> {

	protected void onPreExecute() {
	
		
		
	}
	
	@Override
	protected Void doInBackground(Void... voids) {
		// Simulates a background job.
		/*
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	*/
		//connect to twitter API using Twitter4j
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("bDfcAsCzCAyTtKFmdamZw")
		.setOAuthConsumerSecret("7QHC23uNVWa8U2soN2sXSAzKlEKDBLMfEOTpRp7Yc4")
		.setOAuthAccessToken("781588531-vWqYmNj7S0Ef9QitEpEj8H2ueX747oHRCxggusOD")
		.setOAuthAccessTokenSecret("cmHppptHPHsdvaPrkqRVQO5zEG8QaLfW3WfuDYyCJE");
		tf = new TwitterFactory(cb.build());
		
		twitter = tf.getInstance();

		//Twitter twitter = new TwitterFactory().getInstance();
			
		//twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		
		 Query query = new Query("#" + genres.get(Genre.SELECTED_INDEX));
		 query.count(30);
		 QueryResult result = null;
		 
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HashMap<String,String> hashmap;
		  int counter = 0;
		  
		  list.clear();
		    for (twitter4j.Status status : result.getTweets()) {
		      //add first 30 statuses
		    	if (counter < 30) {
		    	hashmap = new HashMap<String,String>();
		    	hashmap.put("username", status.getUser().getScreenName());
		    	hashmap.put("tweet",  status.getText());
		    	hashmap.put("pic", status.getUser().getProfileImageURL());
		    	
		    	list.add(hashmap);
		    	} else break;
		    	counter++;
		    }
		
		
		
		return null;
	}

	@Override
	protected void onPostExecute(Void v) {
		
		
		//update UI
		adapter.notifyDataSetChanged();
		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
		
	}
	
	
	
}



}  
  

//@Override
//public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//{  
//  
//    ViewGroup viewGroup = (ViewGroup) super.onCreateView(inflater, container, savedInstanceState);
//
//    View lvOld = viewGroup.findViewById(android.R.id.list);
//    
//    final PullToRefreshListView listView = new PullToRefreshListView(getActivity());
//    listView.setId(android.R.id.list);
//    listView.setLayoutParams(new FrameLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//  //  listView.setDrawSelectorOnTop(false);
//
//    FrameLayout parent = (FrameLayout) lvOld.getParent();
//
//    parent.removeView(lvOld);
//    lvOld.setVisibility(View.GONE);
//
//    parent.addView(listView, new FrameLayout.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//    return viewGroup;
//  
//  
//  
//}