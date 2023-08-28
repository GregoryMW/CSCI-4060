package edu.uga.cs.countrytravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String COUNTRY = "edu.uga.cs.CountryTravel.COUNTRY";
    public static final int CANADA = 1;
    public static final int ARGENTINA = 2;
    public static final int SWITZERLAND = 3;
    public static final int ETHIOPIA = 4;
    public static final int JAPAN = 5;

    private Spinner spinner;
    private TextView select;
    private Button overview;
    private Button details;
    private int spinnerPos;
    private int countrySelect = CANADA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);
        select = findViewById(R.id.select);
        overview = findViewById(R.id.overview);
        details = findViewById(R.id.details);

        //Set which country is selected in spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(((String)parent.getItemAtPosition(pos)).equals("Canada"))
                    countrySelect = CANADA;
                else if(((String)parent.getItemAtPosition(pos)).equals("Argentina"))
                    countrySelect = ARGENTINA;
                else if(((String)parent.getItemAtPosition(pos)).equals("Switzerland"))
                    countrySelect = SWITZERLAND;
                else if(((String)parent.getItemAtPosition(pos)).equals("Ethiopia"))
                    countrySelect = ETHIOPIA;
                else if(((String)parent.getItemAtPosition(pos)).equals("Japan"))
                    countrySelect = JAPAN;
                spinnerPos = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //N/A
            }
        });

        //overview button listener
        overview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OverviewActivity.class);
                intent.putExtra(COUNTRY, countrySelect);
                view.getContext().startActivity(intent);
            }
        });

        //details button listener
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(COUNTRY, countrySelect);
                view.getContext().startActivity(intent);
            }
        });
    }
}