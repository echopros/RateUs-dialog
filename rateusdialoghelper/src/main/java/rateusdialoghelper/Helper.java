package rateusdialoghelper;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

public class Helper {

    static public void setDrawableColor(Drawable drawable, int color) {
        drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
