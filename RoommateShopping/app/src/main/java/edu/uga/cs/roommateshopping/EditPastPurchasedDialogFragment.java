package edu.uga.cs.roommateshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

/**
 * A {@link DialogFragment} subclass that represents the edit past purchased dialog.
 */
public class EditPastPurchasedDialogFragment extends DialogFragment {

    int position;
    String key;
    String date;
    int numItems;
    double costPer;

    /**
     * Obtains the Dialog interface to edit a past purchase.
     */
    public interface EditPastPurchasedDialogListener {
        void removePurchase(int position, PastPurchase purchase);
    }

    /**
     * Creates a new instance of each past purchase. The edit past purchase
     * dialog initializes the purchase's position, key, item, date, number of
     * items purchases, and the cost each roommate is to pay.
     * @param position Represents the position of the purchase in the list
     * @param key Represents the purchase's key in the database.
     * @param date Represents the date of the database.
     * @param numItems Represents the number of items in the purchase.
     * @param costPer Represents the total cost each roommate owed in the purchase.
     * @return Returns the edit past purchase dialog,
     */
    public static EditPastPurchasedDialogFragment newInstance(int position, String key, String date, int numItems, double costPer) {
        EditPastPurchasedDialogFragment dialog = new EditPastPurchasedDialogFragment();
        Bundle args = new Bundle();

        args.putString("key", key);
        args.putInt("position", position);
        args.putString("date", date);
        args.putInt("numItems", numItems);
        args.putDouble("costPer", costPer);

        dialog.setArguments(args);

        return dialog;
    }

    /**
     * Creates and sets the variables of the Dialog for the edit past purchase function.
     * @param savedInstanceState Reference to a Bundle object that represents the current state data of the dialog.
     * @return Returns the dialog to the user.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        key = getArguments().getString("key");
        position = getArguments().getInt("position");
        date = getArguments().getString("date");
        numItems = getArguments().getInt("numItems");
        costPer = getArguments().getDouble("costPer");

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.edit_past_purchased_dialog, getActivity().findViewById(R.id.rootPastPurchased));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setView(layout);
        builder.setTitle("Remove Past Purchase?");

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            /**
             * Implements the functionality of the user exiting the dialog
             * by clicking the cancel text.
             * @param dialog Reference to a Dialog Interface object that represents the current dialog.
             * @param temp Reference to a temporary variable.
             */
            @Override
            public void onClick(DialogInterface dialog, int temp) {
                dismiss();
            }
        });

        builder.setPositiveButton(android.R.string.ok, new RemovePastPurchaseListener());

        return builder.create();
    }

    /**
     * Inner class that implements the functionality of allowing a user to
     * remove a past purchase through the click of the button present.
     */
    private class RemovePastPurchaseListener implements DialogInterface.OnClickListener {
        /**
         * Implements the functionality of the user removing the past purchase
         * from the dialog.
         * @param dialog Reference to a Dialog Interface object that represents the current dialog.
         * @param temp Reference to a temporary variable.
         */
        @Override
        public void onClick(DialogInterface dialog, int temp) {
            PastPurchase purchase = new PastPurchase(date, numItems, costPer);
            purchase.setKey(key);

            EditPastPurchasedDialogFragment.EditPastPurchasedDialogListener listener = (EditPastPurchasedDialogListener) getActivity();
            listener.removePurchase(position, purchase);

            dismiss();
        }
    }
}