package com.moutamid.broadcastreceiver.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.moutamid.broadcastreceiver.R;
import com.moutamid.broadcastreceiver.databinding.ActivityAttractionsBinding;
import com.moutamid.broadcastreceiver.fragments.BrowserFragment;
import com.moutamid.broadcastreceiver.fragments.ListFragment1;

public class AttractionsActivity extends AppCompatActivity {
    private static final String TAG = "AttractionsActivity";

    private ActivityAttractionsBinding b;
    FragmentTransaction transaction;
    BrowserFragment browserFragment;
    ListFragment1 listFragment1;

    String RESTAURANT = "restaurant";
    String ATTRACTION = "attraction";

    BrowserFragment browserFragmentH;
    ListFragment1 listFragmentH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        b = ActivityAttractionsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        Log.d(TAG, "onCreate: ");
        transaction = getSupportFragmentManager().beginTransaction();

        listFragment1 = new ListFragment1(ATTRACTION);
        browserFragment = new BrowserFragment(ATTRACTION);

        listFragmentH = new ListFragment1(ATTRACTION);
        browserFragmentH = new BrowserFragment(ATTRACTION);

        // VERTICAL FRAGMENTS
        transaction.add(R.id.list_fragment, listFragment1);
        transaction.add(R.id.browser_fragment, browserFragment);

        // HORIZONTAL FRAGMENTS
        transaction.add(R.id.list_fragment_horiz, listFragmentH);
        transaction.add(R.id.browser_fragment_horiz, browserFragmentH);

        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        int id = item.getItemId();
        switch (id) {
            case R.id.attraction:
                return true;
            case R.id.restaurants:
                startActivity(new Intent(AttractionsActivity.this, RestaurantsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    String currentOrientation = "vertical";

    final String VERTICAL = "vertical";
    final String HORIZONTAL = "horizontal";

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            currentOrientation = HORIZONTAL;

            b.horizontalLayout.setVisibility(View.VISIBLE);
            b.verticalLayout.setVisibility(View.GONE);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            currentOrientation = VERTICAL;

            b.horizontalLayout.setVisibility(View.GONE);
            b.verticalLayout.setVisibility(View.VISIBLE);
        }

        if (b.listFragment.getVisibility() == View.VISIBLE
                || b.listFragmentHoriz.getVisibility() == View.VISIBLE) {
            showListFragment();
            hideBrowserFragment();
        }

        if (b.browserFragment.getVisibility() == View.VISIBLE
                || b.browserFragmentHoriz.getVisibility() == View.VISIBLE) {
            hideListFragment();
            showBrowserFragment();
        }
    }

    public void hideListFragment() {
        Log.d(TAG, "hideListFragment: ");
        if (currentOrientation.equals(VERTICAL))
            b.listFragment.setVisibility(View.GONE);

        if (currentOrientation.equals(HORIZONTAL)) {
            ViewGroup.LayoutParams layoutParams = b.listFragmentHoriz.getLayoutParams();
            layoutParams.width = 0;
            b.listFragmentHoriz.setLayoutParams(layoutParams);
//            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//            b.listFragmentHoriz.setVisibility(View.GONE);
        }
    }

    public void hideBrowserFragment() {
        Log.d(TAG, "hideBrowserFragment: ");
        if (currentOrientation.equals(VERTICAL))
            b.browserFragment.setVisibility(View.GONE);

        if (currentOrientation.equals(HORIZONTAL))
            b.browserFragmentHoriz.setVisibility(View.GONE);

    }

    public void showListFragment() {
        Log.d(TAG, "showListFragment: ");
        if (currentOrientation.equals(VERTICAL))
            b.listFragment.setVisibility(View.VISIBLE);

        if (currentOrientation.equals(HORIZONTAL)) {
            ViewGroup.LayoutParams layoutParams = b.listFragmentHoriz.getLayoutParams();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            b.listFragmentHoriz.setLayoutParams(layoutParams);

//            b.listFragmentHoriz.setVisibility(View.VISIBLE);
        }
    }

    public void showBrowserFragment() {
        Log.d(TAG, "showBrowserFragment: ");
        if (currentOrientation.equals(VERTICAL))
            b.browserFragment.setVisibility(View.VISIBLE);

        if (currentOrientation.equals(HORIZONTAL))
            b.browserFragmentHoriz.setVisibility(View.VISIBLE);
    }

    public void loadAnotherUrlOnF(String url) {
        Log.d(TAG, "loadAnotherUrlOnF: ");
        browserFragment.loadAnotherUrl(url);
        browserFragmentH.loadAnotherUrl(url);

    }

    public void setSelectedOnList(int position){
        listFragment1.setSelectedItemOnRecyc(position);
    }

    public void setSelectedOnListH(int position){
        listFragmentH.setSelectedItemOnRecyc(position);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: ");
        if (b.browserFragment.getVisibility() == View.VISIBLE) {
            hideBrowserFragment();
            showListFragment();
            return;
        }

        if (b.browserFragmentHoriz.getVisibility() == View.VISIBLE) {
            hideBrowserFragment();
            showListFragment();
            return;
        }

        super.onBackPressed();
    }
}