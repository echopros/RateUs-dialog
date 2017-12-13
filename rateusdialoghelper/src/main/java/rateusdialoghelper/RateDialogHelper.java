package rateusdialoghelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Calendar;

import static rateusdialoghelper.ContextHolder.getContext;


/**
 * Contract: call RateDialogHelper.onNewSession from App onCreate.
 * Call showRateDialog from activity where you want to display rate dialog
 */
public class RateDialogHelper {
    private static final int INITIAL_SESSION_AMOUNT = 0;
    private static final int INITIAL_LAST_INSTALL_DATE = -100;

    private static final boolean INITIAL_IS_RATE_DONE = false;
    private static final float MIN_STARS_RATE = 3.1f;

    private static int sessionNum;

    private boolean isRated;

    private int colorCancel = Color.parseColor("#000000");
    private int colorRate = Color.parseColor("#0b7bb1");
    private int colorTitle = Color.parseColor("#000000");
    private int colorTitleAppName = Color.parseColor("#0b7bb1");

    private int sessionAmount = 4;
    private int dayAmount = 2;

    private int colorInactive = -1;
    private int colorActive = -1;

    private String email;

    private boolean showEveryTime = false;

    private RateDialogHelper() {
        isRated = isRateDone();
    }

    /**
     * Has to be called from App class
     * @param appContext
     */
    public static void onNewSession(Context appContext) {
        ContextHolder.setContext(appContext);

        sessionNum = getSessionNum();
        saveNewSessionNum(sessionNum + 1);
    }

    /**
     * @return true if dialog opened, false otherwise
     */
    public boolean showRateDialog(Activity activity) {

        boolean shouldShowDialog = showEveryTime ||
                (!isRated &&
                (getDaysPast(getLastDate()) >= dayAmount) &&
                (sessionNum >= sessionAmount));

        if (shouldShowDialog) {
            showDialog(activity);

            saveNewSessionNum(0);

            long date = getDateNum();
            saveDayWeOpenedRateDialog(date);
        }

        return shouldShowDialog;
    }

    private void showDialog(final Activity activity) {
        //show rate dialog
        RateDialog rateDialog = new RateDialog(activity);
    }

    public static class Builder{
        private RateDialogHelper rateDialogHelper;

        public Builder(){
            rateDialogHelper = new RateDialogHelper();
        }

        public Builder setSessionAmount(int sessionAmount){
            rateDialogHelper.sessionAmount = sessionAmount;
            return this;
        }

        public Builder setDayAmount(int dayAmount){
            rateDialogHelper.dayAmount = dayAmount;
            return this;
        }

        public Builder setTitleColor(int color){
            rateDialogHelper.colorTitle = color;
            return this;
        }

        public Builder setTitleAppNameColor(int color){
            rateDialogHelper.colorTitleAppName = color;
            return this;
        }

        public Builder setCancelColor(int color){
            rateDialogHelper.colorCancel = color;
            return this;
        }

        public Builder setRateColor(int color){
            rateDialogHelper.colorRate = color;
            return this;
        }

        public Builder setFeedbackEmail(String email){
            rateDialogHelper.email = email;
            return this;
        }

        public Builder setShowEveryTime(boolean showEveryTime){
            rateDialogHelper.showEveryTime = showEveryTime;
            return this;
        }

        public Builder setRatingColorActive(int color){
            rateDialogHelper.colorActive = color;
            return this;
        }

        public Builder setRatingColorInactive(int color){
            rateDialogHelper.colorInactive = color;
            return this;
        }

        public RateDialogHelper build(){
            return rateDialogHelper;
        }
    }

    private long getLastDate() {
        long day = LocalPreferenceManager.getLongValue(getContext(),
                LocalPreferenceManager.PREFERENCE_RATE_DAY, INITIAL_LAST_INSTALL_DATE);

        if (day == INITIAL_LAST_INSTALL_DATE) {
            //this is date of installing the app, we should consider this as starting point
            day = getDateNum();
            saveDayWeOpenedRateDialog(getDateNum());
        }

        return day;
    }

    private static int getDaysPast(long date) {
        int year = (int) (date / 10000);
        int month = (int) ((date % 10000) / 100);
        int day = (int) (date % 100);

        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, day);
        thatDay.set(Calendar.MONTH, month);
        thatDay.set(Calendar.YEAR, year);

        Calendar today = Calendar.getInstance();
        long diff = today.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
        int days = (int) (diff / (24 * 60 * 60 * 1000));

