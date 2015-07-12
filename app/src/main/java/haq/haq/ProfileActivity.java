package haq.haq;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileActivity extends Activity {
    private RecyclerView questionsRecyclerView;
    private RecyclerView.Adapter questionsAdapter;
    private RecyclerView.LayoutManager questionsLayoutManager;
    List<AnsweredQuestion> questionsAnsweredByUser = new ArrayList<AnsweredQuestion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_profile);

        // Actual data.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("haqAnswer");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.selectKeys(Arrays.asList("haqKey","haqAnswer"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> answerList, ParseException e) {
                if (e == null) {
                    buildQuestions(answerList);
                    questionsAdapter.notifyDataSetChanged();
                } else {
                    AnsweredQuestion question = new AnsweredQuestion();
                    question.questionAsked = "Whoops";
                    question.answerGiven = e.getMessage();
                    questionsAnsweredByUser.add(question);
                    questionsAdapter.notifyDataSetChanged();
                }
            }
        });

        // Set up card view list for answered questions.
        questionsRecyclerView=(RecyclerView) findViewById(R.id.questionsList);
        questionsRecyclerView.setHasFixedSize(true);
        questionsLayoutManager=new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(questionsLayoutManager);
        questionsAdapter=new QuestionsAdapter(questionsAnsweredByUser);
        questionsRecyclerView.setAdapter(questionsAdapter);
    }

    public void buildQuestions(List<ParseObject> answerList){
        for (ParseObject answer : answerList) {
            final AnsweredQuestion question = new AnsweredQuestion();
            ParseQuery<ParseObject> questionQuery = ParseQuery.getQuery("haqQuestion");
            questionQuery.whereEqualTo("haqKey", answer.getString("haqKey"));
            questionQuery.selectKeys(Arrays.asList("questionText"));
            questionQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    for (ParseObject questionObj : list) {
                        question.questionAsked = questionObj.getString("questionText");
                    }
                    questionsAdapter.notifyDataSetChanged();
                }
            });
            question.answerGiven = answer.getString("haqAnswer");
            questionsAnsweredByUser.add(question);
        }
    }
}