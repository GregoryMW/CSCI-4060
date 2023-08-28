package edu.uga.cs.roommateshopping;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A {@link DialogFragment} subclass that represents the settle cost dialog.
 */
public class SettleCostDialogFragment extends DialogFragment {

    private EditText roommates;
    private TextView costPerRoommate;
    private Button calculate;
    private Button clear;

    /**
     * Obtains the Dialog interface to settle the cost of a purchase.
     */
    public interface SettleCostDialogListener {
        double settleCost(double numRoom);
    }

    /**
     * Creates and sets the variables of the Dialog for the settle cost function.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data of the dialog.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create AlertDialog View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.settle_cost_dialog, getActivity().findViewById(R.id.rootSettleCost));

        roommates = layout.findViewById(R.id.numOfRoommates);
        calculate = layout.findViewById(R.id.settleCost);
        costPerRoommate = layout.findViewById(R.id.textView3);
        clear = layout.findViewById(R.id.clearPurchased);

        // Create New AlertDialog and Set View
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setView(layout);
        builder.setTitle("Settle the Cost");

        calculate.setOnClickListener(new SettleCostListener());
        clear.setOnClickListener(new ClearPurchasedItems());

        return builder.create();
    }

    /**
     * Inner class that implements the functionality of allowing a user to
     * settle the cost of a purchase through the the Dialog.
     */
    private class SettleCostListener implements View.OnClickListener {
        /**
         * Implements the functionality of settling the cost of a purchase
         * through the dialog.
         * The user is prompted to enter the number of roommates in order to
         * calculate the amount each roommate owes.
         * @param view Reference to a View object that represents the view of the dialog.
         */
        @Override
        public void onClick(View view) {
            if (roommates.getText().toString().trim().isEmpty()) {
                roommates.setError("You Must Enter a Valid Number of Roommates");
            }
            else {
                double numRoommates = Double.parseDouble(roommates.getText().toString());
                SettleCostDialogListener listener = (SettleCostDialogListener) getActivity();

                double costPer = listener.settleCost(numRoommates);
                costPerRoommate.setText("$" + costPer);
            }
        }
    }

    /**
     * Inner class that implements the functionality of allowing a user to
     * clear the purchased items.
     */
    private class ClearPurchasedItems implements View.OnClickListener {
        /**
         * Implements the functionality of clearing the purchased items
         * after the cost has been settled.
         * @param view Reference to a View object that represents the view of the dialog.
         */
        @Override
        public void onClick(View view) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Purchased");
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                /**
                 * Updates and clears the purchase list once the user clicks
                 * on the clear button.
                 * @param snapshot Reference to a DataSnapshot that represents the snapshot of data.
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        postSnapshot.getRef().removeValue();
                    }
                }

                /**
                 * Displays an error message if there was an issue clearing the purchase list.
                 * @param error Reference to a DatabaseError object that represents the error.
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Toast.makeText(getContext(), "Cleared Purchased List", Toast.LENGTH_SHORT).show();
            dismiss();}
    }
}