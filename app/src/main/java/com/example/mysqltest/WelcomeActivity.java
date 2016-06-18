package com.example.mysqltest;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity implements View.OnClickListener {
    TextView textView;
    SessionManager manager;
    Button range1,range2,range3,range4;
    String upperLimit,lowerLimit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //Inflate all the views here
        textView=(TextView)findViewById(R.id.welcome_msg);
        range1=(Button)findViewById(R.id.range1);
        range2=(Button)findViewById(R.id.range2);
        range3=(Button)findViewById(R.id.range3);
        range4=(Button)findViewById(R.id.range4);

    //To get data from Login and Launcer Activity
        Bundle bundle=getIntent().getExtras();
            String user=bundle.getString("Username");

            if(user==null) {
                user=bundle.getString("message");
                textView.setText("Hi "+ user);
            }
        else {
                textView.setText("Hi "+ user);
            }
        //to create manager object for setting data
        manager=new SessionManager();

        range1.setOnClickListener(this);
        range2.setOnClickListener(this);
        range3.setOnClickListener(this);
        range4.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.range1:
                lowerLimit="0";
                upperLimit="51";
                Intent intent=new Intent(WelcomeActivity.this,StartGameActivity.class);
                intent.putExtra("lowerLimit", lowerLimit);
                intent.putExtra("upperLimit", upperLimit);
                startActivity(intent);


                break;

            case R.id.range2:
                lowerLimit="0";
                upperLimit="101";
                Intent i2=new Intent(WelcomeActivity.this,StartGameActivity.class);
                i2.putExtra("lowerLimit",lowerLimit);
                i2.putExtra("upperLimit",upperLimit);
                startActivity(i2);

                break;

            case R.id.range3:
                lowerLimit="0";
                upperLimit="301";
                Intent i3=new Intent(WelcomeActivity.this,StartGameActivity.class);
                i3.putExtra("lowerLimit",lowerLimit);
                i3.putExtra("upperLimit",upperLimit);
                startActivity(i3);

                break;

            case R.id.range4:
                lowerLimit="0";
                upperLimit="501";
                Intent i4=new Intent(WelcomeActivity.this,StartGameActivity.class);
                i4.putExtra("lowerLimit",lowerLimit);
                i4.putExtra("upperLimit",upperLimit);
                startActivity(i4);

                break;

            default:
                break;
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                // About option clicked.
                return true;
            case R.id.action_exit:
                // Exit option clicked.

                manager.setPreference(WelcomeActivity.this, "Status", "0");
                Toast.makeText(getApplicationContext(), "You have Suucessfully Logout", Toast.LENGTH_LONG).show();
                finish();

                return true;
            case R.id.action_settings:
                // Settings option clicked.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
