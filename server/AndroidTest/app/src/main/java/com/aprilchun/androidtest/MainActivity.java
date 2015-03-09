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
import com.aprilchun.androidtest.util.Coder;
import com.aprilchun.androidtest.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;


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
                    try {
                        Message msg = handler.obtainMessage();
                        JSONObject jsonObject = new JSONObject(response);
                        msg.obj = jsonObject.get("tag") + ":" + jsonObject.get("desc");
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
                    try {
                        Message msg = handler.obtainMessage();
                        JSONObject jsonObject = new JSONObject(response);
                        msg.obj = jsonObject.get("tag") + ":" + jsonObject.get("desc");
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
                    //String response = reportService.sendLocationReport("bb", androidId, Calendar.getInstance(Constant.TIME_ZONE), 1, 1);
                    try {
                        JSONObject locationJObj = new JSONObject();
                        locationJObj.put("longitude", 1);
                        locationJObj.put("latitude", 1);
                        locationJObj.put("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
                        JSONArray array = new JSONArray();
                        array.put(locationJObj);
                        array.put(locationJObj);
                        String response = reportService.sendLocationReportList("bb", androidId, Calendar.getInstance(Constant.TIME_ZONE), array);
                        Message msg = handler.obtainMessage();
                        JSONObject jsonObject = new JSONObject(response);
                        msg.obj = jsonObject.get("tag") + ":" + jsonObject.get("desc");
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
//                    String response = reportService.sendTextReport("bb", androidId, Calendar.getInstance(Constant.TIME_ZONE), 1, 1, "test");
                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("longitude", 1);
                        obj.put("latitude", 1);
                        obj.put("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
                        obj.put("content", "listTest");
                        JSONArray array = new JSONArray();
                        array.put(obj);
                        array.put(obj);
                        String response = reportService.sendTextReportList("bb", androidId, Calendar.getInstance(Constant.TIME_ZONE), array);
                        Message msg = handler.obtainMessage();
                        JSONObject jsonObject = new JSONObject(response);
                        msg.obj = jsonObject.get("tag") + ":" + jsonObject.get("desc");
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    String response = reportService.sendPhotoReport("bb", androidId, Calendar.getInstance(Constant.TIME_ZONE), 1, 1, 1, file, "test");
                    try {
                        Message msg = handler.obtainMessage();
                        JSONObject jsonObject = new JSONObject(response);
                        msg.obj = jsonObject.get("tag") + ":" + jsonObject.get("desc");
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                    Calendar calendar = Calendar.getInstance(Constant.TIME_ZONE);
                    calendar.set(2015, 1, 1);
                    String startTime = String.valueOf(calendar.getTimeInMillis());
                    calendar.set(2015, 2, 28);
                    String endTime = String.valueOf(calendar.getTimeInMillis());
//                    String response = requestService.getAllTeamLocations("bb", androidId);
//                    String response = requestService.getLatestTeamLocations("bb", androidId);
//                    String response = requestService.getPeriodTeamLocations("bb", androidId, startTime, endTime);
//                    String response = requestService.getAllTeamTextReports("bb", androidId);
//                    String response = requestService.getLatestTeamTextReports("bb", androidId);
//                    String response = requestService.getPeriodTeamTextReports("bb", androidId, startTime, endTime);
//                    String response = requestService.getAllTeamPhotoReports("bb", androidId);
//                    String response = requestService.getLatestTeamPhotoReports("bb", androidId);
                    String response = requestService.getPeriodTeamPhotoReports("bb", androidId, startTime, endTime);
//                    String response = requestService.getPhoto("bb", androidId, "19");
                    try {
                        Message msg = handler.obtainMessage();
                        JSONObject jsonObject = new JSONObject(response);
                        msg.obj = jsonObject.get("tag") + ":" + jsonObject.get("desc");
                        handler.sendMessage(msg);
                        String obj = (String) jsonObject.get("obj");
                        JSONArray array = new JSONArray(obj);
                        for (int i = 0; i < array.length(); i++) {
                            System.out.println(array.get(i));
                        }

//                        System.out.println(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
