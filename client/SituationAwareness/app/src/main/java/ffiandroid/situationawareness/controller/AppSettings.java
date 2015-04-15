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
import android.widget.EditText;
import android.widget.Toast;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.ParameterSetting;

/**
 * This AppSettings Class is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 * <p/>
 * responsible for this file: GuoJunjun & Simen
 */
public class AppSettings extends ActionBarActivity {
    private EditText autoSyncTime;
    private EditText locationUpdateTime;
    private EditText locationUpdateDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        autoSyncTime = (EditText) findViewById(R.id.set_auto_sync_minute);
        locationUpdateTime = (EditText) findViewById(R.id.set_location_update_second);
        locationUpdateDistance = (EditText) findViewById(R.id.set_location_update_distance);
        initParameterSettings();
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
                ParameterSetting.setAutoSyncTime(synctime);
                ParameterSetting.setLocationUpdateTime(locauptime);
                ParameterSetting.setLocationUpdateDistance(locaupdis);
                Toast.makeText(this, "settings saved !", Toast.LENGTH_SHORT).show();
                initParameterSettings();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Wrong input! please use Integer", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            case R.id.menu_item_photo_view:
                startActivity(new Intent(this, PhotoView.class));
                return true;
            case R.id.menu_item_logout:
                rememberMeDelete();
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("ACTION_LOGOUT");
                LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
                return true;
            case R.id.menu_item_location_view:
                startActivity(new Intent(this, LocationView.class));
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
        @Override
        public void onReceive(Context context, Intent intent) {
            startActivity(new Intent(getBaseContext(), Login.class));
            finish();
        }
    };

    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
