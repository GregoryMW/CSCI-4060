package edu.uga.cs.singleactivityarch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainScreen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainScreen extends Fragment {

    private static final String TAG = "SingleArchMainScreen";

    private Button flow1Button;
    private Button flow2Button;

    private int flow;

    public MainScreen() {
        // Required empty public constructor
    }

    public static MainScreen newInstance() {
        MainScreen fragment = new MainScreen();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState ) {
        //Log.d( TAG, "onActivityCreated()" );

        super.onViewCreated(view,savedInstanceState);

        flow1Button = getView().findViewById( R.id.flow1 );
        flow2Button = getView().findViewById( R.id.flow2 );
        flow1Button.setOnClickListener( new ButtonClickListener() );
        flow2Button.setOnClickListener( new ButtonClickListener() );

        //View fragment = getActivity().findViewById(R.id.fragment);
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick( View v ) {
            switch( v.getId() ) {
                case R.id.flow1:
                    Log.d(TAG, "ButtonClickListener: Flow 1 button" );
                    flow = 1;
                    break;
                case R.id.flow2:
                    Log.d(TAG, "ButtonClickListener: Flow 2 button" );
                    flow = 2;
                    break;
            }
            startFlow( flow );
        }
    }

    private void startFlow(int flow) {

        // get the manager and start a transaction
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        if (flow == 1) {
            // create a new fragment for screen A in the flow
            Flow1_A flow1_A = Flow1_A.newInstance();
            fragmentTransaction.replace(R.id.fragment, flow1_A);
        }
        else {
            // create a new fragment for screen A in the flow
            Flow2_A flow2_A = Flow2_A.newInstance();
            fragmentTransaction.replace(R.id.fragment, flow2_A);
        }

        // add it to the back stack to enable the back button
        fragmentTransaction.addToBackStack("main screen");

        // commit the transaction, i.e. make the changes
        fragmentTransaction.commit();

    }
}