package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    Button startButton;
    EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_button);
        name = findViewById(R.id.name_edit);
        startButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, GameWindowActivity.class);
            intent.putExtra("Name", name.getText().toString());
            startActivity(intent);
        });
    }
}


