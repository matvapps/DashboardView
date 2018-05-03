package com.github.matvapps.dashboarddevices.components.Indicators;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;


public class NewCustomIndicator extends Indicator<NewCustomIndicator> {

    private Path indicatorPath = new Path();
    private Path circlePartPath = new Path();
    private Path thinLinePath = new Path();
    private Path circlePath = new Path();

    private Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint circlePartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint thinLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public static final float LINE = 1f, HALF_LINE = .5f, QUARTER_LINE = .25f;
    private float mode;

    private boolean useCenterCircle = true;

    public boolean isUseCenterCircle() {
        return useCenterCircle;
    }

    public void setUseCenterCircle(boolean useCenterCircle) {
        this.useCenterCircle = useCenterCircle;
    }

    public NewCustomIndicator(Context context, float mode, boolean useCenterCircle) {
        super(context);
        this.mode = mode;
        this.useCenterCircle = useCenterCircle;
        updateIndicator();
    }

    @Override
    protected float getDefaultIndicatorWidth() {
        return dpTOpx(2.3f);
    }

    @Override
    public float getBottom() {
        return getCenterY() * mode;
    }

    @Override
    public void draw(Canvas canvas, float degree) {
        canvas.save();
        canvas.rotate(90f + degree, getCenterX(), getCenterY());

        if (useCenterCircle)
            canvas.drawPath(circlePath, circlePaint);

        canvas.drawPath(indicatorPath, indicatorPaint);
        canvas.drawPath(circlePartPath, circlePartPaint);
        canvas.drawPath(thinLinePath, thinLinePaint);
        canvas.restore();
    }

    @Override
    protected void updateIndicator() {
        circlePath.reset();
        thinLinePath.reset();
        indicatorPath.reset();
        circlePartPath.reset();

        RectF oval = new RectF();

        oval.set(getCenterX() - dpTOpx(35), getCenterY() - dpTOpx(35), getCenterX() + dpTOpx(35), getCenterY() + dpTOpx(35));

        circlePath.addArc(oval, 0, 360);
        circlePartPath.addArc(oval, 235, 70);


        indicatorPath.moveTo(getCenterX(), getCenterX() - dpTOpx(35) - dpTOpx(10.5f));
        indicatorPath.lineTo(getCenterX(), getCenterY() - dpTOpx(35));

        thinLinePath.moveTo(getCenterX(), getPadding());
        thinLinePath.lineTo(getCenterX(), getCenterY() - dpTOpx(35));

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(getIndicatorWidth() / 2f);
        circlePaint.setColor(getIndicatorColor());

        circlePartPaint.setStyle(Paint.Style.STROKE);
        circlePartPaint.setStrokeWidth(getIndicatorWidth());
        circlePartPaint.setColor(Color.WHITE);

        thinLinePaint.setStyle(Paint.Style.STROKE);
        thinLinePaint.setStrokeWidth(getIndicatorWidth() / 1.35f);
        thinLinePaint.setColor(getIndicatorColor());

        indicatorPaint.setStyle(Paint.Style.STROKE);
        indicatorPaint.setStrokeWidth(getIndicatorWidth());
        indicatorPaint.setColor(Color.WHITE);
    }

    @Override
    protected void setWithEffects(boolean withEffects) {
        if (withEffects && !isInEditMode()) {
            indicatorPaint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.SOLID));
        } else {
            indicatorPaint.setMaskFilter(null);
        }
    }
}
