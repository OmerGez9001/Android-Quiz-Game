package com.example.quiz;

import android.Manifest;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameWindowActivity extends AppCompatActivity {

    TextView tv_question;
    Button a1, a2, a3, a4;
    Button googleSearch, callHelp;
    EditText phoneText;
    Handler H;

    List<QuestionItem> QuestionItems;

    private static final int REQUEST_CALL = 1;
    int currentQuestion = 0;

    int correct = 0, wrong = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_window);

        tv_question = findViewById(R.id.question);
        a1 = findViewById(R.id.answer1);
        a2 = findViewById(R.id.answer2);
        a3 = findViewById(R.id.answer3);
        a4 = findViewById(R.id.answer4);
        googleSearch = findViewById(R.id.google_search);
        callHelp = findViewById(R.id.call_for_help);
        phoneText = findViewById(R.id.phone_number);

        Toast.makeText(GameWindowActivity.this,
                "Let's go "+getIntent().getStringExtra("Name")+"!",
                Toast.LENGTH_SHORT).show();

        //get all questions
        loadAllQuestions();
        //shuffle
        Collections.shuffle(QuestionItems);
        //get first question
        SetQuestionScreen(currentQuestion);

        a1.setOnClickListener(v -> {
            //check if answer1 is correct
            if(QuestionItems.get(currentQuestion).getAnswer1().equals(QuestionItems.get(currentQuestion).getCorrect())){
                //correct
                correct++;
                Toast.makeText(GameWindowActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                //wrong
                wrong++;
                Toast.makeText(GameWindowActivity.this, "Wrong! Correct answer: "
                        + QuestionItems.get(currentQuestion).getCorrect(), Toast.LENGTH_SHORT).show();
            }

            //load next question
            if (currentQuestion< QuestionItems.size()-1){
                currentQuestion++;
                SetQuestionScreen(currentQuestion);
            } else {
                GameOver();
            }
        });
        a2.setOnClickListener(v -> {
            //check if answer2 is correct
            if(QuestionItems.get(currentQuestion).getAnswer2().equals(QuestionItems.get(currentQuestion).getCorrect())){
                //correct
                correct++;
                Toast.makeText(GameWindowActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                //wrong
                wrong++;
                Toast.makeText(GameWindowActivity.this, "Wrong! Correct answer: "
                        + QuestionItems.get(currentQuestion).getCorrect(), Toast.LENGTH_SHORT).show();
            }

            //load next question
            if (currentQuestion< QuestionItems.size()-1){
                currentQuestion++;
                SetQuestionScreen(currentQuestion);
            } else {
                GameOver();
            }
        });
        a3.setOnClickListener(v -> {
            //check if answer3 is correct
            if(QuestionItems.get(currentQuestion).getAnswer3().equals(QuestionItems.get(currentQuestion).getCorrect())){
                //correct
                correct++;
                Toast.makeText(GameWindowActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                //wrong
                wrong++;
                Toast.makeText(GameWindowActivity.this, "Wrong! Correct answer: "
                        + QuestionItems.get(currentQuestion).getCorrect(), Toast.LENGTH_SHORT).show();
            }

            //load next question
            if (currentQuestion< QuestionItems.size()-1){
                currentQuestion++;
                SetQuestionScreen(currentQuestion);
            } else {
                GameOver();
            }
        });
        a4.setOnClickListener(v -> {
            //check if answer4 is correct
            if(QuestionItems.get(currentQuestion).getAnswer4().equals(QuestionItems.get(currentQuestion).getCorrect())){
                //correct
                correct++;
                Toast.makeText(GameWindowActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                //wrong
                wrong++;
                Toast.makeText(GameWindowActivity.this, "Wrong! Correct answer: "
                        + QuestionItems.get(currentQuestion).getCorrect(), Toast.LENGTH_SHORT).show();
            }

            //load next question
            if (currentQuestion< QuestionItems.size()-1){
                currentQuestion++;
                SetQuestionScreen(currentQuestion);
            } else {
                GameOver();
            }
        });

        googleSearch.setOnClickListener((v -> {
            String search = tv_question.getText().toString();
            searchInWeb(search);
        }));

        callHelp.setOnClickListener(v -> makePhoneCall());
    }


    //game over, moves to EndActivity.
    private void GameOver(){
        Intent intent = new Intent(this, EndActivity.class);
        intent.putExtra("Correct", correct);
        intent.putExtra("Wrong", wrong);
        intent.putExtra("Name", getIntent().getStringExtra("Name"));
        startActivity(intent);
    }

    //set questions to screen
    private void SetQuestionScreen(int num){
        tv_question.setText(QuestionItems.get(num).getQuestion());
        a1.setText(QuestionItems.get(num).getAnswer1());
        a2.setText(QuestionItems.get(num).getAnswer2());
        a3.setText(QuestionItems.get(num).getAnswer3());
        a4.setText(QuestionItems.get(num).getAnswer4());

    }

    //make list of all questions
    private void loadAllQuestions() {
        //initialize...
        QuestionItems = new ArrayList<>();
        H = new Handler();

        //load all questions into json string
        String jsonStr = loadJSONFromAssets("Questions1.json");

        //load all data into list
        H.Load(jsonStr, QuestionItems);
    }

    //load json file from assets
    private String loadJSONFromAssets(String file){
        String jsonStr = "";
        try{
            InputStream is = getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
        } catch (IOException e){
            e.printStackTrace();
        }
        return jsonStr;
    }


    private void searchInWeb(String question)
    {
        try{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY,question);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    private void makePhoneCall(){
        String number = phoneText.getText().toString();
        //check if number was type
        if (number.trim().length() > 0) {
            // check if we have permission
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else { // in case we get permission
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
