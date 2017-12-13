package ratedialogexample.ratedialogexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import rateusdialoghelper.RateDialogHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int colorInactive = Color.parseColor("#c59d23");
        int colorActive = Color.parseColor("#f8c017");

        RateDialogHelper.Builder rateDialogHelperbuilder = new RateDialogHelper.Builder()
                .setRatingColorActive(colorActive)
                .setRatingColorInactive(colorInactive)
                .setTitleAppNameColor(colorActive)
                .setCancelColor(colorInactive)
                .setRateColor(colorActive)
                .setFeedbackEmail("someamil@gmail.com");

        if (BuildConfig.DEBUG){
            //for debug show rate dlg all the time
            rateDialogHelperbuilder.setShowEveryTime(true);
        }

        rateDialogHelperbuilder.build().showRateDialog(this);
    }
}
