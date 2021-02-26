package com.vrfbd.vrfbd;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity {
    static final String url = "https://www.vrfbd.com/signin";
    private static final String TAG = "tag";
    WebView webView;
    AlertDialog.Builder alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        ProgressBar progressBar = findViewById(R.id.progressBarId);
        webView = findViewById(R.id.LogInWebId);

        if (isOnline()) {

            WebSettings webSettings = webView.getSettings();

            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setSupportZoom(true);


            webView.getSettings().setGeolocationEnabled(true);
            webView.setSoundEffectsEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE);
                    setTitle("Loading");
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.GONE);
                    setTitle(view.getTitle());

                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                // Grant permissions for cam
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    Log.d(TAG, "onPermissionRequest");
                    LogIn.this.runOnUiThread(new Runnable() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            Log.d(TAG, request.getOrigin().toString());
                            if (request.getOrigin().toString().equals("file:///")) {
                                Log.d(TAG, "GRANTED");
                                request.grant(request.getResources());
                            } else {
                                Log.d(TAG, "DENIED");
                                request.deny();
                            }
                        }
                    });
                }


            });

            webView.loadUrl(url);


        } else {
            try {

                alertDialog = new AlertDialog.Builder(LogIn.this);

                alertDialog.setTitle("Internet not available");
                alertDialog.setMessage("Please check your internet connectivity and try again");
                alertDialog.setIcon(R.drawable.ic_baseline_warning_24);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog alert = alertDialog.create();


                alert.show();

            } catch (Exception e) {
                Log.d(LogIn.TAG, "Show Dialog: " + e.getMessage());
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();


        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}