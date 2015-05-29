package ffiandroid.situationawareness.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.ImageAdapter;
import ffiandroid.situationawareness.model.LocationReport;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.StatusListener;
import ffiandroid.situationawareness.model.TextReport;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.localdb.DAOlocation;
import ffiandroid.situationawareness.model.localdb.DAOphoto;
import ffiandroid.situationawareness.model.localdb.DAOtextReport;
import ffiandroid.situationawareness.model.util.AdapterContentHolder;

/**
 * @author      Torgrim Bøe Skårsmoen
 * @version     1.0
 * @since       2015-05-2015
 *
 * The main activity for view all of the report currently in the local database
 */
public class AllReportsView extends ActionBarActivity implements StatusListener
{
    private DAOlocation daOlocation;

    private DAOtextReport daOtextReport;
    private ArrayList<AdapterContentHolder> listContent;
    private ImageAdapter imageAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;

    private String menuStatus;



    /**
     * Is part of android apps life cycle and is called automatically
     * by the android system. For more info see the android developer manual
     * on activity life cycle
     * @param  savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LocationReport View OnCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports_view);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));


        // Construct the data source
        listContent = new ArrayList<>();
        // Create the adapter to convert the array to views
        imageAdapter = new ImageAdapter(this, listContent);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.ReportListView);
        listView.setAdapter(imageAdapter);
        addItemClickListener(listView);

        checkFilterCheckboxesAndPopulateListView();
        formatMenuStatus();
    }
    /**
     * Is part of android apps life cycle and is called automatically
     * by the android system. For more info see the android developer manual
     * on activity life cycle
     */
    @Override protected void onResume() {
        super.onResume();
        checkFilterCheckboxesAndPopulateListView();


    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_item_status_and_send_button);
        menuItem.setTitle(String.valueOf(menuStatus));
        return super.onPrepareOptionsMenu(menu);
    }



    /**
     * Refreshes the location report list and
     * text report list used to create the content
     * for this view
     */
    public void refreshLocationAndTextReportList() {
        try {
            daOlocation = new DAOlocation(getApplicationContext());
            List<LocationReport> locationReportlist = daOlocation.getAllLocations();


            daOtextReport = new DAOtextReport(getApplicationContext());
            List<TextReport> textReportlist = daOtextReport.getAllTextReports();

            for (int i = 0; i < locationReportlist.size(); i++) {
                listContent.add(new AdapterContentHolder(null, locationReportlist.get(i).toString()));

            }
            for (int i = 0; i < textReportlist.size(); i++)
            {
                listContent.add(new AdapterContentHolder(null, textReportlist.get(i).toString()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {

            daOlocation.close();
            daOtextReport.close();
        }

    }

    /**
     * Is called when one of the items in the list view
     * is clicked. If this is a {@link LocationReport} or
     * {@link TextReport} just the location or the location
     * and the content of the report is shown. If the clicked
     * item is a {@link PhotoReport} the user is taken to a separate
     * image view
     *
     * @param listView The list view that holds the item that the has been clicked
     */
    private void addItemClickListener(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AdapterContentHolder contentHolder = (AdapterContentHolder) listView.getItemAtPosition(position);
                if (contentHolder.report != null) {
                    Toast.makeText(getApplicationContext(), "Position: " + position + " item: " + contentHolder.report,
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (contentHolder.photoReport.getPath().contains(".")) {
                            Intent intent = new Intent(getBaseContext(), ImageDisplay.class);
                            intent.putExtra("IMAGE", (new Gson()).toJson(contentHolder.photoReport));
                            startActivity(intent);
                        }
                    } catch (NullPointerException e) {
                        Toast.makeText(getApplicationContext(), "Image is still being downloaded", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    /**
     * Is called automatically by android.
     * For more info see the android developer guide or
     * API specification
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_all_reports, menu);
        return true;
    }
    /**
     * Is called automatically by android.
     * For more info see the android developer guide or
     * API specification
     *
     * @param item the menu item selected
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_map_view:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            case R.id.menu_item_report:
                startActivity(new Intent(this, Report.class));
                return true;
            case R.id.menu_item_app_settings:
                startActivity(new Intent(this, AppSettings.class));
                return true;
            case R.id.menu_item_status:
                startActivity(new Intent(this, Status.class));
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
     * Delete the saved preference used to automatically login
     * These field are username, password, ip address and user's full name
     */
    private void rememberMeDelete() {
        getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE).edit().putString(Login.PREF_USERNAME, null)
                .putString(Login.PREF_PASSWORD, null).putString(Login.PREF_NAME, null).putString(Login.PREF_SERVERIP,null).commit();
    }

    /**
     * Is part of android apps life cycle and is called automatically
     * by the android system. For more info see the android developer manual
     * on activity life cycle
     */
    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    /**
     * Is called to refresh the {@link PhotoReport} in this view
     */
    private void refreshImageList() {
        DAOphoto daOphoto = null;
        try {
            daOphoto = new DAOphoto(this);
            AdapterContentHolder contentHolder;
            for (PhotoReport report : daOphoto.getAllPhotos()) {
                contentHolder = new AdapterContentHolder(report, null);
                //images.add(report);
                listContent.add(contentHolder);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(daOphoto != null) {
                daOphoto.close();
            }
        }


    }

    /**
     * Is called when one of the checkboxes used for filtering is clicked.
     * The only thing this method does is call {@link #checkFilterCheckboxesAndPopulateListView()}.
     * @param view
     */
    public void onCheckboxClicked(View view) {
        checkFilterCheckboxesAndPopulateListView();
    }

    /**
     * Is used to determine the current state of all the checkboxes in this view
     * and filters the list view accordingly
     *
     */
    private void checkFilterCheckboxesAndPopulateListView()
    {
        CheckBox location_report = (CheckBox)findViewById(R.id.location_report_filter);
        CheckBox text_report = (CheckBox)findViewById(R.id.text_report_filter);
        CheckBox photo_report = (CheckBox)findViewById(R.id.photo_report_filter);

        listContent.clear();

        if(!location_report.isChecked() && !text_report.isChecked() && !photo_report.isChecked())
        {
            refreshLocationAndTextReportList();
            refreshImageList();
        }
        else {
            try {
                if (location_report.isChecked()) {

                    daOlocation = new DAOlocation(getApplicationContext());
                    List<LocationReport> locationReportlist = daOlocation.getAllLocations();
                    for (int i = 0; i < locationReportlist.size(); i++) {
                        listContent.add(new AdapterContentHolder(null, locationReportlist.get(i).toString()));
                    }

                }
                if (text_report.isChecked()) {

                    daOtextReport = new DAOtextReport(getApplicationContext());
                    List<TextReport> textReportlist = daOtextReport.getAllTextReports();
                    for (int i = 0; i < textReportlist.size(); i++) {
                        listContent.add(new AdapterContentHolder(null, textReportlist.get(i).toString()));
                    }
                }
                if (photo_report.isChecked()) {
                    refreshImageList();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                daOlocation.close();
                daOtextReport.close();
            }
        }

        imageAdapter.notifyDataSetChanged();
    }

    /**
     * Receive broadcast for logout
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    /**
     * Get value from UserInfo and assign it to menu status
     */
    private void formatMenuStatus() {
        menuStatus = UserInfo.getReportDetails();
    }

    /**
     * Called automatically when the menu status changed to update this view's menu status
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
     * last time report succeed or failed
     */
    @Override public void lastReportStatusChanged() {

    }





    /**
     * This method is part of android apps life cycle and is called automatically
     * by the android system. For more info see the android developer manual
     * or API
     */
    @Override protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        super.onSaveInstanceState(outState);
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI", mCapturedImageURI.toString());
        }
        // Always call the superclass so it can save the view hierarchy state
    }
    /**
     * This method is part of android apps life cycle and is called automatically
     * by the android system. For more info see the android developer manual or
     * API
     */
    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(savedInstanceState.getString("mCapturedImageURI"));
        }
    }


}
