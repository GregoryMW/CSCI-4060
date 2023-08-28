package com.introtoandroid.simplefragments;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewFragment;


public class WeedWebViewFragment extends WebViewFragment {

    private static final String DEBUG_TAG = "WeedWebViewFragment";

    public static WeedWebViewFragment newInstance(int index) {
        Log.v(DEBUG_TAG, "WeedWebViewFragment.newInstance: Creating new instance: " + index);
        WeedWebViewFragment fragment = new WeedWebViewFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    public int getShownIndex() {
        int index = -1;
        Bundle args = getArguments();
        if (args != null) {
            index = args.getInt("index", -1);
        }
        if (index == -1) {
            Log.e(DEBUG_TAG, "Not an array index.");
        }

        return index;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onActivityCreated(): " + getShownIndex());
        super.onActivityCreated(savedInstanceState);

        // Load the URL after the Webview has been created in onCreateView()
        String[] weedUrls;
        weedUrls = getResources().getStringArray(
                R.array.weedurls_array);
        int weedUrlIndex = getShownIndex();

        WebView webview = getWebView();
        webview.setPadding(0, 0, 0, 0);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);

        if (weedUrlIndex != -1) {
            String weedeUrl = weedUrls[weedUrlIndex];
            webview.loadUrl(weedeUrl);
        } else {
            webview.loadUrl("https://turf.caes.uga.edu/pest-management/weeds/broadleaf-weeds/common-chickweed.html");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onCreate() ");
        super.onCreate(savedInstanceState);
        Log.d(DEBUG_TAG, "OnCreate(): " + getShownIndex());
    }

    @Override
    public void onAttach(Activity activity) {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onStart(): " + getShownIndex());
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onResume():" +  getShownIndex());
    }

    @Override
    public void onDetach() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onDetach()");
        super.onDetach();
    }

    @Override
    public void onPause() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onPause(): " + getShownIndex());
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onStop(): " + getShownIndex());
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(DEBUG_TAG, "FRAGMENT LIFECYCLE EVENT: WeedWebViewFragment.onDestroy()");
        super.onDestroy();
    }
}
