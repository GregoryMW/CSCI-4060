package edu.uga.cs.roommateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This class represents the main menu of the application after the user
 * has successfully logged in.
 */
public class MainMenu extends AppCompatActivity {

    private static final String SELECTED_ITEM_POSITION = "ItemPosition";
    private int mPosition;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String name;

    /**
     * Creates and sets the variables of the View for the activity.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button addButton = findViewById(R.id.button1);
        Button viewButton = findViewById(R.id.button2);
        Button purchaseButton = findViewById(R.id.button3);
        Button logoutButton = findViewById(R.id.button5);
        Button pastPurchases = findViewById(R.id.button4);

        addButton.setOnClickListener(new AddItemButtonClickListener());
        viewButton.setOnClickListener(new ViewItemsButtonClickListener());
        purchaseButton.setOnClickListener(new ViewPurchasedClickListener());
        logoutButton.setOnClickListener(new LogoutClickListener());
        pastPurchases.setOnClickListener(new ViewPastPurchasedClickListener());

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        name = user.getUid();

        final TextView showUser = findViewById(R.id.nameDisplay);

        reference.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            /**
             * Updates and displays the name of the user logged in.
             * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if (user != null) {
                    String name = user.getName();
                    showUser.setText(name);
                }
            }
            /**
             * Displays an error message if there was an issue validating the
             * user's name.
             * @param error Reference to a DatabaseError object that represents the error.
             */
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        showUser.setText(name);
    }

    /**
     * Inner class that implements the functionality of redirecting the user to
     * the add item screen.
     */
    private class AddItemButtonClickListener implements View.OnClickListener {
        /**
         * Redirects a user to the add item screen.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), AddItem.class);
            startActivity(intent);
        }
    }

    /**
     * Inner class that implements the functionality of redirecting the user
     * to the view items screen.
     */
    private class ViewItemsButtonClickListener implements View.OnClickListener {
        /**
         * Redirects a user to the view items screen.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ViewListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Inner class that implements the functionality of redirecting the user
     * to the view purchased items screen.
     */
    private class ViewPurchasedClickListener implements View.OnClickListener {
        /**
         * Redirects a user to the view purchased items screen.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ViewPurchased.class);
            startActivity(intent);
        }
    }

    /**
     * Inner class that implements the functionality of redirecting the user
     * to the view past complete purchases screen.
     */
    private class ViewPastPurchasedClickListener implements View.OnClickListener {
        /**
         * Redirects a user to the view past purchases screen.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ViewPastPurchased.class);
            startActivity(intent);
        }
    }

    /**
     * Inner class that implements the functionality logging a user out and
     * redirecting them back to the login screen.
     */
    private class LogoutClickListener implements View.OnClickListener {
        /**
         * Redirects a user to back to the login screen.
         * @param view Reference to a View object that represents the current view.
         */
        @Override
        public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainMenu.this, MainActivity.class));
        }
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