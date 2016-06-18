package com.example.mysqltest;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class PlayGameActivity extends Activity implements View.OnClickListener {
    TextView textmsg;
    EditText getChoice;
    Button submitChoice;
    TextView summary;
    TextView actualNumberOfGuess;
    TextView actualNumber;
    TextView score;
int RESULT=0;
    int upper;
    int guessNumber;
    int counter;
    int values;
    int numberDrawn;
    int playgameScore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
//Inflate all views here
        textmsg=(TextView)findViewById(R.id.playgame_msg);
        getChoice=(EditText)findViewById(R.id.choice);
        submitChoice=(Button)findViewById(R.id.submit_choice);
        summary=(TextView)findViewById(R.id.playgame_summary);
        actualNumber=(TextView)findViewById(R.id.playgame_actualNumber);
        actualNumberOfGuess=(TextView)findViewById(R.id.playgame_no_of_guesses);
        score=(TextView)findViewById(R.id.playgame_score);


        //Now take data from welcome activity

        Bundle bundle=getIntent().getExtras();
        String upperLimit=bundle.getString("upperLimit");

        upper=Integer.parseInt(upperLimit);
        Log.d("Number drawn:",upperLimit);

        values=(int)upper;
        numberDrawn=(int)(Math.random() *values);
        String drawnNumber=Integer.toString(numberDrawn);
        if(numberDrawn==0) {
            Log.d("Number not", "Drawn");
        }
        Log.d("Number drawn:",drawnNumber);
        //set text
        String getText=getResources().getString(R.string.playgame_msg);
        textmsg.setText(getText);


        submitChoice.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.submit_choice:

                while(true)
                {
                    String value= getChoice.getText().toString();
                    if(value.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Enter a number",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Log.d("number entered",value);
                        guessNumber=Integer.parseInt(value);

                    }

                    counter++;
                    Log.d("counter:",Integer.toString(counter));
                    if(numberDrawn==guessNumber)
                    {
                        textmsg.setText("Congratulations, your guess is correct!Guess made:"+counter);
                        RESULT=1;

                    break;
                    }
                    if(guessNumber<numberDrawn)
                    {

                        textmsg.setText("The number is greater.Guess made:" + counter);
                        getChoice.setText("");



                        break;
                    }
                    if(guessNumber>numberDrawn)
                    {
                        textmsg.setText("The number is lesser.Guess made:"+counter);
                        getChoice.setText("");


                        break;
                    }

                }
                if(RESULT==1)
                {
                    String playgameSummary=getResources().getString(R.string.summary);
                    summary.setText(playgameSummary);
                    String playGameActualNumber=getResources().getString(R.string.playgame_actualNumber);
                    actualNumber.setText(playGameActualNumber+""+numberDrawn);
                    String playGameActualGuesses=getResources().getString(R.string.playgame_no_of_guesses);
                    actualNumberOfGuess.setText(playGameActualGuesses+""+counter);
                    String playGameScore=getResources().getString(R.string.playgame_score);
                    if(counter>15)

                    {   playgameScore=5;
                        score.setText(playGameScore+" "+Integer.toString(playgameScore));
                    }
                    else if(counter>12)
                    {   playgameScore=10;
                        score.setText(playGameScore+" "+Integer.toString(playgameScore));

                    }else if(counter>8)
                    {
                        playgameScore=20;
                        score.setText(playGameScore+" "+Integer.toString(playgameScore));
                    }
                    else if(counter>4)
                    {
                        playgameScore=30;
                        score.setText(playGameScore+" "+Integer.toString(playgameScore));

                    }else if(counter>2)
                    {
                        playgameScore=40;
                        score.setText(playGameScore+" "+Integer.toString(playgameScore));

                    }
                    else
                    {
                        playgameScore=50;
                        score.setText(playGameScore+" "+Integer.toString(playgameScore));
                    }
                }




        }
    }




}
