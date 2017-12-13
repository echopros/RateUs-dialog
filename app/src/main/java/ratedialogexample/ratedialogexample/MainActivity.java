package ratedialogexample.ratedialogexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rateusdialoghelper.RateDialogHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RateDialogHelper dailog = new RateDialogHelper.Builder()
                .setShowEveryTime(true)
                .build();

        dailog.showRateDialog(this);
    }
}
