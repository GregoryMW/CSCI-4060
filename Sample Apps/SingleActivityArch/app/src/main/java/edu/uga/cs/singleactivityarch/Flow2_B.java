package edu.uga.cs.singleactivityarch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Flow2_B#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Flow2_B extends Fragment {

    public Flow2_B() {
        // Required empty public constructor
    }

    public static Flow2_B newInstance() {
        Flow2_B fragment = new Flow2_B();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flow2__b, container, false);
    }
}