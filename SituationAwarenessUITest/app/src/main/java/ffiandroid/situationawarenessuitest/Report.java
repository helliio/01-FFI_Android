package ffiandroid.situationawarenessuitest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by liang on 08/04/15.
 */
public class Report extends ActionBarActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        Button photoButton;
        photoButton = (Button) findViewById(R.id.button_photo);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "This is a ui test, let´s pretend that a photo is taken", Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_item_LatestReports:
                startActivity(new Intent(this, LatestReports.class));
                finish();
                return true;
            case R.id.menu_item_ARL:
                startActivity(new Intent(this, AutomatedReportLog.class));
                finish();
                return true;
            case R.id.menu_item_Logout:
                startActivity(new Intent(this, Login.class));
                finish();
                return true;
            case R.id.menu_item_main:
                startActivity(new Intent(this, MapsActivity.class));
                finish();
                return true;
            case R.id.menu_item_settings:
                startActivity(new Intent(this, Settings.class));
                finish();
                return true;
            case R.id.menu_item_status:
                startActivity(new Intent(this, Status.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
