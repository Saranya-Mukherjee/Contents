package com.saranya.contents;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Constants {
    public int width;
    public int height;

    public Constants() {
        this.width=Resources.getSystem().getDisplayMetrics().widthPixels;
        this.height=Resources.getSystem().getDisplayMetrics().heightPixels;
    }



}
