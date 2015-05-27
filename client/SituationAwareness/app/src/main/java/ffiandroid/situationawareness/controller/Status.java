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
import android.widget.TextView;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.StatusListener;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This Status Class is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 * <p/>
 * responsible for this file: GuoJunjun
 */
public class Status extends ActionBarActivity implements StatusListener {
    private TextView userId, lastSync, unReportedPhotos, unReportedText, unReportedLocations, currentLocationLatitude,
            currentLocationLongitude;
    private String menuStatus;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        setContentView(R.layout.status);
        userId = (TextView) findViewById(R.id.status_userid);
        lastSync = (TextView) findViewById(R.id.status_last_sync_to_server);
        unReportedText = (TextView) findViewById(R.id.status_not_reported_text);
        unReportedPhotos = (TextView) findViewById(R.id.status_not_reported_photos);
        unReportedLocations = (TextView) findViewById(R.id.status_not_reported_location_items);
        currentLocationLatitude = (TextView) findViewById(R.id.status_current_location_latitude);
        currentLocationLongitude = (TextView) findViewById(R.id.status_current_location_longitude);
        UserInfo.addListener(this);
        formatMenuStatus();
    }

    @Override protected void onResume() {
        super.onResume();
        setStatusValues();
    }

    /**
     * set status values in status view
     */
    private void setStatusValues() {
        userId.setText(UserInfo.getUserID());
        lastSync.setText((UserInfo.isLastSyncSucceed() ? "SUCCEED" : "NOT SUCCEED"));
        unReportedText.setText(String.valueOf(UserInfo.getUnReportedText()));
        unReportedPhotos.setText(String.valueOf(UserInfo.getUnReportedPhotos()));
        unReportedLocations.setText(String.valueOf(UserInfo.getUnReportedLocations()));
        currentLocationLatitude.setText("La: " + String.valueOf(UserInfo.getCurrentLatitude()));
        currentLocationLongitude.setText("Lo: " + String.valueOf(UserInfo.getCurrentLongitude()));
    }


    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_map_view:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            case R.id.menu_item_all_reports:
                startActivity(new Intent(this, AllReportsView.class));
                return true;
            case R.id.menu_item_report:
                startActivity(new Intent(this, Report.class));
                return true;
            case R.id.menu_item_app_settings:
                startActivity(new Intent(this, AppSettings.class));
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
        unReportedLocations.setText(String.valueOf(UserInfo.getUnReportedLocations()));
        currentLocationLatitude.setText("La: " + String.valueOf(UserInfo.getCurrentLatitude()));
        currentLocationLongitude.setText("Lo: " + String.valueOf(UserInfo.getCurrentLongitude()));
    }

    /**
     * text status changed
     */
    @Override public void textStatusChanged() {
        unReportedText.setText(String.valueOf(UserInfo.getUnReportedText()));
    }

    /**
     * photo status changed
     */
    @Override public void photoStatusChanged() {
        unReportedPhotos.setText(String.valueOf(UserInfo.getUnReportedPhotos()));
    }

    /**
     * last time report succeed or not
     */
    @Override public void lastReportStatusChanged() {
        lastSync.setText((UserInfo.isLastSyncSucceed() ? "SUCCEED" : "NOT SUCCEED"));
    }


}
