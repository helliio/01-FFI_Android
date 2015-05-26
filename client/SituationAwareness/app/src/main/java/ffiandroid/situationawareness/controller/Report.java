package ffiandroid.situationawareness.controller;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.TextReport;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.StatusListener;

import ffiandroid.situationawareness.model.localdb.DAOphoto;
import ffiandroid.situationawareness.model.localdb.DAOtextReport;
import ffiandroid.situationawareness.model.util.AsyncDrawable;
import ffiandroid.situationawareness.model.util.BitmapWorkerTask;
import ffiandroid.situationawareness.model.util.Coder;

/**
 * This Report Class is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/2015.
 * <p/>
 * responsible for this file: GuoJunjun
 */
public class Report extends ActionBarActivity implements StatusListener{
    private EditText reportDescription;
    private EditText reportTitle;
    private TextView textLocation;
    private Location location;
    private String menuStatus;

    private Uri mCapturedImageURI;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        reportTitle = (EditText) findViewById(R.id.report_title);
        reportDescription = (EditText) findViewById(R.id.report_edit_text_report);
        textLocation = (TextView) findViewById(R.id.coordinates);

        if(MapActivity.newReportLocation == null && savedInstanceState != null)
        {
            System.out.println("Saved instance latitude" + savedInstanceState.getString("latitude"));
            System.out.println("Saved instance longitude" + savedInstanceState.getString("longitude"));
            textLocation.setText("Lat: " + savedInstanceState.getString("latitude") + "Long:" + savedInstanceState.getString("longitude"));
        }
        else if(MapActivity.newReportLocation != null)
        {
            location = MapActivity.newReportLocation;
            System.out.println("Location from mapActivity: ");
            System.out.println("Latitude: " + location.getLatitude());
            System.out.println("Longitude: " + location.getLongitude());
            textLocation.setText("Lat: " + location.getLatitude() + "Long:" + location.getLongitude());
        }
        else
        {
            textLocation.setText("Lat: No Location Available Long: No location Available");
        }
        formatMenuStatus();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> Report view Resumed");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> Report view Paused");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> Report view Restarted");
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> Report view Stopped");
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> Report view started");
    }
    /**
     * user click add photo button
     */
    public void addPhotoButtonOnClicked(View view){
        if(photoPath == null) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.setTitle("New Photo Report");
            Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
            btnExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.btnChoosePath).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activeGallery();
                    dialog.dismiss();
                }
            });
            dialog.findViewById(R.id.btnTakePhoto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activeTakePhoto();
                    dialog.dismiss();
                }
            });
            // show dialog on screen
            dialog.show();
        }
        else
        {
            ((ImageView)findViewById(R.id.attached_image)).setImageBitmap(null);
            ((Button)findViewById(R.id.btn_photo)).setText("Attach Photo");
            ((TextView)findViewById(R.id.attached_image_path)).setText("Photo Path:");
            photoPath = null;

        }
    }

    /**
     * take a photo
     */
    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String fileName = "temp.jpg";
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            mCapturedImageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            takePictureIntent.putExtra("location", location);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * to gallery
     */
    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (requestCode == RESULT_LOAD_IMAGE &&
                        resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    photoPath = cursor.getString(columnIndex);
                    cursor.close();
                    checkImageAddressAndLoadBitmap(photoPath);
                }
            case REQUEST_IMAGE_CAPTURE:
                if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(mCapturedImageURI, projection, null, null, null);
                    int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    photoPath = cursor.getString(column_index_data);
                    checkImageAddressAndLoadBitmap(photoPath);
                }
        }
    }



    private void checkImageAddressAndLoadBitmap(String photoPath)
    {
        if(photoPath != null)
        {
            // TODO(Torgrim): Check that the image file actually exists
            ((TextView)findViewById(R.id.attached_image_path)).setText("Photo Path: " + photoPath);
            ImageView view = (ImageView)(findViewById(R.id.attached_image));
            Bitmap pl = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher);
            Bitmap placeHolderBitmap = Bitmap.createScaledBitmap(pl, 256, 256, true);
            if (AsyncDrawable.cancelPotentialWork(photoPath, view)) {
                final BitmapWorkerTask task = new BitmapWorkerTask(view);
                final AsyncDrawable asyncDrawable =
                        new AsyncDrawable(getApplicationContext().getResources(), placeHolderBitmap, task);
                view.setImageDrawable(asyncDrawable);
                task.execute(photoPath);
            }

            ((Button)findViewById(R.id.btn_photo)).setText("Remove Photo");

        }
    }


    /* user click send report button
     *
     * @param view
     */
    public void sendReportOnClicked(View view) {
        String report = reportDescription.getText().toString();
        if(photoPath == null)
        {
            if (validTextInput(report)) {
                Toast.makeText(this, "connecting database ...", Toast.LENGTH_SHORT).show();
                sendTextReportToDB(report);
            } else {
                Toast.makeText(this, "input text not valid !", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            sendPhotoReportToDB();

        }
    }

    /**
     * send text report to database
     *
     * @param report
     */
    private void sendTextReportToDB(String report) {
        DAOtextReport daOtextReport = null;
        try
        {
            daOtextReport = new DAOtextReport(getApplicationContext());
            daOtextReport.addReport(new TextReport(report, System.currentTimeMillis(), location.getLatitude(), location.getLongitude()));
            Toast.makeText(this, "Report saved in local database!", Toast.LENGTH_SHORT).show();
            ((ImageView)findViewById(R.id.attached_image)).setImageBitmap(null);
            ((Button)findViewById(R.id.btn_photo)).setText("Attach Photo");
            ((TextView)findViewById(R.id.attached_image_path)).setText("Photo Path:");
            photoPath = null;
            reportTitle.getText().clear();
            reportDescription.getText().clear();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally {
            if(daOtextReport != null){
                daOtextReport.close();
            }
        }

    }


    private void sendPhotoReportToDB() {
        DAOphoto daoPhotoReport = null;
        try
        {
            daoPhotoReport = new DAOphoto(getApplicationContext());
            PhotoReport photoReport = new PhotoReport();
            photoReport.setUserid(Coder.encryptMD5(UserInfo.getUserID()));

            if(reportTitle.getText().toString().length() > 0) {
                photoReport.setTitle(reportTitle.getText().toString());
            }
            else
            {
                photoReport.setTitle("This is the Title of a photo report");
            }
            if(reportDescription.getText().toString().length() > 0) {
                photoReport.setDescription(reportDescription.getText().toString());
            }
            else
            {
                photoReport.setDescription("This is the content of the photo report");
            }
            photoReport.setDatetime(System.currentTimeMillis());
            photoReport.setName(UserInfo.getName());
            photoReport.setLatitude(location.getLatitude());
            photoReport.setLongitude(location.getLongitude());
            String extension = photoPath.substring(photoPath.indexOf("."), photoPath.length());
            photoReport.setExtension(extension);
            // TODO(Torgrim): Check that the extension is correct
            System.out.println("New photo report extension::::: " + extension);
            photoReport.setPath(photoPath);
            photoReport.setIsreported(false);

            daoPhotoReport.addPhoto(photoReport);
            Toast.makeText(this, "Report saved in local database!", Toast.LENGTH_SHORT).show();

            ((ImageView)findViewById(R.id.attached_image)).setImageBitmap(null);
            ((Button)findViewById(R.id.btn_photo)).setText("Attach Photo");
            ((TextView)findViewById(R.id.attached_image_path)).setText("Photo Path:");
            photoPath = null;
            reportDescription.getText().clear();
            reportTitle.getText().clear();
        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally {
            if(daoPhotoReport != null){
                daoPhotoReport.close();
            }
        }


        System.out.println("ID1 ====================== Time the report was saved to the local Database: " + System.currentTimeMillis());
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
            case R.id.menu_item_map_view:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            case R.id.menu_item_all_reports:
                startActivity(new Intent(this, AllReportsView.class));
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


    @Override protected void onSaveInstanceState(Bundle outState) {
        // Save the user's current game state
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);
        if (mCapturedImageURI != null) {
            outState.putString("mCapturedImageURI", mCapturedImageURI.toString());
            if(location != null) {
                outState.putString("latitude", Double.toString(location.getLatitude()));
                outState.putString("longitude", Double.toString(location.getLongitude()));
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Inside on save instance state ");
    }

    @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy

        // Restore state members from saved instance
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("mCapturedImageURI")) {
            mCapturedImageURI = Uri.parse(savedInstanceState.getString("mCapturedImageURI"));
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Inside Restore Instance State");
    }


    @Override protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> Report view Destroyed");
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
