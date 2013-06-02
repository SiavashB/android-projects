package com.wrudt.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.wrudt.beta.DownloaderTask;
import com.wrudt.beta.DownloaderTask.OnResponseListener;
//import com.wrudt.beta.MainActivity.DownloadWebpageText;
//import com.wrudt.beta.MainActivity.URLpostrequest;
 
public class UserFunctions {
 
    private JSONParser jsonParser;
    
	private static String registerURL = "http://www.whatrudoingtonight.com/mobilelogin.php";

	private static String register_tag = "register";
 
    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    

 
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
 
	public void registerUser(User user, OnResponseListener onResponseListener) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", user.name));
		params.add(new BasicNameValuePair("email", user.email));
		params.add(new BasicNameValuePair("password", user.password));
		params.add(new BasicNameValuePair("sex", user.getSex(null)));
		params.add(new BasicNameValuePair("city", user.city));
		

		DownloaderTask mytask = new DownloaderTask(registerURL, onResponseListener);
		try {
			mytask.execute(params);
			//JSONObject json = mytask.get();
			//handleRegisterResult(json);

		} 
//		catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		finally{
			
		}

	}

 

 
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
 
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
    
    
 
}