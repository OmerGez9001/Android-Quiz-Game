package com.example.quiz;


import androidx.appcompat.app.AppCompatActivity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmClockActivity extends AppCompatActivity {
    int currentHour, currentMinute;
    TextView timeHour;
    TextView timeMinute;
    TimePickerDialog timePickerDialog;
    Calendar calender;
    Button setTime, setClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);

        timeMinute = findViewById(R.id.minute_txt);
        timeHour = findViewById(R.id.hour_txt);
        setTime = findViewById(R.id.btn_set_time);
        setClock = findViewById(R.id.btn_set_alarm);

        setTime.setOnClickListener(v->{
            // initialized the Calendar in order to get current hour and time.
            calender = Calendar.getInstance();
            currentHour = calender.get(Calendar.HOUR_OF_DAY);
            currentMinute = calender.get(Calendar.MINUTE);

            timePickerDialog = new TimePickerDialog(AlarmClockActivity.this, (view, hourOfDay, minutes) -> {
                // in this method we set the time and hour inside our edittext
                timeHour.setText(String.format("%02d", hourOfDay));
                timeMinute.setText(String.format("%02d", minutes));
            }, currentHour, currentMinute, false);
            timePickerDialog.show();
        });
        setClock.setOnClickListener(v -> {
            // int cHour, cMinute;
            //  cHour = Integer.parseInt(timeHour.getText().toString());
            //  cMinute = Integer.parseInt(timeMinute.getText().toString());

            if(!timeHour.getText().toString().isEmpty() && !timeMinute.getText().toString().isEmpty()) {
                Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                intent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(timeHour.getText().toString()));
                intent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(timeMinute.getText().toString()));
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Alarm for playing quiz");
                // before start the activity we'll check if we have app that can open the action
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else
                    Toast.makeText(this, "There is not supported app for this action", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Please choose time", Toast.LENGTH_SHORT).show();
        });
    }
}