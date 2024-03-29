package com.wrudt.beta;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.wrudt.beta.DownloaderTask.OnResponseListener;
import com.wrudt.library.DatabaseHandler;
import com.wrudt.library.User;
import com.wrudt.library.UserFunctions;

public class RegisterActivity extends Activity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	private static String registerURL = "http://www.whatrudoingtonight.com/mobilelogin.php";

	private static String register_tag = "register";
	
	private String array_spinner[];
	
	private Spinner citySpinner;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_lo);

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
		
		array_spinner=new String[3];
		array_spinner[0]="sandiego";
		array_spinner[1]="orangecounty";
		array_spinner[2]="losangeles";
		citySpinner = (Spinner) findViewById(R.id.city_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_dropdown_item_1line, array_spinner);
		citySpinner.setAdapter(adapter);

		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				User user = new User();
				RadioGroup r = (RadioGroup)findViewById(R.id.radioGroup1);
				int checkedid = ((RadioGroup)findViewById(R.id.radioGroup1)).getCheckedRadioButtonId();
				
				user.name = inputFullName.getText().toString();
								user.email = inputEmail.getText().toString();
				user.password = inputPassword.getText().toString();
				user.city = (String)citySpinner.getSelectedItem();
				user.setSex(checkedid);	
						
				//String sex = ((RadioButton)findViewById(checkedid)).getText().toString();
							
				UserFunctions userFunction = new UserFunctions();
				userFunction.registerUser(user, onResponseListener);

			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}

	/**
	 * function make Login Request
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * */

	
	public OnResponseListener onResponseListener = new OnResponseListener() {
		
		@Override
		public void onSuccess( JSONObject json) {
			UserFunctions userFunction = new UserFunctions();

			// check for login response
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					registerErrorMsg.setText("");
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						// user successfully registred
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
						// Close Registration Screen
						finish();
					} else {
						// Error in registration
						registerErrorMsg.setText("Error occured in registration");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			
		}
		
		@Override
		public void onFailure(String message) {
			
			registerErrorMsg.setText(message);
		}
	};

	private void handleRegisterResult(JSONObject json) {

		
	}

}
