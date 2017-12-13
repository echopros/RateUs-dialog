package rateusdialoghelper;

import android.content.Context;

public class ContextHolder {

    private static Context context;

    public static void setContext(Context appContext){
        context = appContext;
    }

    public static Context getContext(){
        return context;
    }
}
