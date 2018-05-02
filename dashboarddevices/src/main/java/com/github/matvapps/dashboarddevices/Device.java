package com.github.matvapps.dashboarddevices;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by Alexandr.
 */
public abstract class Device extends Gauge {
    public Device(Context context) {
        super(context);
    }

    public Device(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Device(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract int getSize();
    public abstract float getSpeedometerWidth();
//    public abstract int getPadding();
//    public abstract boolean isInEditMode();
}
