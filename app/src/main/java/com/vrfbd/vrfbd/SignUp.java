package com.vrfbd.vrfbd;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    static final String url = "\"https://www.vrfbd.com/signup\"";
    private static final String TAG = "tag";
    WebView webView;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        webView = findViewById(R.id.SignUpWebId);


        if (isOnline()) {

            WebSettings webSettings = webView.getSettings();

            webSettings.setAllowFileAccessFromFileURLs(true);
            webSettings.setAllowUniversalAccessFromFileURLs(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setSupportZoom(true);

            webView.setWebViewClient(new WebViewClient());

            webView.setWebChromeClient(new WebChromeClient() {
                // Grant permissions for cam
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    Log.d(TAG, "onPermissionRequest");
                    SignUp.this.runOnUiThread(new Runnable() {
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

                alertDialog = new AlertDialog.Builder(SignUp.this);

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
                Log.d(SignUp.TAG, "Show Dialog: " + e.getMessage());
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