package com.example.mysqltest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

public class AboutUsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_us);

    }

}
