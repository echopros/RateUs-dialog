package rateusdialoghelper;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalPreferenceManager {

    public static final String PREFERENCE_NAME = "stolen_preferences";
    public static final String STRING_DEFAULT_VALUE = "";
    public static final long LONG_DEFAULT_VALUE = 0L;
    public static final int INT_DEFAULT_VALUE = 0;
    public static final boolean BOOLEAN_DEFAULT_VALUE = false;

    public static final String PREFERENCE_RATE_DAY = "PREFERENCE_RATE_DAY";
    public static final String PREFERENCE_RATE_SESSION = "PREFERENCE_RATE_SESSION";
    public static final String PREFERENCE_RATE_DONE = "PREFERENCE_RATE_DONE";
    public static final String TWITTER_APP_NOT_INSTALLED_WARN = "TWITTER_APP_NOT_INSTALLED_WARN";

    /**
     * Function: setStringValue()
     */
    public static void setStringValue(Context context, String key, String value) {
        setStringValuePrivate(context, key, value).commit();
    }

    public static void setStringValueAsync(Context context, String key, String value) {
        setStringValuePrivate(context, key, value).apply();
    }

    private static SharedPreferences.Editor setStringValuePrivate(Context context, String key, String value) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        if (value == null) {
            editor.remove(key);
        } else {
            editor.putString(key, value);
        }

        return editor;
    }

    /**
     * Function: getStringValue()
     */
    public static String getStringValue(Context context, String key, String def) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return setting == null ? def : setting
                .getString(key, def);
    }

    public static String getStringValue(Context context, String key) {
        return getStringValue(context, key, STRING_DEFAULT_VALUE);
    }

    /**
     * Function: setLongValue()
     */
    private static SharedPreferences.Editor setLongValuePrivate(Context context, String key, long value) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putLong(key, value);
        return editor;
    }

    public static void setLongValue(Context context, String key, long value) {
        setLongValuePrivate(context, key, value).commit();
    }

    public static void setLongValueAsync(Context context, String key, long value) {
        setLongValuePrivate(context, key, value).apply();
    }

    /**
     * Function: getLongValue()
     */
    public static long getLongValue(Context context, String key, long def) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return setting == null ? def : setting
                .getLong(key, def);
    }

    public static long getLongValue(Context context, String key) {
        return getLongValue(context, key, LONG_DEFAULT_VALUE);
    }

    /**
     * Function: setIntValue()
     */
    private static SharedPreferences.Editor setIntValuePrivate(Context context, String key, int value) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt(key, value);
        return editor;
    }

    public static void setIntValue(Context context, String key, int value) {
        setIntValuePrivate(context, key, value).commit();
    }

    public static void setIntValueAsync(Context context, String key, int value) {
        setIntValuePrivate(context, key, value).apply();
    }

    /**
     * Function: getIntValue()
     */
    public static int getIntValue(Context context, String key, int def) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return setting == null ? def : setting.getInt(
                key, def);
    }

    public static int getIntValue(Context context, String key) {
        return getIntValue(context, key, INT_DEFAULT_VALUE);
    }

    /**
     * Function: setBooleanValue()
     */
    private static SharedPreferences.Editor setBooleanValuePrivate(Context context, String key, boolean value) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean(key, value);
        return editor;
    }

    public static void setBooleanValue(Context context, String key,
                                       boolean value) {
        setBooleanValuePrivate(context, key, value).commit();
    }

    public static void setBooleanValueAsync(Context context, String key,
                                            boolean value) {
        setBooleanValuePrivate(context, key, value).apply();
    }

    /**
     * Function: getBooleanValue()
     */
    public static boolean getBooleanValue(Context context, String key, boolean bDefault) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        return setting == null ? bDefault : setting.getBoolean(key, bDefault);
    }

    public static boolean getBooleanValue(Context context, String key) {
        return getBooleanValue(context, key, BOOLEAN_DEFAULT_VALUE);
    }

    /**
     * Function: removeKey()
     */
    public static void removeKey(Context context, String key) {
        SharedPreferences setting = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();

        editor.remove(key);
        editor.commit();
    }
}

