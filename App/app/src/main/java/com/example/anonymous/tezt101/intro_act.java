package com.example.anonymous.tezt101;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class intro_act extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_act);
        if(isNetworkAvailable()) {
            runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                    findViewById(R.id.netInfo).setVisibility(View.INVISIBLE);
                    findViewById(R.id.netInfoSub).setVisibility(View.INVISIBLE);
                  }
              }
            );
        }
        final Context context = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FileHandler fControl = new FileHandler("datafile.txt", context);
                if(fControl.content.isEmpty()){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.set_1).setVisibility(View.VISIBLE);
                            findViewById(R.id.set_2).setVisibility(View.VISIBLE);
                            findViewById(R.id.christ_logo).setVisibility(View.INVISIBLE);
                        }});
                    if(!isNetworkAvailable()) {

                        while (!isNetworkAvailable());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            TextView v = findViewById(R.id.set_2);
                            v.setText("Please wait..");
                            v.setTextColor(Color.WHITE);
                            findViewById(R.id.netInfo).setVisibility(View.INVISIBLE);
                            findViewById(R.id.netInfoSub).setVisibility(View.INVISIBLE);
                        }
                    });
                    String link = "https://evntmgmt.000webhostapp.com/androidConnectPost.php?ts=" + fControl.getLastUpdated();
                    InputStream is = null;
                    BufferedReader br = null;
                    try {
                        URL url = new URL(link);
                        final URLConnection conn = url.openConnection();

                        is = conn.getInputStream();
                        //if(is.available() == 0) continue;
                        br = new BufferedReader(new InputStreamReader(is));

                        String line = null;
                        String content = "";
                        while ((line = br.readLine()) != null) {
                            content += line;
                        }

                        String temp = "";
                        for (int i = 0; i < content.length(); ++i) {
                            if (content.charAt(i) == '^') {
                                if (!FileHandler.valid(temp)) break;
                                fControl.appendOnFile(temp);
                                temp = "";
                            } else temp += content.charAt(i);break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (br != null) {
                            try {
                                br.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

                Intent i = new Intent(intro_act.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, 2000);


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
