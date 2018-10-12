package com.example.ash.appackers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmergencyActivity extends AppCompatActivity {

    private Button buttonCallPolice, buttonEmergencyContact, buttonManageEmergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        buttonCallPolice = findViewById(R.id.buttonCallPolice);
        buttonEmergencyContact = findViewById(R.id.buttonEmergencyContact);
        buttonManageEmergency = findViewById(R.id.buttonManageEmergency);

        buttonManageEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emergencyContactIntent = new Intent(getApplicationContext(), EmergencyContactActivity.class);
                EmergencyActivity.this.startActivity(emergencyContactIntent);
            }
        });

    }
}
