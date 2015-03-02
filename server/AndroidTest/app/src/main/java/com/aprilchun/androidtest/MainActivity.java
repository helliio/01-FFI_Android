package com.aprilchun.androidtest;

import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aprilchun.androidtest.service.ReportService;
import com.aprilchun.androidtest.service.RequestService;
import com.aprilchun.androidtest.service.UserService;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt1 = (Button) this.findViewById(R.id.button1);
        Button bt2 = (Button) this.findViewById(R.id.button2);
        Button bt3 = (Button) this.findViewById(R.id.button3);
        Button bt4 = (Button) this.findViewById(R.id.button4);
        Button bt5 = (Button) this.findViewById(R.id.button5);
        Button bt6 = (Button) this.findViewById(R.id.button6);

        bt1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    new UserService().register("bb", "bb", "bb", "1");
                    Looper.loop();
                }
            };
        });

        bt2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    new UserService().login("bb", androidId, "bb");
                    Looper.loop();
                }
            };
        });

        bt3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    new ReportService().sendLocationReport("bb", androidId, 1, 1);
                    Looper.loop();
                }
            };
        });

        bt4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    new ReportService().sendLocationReport("bb", androidId, 1, 1);
                    Looper.loop();
                }
            };
        });

        bt5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    File file = new File(getFilesDir(), "test.txt");
                    try {
                        file = new File("/storage/emulated/0/DCIM/Screenshots/", "test.png");
                        System.out.println(file.length());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new ReportService().sendPhotoReport("bb", androidId, 1, 1, 1, file, "test");
                    Looper.loop();
                }
            };
        });

        bt6.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    new RequestService().getTeamLocations("bb", androidId);
                    Looper.loop();
                }
            };
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
