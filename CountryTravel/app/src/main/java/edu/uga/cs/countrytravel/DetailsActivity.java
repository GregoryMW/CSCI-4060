package edu.uga.cs.countrytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class DetailsActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageView countryFlag;
        TextView content;
        InputStream input;
        String txt = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        countryFlag = findViewById(R.id.countryFlagD);
        content = findViewById(R.id.detailsText);

        Intent intent = getIntent();
        int countrySelect = intent.getIntExtra(MainActivity.COUNTRY, MainActivity.CANADA);

        //Similar implementation as overview activity
        if (countrySelect == MainActivity.CANADA) {
            countryFlag.setImageResource(R.drawable.canada_flag);
            input = getResources().openRawResource(R.raw.details_canada);
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
            input = getResources().openRawResource(R.raw.details_argentina);
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
            input = getResources().openRawResource(R.raw.details_switzerland);
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
            input = getResources().openRawResource(R.raw.details_ethiopia);
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
            input = getResources().openRawResource(R.raw.details_japan);
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