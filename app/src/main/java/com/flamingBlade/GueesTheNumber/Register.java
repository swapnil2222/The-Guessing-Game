package com.flamingBlade.GueesTheNumber;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{

    private EditText username,fullname,password,phone,email;
	private Button  mRegister;
    private  Button mlogin;
    NetworkInfo wifiCheck;
	
	 // Progress Dialog
    private ProgressDialog pDialog;
 
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    
    //php register script
    
    //localhost :  
    //testing on your device
    //put your local ip instead,  on windows, run CMD > ipconfig
    //or in mac's terminal type ifconfig and look for the ip under en0 or en1
   // private static final String REGISTER_URL = "http://xxx.xxx.x.x:1234/webservice/register.php";
    
    //testing on Emulator:
    private static final String REGISTER_URL = "http://sggsapp.tk/GuessNumber/signup.php";
    
  //testing from a real server:
    //private static final String REGISTER_URL = "http://www.mybringback.com/webservice/register.php";
    
    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.signin_password);
        fullname=(EditText)findViewById(R.id.fullname);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);

		mRegister = (Button)findViewById(R.id.btnRegister);
		mRegister.setOnClickListener(this);
		mlogin=(Button)findViewById(R.id.btnLinkToLoginScreen);
        mlogin.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
      switch (v.getId())
      {
          case R.id.btnRegister:
              String fullName = fullname.getText().toString();
              String userName = username.getText().toString();
              String passWord = password.getText().toString();
              String phoneNumber = phone.getText().toString();
              String emailAddress = email.getText().toString();


              if(fullName.equals("")||passWord.equals("")||userName.equals("")||phoneNumber.equals("")||emailAddress.equals(""))
              {
                  Toast.makeText(getApplicationContext(),"Enter all the fields",Toast.LENGTH_LONG).show();
              }

              if(isInternetOn())
              {

                  new CreateUser().execute(fullName, userName, passWord, phoneNumber, emailAddress);  }
              else
              {
                  Toast.makeText(getApplicationContext(),"Please Check Internet Connection",Toast.LENGTH_LONG).show();
              }


              break;

          case R.id.btnLinkToLoginScreen:
              Intent i=new Intent(this,Login.class);
              startActivity(i);
              break;

          default:
              break;

      }

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

    public final boolean isInternetOn() {

        //get connectivity manager object to check internet
        ConnectivityManager connection=(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        wifiCheck=connection.getNetworkInfo(connection.TYPE_WIFI);

        //Check for network Connection
        if(connection.getNetworkInfo(0).getState()==NetworkInfo.State.CONNECTED || connection.getNetworkInfo(0).getState()== NetworkInfo.State.CONNECTING ||
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

                Intent i=new Intent(Register.this,AboutUsActivity.class);
                startActivity(i);

                // Toast.makeText(getApplicationContext(),"AboutPage",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    class CreateUser extends AsyncTask<String, String, String> {

		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Register.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
		
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			 // Check for success tag
            int success;
            String fullname=args[0];
            String username = args[1];
            String password = args[2];
            String phone=args[3];
            String email=args[4];



            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("fullname",fullname));
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("phonenumber", phone));
                params.add(new BasicNameValuePair("emailaddress",email));




            //    Log.d("request!", "starting");
                
                //Posting user data to script 
                JSONObject json = jsonParser.makeHttpRequest(
                       REGISTER_URL, "POST", params);
 
                // full json response
              //  Log.d("Registering attempt", json.toString());
 
                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                //	Log.d("SignUp Successful!", json.toString());
                	finish();
                	return json.getString(TAG_MESSAGE);
                }else{
                //	Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
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
            if (file_url != null){
            	Toast.makeText(Register.this, file_url, Toast.LENGTH_LONG).show();
            }
 
        }
		
	}
		 

}
