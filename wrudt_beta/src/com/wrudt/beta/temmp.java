//
//package com.wrudt.beta;
//
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.List;
//
//import org.apache.http.NameValuePair;
//
//import android.app.Activity;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Menu;
//import android.view.View;
//import android.widget.TextView;
//
//public class MainActivity extends Activity {
//	
//	TextView textView;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.mainlayout);
//		textView = (TextView) findViewById(R.id.main_tv);
//	}
//	
//	public class URLpostrequest {
//		String URL;
//		String params; //POST parameters
//	}
//	
//    public void myClickHandler(View view) throws UnsupportedEncodingException {
//        // Gets the URL from the UI's text field.
//        //String stringUrl = urlText.getText().toString();
//
//    	URLpostrequest myURL = new URLpostrequest();
//    	
//    	myURL.URL = "http://www.whatrudoingtonight.com/mobilelogin.php";
//    	
//    	myURL.params = URLEncoder.encode("tag", "UTF-8") + "=" + URLEncoder.encode("login", "UTF-8");
//    	myURL.params += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("clumsygenius@gmail.com", "UTF-8");  	
//    	myURL.params += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("2713358", "UTF-8");
//            	
//        ConnectivityManager connMgr = (ConnectivityManager) 
//            getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new DownloadWebpageText().execute(myURL);
//        } else {
//            textView.setText("No network connection available.");
//        }
//    }
//    
//    private class DownloadWebpageText extends AsyncTask<URLpostrequest, Long, String> {
//    	    	
//      	
//        @Override
//        protected String doInBackground(URLpostrequest... urls) {
//              
//            // params comes from the execute() call: params[0] is the url.
//            try {
//            
//                return downloadUrl(urls[0]);
//            } catch (IOException e) {
//                return "Unable to retrieve web page. URL may be invalid.";
//            }
//        }
//        // onPostExecute displays the results of the AsyncTask.
//        @Override
//        protected void onPostExecute(String result) {
//            textView.setText(result);
//       }
//    }
//    
//    private String downloadUrl(URLpostrequest myurl) throws IOException {
//        InputStream is = null;
//        OutputStream os = null;
//        
//        // Only display the first 500 characters of the retrieved
//        // web page content.
//        int len = 500;
//            
//        try {
//            URL url = new URL(myurl.URL);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000 /* milliseconds */);
//            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("POST");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//            
////            List<NameValuePair> params = new ArrayList<NameValuePair>();
////            params.add(new BasicNameValuePair("tag", "register"));
////            params.add(new BasicNameValuePair("email", "clumsygenius@gmail.com"));
////            params.add(new BasicNameValuePair("password", "s2713358"));
//            
//            
//            os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(myurl.params);
//            writer.close();
//            os.close();
//            
//            // Starts the query
//            conn.connect();
//            int response = conn.getResponseCode();
//            Log.d("http response", "The response is: " + response);
//            is = conn.getInputStream();        
//            
//            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
//            return contentAsString;
//            
//        // Makes sure that the InputStream is closed after the app is
//        // finished using it.
//        } finally {
//            if (is != null) {
//                is.close();
//            } 
//        }
//    }
//    
//    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
//        Reader reader = null;
//        reader = new InputStreamReader(stream, "UTF-8");        
//        char[] buffer = new char[len];
//        reader.read(buffer);
//        return new String(buffer);
//    }
//    
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//}