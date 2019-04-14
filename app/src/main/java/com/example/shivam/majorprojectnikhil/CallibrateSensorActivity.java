package com.example.shivam.majorprojectnikhil;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.renderscript.Double4;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CallibrateSensorActivity extends AppCompatActivity {
TextView temp,humidity,soil;
Button activate, relay;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;
    private void displayBig(String eventInfo ){
        String message=eventInfo;
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("My Notification");
        builder.setContentText(eventInfo);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));


        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(2,builder.build());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callibrate_sensor);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        temp =  (TextView)findViewById(R.id.temperature_text_view);
        humidity = (TextView)findViewById(R.id.humidity_text_view);
        soil  = (TextView)findViewById(R.id.soil_moisture_text_view);
        activate = (Button)findViewById(R.id.activate_btn);
        relay = (Button)findViewById(R.id.relay_btn);


        relay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("user").child("water").setValue(true);
                databaseReference.child("user").child("water").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue(Boolean.class)){

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        temp.setText("Temp  : "+dataSnapshot.child("Temp").getValue(Double.class));
                        humidity.setText("Humidity : "+String.valueOf(dataSnapshot.child("Humidty").getValue(Double.class)));
                        soil.setText("Soil Moisture : "+dataSnapshot.child("soil_mois").getValue(Double.class));
                        if (dataSnapshot.child("fill_water").getValue(Boolean.class) && dataSnapshot.child("soil_mois").getValue(Float.class)<75){
                            String message  = "water level is low, the tank is filling";
                            displayBig(message.toUpperCase());
                        }else {
                            String message = "water level is fine, you can go on to water the plants";
                            displayBig(message.toUpperCase());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(CallibrateSensorActivity.this,"Unable to Connect to Database",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }
}
