package edu.uga.cs.billofmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView amount;
    private EditText bill;
    private EditText diners;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        bill = findViewById(R.id.bill);
        diners = findViewById(R.id.diners);
        Button tip10 = findViewById(R.id.tip10);
        Button tip15 = findViewById(R.id.tip15);
        Button tip18 = findViewById(R.id.tip18);
        DecimalFormat df = new DecimalFormat("####0.00");

        //Tip on total amount
        //Checks to see if user has entered a positive integer and gives an error toast if they did not
        tip10.setOnClickListener((view) -> {
            if (Double.parseDouble(bill.getText().toString()) <= 0 || Double.parseDouble(diners.getText().toString()) <= 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "Enter positive values", Toast.LENGTH_SHORT);
                toast.show();
                amount.setText("$0.00");
            } else
                amount.setText("$" + df.format((Double.parseDouble(bill.getText().toString()) * 1.1 / Double.parseDouble(diners.getText().toString()))));
        });

        tip15.setOnClickListener((view) -> {
            if (Double.parseDouble(bill.getText().toString()) <= 0 || Double.parseDouble(diners.getText().toString()) <= 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "Enter positive values", Toast.LENGTH_SHORT);
                toast.show();
                amount.setText("$0.00");
            } else
                amount.setText("$" + df.format((Double.parseDouble(bill.getText().toString()) * 1.15 / Double.parseDouble(diners.getText().toString()))));
        });

        tip18.setOnClickListener((view) -> {
            if (Double.parseDouble(bill.getText().toString()) <= 0 || Double.parseDouble(diners.getText().toString()) <= 0) {
                Toast toast = Toast.makeText(getApplicationContext(), "Enter positive values", Toast.LENGTH_SHORT);
                toast.show();
                amount.setText("$0.00");
            } else
                amount.setText("$" + df.format((Double.parseDouble(bill.getText().toString()) * 1.18 / Double.parseDouble(diners.getText().toString()))));
        });
    }
}