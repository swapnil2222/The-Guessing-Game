package com.example.mysqltest;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements OnClickListener {

	private EditText user, pass;
	private Button mSubmit, mRegister;
    NetworkInfo wifiCheck;

	// Progress Dialog
	private ProgressDialog pDialog;
    SessionManager manager;
	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// php login script location:

	// localhost :
	// testing on your device
	// put your local ip instead, on windows, run CMD > ipconfig
	// or in mac's terminal type ifconfig and look for the ip under en0 or en1
	// private static final String LOGIN_URL =
	// "http://xxx.xxx.x.x:1234/webservice/login.php";

	// testing on Emulator:
	private static final String LOGIN_URL = "http://sggsapp.tk/GuessNumber/login.php";

	// testing from a real server:
	// private static final String LOGIN_URL =
	// "http://www.mybringback.com/webservice/login.php";

	// JSON element ids from repsonse of php script:
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// setup input fields
		user = (EditText) findViewById(R.id.username);
		pass = (EditText) findViewById(R.id.password);

		// setup buttons
		mSubmit = (Button) findViewById(R.id.btnLogin);
		mRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

		// register listeners
		mSubmit.setOnClickListener(this);
		mRegister.setOnClickListener(this);
        manager=new SessionManager();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLogin:
            String username = user.getText().toString();
            String password = pass.getText().toString();

            if(username.equals("")||password.equals(""))
            {
                Toast.makeText(getApplicationContext(),"Enter all the credentials",Toast.LENGTH_LONG).show();
            }

            if(isInternetOn()) {

                new AttemptLogin().execute(username, password);

            } else
            {
                Toast.makeText(getApplicationContext(),"Please Check Internet Connection",Toast.LENGTH_LONG).show();
            }



			break;
		case R.id.btnLinkToRegisterScreen:
			Intent i = new Intent(this, Register.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}
    public final boolean isInternetOn() {

        //get connectivity manager object to check internet
        ConnectivityManager connection=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        wifiCheck=connection.getNetworkInfo(connection.TYPE_WIFI);

        //Check for network Connection
        if(connection.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTED || connection.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTING ||
                connection.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connection.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED )
        {
            //Toast.makeText(getApplicationContext(),"Connected to internet",Toast.LENGTH_LONG).show();
            return true;
        }
        else if(connection.getNetworkInfo(0).getState()== NetworkInfo.State.DISCONNECTED ||
                connection.getNetworkInfo(1).getState()==NetworkInfo.State.DISCONNECTED
                )
        {
            //
        }



        return false;
    }

	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
		startActivity(intent);
		finish();
		//System.exit(0);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_about, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_about:
				// About option clicked.
				Intent i=new Intent(Login.this,AboutUsActivity.class);
				startActivity(i);
			//	Toast.makeText(getApplicationContext(),"AboutPage",Toast.LENGTH_LONG).show();
				return true;
					default:
				return super.onOptionsItemSelected(item);
		}
	}



	class AttemptLogin extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Attempting login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;
			try {
				// Building Parameters
                String username = args[0];
                String password = args[1];

                List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
						params);

				// check your log for json response
				Log.d("Login attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					Log.d("Login Successful!", json.toString());
					// save user data
					//Log.d("succesful login", " by me");
                    manager.setPreference(Login.this, "Status", "1");
                    //Log.d("succesful login", " by me1");
                    manager.setPreference(Login.this, "Username", username);
                    Log.d("Username after login", username);
                    Intent i = new Intent(Login.this, WelcomeActivity.class);
                    //Log.d("succesful login"," by me3");

                    i.putExtra("Username",username);
                    finish();
                    startActivity(i);
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
			}

		}

	}

}
