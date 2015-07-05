package haq.haq;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity {
    private RecyclerView questionsRecyclerView;
    private RecyclerView.Adapter questionsAdapter;
    private RecyclerView.LayoutManager questionsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Dummy data.
        AnsweredQuestion question1 = new AnsweredQuestion();
        question1.questionAsked = "If you were a food item, though.";
        question1.answerGiven = "Burrito.";
        AnsweredQuestion question2 = new AnsweredQuestion();
        question1.questionAsked = "If you were an alcohol, though.";
        question1.answerGiven = "Maker's Mark.";

        List<AnsweredQuestion> questionsAnsweredByUser = new ArrayList<AnsweredQuestion>();
        questionsAnsweredByUser.add(question1);
        questionsAnsweredByUser.add(question2);

        // Set up card view list for answered questions.
        questionsRecyclerView = (RecyclerView) findViewById(R.id.questionsList);
        questionsRecyclerView.setHasFixedSize(true);
        questionsLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(questionsLayoutManager);
        questionsAdapter = new QuestionsAdapter(questionsAnsweredByUser);
        questionsRecyclerView.setAdapter(questionsAdapter);
    }
}
