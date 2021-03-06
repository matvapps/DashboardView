package com.github.matvapps.dashboardview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.github.matvapps.dashboarddevices.R;
import com.github.matvapps.dashboardview.components.Indicators.NoIndicator;


public class DashboardTachometer extends Tachometer {

    private Path smallMarkPath = new Path(),
            middleMarkPath = new Path(),
            bigMarkPath = new Path();
    private Paint speedometerPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            speedometerTopArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            speedometerLightArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            pointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            pointerBackPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            bigMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            middleMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG),
            smallMarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF speedometerRect = new RectF(),
            speedometerTopArc = new RectF(),
            speedometerLightArc = new RectF();


    private int speedometerColor = Color.parseColor("#2BBDEC"), pointerColor = 0x00FFFFFF;

    private boolean withPointer = true;

    public DashboardTachometer(Context context) {
        this(context, null);
    }

    public DashboardTachometer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardTachometer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttributeSet(context, attrs);
    }

    @Override
    protected void defaultGaugeValues() {
        super.setTextColor(0xFFFFFFFF);
        super.setValueTextColor(0xFFFFFFFF);
        super.setUnitTextColor(0xFFFFFFFF);
        super.setValueTextSize(dpTOpx(20f));
        super.setUnitTextSize(dpTOpx(15f));
        super.setValueTextFormat((byte) 0);
        super.setValueTextPosition(Position.CENTER);
        super.setSpeedTextTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    @Override
    protected void defaultSpeedometerValues() {
        super.setIndicator(new NoIndicator(getContext())
                .setIndicatorWidth(dpTOpx(48f))
                .setIndicatorColor(0x36ffffff));

        super.setTextSize(dpTOpx(14f));
        super.setBackgroundCircleColor(0x00FFFFFF);
        super.setSpeedometerWidth(dpTOpx(26.5f));
        super.setMaxValue(7010);
    }

    private void init() {
        speedometerPaint.setStyle(Paint.Style.STROKE);
        speedometerTopArcPaint.setStyle(Paint.Style.STROKE);
        speedometerLightArcPaint.setStyle(Paint.Style.STROKE);

//        speedometerPaint.setStrokeCap(Paint.Cap.ROUND);

        bigMarkPaint.setStyle(Paint.Style.STROKE);
        bigMarkPaint.setStrokeCap(Paint.Cap.ROUND);
        bigMarkPaint.setStrokeWidth(dpTOpx(1.3f));

        middleMarkPaint.setStyle(Paint.Style.STROKE);
        middleMarkPaint.setStrokeCap(Paint.Cap.ROUND);
        middleMarkPaint.setStrokeWidth(dpTOpx(0.8f));

        smallMarkPaint.setStyle(Paint.Style.STROKE);
        smallMarkPaint.setStrokeCap(Paint.Cap.ROUND);
        smallMarkPaint.setStrokeWidth(dpTOpx(0.7f));


        circlePaint.setColor(0xFFFFFFFF);
    }

    private void initAttributeSet(Context context, AttributeSet attrs) {
        if (attrs == null) {
            initAttributeValue();
            return;
        }
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DashboardSpeedometer, 0, 0);

        speedometerColor = a.getColor(R.styleable.DashboardSpeedometer_dd_deviceColor, speedometerColor);
        pointerColor = a.getColor(R.styleable.DashboardSpeedometer_dd_pointerColor, pointerColor);
        circlePaint.setColor(a.getColor(R.styleable.DashboardSpeedometer_dd_centerCircleColor, circlePaint.getColor()));
        withPointer = a.getBoolean(R.styleable.DashboardSpeedometer_dd_withPointer, withPointer);
//        super.setTextSize(a.getDimension(R.styleable.Device_dd_markTextSize, dpTOpx(11f)));
        a.recycle();
        initAttributeValue();
    }

    private void initAttributeValue() {
        pointerPaint.setColor(pointerColor);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);

        float risk = getRingWidth() * 2.45f + getPadding();
        speedometerRect.set(risk, risk, getSize() - risk, getSize() - risk);

        float risk2 = getRingWidth() * 0.32f;
        speedometerTopArc.set(risk2, risk2, getSize() - risk2, getSize() - risk2);

        float risk3 = getRingWidth() * 1.38f;
        speedometerLightArc.set(risk3, risk3, getSize() - risk3, getSize() - risk3);

        updateRadial();
        updateBackgroundBitmap();
    }

    private void initDraw() {
        speedometerPaint.setStrokeWidth(getRingWidth());
        speedometerPaint.setShader(updateSweep());

        speedometerTopArcPaint.setStrokeWidth(getRingWidth() * 0.35f);
        speedometerTopArcPaint.setShader(updateSweepForTopArc());

        speedometerLightArcPaint.setStrokeWidth(getRingWidth() * 2.85f);
        speedometerLightArcPaint.setShader(updateSweepForWhiteArc());

        smallMarkPaint.setColor(Color.parseColor("#87FFFFFF"));
        middleMarkPaint.setColor(Color.parseColor("#2BBDEC"));
        bigMarkPaint.setColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDraw();

        canvas.drawArc(speedometerRect, getStartDegree(), getEndDegree() - getStartDegree(), false, speedometerPaint);
        canvas.drawArc(speedometerTopArc, getStartDegree(), getEndDegree() - getStartDegree(), false, speedometerTopArcPaint);
        canvas.drawArc(speedometerLightArc, getStartDegree() - 1f, getEndDegree() - getStartDegree(), false, speedometerLightArcPaint);

//        if (withPointer) {
//            canvas.save();
//            canvas.rotate(90 + getDegree(), getSize() * .5f, getSize() * .5f);
//            canvas.drawCircle(getSize() * .5f, getRingWidth() * .5f + dpTOpx(8) + getPadding()
//                    , getRingWidth() * .5f + dpTOpx(8), pointerBackPaint);
//            canvas.drawCircle(getSize() * .5f, getRingWidth() * .5f + dpTOpx(8) + getPadding()
//                    , getRingWidth() * .5f + dpTOpx(1), pointerPaint);
//            canvas.restore();
//        }

        drawSpeedUnitText(canvas);
        drawIndicator(canvas);

//        int c = getCenterCircleColor();
//        circlePaint.setColor(Color.argb(120, Color.red(c), Color.green(c), Color.blue(c)));
//        canvas.drawCircle(getSize() *.5f, getSize() *.5f, getWidthPa()/14f, circlePaint);
//        circlePaint.setColor(c);
//        canvas.drawCircle(getSize() *.5f, getSize() *.5f, getWidthPa()/22f, circlePaint);

        drawNotes(canvas);
    }

    @Override
    protected void updateBackgroundBitmap() {
        Canvas c = createBackgroundBitmapCanvas();
        initDraw();

        smallMarkPath.reset();
        smallMarkPath.moveTo(getSize() * 0.5f, getPadding());
        smallMarkPath.lineTo(getSize() * 0.5f, getPadding() + getSize() / 50);

        middleMarkPath.reset();
        middleMarkPath.moveTo(getSize() * 0.5f, getPadding());
        middleMarkPath.lineTo(getSize() * 0.5f, getPadding() + getSize() / 30);

        bigMarkPath.reset();
        bigMarkPath.moveTo(getSize() * 0.5f, getPadding() + dpTOpx(40));
        bigMarkPath.lineTo(getSize() * 0.5f, getPadding() + getSize() / 33f + dpTOpx(40));


        c.save();
        c.rotate(90f + getStartDegree(), getSize() * .5f, getSize() * .5f);

        float everyTick = (getEndDegree() - getStartDegree()) / (getTickNumber() - 1);
        float everyDegree = (getEndDegree() - getStartDegree()) / ((getTickNumber() - 1) * 17);

        for (int i = getStartDegree(); i < getEndDegree(); i += everyTick) {
            if (i != getStartDegree())
                c.rotate(everyTick, getSize() * .5f, getSize() * .5f);

            c.drawPath(bigMarkPath, bigMarkPaint);
        }

        c.rotate(-(getEndDegree() - getStartDegree()), getSize() * .5f, getSize() * .5f);

        for (int i = getStartDegree(); i < getEndDegree() + everyDegree; i += everyDegree) {

            c.rotate(everyDegree, getSize() * .5f, getSize() * .5f);

            c.drawPath(smallMarkPath, smallMarkPaint);
        }


        c.restore();

        if (getTickNumber() > 0)
            drawTicks(c);
        else
            drawDefMinMaxSpeedPosition(c);
    }

    private SweepGradient updateSweepForTopArc() {
        int startColor = Color.argb(110, Color.red(speedometerColor), Color.green(speedometerColor), Color.blue(speedometerColor));
        int color2 = Color.argb(220, Color.red(speedometerColor), Color.green(speedometerColor), Color.blue(speedometerColor));
        int inactiveColorStart = Color.argb(40, Color.red(Color.BLACK), Color.green(Color.BLACK), Color.blue(Color.BLACK));
        int inactiveColorEnd = Color.argb(40, Color.red(Color.BLACK), Color.green(Color.BLACK), Color.blue(Color.BLACK));
        float position = getOffsetSpeed() * (getEndDegree() - getStartDegree()) / 360f;
        SweepGradient sweepGradient = new SweepGradient(getSize() * .5f, getSize() * .5f
                , new int[]{startColor, color2, speedometerColor, inactiveColorStart, inactiveColorEnd, startColor}
                , new float[]{0f, position * .5f, position, position, .99f, 1f});

        Matrix matrix = new Matrix();
        matrix.postRotate(getStartDegree(), getSize() * .5f, getSize() * .5f);
        sweepGradient.setLocalMatrix(matrix);
        return sweepGradient;
    }

    private SweepGradient updateSweepForWhiteArc() {
        int startColor = Color.argb(0, Color.red(Color.TRANSPARENT), Color.green(Color.TRANSPARENT), Color.blue(Color.TRANSPARENT));
        int color2 = Color.argb(12, Color.red(Color.WHITE), Color.green(Color.WHITE), Color.blue(Color.WHITE));
        int inactiveColorStart = Color.argb(0, Color.red(Color.WHITE), Color.green(Color.WHITE), Color.blue(Color.WHITE));
        int inactiveColorEnd = Color.argb(0, Color.red(Color.WHITE), Color.green(Color.WHITE), Color.blue(Color.WHITE));
        float position = getOffsetSpeed() * (getEndDegree() - getStartDegree()) / 360f;
        SweepGradient sweepGradient = new SweepGradient(getSize() * .5f, getSize() * .5f
                , new int[]{color2, color2, color2, inactiveColorStart, inactiveColorEnd, color2}
                , new float[]{0f, position * .5f, position, position, .99f, 1f});

        Matrix matrix = new Matrix();
        matrix.postRotate(getStartDegree(), getSize() * .5f, getSize() * .5f);
        sweepGradient.setLocalMatrix(matrix);
        return sweepGradient;
    }


    private SweepGradient updateSweep() {
        int startColor = Color.argb(110, Color.red(speedometerColor), Color.green(speedometerColor), Color.blue(speedometerColor));
        int color2 = Color.argb(220, Color.red(speedometerColor), Color.green(speedometerColor), Color.blue(speedometerColor));
        int inactiveColorStart = Color.argb(40, Color.red(Color.BLACK), Color.green(Color.BLACK), Color.blue(Color.BLACK));
        int inactiveColorEnd = Color.argb(40, Color.red(Color.BLACK), Color.green(Color.BLACK), Color.blue(Color.BLACK));
        float position = getOffsetSpeed() * (getEndDegree() - getStartDegree()) / 360f;
        SweepGradient sweepGradient = new SweepGradient(getSize() * .5f, getSize() * .5f
                , new int[]{startColor, color2, speedometerColor, inactiveColorStart, inactiveColorEnd, startColor}
                , new float[]{0f, position * .5f, position, position, .99f, 1f});

        Matrix matrix = new Matrix();
        matrix.postRotate(getStartDegree(), getSize() * .5f, getSize() * .5f);
        sweepGradient.setLocalMatrix(matrix);
        return sweepGradient;
    }

    private void updateRadial() {
        int centerColor = Color.argb(160, Color.red(pointerColor), Color.green(pointerColor), Color.blue(pointerColor));
        int edgeColor = Color.argb(10, Color.red(pointerColor), Color.green(pointerColor), Color.blue(pointerColor));
        RadialGradient pointerGradient = new RadialGradient(getSize() * .5f, getRingWidth() * .5f + dpTOpx(8) + getPadding()
                , getRingWidth() * .5f + dpTOpx(8), new int[]{centerColor, edgeColor}
                , new float[]{.4f, 1f}, Shader.TileMode.CLAMP);
        pointerBackPaint.setShader(pointerGradient);
    }

    public int getSpeedometerColor() {
        return speedometerColor;
    }

    public void setSpeedometerColor(int speedometerColor) {
        this.speedometerColor = speedometerColor;
        invalidate();
    }

    public int getPointerColor() {
        return pointerColor;
    }

    public void setPointerColor(int pointerColor) {
        this.pointerColor = pointerColor;
        pointerPaint.setColor(pointerColor);
        updateRadial();
        invalidate();
    }

    public int getCenterCircleColor() {
        return circlePaint.getColor();
    }

    /**
     * change the color of the center circle (if exist),
     * <b>this option is not available for all Speedometers</b>.
     *
     * @param centerCircleColor new color.
     */
    public void setCenterCircleColor(int centerCircleColor) {
        circlePaint.setColor(centerCircleColor);
        if (!isAttachedToWindow())
            return;
        invalidate();
    }

    public boolean isWithPointer() {
        return withPointer;
    }

    /**
     * enable to draw circle pointer on speedometer arc.<br>
     * this will not make any change for the Indicator.
     *
     * @param withPointer true: draw the pointer,
     *                    false: don't draw the pointer.
     */
    public void setWithPointer(boolean withPointer) {
        this.withPointer = withPointer;
        if (!isAttachedToWindow())
            return;
        invalidate();
    }

    /**
     * this Speedometer doesn't use this method.
     *
     * @return {@code Color.TRANSPARENT} always.
     */
    @Deprecated
    @Override
    public int getLowSpeedColor() {
        return 0;
    }

    /**
     * this Speedometer doesn't use this method.
     *
     * @param lowSpeedColor nothing.
     */
    @Deprecated
    @Override
    public void setLowSpeedColor(int lowSpeedColor) {
    }

    /**
     * this Speedometer doesn't use this method.
     *
     * @return {@code Color.TRANSPARENT} always.
     */
    @Deprecated
    @Override
    public int getMediumSpeedColor() {
        return 0;
    }

    /**
     * this Speedometer doesn't use this method.
     *
     * @param mediumSpeedColor nothing.
     */
    @Deprecated
    @Override
    public void setMediumSpeedColor(int mediumSpeedColor) {
    }

    /**
     * this Speedometer doesn't use this method.
     *
     * @return {@code Color.TRANSPARENT} always.
     */
    @Deprecated
    @Override
    public int getHighSpeedColor() {
        return 0;
    }

    /**
     * this Speedometer doesn't use this method.
     *
     * @param highSpeedColor nothing.
     */
    @Deprecated
    @Override
    public void setHighSpeedColor(int highSpeedColor) {
    }
}
