package ui.activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import ui.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(new Intent(this, HomeActivity.class))
                .addNextIntent(new Intent(this, LoginIntroActivity.class))
                .startActivities();
    }
}
