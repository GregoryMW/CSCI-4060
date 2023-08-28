package edu.uga.cs.singleactivityarch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Flow1_B#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Flow1_B extends Fragment {

    public Flow1_B() {
        // Required empty public constructor
    }

    public static Flow1_B newInstance() {
        Flow1_B fragment = new Flow1_B();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flow1__b, container, false);
    }
}