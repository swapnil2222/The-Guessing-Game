package com.flamingBlade.GueesTheNumber;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by swapnil on 5/21/2016.
 */
public class SessionManager {

    public void setPreference(Context context,String key,String value)
    {
        SharedPreferences.Editor editor=context.getSharedPreferences("SGGSAtAGlance", Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.commit();
    }
    public String getPreference(Context context,String key)
    {
        SharedPreferences preferences=context.getSharedPreferences("SGGSAtAGlance", Context.MODE_PRIVATE);
        String position=preferences.getString(key,"");
        return position;
    }

}

