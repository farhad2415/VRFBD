package com.vrfbd.vrfbd;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button Login, Signup;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = findViewById(R.id.LogInId);
        Signup = findViewById(R.id.SignUpId);
        TextView textView = this.findViewById(R.id.textview_marquee);

        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        textView.setText("বৈধভাবে টাকা আয় করতে আমাদের সাথেই থাকুন!");
        textView.setVisibility(View.VISIBLE);
        textView.setSelected(true);
        textView.setSingleLine(true);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogIn.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Login Panel", Toast.LENGTH_LONG).show();
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "SignUp Panel", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void onBackPressed() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_warning_24);
        alertDialogBuilder.setTitle("Exit!");
        alertDialogBuilder.setMessage("Are you sure to Exit now?");
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}