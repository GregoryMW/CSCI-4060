package edu.uga.cs.countrytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class OverviewActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView countryFlag;
        TextView content;
        InputStream input;
        String txt = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        countryFlag = findViewById(R.id.countryFlag);
        content = findViewById(R.id.overviewText);

        Intent intent = getIntent();
        int countrySelect = intent.getIntExtra(MainActivity.COUNTRY, MainActivity.CANADA);

        //Takes the chosen country and loads its appropriate flag and uses the input stream to read the appropriate text file
        if (countrySelect == MainActivity.CANADA) {
            countryFlag.setImageResource(R.drawable.canada_flag);
            input = getResources().openRawResource(R.raw.overview_canada);
            try {
                byte[] buffer = new byte[input.available()];
                while (input.read(buffer) != -1)
                    txt = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(txt);
        }
        else if (countrySelect == MainActivity.ARGENTINA) {
            countryFlag.setImageResource(R.drawable.argentina_flag);
            input = getResources().openRawResource(R.raw.overview_argentina);
            try {
                byte[] buffer = new byte[input.available()];
                while (input.read(buffer) != -1)
                    txt = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(txt);
        }
        else if (countrySelect == MainActivity.SWITZERLAND) {
            countryFlag.setImageResource(R.drawable.switzerland_flag);
            input = getResources().openRawResource(R.raw.overview_switzerland);
            try {
                byte[] buffer = new byte[input.available()];
                while (input.read(buffer) != -1)
                    txt = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(txt);
        }
        else if (countrySelect == MainActivity.ETHIOPIA) {
            countryFlag.setImageResource(R.drawable.ethiopia_flag);
            input = getResources().openRawResource(R.raw.overview_ethiopia);
            try {
                byte[] buffer = new byte[input.available()];
                while (input.read(buffer) != -1)
                    txt = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(txt);
        }
        else if (countrySelect == MainActivity.JAPAN) {
            countryFlag.setImageResource(R.drawable.japan_flag);
            input = getResources().openRawResource(R.raw.overview_japan);
            try {
                byte[] buffer = new byte[input.available()];
                while (input.read(buffer) != -1)
                    txt = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            content.setText(txt);
        }
    }
}