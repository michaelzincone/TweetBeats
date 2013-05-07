package com.final_proj.zincone;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Genre extends ListFragment {
	
	ArrayList<String> items;
	ArrayAdapter arrayAdapter;
	
	public static int SELECTED_INDEX = 1;
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View v = super.onCreateView(inflater, container, savedInstanceState);
		
		items = new ArrayList<String>();
		
		
		items.add("Country");
		items.add("EDM");
		items.add("Folk");
		items.add("Hip-hop/Rap");
		items.add("House");
		items.add("Indie");
		items.add("Jazz");
		items.add("Latin");
		items.add("Punk");
		items.add("Reggae");
		items.add("Rock");
		Context context = this.getActivity();
		arrayAdapter = new ArrayAdapter(context,android.R.layout.simple_list_item_single_choice,items);
		
		this.setListAdapter(arrayAdapter);
		
		
		return v;
		
		
	}
	
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 
		
		this.getListView().setSelection(1);
		
		
		this.getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				
				SELECTED_INDEX = index;
			}
			
		});
	}
}
