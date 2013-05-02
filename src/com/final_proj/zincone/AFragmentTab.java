package com.final_proj.zincone;


import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshBaseListFragment;
import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class AFragmentTab extends PullToRefreshBaseListFragment<PullToRefreshListView>
{
	 private LinkedList<String> mListItems;
	 private ArrayAdapter<String> mAdapter;
	 private PullToRefreshListFragment mPullRefreshListFragment;
	 Context context;
	 private PullToRefreshListView mPullToRefreshListView;

	 



@Override
protected PullToRefreshListView onCreatePullToRefreshListView(
		LayoutInflater inflater, Bundle savedInstanceState) {
	context = this.getActivity();
	
	mPullToRefreshListView = new PullToRefreshListView(context);
	
	mListItems = new LinkedList<String>();
	mListItems.addAll(Arrays.asList(mStrings));
	mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, mListItems);

	mPullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

		@Override
		public void onRefresh(PullToRefreshBase refreshView) {
			new GetDataTask().execute();
		}
		
	});
	

	// You can also just use setListAdapter(mAdapter) or
	// mPullRefreshListView.setAdapter(mAdapter)
	mPullToRefreshListView.setAdapter(mAdapter);
	
	//mPullRefreshListFragment.setListShown(true);


	//mPullToRefreshListView.onRefreshComplete();
	
	mPullToRefreshListView.setVisibility(View.VISIBLE);
	
	
	
	return mPullToRefreshListView;
}

public void onStart() {
	super.onStart();
	this.setListShown(true);
}





private class GetDataTask extends AsyncTask<Void, Void, String[]> {

	@Override
	protected String[] doInBackground(Void... params) {
		// Simulates a background job.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		return mStrings;
	}

	@Override
	protected void onPostExecute(String[] result) {
		
		
		mListItems.addFirst("Added after refresh...");
		mAdapter.notifyDataSetChanged();

		// Call onRefreshComplete when the list has been refreshed.
		mPullToRefreshListView.onRefreshComplete();
		
		

		super.onPostExecute(result);
	}
}

private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
		"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
		"Allgauer Emmentaler", "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
		"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
		"Allgauer Emmentaler" };

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