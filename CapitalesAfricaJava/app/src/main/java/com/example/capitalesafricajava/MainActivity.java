package com.example.capitalesafricajava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Intent juego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button comenzar = (Button) findViewById(R.id.botonComenzar);
        DbHandler dbHandler = new DbHandler(MainActivity.this);
        dbHandler.deleteAll();


        comenzar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                juego = new Intent(MainActivity.this, Juego.class);
                startActivity(juego);
            }
        });
    }
}