package com.example.testapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.thanosfisherman.wifiutils.WifiUtils;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionErrorCode;
import com.thanosfisherman.wifiutils.wifiConnect.ConnectionSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionErrorCode;
import com.thanosfisherman.wifiutils.wifiDisconnect.DisconnectionSuccessListener;
import com.thanosfisherman.wifiutils.wifiRemove.RemoveErrorCode;
import com.thanosfisherman.wifiutils.wifiRemove.RemoveSuccessListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private View parentLayout;
    private EditText etSSID, etPass;
    private Button btnConnect,btnDisconnect;
    private String networkSSID, networkPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSSID = findViewById(R.id.etSSID);
        etPass = findViewById(R.id.etPass);
        btnConnect=findViewById(R.id.btnconnect);
        btnDisconnect=findViewById(R.id.btndisconnect);
        parentLayout = findViewById(android.R.id.content);

        networkSSID = etSSID.getText().toString().trim();
        networkPass = etPass.getText().toString().trim();

        btnConnect.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                          public void onClick(View view) {
                                             /* WifiUtils.withContext(getApplicationContext())
                                                      .connectWith("hotifi_1@hotifi","84:16:f9:61:4d:ca", "22B.baker.street")
                                                      .setTimeout(10000)
                                                      .onConnectionResult(new ConnectionSuccessListener() {
                                                          @Override
                                                          public void success() {
                                                              Snackbar.make(parentLayout, "Connected", Snackbar.LENGTH_LONG)
                                                                      .setDuration(3000)
                                                                      .setActionTextColor(getResources().getColor(android.R.color.white))
                                                                      .setAction("Close", new View.OnClickListener() {
                                                                          @Override
                                                                          public void onClick(View view) {

                                                                          }
                                                                      })
                                                                      .show();
                                                              //Start Service Which Will Remove Wifi Password User When He Closes App** REMOVED NOT NEEDED IN THIS CASE



                                                          @Override
                                                          public void failed(@Nullable ConnectionErrorCode errorCode) {
                                                              assert errorCode != null;
                                                              Toast.makeText(getApplicationContext(), "Fail " + errorCode.toString(), Toast.LENGTH_SHORT).show();
                                                              Snackbar.make(parentLayout, "Failed to connect ", Snackbar.LENGTH_LONG)
                                                                      .setDuration(3000)
                                                                      .setActionTextColor(getResources().getColor(android.R.color.white))
                                                                      .setAction("Close", new View.OnClickListener() {
                                                                          @Override
                                                                          public void onClick(View view) {
                                                                          }
                                                                      })
                                                                      .show();
                                                              //SharedPrefsManager.getInstance(getApplicationContext()).saveTransactionStatus(true, false, false, true);
                                                          }
                                                      })
                                                      .start();*/


                                             WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()){
                wifiManager.setWifiEnabled(true);
            }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                WifiNetworkSuggestion networkSuggestion1 =
                        new WifiNetworkSuggestion.Builder()
                                //.setSsid("hotifi_1@hotifi")
                                //.setWpa2Passphrase("221B.baker.street")
                                .setSsid(networkSSID)
                                .setWpa2Passphrase(networkPass)
                                .build();

                WifiNetworkSuggestion networkSuggestion2 =
                        new WifiNetworkSuggestion.Builder()
                                //.setSsid("hotifi_1@hotifi")
                                //.setWpa3Passphrase("221B.baker.street")
                                .setSsid(networkSSID)
                                .setWpa2Passphrase(networkPass)
                                .build();

                List<WifiNetworkSuggestion> suggestionsList = new ArrayList<>();
                suggestionsList.add(networkSuggestion1);
                suggestionsList.add(networkSuggestion2);

                wifiManager.addNetworkSuggestions(suggestionsList);
            }

        else{
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                //wifiConfiguration.SSID = String.format("\"%s\"", "hotifi_1@hotifi");
                //wifiConfiguration.preSharedKey = String.format("\"%s\"", "221B.baker.street");
                wifiConfiguration.SSID = String.format("\"%s\"", networkSSID);
                wifiConfiguration.preSharedKey = String.format("\"%s\"", networkPass);
            int wifiID = wifiManager.addNetwork(wifiConfiguration);
                wifiManager.enableNetwork(wifiID, true);
            }
                                          }
                                      }
);
                btnDisconnect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*WifiUtils.withContext(getApplicationContext())
                                .disconnect(new DisconnectionSuccessListener() {
                                    @Override
                                    public void success() {
                                        Toast.makeText(MainActivity.this, "Disconnect success!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void failed(@NonNull DisconnectionErrorCode errorCode) {
                                        Toast.makeText(MainActivity.this, "Failed to disconnect: " + errorCode.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });*/
                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        int networkId = wifiManager.getConnectionInfo().getNetworkId();
                        wifiManager.removeNetwork(networkId);
                        wifiManager.saveConfiguration();

                    }
                }

        );



    }
}