package ffiandroid.situationawareness.controller;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import ffiandroid.situationawareness.model.util.Coder;

/**
 * Created by Torgrim on 13/05/2015.
 */
public class AllReportsView extends ActionBarActivity implements StatusListener
{
    private DAOlocation daOlocation;

    private DAOtextReport daOtextReport;

    //private ArrayList<PhotoReport> images;
    private ArrayList<AdapterContentHolder> listContent;
    private ImageAdapter imageAdapter;
    private ListView listView;
    private Uri mCapturedImageURI;

    private String menuStatus;
    private String photoPath;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // LocationReport View OnCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reports_view);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));


        // Construct the data source
        //images = new ArrayList();
        listContent = new ArrayList<>();
        // Create the adapter to convert the array to views
        imageAdapter = new ImageAdapter(this, listContent);
        // Attach the adapter to a ListView
        listView = (ListView) findViewById(R.id.ReportListView);
        listView.setAdapter(imageAdapter);
        addItemClickListener(listView);

        checkFilterCheckboxesAndPopulateListView();
        formatMenuStatus();
        //initDB();
    }

    @Override protected void onResume() {

        // LocationReport view OnResume
        /*
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getList());
        ListView listView = (ListView) findViewById(R.id.ReportListView);
        listView.setAdapter(adapter);
        addItemClickListener(listView);
        */
        super.onResume();
        checkFilterCheckboxesAndPopulateListView();


    }

    @Override public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.menu_item_status_and_send_button);
        menuItem.setTitle(String.valueOf(menuStatus));
        return super.onPrepareOptionsMenu(menu);
    }



    // LocationReport View Methods
    /**
     * get all data entries from database as a string list
     *
     * @return String [] list
     */
    public void refreshLocationAndTextReportList() {
        try {
            daOlocation = new DAOlocation(getApplicationContext());
            List<LocationReport> locationReportlist = daOlocation.getAllLocations();


            daOtextReport = new DAOtextReport(getApplicationContext());
            List<TextReport> textReportlist = daOtextReport.getAllTextReports();

            int totalListSize = (locationReportlist.size() + textReportlist.size());
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
     * Toast the clicked item
     *
     * @param listView
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_all_reports, menu);
        return true;
    }

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
     * delete remembered information from a user
     */
    private void rememberMeDelete() {
        getSharedPreferences(Login.PREFS_NAME, MODE_PRIVATE).edit().putString(Login.PREF_USERNAME, null)
                .putString(Login.PREF_PASSWORD, null).commit();
    }


    @Override protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }



    // PhotoReport View Methods
    /**
     * initialize database
     */
    // NOTE(Torgrim): Is this necessary??
    private void initDB() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshImageList();
            }
        });
    }

    /**
     * refresh image list
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
            daOphoto.close();
        }


    }

    // NOTE(Torgrim): Added for testing of filtering
    public void onCheckboxClicked(View view) {


        checkFilterCheckboxesAndPopulateListView();
    }


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
     * receive broadcast for logout
     */
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //            startActivity(new Intent(getBaseContext(), Login.class));
            finish();
        }
    };

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






    @Override protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI", mCapturedImageURI.toString());
        }
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(savedInstanceState.getString("mCapturedImageURI"));
        }
    }


}
