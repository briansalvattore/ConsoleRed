package app.horses.console.red;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        SpMain.init(this);
    }
}
