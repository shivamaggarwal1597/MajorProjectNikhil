package com.example.shivam.majorprojectnikhil;

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
                databaseReference.child("nikhil").child("water").setValue(true);
                databaseReference.child("nikhil").child("water").addListenerForSingleValueEvent(new ValueEventListener() {
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
                databaseReference.child("nikhil").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        temp.setText("Temp  : "+dataSnapshot.child("temp").getValue(String.class));
                        humidity.setText("Humidity : "+dataSnapshot.child("humidity").getValue(String.class));
                        soil.setText("Soil Moisture : "+dataSnapshot.child("soil").getValue(String.class));
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
