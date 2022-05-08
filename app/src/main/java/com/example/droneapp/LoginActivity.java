package com.example.droneapp;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    TextView textView;
    Button button,flyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        textView = findViewById(R.id.Name);
        button = findViewById(R.id.connectButton);
        flyButton = findViewById(R.id.FlyButton);

        flyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName = textView.getText().toString();

                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

                 if(wifiManager.isWifiEnabled()) {
                    Intent intent = new Intent(LoginActivity.this, MainBoard.class);
                    intent.putExtra("name",fullName);
                    startActivity(intent);
                }
                else{
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                    alertDialog.setTitle("Info");
                    alertDialog.setMessage("No Device Detected! PLease cross check your connection");
                    alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                    alertDialog.setPositiveButton("OK", (dialog, which) -> finish()).setNegativeButton("Cancel", null);
                    alertDialog.show();
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textView.getText().toString().isEmpty()){
                    textView.setError("Please provide your name first");
                    textView.setEnabled(true);
                }
                else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_MAIN, null);
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                        intent.setComponent(cn);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (ActivityNotFoundException ignored) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                }
            }
        });
    }

}