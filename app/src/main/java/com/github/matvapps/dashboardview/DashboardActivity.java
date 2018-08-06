package com.github.matvapps.dashboardview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private DashboardSpeedometer customSpeedometer;
    private SeekBar seekBarSpeed;

    private DashboardTachometer customTachometer;
    private SeekBar seekBarTurnovers;

    private TextView textSpeed, textTurnovers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

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
                customTachometer.moveTo(seekBarTurnovers.getProgress());
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
                customSpeedometer.moveTo(seekBarSpeed.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
