package edu.uga.cs.countrytravelfragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CountryListFragment extends ListFragment {

    private final String[] countryList = {
            "Argentina",
            "Canada",
            "Ethiopia",
            "Japan",
            "Switzerland"
    };

    boolean twoFragmentsActivity = false;
    int countryIndex = 0;

    public CountryListFragment()
    {
        //Default constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setListAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_activated_1, countryList));

        View detailsFrame = getActivity().findViewById(R.id.countryInfo);
        twoFragmentsActivity = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null)
        {
            countryIndex = savedInstanceState.getInt("CountrySelection", 0);
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setItemChecked(countryIndex, true);

        if (twoFragmentsActivity)
        {
            showCountryInfo(countryIndex);
            getListView().smoothScrollToPosition(countryIndex);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        showCountryInfo(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("CountrySelection", countryIndex);
    }

    void showCountryInfo(int countryIndex)
    {
        this.countryIndex = countryIndex;

        if (twoFragmentsActivity)
        {
            getListView().setItemChecked(countryIndex, true);
            CountryInfoFragment details = (CountryInfoFragment) getParentFragmentManager().findFragmentById(R.id.countryInfo);

            if (details == null || details.getShownCountryIndex() != countryIndex)
            {
                details = CountryInfoFragment.newInstance(countryIndex);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.countryInfo, details);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        }
        else
        {
            Intent intent = new Intent();
            intent.setClass(getActivity(), CountryInfoActivity.class);
            intent.putExtra("CountryIndex", countryIndex);
            startActivity(intent);
        }
    }
}