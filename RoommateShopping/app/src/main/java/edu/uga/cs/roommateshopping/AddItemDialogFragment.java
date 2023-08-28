package edu.uga.cs.roommateshopping;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * A {@link DialogFragment} subclass that represents the add item dialog.
 */
public class AddItemDialogFragment extends DialogFragment {

    private EditText itemInput;
    private EditText quantityInput;
    private EditText priceInput;

    /**
     * Obtains the Dialog interface to add an item.
     */
    public interface AddItemDialogListener {
        void addItem(Item item);
    }

    /**
     * Creates and sets the variables of the Dialog for the add item function.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data of the dialog.
     * @return Returns the dialog to the user.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create AlertDialog View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_item_dialog, getActivity().findViewById(R.id.rootAdd));

        // Get Objects in the AlertDialog
        itemInput = layout.findViewById(R.id.item);
        quantityInput = layout.findViewById(R.id.quantity);
        priceInput = layout.findViewById(R.id.price);

        // Create New AlertDialog and Set View
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setView(layout);
        builder.setTitle("Add Item");

        // Negative Button Listener
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            /**
             * Implements the functionality of the user exiting the dialog
             * by clicking the cancel text.
             * @param dialog Reference to a Dialog Interface object that represents the current dialog.
             * @param temp Reference to a temporary variable.
             */
            @Override
            public void onClick(DialogInterface dialog, int temp) {
                dialog.dismiss();
            }
        });

        // Positive Button Listener
        builder.setPositiveButton(android.R.string.ok, new AddItemListener());

        return builder.create();
    }

    /**
     * Inner class that implements the functionality of allowing a user to
     * add an item through the the Dialog.
     */
    private class AddItemListener implements DialogInterface.OnClickListener {
        /**
         * Implements the functionality of adding an item to the shopping list
         * through the dialog.
         * From the dialog, the user is prompted to enter the item, quantity, and price.
         * @param dialog Reference to a View object that represents the current view.
         * @param temp Reference to a temporary variable.
         */
        @Override
        public void onClick(DialogInterface dialog, int temp) {
            String item = itemInput.getText().toString().trim();
            int quantity = Integer.parseInt(quantityInput.getText().toString());
            double price = Double.parseDouble(priceInput.getText().toString());

            // Create New Item Object
            Item item1 = new Item(item, quantity, price);

            // Get Activity's Listener to Add New Item
            AddItemDialogListener listener = (AddItemDialogListener) getActivity();

            // Add New Item
            listener.addItem(item1);

            dismiss();
        }
    }
}