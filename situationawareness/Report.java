

package ffiandroid.situationawareness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ffiandroid.situationawareness.localdb.DAOtextReport;
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
        textReport = (EditText) findViewById(R.id.report_edit_text_report);
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
