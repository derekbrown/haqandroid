package haq.haq;

import android.app.Application;
import android.content.Intent;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "OCqBW8JbycHKmfDmEKTR6GKK0gjj32SQtRhvlgyP", "ZbAf5V8CCuMBYEUGVw53678reWUjDZ3QBlMn1azU");
        ParseFacebookUtils.initialize(getApplicationContext());
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(getApplicationContext(), HaqActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