        return days;
    }

    private static long getDateNum() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return year * 10000 + month * 100 + day;
    }

    private void saveDayWeOpenedRateDialog(long day) {
        LocalPreferenceManager.setLongValueAsync(getContext(),
                LocalPreferenceManager.PREFERENCE_RATE_DAY, day);
    }

    private static int getSessionNum() {
        return LocalPreferenceManager.getIntValue(getContext(),
                LocalPreferenceManager.PREFERENCE_RATE_SESSION, INITIAL_SESSION_AMOUNT);
    }

    private static void saveNewSessionNum(int sessionNumber) {
        sessionNum = sessionNumber;

        LocalPreferenceManager.setIntValueAsync(getContext(),
                LocalPreferenceManager.PREFERENCE_RATE_SESSION, sessionNumber);
    }

    private void saveRateDone(boolean isDone) {
        isRated = true;

        LocalPreferenceManager.setBooleanValueAsync(getContext(),
                LocalPreferenceManager.PREFERENCE_RATE_DONE, isDone);
    }

    private boolean isRateDone() {
        return LocalPreferenceManager.getBooleanValue(getContext(),
                LocalPreferenceManager.PREFERENCE_RATE_DONE, INITIAL_IS_RATE_DONE);
    }

    private class RateDialog {

        private static final String MARKET_URL_PREFIX = "market://details?id=";
        private static final String MARKET_BROWSER_URL_PREFIX = "https://play.google.com/store/apps/details?id=";

        private RatingBar ratingBar;

        private Button btnCancel;

        private Button btnRate;

        private TextView tvTitle;

        private float likes = 0;

        private Activity activity;

        private RateDialog(Activity activity) {
            this.activity = activity;
            init();
        }

        private void init() {
            View dialogView = LayoutInflater.from(activity).inflate(R.layout.rate_dialog, null, false);
            tvTitle = (TextView) dialogView.findViewById(R.id.tvTitle);
            btnRate = (Button) dialogView.findViewById(R.id.btnRate);
            btnCancel = (Button) dialogView.findViewById(R.id.btnCancel);
            ratingBar = (RatingBar) dialogView.findViewById(R.id.ratingBar);

            tvTitle.setTextColor(colorTitle);
            final SpannableStringBuilder sb = new SpannableStringBuilder();
            sb.append(getContext().getResources().getString(R.string.title_rating_pref));
            sb.append(" ");
            SpannableString s1 = new SpannableString(getContext().getResources().getString(R.string.app_name));
            s1.setSpan(new ForegroundColorSpan(colorTitleAppName), 0, s1.length(), 0);
            s1.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, s1.length(), 0);

            sb.append(s1);

            sb.append("? ");
            sb.append(getContext().getResources().getString(R.string.title_rating_suf));

            tvTitle.setText(sb);

            LayerDrawable layerDrawable = (LayerDrawable)ratingBar.getProgressDrawable();
            LayerDrawable layerDrawable1 = (LayerDrawable)ratingBar.getIndeterminateDrawable();

            if (colorInactive != -1) {
                Helper.setDrawableColor(layerDrawable.getDrawable(0), colorInactive);
                Helper.setDrawableColor(layerDrawable1.getDrawable(0), colorInactive);
            }

            if (colorActive != -1) {
                Helper.setDrawableColor(layerDrawable.getDrawable(1), colorActive);
                Helper.setDrawableColor(layerDrawable1.getDrawable(1), colorActive);
            }

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        btnRate.setEnabled(true);
                        likes = rating;
                    }
                }
            });

            final AlertDialog dialog = new AlertDialog.Builder(activity).create();
            dialog.setView(dialogView);
            dialog.show();

            btnRate.setTextColor(colorRate);
            btnRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (likes >= MIN_STARS_RATE) {

                        saveRateDone(true);

                        final String appPackageName = activity.getPackageName();
                        try {
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URL_PREFIX + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_BROWSER_URL_PREFIX + appPackageName)));
                        }
                    } else {
                        //open secondary dialog
                        new FeedbackDialog(activity);
                        dialog.dismiss();
                    }

                    dialog.dismiss();
                }
            });

            btnCancel.setTextColor(colorCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }

    private class FeedbackDialog {

        private Activity activity;

        private FeedbackDialog(Activity activity) {
            this.activity = activity;
            init();
        }

        private void init() {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.developer_dlg, null, false);

            final AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
            alertDialog.setView(view);

            TextView tvTitle = view.findViewById(R.id.tvTitle);
            Button btnCancel = view.findViewById(R.id.btnCancel);
            Button btnSend = view.findViewById(R.id.btnSend);

            btnCancel.setTextColor(colorCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });

            btnSend.setTextColor(colorRate);
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendReport(activity, "Feedback from (" + android.os.Build.MODEL + ")", "Give us feedback!", email);
                    saveRateDone(true);
                    alertDialog.dismiss();
                }
            });

            tvTitle.setTextColor(colorTitle);
            alertDialog.show();
        }
    }

    private static void sendReport(Activity activity, String title, String preFillString, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, title);
        intent.putExtra(Intent.EXTRA_TEXT, preFillString);

        activity.startActivity(Intent.createChooser(intent, getContext().getResources().getString(R.string.send_via)));
    }
}
