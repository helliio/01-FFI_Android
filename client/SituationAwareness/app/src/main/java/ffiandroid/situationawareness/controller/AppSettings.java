package ffiandroid.situationawareness.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.ParameterSetting;
import ffiandroid.situationawareness.model.StatusListener;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.util.Constant;

/**
 * This AppSettings Class is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 * <p/>
 * responsible for this file: GuoJunjun & Simen
 */
public class AppSettings extends ActionBarActivity implements StatusListener {
    private EditText autoSyncTime;
    private EditText locationUpdateTime;
    private EditText locationUpdateDistance;
    private EditText serverIPAddress;
    private String menuStatus;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        autoSyncTime = (EditText) findViewById(R.id.set_auto_sync_minute);
        locationUpdateTime = (EditText) findViewById(R.id.set_location_update_second);
        locationUpdateDistance = (EditText) findViewById(R.id.set_location_update_distance);
        serverIPAddress = (EditText) findViewById(R.id.server_ip_settings_edit_text);

        ((TextView)(findViewById(R.id.settings_current_server_ip))).setText("Current Server IP: " + Constant.SERVICE_URL);
        ((TextView)findViewById(R.id.server_ip_settings_default)).setText("Default IP: " + Constant.DEFAULT_SERVICE_URL);

        initParameterSettings();
        formatMenuStatus();
    }


    public void onIPSettingsCheckBoxClicked(View view)
    {
        CheckBox setting_checkBox = ((CheckBox) findViewById(R.id.setting_ip_check_box));
        if(setting_checkBox.isChecked())
        {
            findViewById(R.id.server_ip_settings_edit_text).setEnabled(false);
            ((EditText)findViewById(R.id.server_ip_settings_edit_text)).getText().clear();
            ((TextView)findViewById(R.id.server_ip_settings_default)).setText("Default IP: " + Constant.DEFAULT_SERVICE_URL);
            Constant.SERVICE_URL = Constant.DEFAULT_SERVICE_URL;
        }
        else
        {
            findViewById(R.id.server_ip_settings_edit_text).setEnabled(true);
        }
    }

    private void initParameterSettings() {
        autoSyncTime.setText((ParameterSetting.getAutoSyncTime() / 60000) + "");
        locationUpdateTime.setText((ParameterSetting.getLocationUpdateTime() / 1000) + "");
        locationUpdateDistance.setText((ParameterSetting.getLocationUpdateDistance()) + "");
    }

    public void parameterSettingClicked(View v) {
        int synctime, locauptime, locaupdis;
        try {
            synctime = Integer.parseInt(autoSyncTime.getText().toString());
            locauptime = Integer.parseInt(locationUpdateTime.getText().toString());
            locaupdis = Integer.parseInt(locationUpdateDistance.getText().toString());
            if (synctime < 1 && synctime > 999) {
                Toast.makeText(this, "Wrong Auto Sync input! 0 < AutoSync < 999", Toast.LENGTH_SHORT).show();
            } else if (locauptime < 1 && locauptime > 999) {
                Toast.makeText(this, "Wrong input! 0 < location update time < 999", Toast.LENGTH_SHORT).show();
            } else if (locaupdis < 1 && locaupdis > 999) {
                Toast.makeText(this, "Wrong input! 0 < location update distance < 999", Toast.LENGTH_SHORT).show();
            } else {
                CheckBox setting_checkBox = ((CheckBox) findViewById(R.id.setting_ip_check_box));
                ParameterSetting.setAutoSyncTime(synctime);
                ParameterSetting.setLocationUpdateTime(locauptime);
                ParameterSetting.setLocationUpdateDistance(locaupdis);
                if(!setting_checkBox.isChecked()) {
                    getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE).edit().putString(Login.PREF_SERVERIP,
                            serverIPAddress.getText().toString()).commit();
                    Constant.SERVICE_URL = "http://" + serverIPAddress.getText().toString() + ":8080/";
                    ((TextView) findViewById(R.id.settings_current_server_ip)).setText("Current Server IP: " + Constant.SERVICE_URL);
                }
                else
                {
                    ((TextView) findViewById(R.id.settings_current_server_ip)).setText("Current Server IP: " + Constant.SERVICE_URL);
                    getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE).edit().putString(Login.PREF_SERVERIP,
                            Constant.DEFAULT_SERVICE_URL.toString()).commit();
                }
                Toast.makeText(this, "settings saved !", Toast.LENGTH_SHORT).show();
                initParameterSettings();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Wrong input! please use Integer", Toast.LENGTH_SHORT).show();
        }
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_settings, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_report:
                startActivity(new Intent(this, Report.class));
                return true;
            case R.id.menu_item_status:
                startActivity(new Intent(this, Status.class));
                return true;
            case R.id.menu_item_map_view:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            case R.id.menu_item_report_view:
                startActivity(new Intent(this, ReportView.class));
                return true;
            case R.id.menu_item_logout:
                rememberMeDelete();
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("ACTION_LOGOUT");
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * delete remembered information from a user
     */
    private void rememberMeDelete() {
        getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE).edit().putString(Login.PREF_USERNAME, null)
                .putString(Login.PREF_PASSWORD, null).commit();
    }

    /**
     * receive broadcast for logout
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override public void onReceive(Context context, Intent intent) {
            //            startActivity(new Intent(getBaseContext(), Login.class));
            finish();
        }
    };

    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_item_status_and_send_button);
        menuItem.setTitle(String.valueOf(menuStatus));
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * get value form UserInfo and assign it to menu status
     */
    private void formatMenuStatus() {
        menuStatus = UserInfo.getReportDetails();
    }

    /**
     * menu status changed
     */
    @Override public void menuStatusChanged() {
        formatMenuStatus();
    }

    /**
     * location status changed
     */
    @Override public void locationStatusChanged() {

    }

    /**
     * text status changed
     */
    @Override public void textStatusChanged() {

    }

    /**
     * photo status changed
     */
    @Override public void photoStatusChanged() {

    }

    /**
     * last time report succeed or not
     */
    @Override public void lastReportStatusChanged() {

    }

}
