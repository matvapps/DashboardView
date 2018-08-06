# DashboardView

### Preview

<img src="/dashboard_preview.gif?raw=true" width="640px">


Easy in use android library for creating dashboard. With this library you
can add the speedometer or tachometer to your app.

## Usage

### Create speedometer

```xml
            <com.github.matvapps.dashboardview.DashboardSpeedometer
                android:id="@+id/dashboard_speedometer"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:dd_indicator="DashboardIndicator"
                app:dd_tickNumber="13" />
```

### Create tachometer

```xml
            <com.github.matvapps.dashboardview.DashboardTachometer
                android:id="@+id/dashboard_tachometer"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:dd_indicator="DashboardIndicatorWithCircle"
                app:dd_tickNumber="8"/>
```
  
### Change color of speedometer or tachometer
```xml
                app:dd_deviceColor="@color/colorDeviceGreen"
```

### Change indicator color of speedometer or tachometer:
```xml  
                app:dd_indicatorColor="#a1d50000"
```

### Use this for customize unit for your device:
```xml
                app:dd_unit="km/h"
                app:dd_unitTextSize="16sp"
```
or remove it:
```xml
                app:dd_use_unit="false"
```

### For adding tremble, it's add realism to your device use this:
```xml
                app:dd_withTremble="true"
                app:dd_trembleDegree="2"
                app:dd_trembleDuration="200"
```

### Also you can customize text color
for center text:
```xml
                app:dd_textColor="#1592ff"
```

for speed text:
```xml
                app:dd_speedTextColor="#1592ff"
```

### Indicator light
The shadow that follows the indicator (true by default)
```xml
                app:dd_withIndicatorLight="true"
```



#### In activity
```java
speedometer = (DashboardSpeedometer) findViewById(R.id.dashboard_speedometer);
tachometer = (DashboardTachometer) findViewById(R.id.dashboard_tachometer);
```

#### Move to value
```java
speedometer.moveTo(240);
```
Or with duration (in millis):

```java
speedometer.moveTo(240, 1300);
```


### Full activity code:
```java
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
```


### Download

Download via Gradle:

```gradle
implementation 'com.github.matvapps:DashboardView:0.0.1'
```
or Maven:
```xml
<dependency>
  <groupId>com.github.matvapps</groupId>
  <artifactId>DashboardView</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```



Take a look at the [sample project](sample) for more information.

### License 

```
Copyright 2017 github.com/matvapps

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


[sample]: <https://github.com/matvapps/DashboardView/tree/master/app>

