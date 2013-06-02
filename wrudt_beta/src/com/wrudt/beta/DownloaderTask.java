package com.wrudt.beta;

import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import android.os.AsyncTask;

import com.wrudt.library.JSONParser;

public class DownloaderTask extends AsyncTask<List<NameValuePair>, Long, JSONObject> {

	OnResponseListener listner;
	String url;
	private JSONParser jsonParser;


	public DownloaderTask(String url, OnResponseListener listner) {
		this.listner = listner;
		this.url = url;
	}
	public DownloaderTask(String url) {
		this.url = url;
	}

	@Override
	protected JSONObject doInBackground(List<NameValuePair>... params) {

		jsonParser = new JSONParser();
		JSONObject json = jsonParser.getJSONFromUrl(this.url, params[0]);

		return json;
	}

	// onPostExecute displays the results of the AsyncTask.
	@Override
	protected void onPostExecute(JSONObject json) {

		if(listner != null){
			if(json != null){
				this.listner.onSuccess(json);
			}else{
				this.listner.onFailure("json is null");
			}
		
			
		}
		//return json;

	}

	public interface OnResponseListener {
		public void onSuccess(JSONObject json);
		public void onFailure(String message);
	}

}
