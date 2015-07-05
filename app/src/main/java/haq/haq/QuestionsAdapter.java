package haq.haq;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder> {
    private List<AnsweredQuestion> questionsDataset;

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        protected TextView questionAskedView;
        protected TextView answerGivenView;
        public QuestionViewHolder(View v) {
            super(v);
            questionAskedView = (TextView) v.findViewById(R.id.question_asked);
            answerGivenView = (TextView) v.findViewById(R.id.answer_given);
        }
    }

    public QuestionsAdapter(List<AnsweredQuestion> questionsList) {
        this.questionsDataset = questionsList;
    }

    @Override
    public QuestionsAdapter.QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.questions_text_view, parent, false);
        return new QuestionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        AnsweredQuestion aq = questionsDataset.get(position);
        holder.questionAskedView.setText(aq.questionAsked);
        holder.answerGivenView.setText(aq.answerGiven);
    }

    @Override
    public int getItemCount() {
        return questionsDataset.size();
    }
}
