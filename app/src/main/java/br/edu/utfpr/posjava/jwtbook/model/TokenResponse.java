package br.edu.utfpr.posjava.jwtbook.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by talyssondecastro on 18/05/17.
 */

public class TokenResponse {

    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static String getTokenSharedPreferences(Context context) {
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(context);
        String token = preferences2.getString("token", "");
        return token;
    }

    public void setTokenSharedPreferences(String token, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

}
