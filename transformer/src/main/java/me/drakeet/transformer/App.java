package me.drakeet.transformer;

import android.app.Application;
import me.drakeet.timemachine.TimeKey;

/**
 * @author drakeet
 */
public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        TimeKey.install(getString(R.string.app_name), "drakeet");
    }
}
