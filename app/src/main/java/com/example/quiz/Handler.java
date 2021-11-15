package com.example.quiz;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

public class Handler {
    public Handler(){}

    public void Load(String jsonStr, List<QuestionItem> QuestionItems){
        try{
            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray Q = jsonObj.getJSONArray("questions");
            for (int i = 0; i < Q.length(); i++){
                JSONObject question = Q.getJSONObject(i);

                String questionStr = question.getString("question");
                String a1Str = question.getString("answer1");
                String a2Str = question.getString("answer2");
                String a3Str = question.getString("answer3");
                String a4Str = question.getString("answer4");
                String correctStr = question.getString("correct");

                QuestionItems.add(new QuestionItem(
                        questionStr,
                        a1Str,
                        a2Str,
                        a3Str,
                        a4Str,
                        correctStr
                ));

            }

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

}

