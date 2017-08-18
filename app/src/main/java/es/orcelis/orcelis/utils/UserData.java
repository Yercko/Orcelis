package es.orcelis.orcelis.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import es.orcelis.orcelis.models.Token;

/**
 * Created by yercko on 23/05/2017.
 */

public class UserData {
    /**
     *
     */
    private static UserData instance = null;

    //variables de estado de explotaciones
    private static int posicion_actual = 0;
    private static boolean pasos_view_visible = false;

    public Token token;
    public boolean logueado;

    /**
     *
     */
    public UserData(){
        super();
    }

    /**
     * Static method
     * @return current class instance
     */
    public static UserData getInstance(Context context){

        if(instance == null){
            instance = new UserData();
            instance.refresh(context);
        }

        return instance;
    }

    public static int getPosicion_actual() {
        return posicion_actual;
    }

    public static void setPosicion_actual(int posicion_actual) {
        UserData.posicion_actual = posicion_actual;
    }

    public static boolean isPasos_view_visible() {
        return pasos_view_visible;
    }

    public static void setPasos_view_visible(boolean pasos_view_visible) {
        UserData.pasos_view_visible = pasos_view_visible;
    }



    public void refresh(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json;
        try {
            json = prefs.getString("token", null);
            token = gson.fromJson(json, Token.class);
        }
        catch(Exception ex) {
            token = null;
        }
        logueado = prefs.getBoolean("logueado",false);
    }



    public void save(Context context) {
        //save preferences
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        Gson gson = new Gson();
        String json;
        json = gson.toJson(token);
        editor.putString("token", json);
        editor.putBoolean("logueado",logueado);
        editor.apply();

    }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isLogueado() {
        return logueado;
    }

    public void setLogueado(boolean logueado) {
        this.logueado = logueado;
    }
}
