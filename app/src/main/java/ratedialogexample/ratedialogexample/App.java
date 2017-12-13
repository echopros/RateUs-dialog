package ratedialogexample.ratedialogexample;

import android.app.Application;

import rateusdialoghelper.RateDialogHelper;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        RateDialogHelper.onNewSession(getApplicationContext());
    }
}
