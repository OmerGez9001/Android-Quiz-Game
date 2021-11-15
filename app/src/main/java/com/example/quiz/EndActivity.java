package com.example.quiz;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EndActivity extends AppCompatActivity {

    //for results
    TextView tvResults, tvName;

    //for expandable
    TextView share, manage;
    LinearLayout layoutManage, layoutShare;
    CardView cardManage, cardShare;

    //for buttons
    Button setDate, setAlarmClock, saveResult;
    Button shareSMS, shareEmail;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        //for results
        tvResults = findViewById(R.id.result);
        tvName = findViewById(R.id.name_of_player);

        //for expandable
        share = findViewById(R.id.share);
        manage = findViewById(R.id.manage);
        layoutManage = findViewById(R.id.manage_layout);
        layoutShare = findViewById(R.id.share_layout);
        cardManage = findViewById(R.id.expandable_card);
        cardShare = findViewById(R.id.expandable_card_2);

        //for buttons
        setDate = findViewById(R.id.date);
        setAlarmClock = findViewById(R.id.alarm);
        saveResult = findViewById(R.id.save);
        shareSMS = findViewById(R.id.sms);
        shareEmail = findViewById(R.id.email);

        //set results and name
        tvName.setText("Congrats " + getIntent().getStringExtra("Name") + "! You finished the quiz.");
        tvResults.setText("Correct: " + getIntent().getIntExtra("Correct", 0)
                +
                "\nWrong: " + getIntent().getIntExtra("Wrong",0));

        //expandable
        manage.setOnClickListener((v) -> {
            if (layoutManage.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cardManage, new AutoTransition());
                layoutManage.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(cardManage, new AutoTransition());
                layoutManage.setVisibility(View.GONE);
            }
        });
        share.setOnClickListener((v) -> {
            if (layoutShare.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cardShare, new AutoTransition());
                layoutShare.setVisibility(View.VISIBLE);
            } else {
                TransitionManager.beginDelayedTransition(cardShare, new AutoTransition());
                layoutShare.setVisibility(View.GONE);
            }
        });


        //buttons
        setDate.setOnClickListener((v) -> {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setData(CalendarContract.Events.CONTENT_URI);
            intent.putExtra(CalendarContract.Events.TITLE, "Quiz Game Reminder");
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "Time's up! Are you ready to play?");
            intent.putExtra(CalendarContract.Events.ALL_DAY, true);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No app supports the calender", Toast.LENGTH_SHORT).show();
            }
        });
        setAlarmClock.setOnClickListener(v -> startActivity(new Intent(this, AlarmClockActivity.class)));
        saveResult.setOnClickListener((v) -> createAndSaveFile());
        shareSMS.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"));
            intent.putExtra("sms_body","Hey! Check out my results on the the quiz: \n"
                    +"Correct: " + getIntent().getIntExtra("Correct", 0)
                    +
                    "\nWrong: " + getIntent().getIntExtra("Wrong",0));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "There is not supported app for this action", Toast.LENGTH_SHORT).show();
            }
        });
        shareEmail.setOnClickListener(v->startActivity(new Intent(this, EmailActivity.class)));

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createAndSaveFile() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, "Quiz Results - " + getIntent().getStringExtra("Name"));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    assert data != null;
                    Uri uri = data.getData();
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);
                    outputStream.write(("Date: "+date+"\n" +"Correct: " + getIntent().getIntExtra("Correct", 0)
                            +
                            "\nWrong: " + getIntent().getIntExtra("Wrong",0)).getBytes());
                    outputStream.close();
                    Toast.makeText(this, "File is saved", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(this, "Failed to save file", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to save file", Toast.LENGTH_SHORT).show();
            }

        }
    }
}