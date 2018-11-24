package com.example.alexislebreton.applicationebay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SinscrireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinscrire);

        final Button sinscrire_btn_sinscrire = (Button)findViewById(R.id.sinscrire_btn_sinscrire);
        sinscrire_btn_sinscrire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();

            }
        });
    }
}
