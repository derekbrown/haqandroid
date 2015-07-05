package haq.haq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_haq);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void loginUser(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username_input);
        String usernameInput = usernameText.getText().toString();
        EditText passwordText = (EditText) findViewById(R.id.password_input);
        String passwordInput = passwordText.getText().toString();
        EditText emailText = (EditText) findViewById(R.id.email_input);
        String emailInput = emailText.getText().toString();
        ParseUser user = new ParseUser();
        user.setUsername(usernameInput);
        user.setPassword(passwordInput);
        user.setEmail(emailInput);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Intent intent = new Intent(LoginActivity.this, HaqActivity.class);
                    startActivity(intent);
                } else {
                    // Signup failed.
                }
            }
        });
    }

    public void loginFacebookUser(View view) {
        List<String> haqPermissions = Arrays.asList("public_profile", "user_friends", "email");
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, haqPermissions, new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser == null) {
                    Log.d("Haq", "User cancelled the Facebook login.");
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (parseUser.isNew()) {
                    Log.d("Haq", "User signed up and logged in through Facebook!");
                    Intent intent = new Intent(LoginActivity.this, HaqActivity.class);
                    startActivity(intent);
                } else {
                    Log.d("Haq", "User logged in through Facebook!");
                    Intent intent = new Intent(LoginActivity.this, HaqActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

//    AccessToken accessToken = AccessToken.getCurrentAccessToken();
//    GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//        @Override
//        public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
//            try {
//                Toast toast = Toast.makeText(HaqActivity.this, jsonObject.getString("name"), Toast.LENGTH_SHORT);
//                toast.show();
//            } catch (JSONException e) {}
//
//
//        }
//    });
//    Bundle parameters = new Bundle();
//    parameters.putString("fields", "id,name,link");
//    request.setParameters(parameters);
//    request.executeAsync();
}
