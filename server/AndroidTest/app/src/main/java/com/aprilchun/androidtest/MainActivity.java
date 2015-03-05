package com.aprilchun.androidtest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aprilchun.androidtest.service.ReportService;
import com.aprilchun.androidtest.service.RequestService;
import com.aprilchun.androidtest.service.UserService;
import com.aprilchun.androidtest.service.impl.SoapReportService;
import com.aprilchun.androidtest.service.impl.SoapRequestService;
import com.aprilchun.androidtest.service.impl.SoapUserService;

import org.json.JSONArray;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    private UserService userService = new SoapUserService();
    private ReportService reportService = new SoapReportService();
    private RequestService requestService = new SoapRequestService();

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
                Toast.makeText(getBaseContext(), "Registering...", Toast.LENGTH_LONG).show();
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String response = userService.register("bb", "bb", "bb", "1");
                    Message msg = handler.obtainMessage();
                    msg.obj = response;
                    handler.sendMessage(msg);
                    Looper.loop();
                }
            };
        });

        bt2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Logging...", Toast.LENGTH_LONG).show();
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    String response = userService.login("bb", androidId, "bb");
                    Message msg = handler.obtainMessage();
                    msg.obj = response;
                    handler.sendMessage(msg);
                    Looper.loop();
                }
            };
        });

        bt3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sending location...", Toast.LENGTH_LONG).show();
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    String response = reportService.sendLocationReport("bb", androidId, 1, 1);
                    Message msg = handler.obtainMessage();
                    msg.obj = response;
                    handler.sendMessage(msg);
                    Looper.loop();
                }
            };
        });

        bt4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sending text...", Toast.LENGTH_LONG).show();
                new Thread(serviceThread).start();
            }

            Runnable serviceThread = new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    String androidId = Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    String response = reportService.sendTextReport("bb", androidId, 1, 1, "test");
                    Message msg = handler.obtainMessage();
                    msg.obj = response;
                    handler.sendMessage(msg);
                    Looper.loop();
                }
            };
        });

        bt5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Sending photo...", Toast.LENGTH_LONG).show();
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
                    String response = reportService.sendPhotoReport("bb", androidId, 1, 1, 1, file, "test");
                    Message msg = handler.obtainMessage();
                    msg.obj = response;
                    handler.sendMessage(msg);
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
                    Message msg = handler.obtainMessage();
                    msg.obj = ((JSONArray)requestService.getTeamAllLocations("bb", androidId)).toString();
                    handler.sendMessage(msg);
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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getBaseContext(), (String) msg.obj, Toast.LENGTH_LONG).show();
        }
    };
}
