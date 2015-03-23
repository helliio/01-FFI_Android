package ffiandroid.situationawareness;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ffiandroid.situationawareness.model.UserInfo;

/**
 * This Status Class is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 * <p/>
 * responsible for this file: GuoJunjun
 */
public class Status extends ActionBarActivity {
    private TextView userId, lastSync, unReportedPhotos, unReportedText, unReportedLocations, currentLocationLatitude,
            currentLocationLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);

        userId = (TextView) findViewById(R.id.status_userid);
        lastSync = (TextView) findViewById(R.id.status_last_sync_to_server);
        unReportedText = (TextView) findViewById(R.id.status_not_reported_text);
        unReportedPhotos = (TextView) findViewById(R.id.status_not_reported_photos);
        unReportedLocations = (TextView) findViewById(R.id.status_not_reported_location_items);
        currentLocationLatitude = (TextView) findViewById(R.id.status_current_location_latitude);
        currentLocationLongitude = (TextView) findViewById(R.id.status_current_location_longitude);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_status, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_app_settings:
                startActivity(new Intent(this, AppSettings.class));
                return true;
            case R.id.menu_item_report:
                startActivity(new Intent(this, Report.class));
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
                startActivity(new Intent(this, Login.class));
                return true;
            case R.id.menu_item_location_view:
                startActivity(new Intent(this, LocationView.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
