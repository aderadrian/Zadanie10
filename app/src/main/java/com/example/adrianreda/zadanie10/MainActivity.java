package com.example.adrianreda.zadanie10;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager menedzer;
    private List<Sensor> listaCzujnik;
    private TextView lista_czujnikow;

    private ImageView iv;
    private TextView stopnie;
    private Button sensorActivateButton;
    private float obecnestopnie = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView) findViewById(R.id.imageView);
        stopnie = (TextView) findViewById(R.id.textView);
        lista_czujnikow = (TextView) findViewById(R.id.textView1);
        menedzer=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensorActivateButton = (Button)findViewById(R.id.button);



    }
    @Override
    protected void onPause() {
        super.onPause();
        menedzer.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        menedzer.registerListener(this, menedzer.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);


        listaCzujnik = menedzer.getSensorList(Sensor.TYPE_ALL);
        StringBuilder napis = new StringBuilder();
        napis.append("Lista czujnikow:\n");

        for (Sensor czujniki : listaCzujnik) {

            napis.append(czujniki.getName());
            napis.append(";\n");

        }
        lista_czujnikow.setText(napis);
    }








    @Override
    public void onSensorChanged(SensorEvent event) {
        float degree = Math.round(event.values[0]);
        stopnie.setText("Stopnie: " + Float.toString(degree));
        RotateAnimation rotateAnimation = new RotateAnimation(obecnestopnie, -degree, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(210);
        rotateAnimation.setFillAfter(true);

        iv.startAnimation(rotateAnimation);
        obecnestopnie = -degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
