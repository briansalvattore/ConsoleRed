package app.horses.console.red;

import android.content.Context;
import android.content.SharedPreferences;

public class SpMain {

    private static Context context;

    private static final String SHARED_PREF_NAME = "console";
    private static final String PREF_SESSION = "session";

    public static SpMain init(Context context){
        setContext(context);
        return new SpMain();
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        SpMain.context = context;
    }

    protected static SharedPreferences getPrefs() {

        return getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    protected static void setString(final String key, final String value){

        getPrefs().edit().putString(key, value).commit();
    }

    protected static void deleteSharedPreferences(){

        getContext().getSharedPreferences(SHARED_PREF_NAME, 0).edit().clear().commit();
    }




    public static boolean isSession() {

        return Boolean.parseBoolean(getPrefs().getString(PREF_SESSION, "false"));
    }

    public static void setSession(boolean value) {

        setString(PREF_SESSION, String.valueOf(value));
    }
}
