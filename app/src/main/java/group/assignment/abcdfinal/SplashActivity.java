    package group.assignment.abcdfinal;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

    public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseUser user = auth.getCurrentUser();

        new Handler().postDelayed(() -> {
            if (user == null) {
                startActivity(new Intent(SplashActivity.this, ReplacerActivity.class));

            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }
            finish();
        }, 2500);
    }
}