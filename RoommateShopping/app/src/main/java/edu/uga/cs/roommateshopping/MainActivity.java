package edu.uga.cs.roommateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class represents the login screen of the application.
 */
public class MainActivity extends AppCompatActivity {

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button login;
    private Button register;

    private FirebaseAuth mAuth;

    /**
     * Creates and sets the variables of the View for the activity.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        login.setOnClickListener(new ButtonClickListener());
        register.setOnClickListener(new ButtonClickListener());

        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Inner class that implements the functionality of allowing a user to
     * login or register through the buttons provided.
     */
    private class ButtonClickListener implements View.OnClickListener {
        /**
         * Directs the user to the proper activity based on the button
         * that was clicked.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
                case R.id.registerButton:
                    intent = new Intent(view.getContext(), Registration.class);
                    startActivity(intent);
                    break;
                case R.id.loginButton:
                    login();
            }
        }
    }

    /**
     * Allows a user to login based on their credentials.
     * The user must login using an email and password that was
     * given by them during the registration process.
     */
    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate User Credentials
        if (email.isEmpty()) {
            emailEditText.setError("Required Field");
            emailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Required Field");
            passwordEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid Email Format");
            emailEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password Must Be At Least 6 Characters");
            passwordEditText.requestFocus();
            return;
        }

        // Log In With Saved Credentials
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            /**
             * Redirects the user to the main menu screen if the credentials match.
             * @param task Reference to a Task object that represents whether a task was successful or not.
             */
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Proceed to Home Screen
                    startActivity(new Intent(MainActivity.this, MainMenu.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "Error: Invalid Email And/Or Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Saves the current state of the activity
     * @param outState The bundle that is saved
     */
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of item position
        outState.putInt(SELECTED_ITEM_POSITION, mPosition);
    }

    /**
     * Restores the state of the activity
     * @param savedInstanceState The saved state that is restored
     */
    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Read the state of item position
        mPosition = savedInstanceState.getInt(SELECTED_ITEM_POSITION);
    }

    /**
     * Moves current activity to back stack when pressing the back button
     */
    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}