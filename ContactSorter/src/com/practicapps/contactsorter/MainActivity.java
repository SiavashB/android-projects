package com.practicapps.contactsorter;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MainActivity extends Activity implements OnScrollListener,
		OnItemClickListener {

	private static final int CONTACTS_TO_LOAD = 50;
	private static final int LIST_LOAD_THRESHHOLD = 4;

	int contact_index = 0;
	ContentResolver cr;
	Cursor cur;
	boolean contactsRemain = true;

	ListView lv;
	ArrayAdapter<String> mAdaper;
	ArrayList<String> contacts = new ArrayList<String>();
	ArrayList<String> idArray = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		cr = getContentResolver();
		cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, // projection,
				null, // selection,
				null, // selectionArgs,
				"_ID DESC" // sortOrder
		);

		if (cur.getCount() > 0) {
			lv = (ListView) findViewById(R.id.listView1);
			mAdaper = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, contacts);
			lv.setAdapter(mAdaper);
			lv.setOnScrollListener(this);
			lv.setOnItemClickListener(this);

			getContacts();

		} else {
			Button b = (Button) findViewById(R.id.button1);
			b.setText("You have no contacts :(");
		}

	}

	/***************************************************************************
	 * 
	 ***************************************************************************/
	private void getContacts() {
		int i = 0;

		while (contactsRemain = cur.moveToNext()) {
			String id = cur.getString(cur
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cur.getString(cur
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			if (Integer
					.parseInt(cur.getString(cur
							.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

				String row = id + " - " + name;
				contacts.add(row);
				idArray.add(id);
				// mAdaper.add(row);
				if (i++ > CONTACTS_TO_LOAD)
					break;
			}
		}
		mAdaper.notifyDataSetChanged();
	}

	/***************************************************************************
 * 
 ***************************************************************************/
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int lastVisible = firstVisibleItem + visibleItemCount;

		if (contactsRemain
				&& (lastVisible >= (totalItemCount - LIST_LOAD_THRESHHOLD))) {
			getContacts();
		}

	}
	/***************************************************************************
 * 
 ***************************************************************************/
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	/***************************************************************************
 * 
 ***************************************************************************/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI,
				idArray.get(position));
		intent.setData(uri);
		this.startActivity(intent);

	}

	public void popup(final View view) {

		LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		View popupView = layoutInflater.inflate(R.layout.popupwindow, null);

		final PopupWindow popupWindow = new PopupWindow(popupView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
		btnDismiss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});

		popupWindow.showAsDropDown(view, 0, 100);
	}
}

/*
 * public class MainActivity extends ListActivity implements
 * LoaderManager.LoaderCallbacks<Cursor> {
 * 
 * // This is the Adapter being used to display the list's data
 * SimpleCursorAdapter mAdapter;
 * 
 * // These are the Contacts rows that we will retrieve static final String[]
 * PROJECTION = new String[] {ContactsContract.Data._ID,
 * ContactsContract.Data.DISPLAY_NAME};
 * 
 * // This is the select criteria static final String SELECTION = "((" +
 * ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND (" +
 * ContactsContract.Data.DISPLAY_NAME + " != '' ))";
 * 
 * @Override protected void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * 
 * // Create a progress bar to display while the list loads ProgressBar
 * progressBar = new ProgressBar(this); progressBar.setLayoutParams(new
 * LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
 * Gravity.CENTER)); progressBar.setIndeterminate(true);
 * getListView().setEmptyView(progressBar);
 * 
 * // Must add the progress bar to the root of the layout ViewGroup root =
 * (ViewGroup) findViewById(android.R.id.content); root.addView(progressBar);
 * 
 * // For the cursor adapter, specify which columns go into which views String[]
 * fromColumns = {ContactsContract.Data.DISPLAY_NAME}; int[] toViews =
 * {android.R.id.text1}; // The TextView in simple_list_item_1
 * 
 * // Create an empty adapter we will use to display the loaded data. // We pass
 * null for the cursor, then update it in onLoadFinished() mAdapter = new
 * SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, null,
 * fromColumns, toViews, 0); setListAdapter(mAdapter);
 * 
 * // Prepare the loader. Either re-connect with an existing one, // or start a
 * new one. getLoaderManager().initLoader(0, null, this); }
 * 
 * // Called when a new Loader needs to be created public Loader<Cursor>
 * onCreateLoader(int id, Bundle args) { // Now create and return a CursorLoader
 * that will take care of // creating a Cursor for the data being displayed.
 * return new CursorLoader(this, ContactsContract.Data.CONTENT_URI, PROJECTION,
 * SELECTION, null, null); }
 * 
 * // Called when a previously created loader has finished loading public void
 * onLoadFinished(Loader<Cursor> loader, Cursor data) { // Swap the new cursor
 * in. (The framework will take care of closing the // old cursor once we
 * return.) mAdapter.swapCursor(data); }
 * 
 * // Called when a previously created loader is reset, making the data
 * unavailable public void onLoaderReset(Loader<Cursor> loader) { // This is
 * called when the last Cursor provided to onLoadFinished() // above is about to
 * be closed. We need to make sure we are no // longer using it.
 * mAdapter.swapCursor(null); }
 * 
 * @Override public void onListItemClick(ListView l, View v, int position, long
 * id) { // Do something when a list item is clicked } }
 */