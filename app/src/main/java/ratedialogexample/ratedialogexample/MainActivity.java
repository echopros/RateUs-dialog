package ratedialogexample.ratedialogexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rateusdialoghelper.RateDialogHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnShow = findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create().showRateDialogImmidiatly(MainActivity.this);
            }
        });

        create().showRateDialog(this);
    }

    private RateDialogHelper create(){
        int colorInactive = Color.parseColor("#c59d23");
        int colorActive = Color.parseColor("#f8c017");

        RateDialogHelper.Builder rateDialogHelperbuilder = new RateDialogHelper.Builder()
                .setRatingColorActive(colorActive)
                .setRatingColorInactive(colorInactive)
                .setTitleAppNameColor(colorActive)
                .setCancelColor(colorInactive)
                .setAppName(getResources().getString(R.string.app_name))
                .setRateColor(colorActive)
                .setFeedbackEmail("someamil@gmail.com");

        if (BuildConfig.DEBUG){
            //for debug show rate dlg all the time
            rateDialogHelperbuilder.setShowEveryTime(true);
        }

        return rateDialogHelperbuilder.build();
    }
}
