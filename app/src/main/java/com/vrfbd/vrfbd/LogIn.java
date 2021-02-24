package com.vrfbd.vrfbd;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

        webView = findViewById(R.id.LogInWebId);

        if (isOnline()) {

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient());
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
        NetworkInfo[] netInfo = conMgr.getAllNetworkInfo();


        if (netInfo == null) {
            Toast.makeText(getApplicationContext(), "No Internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}