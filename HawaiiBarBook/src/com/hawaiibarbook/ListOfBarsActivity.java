package com.hawaiibarbook;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfBarsActivity extends Activity {
	
	ListView lv;
	ArrayAdapter<String> mAdaper;
	ArrayList<String> bars = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_and_list_lo);
		
		bars.add("dog and duck");
		bars.add("Ambrosia");
		bars.add("Threes");
		bars.add("Tiki");
		bars.add("Mooses");
		bars.add("Oceans");
		
		
		lv = (ListView) findViewById(R.id.listView1);
		mAdaper = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, bars);
		lv.setAdapter(mAdaper);
		//lv.setOnScrollListener(this);
		//lv.setOnItemClickListener(this);
		
		
	}

}
