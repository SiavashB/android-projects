package com.wrudt.beta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wrudt.library.DatabaseHandler;
import com.wrudt.library.UserFunctions;

public class LoginActivity extends Activity {
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	private static String login_tag = "login";
	private static String loginURL = "http://www.whatrudoingtonight.com/mobilelogin.php";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_lo);

		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.login_btn);
		btnLinkToRegister = (Button) findViewById(R.id.register_btn_fromlogin);
		loginErrorMsg = (TextView) findViewById(R.id.login_error_txt_view);
		
		TextView txt = (TextView) findViewById(R.id.wrudt_logo);  
		Typeface font = Typeface.createFromAsset(getAssets(), "Acens.ttf");  
		txt.setTypeface(font); 

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				loginUser(email, password);

			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * */

	public void loginUser(String email, String password) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));

		DownloaderTask mytask = new DownloaderTask(loginURL);
		try {
			mytask.execute(params);
			JSONObject json = mytask.get();
			handleLoginResult(json);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return json
		// Log.e("JSON", json.toString());

	}

	public void handleLoginResult(JSONObject json) {
		UserFunctions userFunction = new UserFunctions();

		// check for login response
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				loginErrorMsg.setText("");
				String res = json.getString(KEY_SUCCESS);
				if (Integer.parseInt(res) == 1) {
					// user successfully logged in
					// Store user details in SQLite Database
					DatabaseHandler db = new DatabaseHandler(
							getApplicationContext());
					JSONObject json_user = json.getJSONObject("user");

					// Clear all previous data in database
					userFunction.logoutUser(getApplicationContext());
					db.addUser(json_user.getString(KEY_NAME),
							json_user.getString(KEY_EMAIL),
							json.getString(KEY_UID),
							json_user.getString(KEY_CREATED_AT));

					// Launch Dashboard Screen
					Intent dashboard = new Intent(getApplicationContext(),
							DashboardActivity.class);

					// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(dashboard);

					// Close Login Screen
					finish();
				} else {
					// Error in login
					loginErrorMsg.setText("Incorrect username/password");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}