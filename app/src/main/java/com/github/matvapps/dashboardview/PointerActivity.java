package com.github.matvapps.dashboardview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.matvapps.dashboarddevices.CustomSpeedometer;
import com.github.matvapps.dashboarddevices.CustomTachometer;

import java.util.Locale;

public class PointerActivity extends AppCompatActivity {

    CustomSpeedometer customSpeedometer;
    SeekBar seekBarSpeed;

    CustomTachometer customTachometer;
    SeekBar seekBarTurnovers;

    TextView textSpeed, textTurnovers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointer);

        customSpeedometer = findViewById(R.id.pointerSpeedometer);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);

        customTachometer = findViewById(R.id.pointerTachometer);
        seekBarTurnovers = findViewById(R.id.seekBarTurnovers);

        textSpeed = findViewById(R.id.textSpeed);
        textTurnovers = findViewById(R.id.textTurnovers);

        seekBarTurnovers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textTurnovers.setText(String.format(Locale.getDefault(), "%d", i));
                customTachometer.speedTo(seekBarTurnovers.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSpeed.setText(String.format(Locale.getDefault(), "%d", progress));
                customSpeedometer.speedTo(seekBarSpeed.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        pointerSpeedometer.setOnSpeedChangeListener(new OnSpeedChangeListener() {
//            @Override
//            public void onSpeedChange(Gauge gauge, boolean isSpeedUp, boolean isByTremble) {
//                textSpeedChange.setText(String.format(Locale.getDefault(), "onSpeedChange %d"
//                        , gauge.getCurrentIntSpeed()));
//            }
//        });
    }
}
