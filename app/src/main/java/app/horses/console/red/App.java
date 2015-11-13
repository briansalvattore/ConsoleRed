package app.horses.console.red;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this,
                "rn8gIUOfyOGB71PgGI29umb0rvRysKb4EvpyeNmC",
                "zfMz77iHz5HGTfis0lg3g9rvM4gEAPuMxyARVlmA");

        SpMain.init(this);
    }
}
