package com.moutamid.broadcastreceiver.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.moutamid.broadcastreceiver.R;
import com.moutamid.broadcastreceiver.databinding.FragmentBrowserBinding;

public class BrowserFragment extends Fragment {
    private static final String TAG = "BrowserFragment";

    String text;

    public BrowserFragment(String text) {
        this.text = text;
        Log.d(TAG, "BrowserFragment: ");
        // Required empty public constructor
    }

    public BrowserFragment() {
        // Required empty public constructor
    }

    private FragmentBrowserBinding b;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentBrowserBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView: ");

        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Loading...");

        b.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
        b.webView.getSettings().setJavaScriptEnabled(true);

        b.webView.loadUrl("https://google.com");

        return b.getRoot();
    }

    public void loadAnotherUrl(String url) {
        Log.d(TAG, "loadAnotherUrl: ");
        progressDialog.show();
        b.webView.loadUrl(url);
    }

}