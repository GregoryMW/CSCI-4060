package edu.uga.cs.countrytravelfragments;

import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class CountryInfoFragment extends Fragment {

    InputStream input;
    String txt = null;

    public CountryInfoFragment()
    {
        //Default Constructor
    }

    public static CountryInfoFragment newInstance(int countryIndex)
    {
        CountryInfoFragment fragment = new CountryInfoFragment();
        Bundle args = new Bundle();
        args.putInt("CountryIndex", countryIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle)
    {
        ScrollView scroller = new ScrollView(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(linearLayout.VERTICAL);
        ImageView imageView = new ImageView(getActivity());
        TextView textView = new TextView(getActivity());
        scroller.addView(linearLayout);
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
        imageView.setAdjustViewBounds(true);

        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getActivity().getResources().getDisplayMetrics());
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);

        switch (getShownCountryIndex())
        {
            case 0:
            {
                imageView.setImageResource(R.drawable.argentina_flag);
                input = getResources().openRawResource(R.raw.argentina);
                break;
            }
            case 1:
            {
                imageView.setImageResource(R.drawable.canada_flag);
                input = getResources().openRawResource(R.raw.canada);
                break;
            }
            case 2:
            {
                imageView.setImageResource(R.drawable.ethiopia_flag);
                input = getResources().openRawResource(R.raw.ethiopia);
                break;
            }
            case 3:
            {
                imageView.setImageResource(R.drawable.japan_flag);
                input = getResources().openRawResource(R.raw.japan);
                break;
            }
            case 4:
            {
                imageView.setImageResource(R.drawable.switzerland_flag);
                input = getResources().openRawResource(R.raw.switzerland);
                break;
            }
        }

        try {
            byte[] buffer = new byte[input.available()];
            while (input.read(buffer) != -1)
                txt = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText(txt);

        return scroller;
    }

    public int getShownCountryIndex()
    {
        return getArguments().getInt("CountryIndex", 0);
    }
}