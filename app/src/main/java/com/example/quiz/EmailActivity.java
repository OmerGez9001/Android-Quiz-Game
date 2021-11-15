package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {

    private EditText editTextToUser;
    private EditText editTextSubject;
    private EditText editTextMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        editTextToUser = findViewById(R.id.edit_text_to);
        editTextSubject = findViewById(R.id.edit_text_subject);
        editTextMessage = findViewById(R.id.edit_text_message);

        Button buttonSend = findViewById(R.id.button_send);

        editTextMessage.setText("Hey! Check out my results on the the quiz: \n"
                +"Correct: " + getIntent().getIntExtra("Correct", 0)
                +
                "\nWrong: " + getIntent().getIntExtra("Wrong",0));
        buttonSend.setOnClickListener(v -> sendEmail());
    }

    private void sendEmail() {
        //option to send email to more than one person
        String recipientList = editTextToUser.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = editTextSubject.getText().toString();
        String message = editTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));

        //   Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);    // A String[] holding e-mail addresses that should be delivered to.
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        // intent.setType("text/html");   // tell our app to open only email clients
        startActivity(Intent.createChooser(intent, "Choose which email app to use"));

    }

}