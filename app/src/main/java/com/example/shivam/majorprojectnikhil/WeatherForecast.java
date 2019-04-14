package com.example.shivam.majorprojectnikhil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import az.openweatherapi.OWService;
import az.openweatherapi.listener.OWRequestListener;
import az.openweatherapi.model.OWResponse;
import az.openweatherapi.model.gson.common.Coord;
import az.openweatherapi.model.gson.five_day.ExtendedWeather;
import az.openweatherapi.model.gson.five_day.WeatherForecastElement;
import az.openweatherapi.utils.OWSupportedUnits;

public class WeatherForecast extends AppCompatActivity {
    OWService mOWService;
    double lat_c,lon_c;
    TextView min_temp,max_temp;
    double rain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        mOWService = new OWService("f512468636ab7a42a5354384b04d1b6e");
        mOWService.setLanguage(Locale.ENGLISH);
        mOWService.setMetricUnits(OWSupportedUnits.METRIC);
        Coord cord  = new Coord() ;
        cord.setLat(28.59);

        cord.setLon(77.02);
        min_temp = (TextView)findViewById(R.id.min_temp);
        max_temp = (TextView)findViewById(R.id.max_temp);

        mOWService.getFiveDayForecast(cord, new OWRequestListener<ExtendedWeather>() {
            @Override
            public void onResponse(OWResponse<ExtendedWeather> owResponse) {
                double temp_min = owResponse.body().getList().get(2).getMain().getTempMin();
                double temp_max = owResponse.body().getList().get(2).getMain().getTempMax();
                for(WeatherForecastElement weatherForecastElement : owResponse.body().getList()){
                    weatherForecastElement.getMain().getHumidity();
                    String s = weatherForecastElement.getWeather().get(0).getDescription();
                    weatherForecastElement.getRain();


                }
                //Null value check
                if (owResponse.body().getList().get(3).getRain()!=null){
                rain =  owResponse.body().getList().get(3).getRain().get3h();
                }
                double humidity =  owResponse.body().getList().get(2).getMain().getHumidity();
                min_temp    .setText("Minimum Temp is  : "+ temp_min  + "  Rain : " + rain);
                max_temp.setText("Maximum Temp is :"+ temp_max+" Humidity "+ humidity);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

    }
}
