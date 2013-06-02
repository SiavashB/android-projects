package com.hawaiibarbook;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class BarListLocationActivity extends Activity {
	
	ListView lv;
	ArrayAdapter<String> mAdaper;
	ArrayList<String> bars = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_and_list_lo);
		// Show the Up button in the action bar.
		//setupActionBar();
		
		bars.add("dog and duck");
		bars.add("Ambrosia");
		bars.add("Threes");
		bars.add("Tiki");
		bars.add("Mooses");
		bars.add("Oceans");
		bars.add("this bar");
		bars.add("That bar");
		bars.add("Tity bar");
		
		
		lv = (ListView) findViewById(R.id.listView1);
		mAdaper = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, bars);
		lv.setAdapter(mAdaper);
		//lv.setOnScrollListener(this);
		//lv.setOnItemClickListener(this);
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	private void setupActionBar() {
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//			getActionBar().setDisplayHomeAsUpEnabled(true);
//		}
//	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.bar_list_location, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case android.R.id.home:
//			// This ID represents the Home or Up button. In the case of this
//			// activity, the Up button is shown. Use NavUtils to allow users
//			// to navigate up one level in the application structure. For
//			// more details, see the Navigation pattern on Android Design:
//			//
//			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
//			//
//			NavUtils.navigateUpFromSameTask(this);
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

}
