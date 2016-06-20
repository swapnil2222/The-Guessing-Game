package com.example.mysqltest;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartGameActivity extends Activity implements View.OnClickListener {
TextView guessMsg;
    Button startGame;
    String upperLimit;
    String lowerLimit;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        guessMsg=(TextView)findViewById(R.id.guess_desc_msg);
        startGame=(Button)findViewById(R.id.start_Game);

        //get data from welcome qactivity
        Bundle bundle=getIntent().getExtras();
        lowerLimit=bundle.getString("lowerLimit");
        username=bundle.getString("username");
        Log.d("lowerLimit:",lowerLimit);
        upperLimit=bundle.getString("upperLimit");
        Integer upper=Integer.parseInt(upperLimit);
        upper=upper-1;
       if(upperLimit==null)
       {
           Toast.makeText(getApplicationContext(),"Upperlimit is blanck",Toast.LENGTH_LONG).show();
       }
       // Log.d("upperLimit:",upperLimit);
        String getText=getResources().getString(R.string.guess_desc);
        guessMsg.setText(getText+" "+lowerLimit+" to "+upper.toString());

        startGame.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.start_Game:
            Intent i=new Intent(StartGameActivity.this,PlayGameActivity.class);
            i.putExtra("upperLimit",upperLimit);
            i.putExtra("lowerLimit",lowerLimit);
            i.putExtra("username",username);

            startActivity(i);

            break;

        default:
            break;
    }
    }
}
