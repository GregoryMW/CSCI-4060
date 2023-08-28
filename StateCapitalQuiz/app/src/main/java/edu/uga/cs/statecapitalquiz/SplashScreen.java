package edu.uga.cs.statecapitalquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Represents the splash screen that appears when opening
 * the application.
 */
public class SplashScreen extends AppCompatActivity {
    /**
     * Creates instances and determines the duration of the
     * splash screen.
     * @param savedInstanceState saves the state of an instance.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Thread thread = new Thread() {
            /**
             * Runs the splash screen for 5000 millis.
             */
            @Override
            public void run() {
                try {
                    sleep(5000);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
