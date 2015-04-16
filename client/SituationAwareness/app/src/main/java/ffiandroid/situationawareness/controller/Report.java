

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
import ffiandroid.situationawareness.model.localdb.DAOtextReport;
import ffiandroid.situationawareness.model.TextReport;

/**
 * This Report Class is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 * <p/>
 * responsible for this file: GuoJunjun
 */
public class Report extends ActionBarActivity {
    private EditText textReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        textReport = (EditText) findViewById(R.id.report_edit_text_report);
    }
    /**
     * user click add photo button
     */
    public void addPhotoButtonOnClicked(View view){
        startActivity(new Intent(this, PhotoView.class));
    }

    /**
     * user click send report button
     *
     * @param view
     */
    public void textReportOnClicked(View view) {
        String report = textReport.getText().toString();
        if (validTextInput(report)) {
            Toast.makeText(this, "connecting database ...", Toast.LENGTH_SHORT).show();
            sendTextReportToDB(report);
        } else {
            Toast.makeText(this, "input text not valid !", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * send text report to database
     *
     * @param report
     */
    private void sendTextReportToDB(String report) {
        new DAOtextReport(getApplicationContext()).addReport(new TextReport(report));
        Toast.makeText(this, "Report saved in local database!", Toast.LENGTH_SHORT).show();
        textReport.getText().clear();
    }

    /**
     * valid text input
     *
     * @param text
     * @return true if input text is valid, false otherwise
     */
    private boolean validTextInput(String text) {
        if (text.length() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_app_settings:
                startActivity(new Intent(this, AppSettings.class));
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
//            startActivity(new Intent(getBaseContext(), Login.class));
            finish();
        }
    };

    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
