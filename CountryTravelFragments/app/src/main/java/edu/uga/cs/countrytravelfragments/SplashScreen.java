package edu.uga.cs.countrytravelfragments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class SplashScreen extends AppCompatActivity {

    private Button startButton;
    private TextView splashText;
    InputStream input;
    String txt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        startSplash();
    }

    public void openMainActivity()
    {
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void startSplash()
    {
        startButton = findViewById(R.id.splashButton);
        splashText = findViewById(R.id.splashText);

        input = getResources().openRawResource(R.raw.splash_text);
        try {
            byte[] buffer = new byte[input.available()];
            while (input.read(buffer) != -1)
                txt = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        splashText.setText(txt);

        startButton.setOnClickListener((view ->
        {
            openMainActivity();
        }));
    }
}