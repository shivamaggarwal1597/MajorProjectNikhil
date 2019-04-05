package com.example.shivam.majorprojectnikhil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigationActivity extends AppCompatActivity {
Button callibrate,forecast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        callibrate = (Button)findViewById(R.id.navigate_callibate_sensor_button);
        forecast = (Button)findViewById(R.id.navigate_weather_forecast_button);
        forecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationActivity.this,WeatherForecast.class);
                startActivity(intent);
            }
        });
        callibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NavigationActivity.this, CallibrateSensorActivity.class);
                startActivity(intent);
            }
        });
    }
}
