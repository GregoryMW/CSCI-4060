package edu.uga.cs.roommateshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class represents the registration screen of the application.
 */
public class Registration extends AppCompatActivity {

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button register;

    private FirebaseAuth mAuth;

    /**
     * Creates and sets the variables of the View for the activity.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        register = findViewById(R.id.registerButton);

        register.setOnClickListener(new ButtonClickListener());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Inner class that redirects the user to register.
     */
    public class ButtonClickListener implements View.OnClickListener {
        /**
         * Redirects the user to the registration screen.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            register();
        }
    }

    /**
     * Implements the registration functionality. The user is
     * prompted to enter a name, email, username, and password.
     */
    private void register() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate User Credentials
        if (name.isEmpty()) {
            nameEditText.setError("Required Field");
            nameEditText.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailEditText.setError("Required Field");
            emailEditText.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            usernameEditText.setError("Required Field");
            usernameEditText.requestFocus();
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

        // Create User Using Username and Password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        User user = new User(name, email, username, password);

                        // Add to Realtime Database
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(Registration.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(Registration.this, "Error: Registration Failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                    else {
                        Toast.makeText(Registration.this, "Error: Email is connected to an existing account.", Toast.LENGTH_LONG).show();
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
}