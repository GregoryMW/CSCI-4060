package edu.uga.cs.singleactivityarch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Flow2_A#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Flow2_A extends Fragment {

    Button nextButton;

    public Flow2_A() {
        // Required empty public constructor
    }

    public static Flow2_A newInstance() {
        Flow2_A fragment = new Flow2_A();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flow2__a, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        //Log.d( TAG, "onActivityCreated()" );

        super.onViewCreated(view,savedInstanceState);

        nextButton = getView().findViewById( R.id.button );
        nextButton.setOnClickListener( new ButtonClickListener() );
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            Flow2_B flow2_B = Flow2_B.newInstance();

            // get the manager and start a transaction
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment, flow2_B);

            // add this fragment to backstack
            fragmentTransaction.addToBackStack( "flow2 A" );

            // commit the transaction, i.e. make the changes
            fragmentTransaction.commit();
        }
    }
}