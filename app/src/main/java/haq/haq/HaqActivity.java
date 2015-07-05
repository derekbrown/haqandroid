package haq.haq;

import android.app.Activity;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.ParseFacebookUtils;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class HaqActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.haq.haq.MESSAGE";
    public String haqKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haq);
        getHaqQuestion(getCurrentFocus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_haq, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getHaqQuestion(View view) {
        final TextView question = (TextView) findViewById(R.id.haq_question);
        Random rg = new Random();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("haqQuestion");
        query.setLimit(1);
        // The following needs to be dynamically set based on unanswered questions by user.
        query.setSkip(rg.nextInt(9));
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    String questionText = parseObject.getString("questionText");
                    haqKey = parseObject.getString("haqKey");
                    question.setText(questionText);
                } else {
                    String questionText = "There was an error.";
                    question.setText(questionText);
                }
            }
        });
    }

    public void sendMessage(View view) {
        ParseUser user = ParseUser.getCurrentUser();
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String answer = editText.getText().toString();
        ParseObject haqAnswerObject = new ParseObject("haqAnswer");
        haqAnswerObject.put("haqKey", haqKey);
        haqAnswerObject.put("haqAnswer", answer);
        haqAnswerObject.put("user", user);
        haqAnswerObject.saveInBackground();
        intent.putExtra(EXTRA_MESSAGE, answer);
        startActivity(intent);
    }

    public void logOut(View view) {
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void viewMyProfile(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
