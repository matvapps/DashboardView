package com.github.matvapps.dashboarddevices.components.Indicators;

import android.content.Context;
import android.graphics.Canvas;


public class NoIndicator extends Indicator<NoIndicator> {

    public NoIndicator(Context context) {
        super(context);
    }

    @Override
    protected float getDefaultIndicatorWidth() {
        return 0f;
    }

    @Override
    public void draw(Canvas canvas, float degree) {
    }

    @Override
    protected void updateIndicator() {
    }

    @Override
    protected void setWithEffects(boolean withEffects) {
    }
}
