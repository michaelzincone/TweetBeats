package com.final_proj.zincone;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tweetbeats.R;

public class MainActivity extends Activity {
	
	Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this.getApplicationContext();
		//set up tabs
		
		ActionBar actionBar = getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab beatsTab = actionBar.newTab().setText("Tweets");
		ActionBar.Tab genreTab = actionBar.newTab().setText("Genre");
		
		
		//add tab listeners to tabs
		genreTab.setTabListener(new TabListener<Genre>(this, "genre", Genre.class));
		beatsTab.setTabListener(new TabListener<Tweets>(this, "tweets", Tweets.class));
		
		
		//add tabs to the actionbar
		actionBar.addTab(genreTab);
		actionBar.addTab(beatsTab);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class TabListener<T extends Fragment> implements ActionBar.TabListener {  
		   private Fragment mFragment;  
		   private final Activity mActivity;  
		   private final String mTag;  
		   private final Class<T> mClass;  
		  
		   /** Constructor used each time a new tab is created.  
		    * @param activity The host Activity, used to instantiate the fragment  
		    * @param tag The identifier tag for the fragment  
		    * @param clz The fragment's Class, used to instantiate the fragment  
		    */  
		   public TabListener(Activity activity, String tag, Class<T> clz) {  
		     mActivity = activity;  
		     mTag = tag;  
		     mClass = clz;  
		   }  
		   /* The following are each of the ActionBar.TabListener callbacks */  

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction ft) {
			
			
		}
		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction ft) {
			
			// if (mFragment == null) {  
			       // If not, instantiate and add it to the activity  
			       mFragment = Fragment.instantiate(mActivity, mClass.getName());  
			       if (mTag.equalsIgnoreCase("tweets")) {
			    	  
			    	   LayoutInflater inflater = getLayoutInflater();
			    	   View layout = inflater.inflate(R.layout.progress_toast, (ViewGroup) findViewById(R.id.toast_layout_root));
			    	   
			    	   
			    	   Button refreshBtn=(Button)layout.findViewById(R.id.detail_refresh_btn);
			    	   //sets loading bar
			    	   
			    	   AnimationDrawable d=(AnimationDrawable)refreshBtn.getCompoundDrawables()[0];
			    	   
			    	   Toast toast = new Toast(getApplicationContext());
			    	   toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			    	   toast.setDuration(Toast.LENGTH_SHORT);
			    	   toast.setView(layout);
			    	   toast.show();

			    	   //animates loading drawable
			    	   d.start();
			    	   
			       }
			       //add fragment to the fragmenttransaction
			       ft.add(android.R.id.content, mFragment, mTag);  
			
		}
		@Override
		public void onTabUnselected(Tab arg0, android.app.FragmentTransaction ft) {
			
			 if (mFragment != null) {  
			       // Detach the fragment, because another one is being attached  
			       ft.remove(mFragment);  
			     }  
			
		}

		 }  

}



