package com.github.matvapps.dashboarddevices.components.Indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.matvapps.dashboarddevices.Device;


@SuppressWarnings("unchecked,unused,WeakerAccess")
public abstract class Indicator<I extends Indicator> {

    protected Paint indicatorPaint =  new Paint(Paint.ANTI_ALIAS_FLAG);

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    private float density;
    private float indicatorWidth;
    private float viewSize;
    private float speedometerWidth;
    private int indicatorColor = 0xff2196F3;
    private int padding;
    private boolean inEditMode;

    protected Indicator (Context context) {
        this.density = context.getResources().getDisplayMetrics().density;
        init();
    }


    private void init() {
        indicatorPaint.setColor(indicatorColor);
        indicatorWidth = getDefaultIndicatorWidth();
    }

    public abstract void draw(Canvas canvas, float degree);
    /** called when size change or color, width */
    protected abstract void updateIndicator();
    /** if indicator have effects like BlurMaskFilter */
    protected abstract void setWithEffects(boolean withEffects);
    protected abstract float getDefaultIndicatorWidth();

    /** Top Y position of indicator */
    public float getTop(){
        return getPadding();
    }
    /** Bottom Y position of indicator */
    public float getBottom(){
        return getCenterY();
    }
    /** down point after center */
    public float getLightBottom() {
        return getCenterY() > getBottom() ? getBottom() : getCenterY();
    }

    /**
     * must call in {@code speedometer.onSizeChanged()}
     * @param device target speedometer.
     */
    public void onSizeChange(Device device) {
        setTargetSpeedometer(device);
    }

    /**
     * to change indicator's data,
     * this method called by the library.
     * @param device target speedometer.
     */
    public void setTargetSpeedometer(Device device) {
        updateData(device);
        updateIndicator();
    }

    private void updateData(Device device) {
        this.viewSize = device.getSize();
        this.speedometerWidth = device.getSpeedometerWidth();
        this.padding = device.getPadding();
        this.inEditMode = device.isInEditMode();
    }

    public float dpTOpx(float dp) {
        return dp * density;
    }

    public float getIndicatorWidth() {
        return indicatorWidth;
    }

    public I setIndicatorWidth(float indicatorWidth) {
        this.indicatorWidth = indicatorWidth;
        return (I) this;
    }

    /**
     * @return size of Speedometer View without padding.
     */
    public float getViewSize() {
        return viewSize - (padding*2f);
    }

    public int getIndicatorColor() {
        return indicatorColor;
    }

    public I setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        return (I) this;
    }

    /**
     * @return x center of speedometer.
     */
    public float getCenterX() {
        return viewSize /2f;
    }

    /**
     * @return y center of speedometer.
     */
    public float getCenterY() {
        return viewSize /2f;
    }

    public int getPadding() {
        return padding;
    }

    public float getSpeedometerWidth() {
        return speedometerWidth;
    }

    public void noticeIndicatorWidthChange(float indicatorWidth) {
        this.indicatorWidth = indicatorWidth;
        updateIndicator();
    }

    public void noticeIndicatorColorChange(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        updateIndicator();
    }

    public void noticeSpeedometerWidthChange(float speedometerWidth) {
        this.speedometerWidth = speedometerWidth;
        updateIndicator();
    }

    public void noticePaddingChange(int newPadding) {
        this.padding = newPadding;
        updateIndicator();
    }

    public void withEffects(boolean withEffects) {
        setWithEffects(withEffects);
        updateIndicator();
    }

    public boolean isInEditMode() {
        return inEditMode;
    }

    /** indicator's shape */
    public enum Indicators {
        NoIndicator, NormalIndicator, NormalSmallIndicator, TriangleIndicator
        , SpindleIndicator, NewCustomIndicator, HalfLineIndicator, QuarterLineIndicator
        , KiteIndicator, NeedleIndicator, NewCustomIndicatorWithCircle
    }

    /**
     * create new {@link Indicator} with default values.
     * @param context required.
     * @param indicator new indicator (Enum value).
     * @return new indicator object.
     */
    public static Indicator createIndicator (Context context, Indicators indicator) {
        switch (indicator) {
            case NoIndicator :
                return new NoIndicator(context);
            case NewCustomIndicator:
                return new NewCustomIndicator(context, NewCustomIndicator.LINE, false);
            case NewCustomIndicatorWithCircle:
                return new NewCustomIndicator(context, NewCustomIndicator.LINE, true);
            case HalfLineIndicator :
                return new NewCustomIndicator(context, NewCustomIndicator.HALF_LINE, false);
            case QuarterLineIndicator :
                return new NewCustomIndicator(context, NewCustomIndicator.QUARTER_LINE, false);
            default :
                return new NoIndicator(context);
        }
    }
}
