package ffiandroid.situationawareness.controller;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.ResourceProxy;
import org.osmdroid.bonuspack.cachemanager.CacheManager;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.R;
import ffiandroid.situationawareness.model.LocationReport;
import ffiandroid.situationawareness.model.OSMmap;
import ffiandroid.situationawareness.model.ParameterSetting;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.StatusListener;
import ffiandroid.situationawareness.model.TextReport;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.util.AsyncDrawable;
import ffiandroid.situationawareness.model.util.BitmapWorkerTask;
import ffiandroid.situationawareness.model.datahandling.PerformBackgroundTask;
import ffiandroid.situationawareness.model.datahandling.StartSync;
import ffiandroid.situationawareness.model.localdb.DAOlocation;

/**
 * This file is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/15.
 * <p/>
 * responsible for this file: GuoJunjun & Simen & Torgrim
 */
public class MapActivity extends ActionBarActivity implements LocationListener, MapEventsReceiver, StatusListener {
    private MapView mMapView;
    private MapController mMapController;
    private LocationManager locationManager;
    private Location myCurrentLocation;
    public static Location newReportLocation;
    private String bestProvider;
    private Marker startMarker;
    private CacheManager cacheManager;
    private MapEventsOverlay mapEventsOverlay;
    private String menuStatus;


    // NOTE(Torgrim): Added for testing purpose
    // TODO(Torgrim): Clean up unnecessary member variables, methods and imports
    private ArrayList<Marker> coworkersLocationMarkers = new ArrayList();
    private ArrayList<LocationReport> coworkersLocationReportsPresent = new ArrayList<>();
    private ArrayList<String> coworkersLocationReportsIDPresent = new ArrayList<>();

    private ArrayList<Marker> coworkersTextReportMarkers = new ArrayList<>();
    private ArrayList<TextReport> coworkerTextReportsPresent = new ArrayList<>();
    private ArrayList<String> coworkersTextReportsIDPresent = new ArrayList<>();

    private ArrayList<Marker> coworkersPhotoReportMarkers = new ArrayList<>();
    private ArrayList<PhotoReport> coworkersPhotoReportsPresent = new ArrayList<>();
    private ArrayList<String> currentPhotoReportsPresent = new ArrayList<>();

    private ArrayList<Marker> allCoworkersMarkers = new ArrayList<>();

    private static long timeSinceLastLocationUpload = 0;
    private static long timeSinceLastLocationDownload = 0;
    private static long timeOfLastLocationUpload = 0;
    private static long timeOfLastLocationDownload = 0;

    private static long timeSinceLastTextUpload = 0;
    private static long timeSinceLastTextDownload = 0;
    private static long timeOfLastTextDownload = 0;
    private static long timeOfLastTextUpload = 0;

    private static long timeSinceLastPhotoUpload = 0;
    private static long timeSinceLastPhotoDownload = 0;
    private static long timeOfLastPhotoUpload = 0;
    private static long timeOfLastPhotoDownload = 0;

    private long timeOfLastNewPositionAdded = 0;


    ArrayList<Marker> clusterMarkers = new ArrayList<>();

    private Marker topLeftMarker;
    private Marker topLeftMiddleMarker;
    private Marker topRightMiddleMarker;
    private Marker topRightMarker;

    private Marker centerTopLeftMarker;
    private Marker centerTopLeftMiddleMarker;
    private Marker centerTopRightMiddleMarker;
    private Marker centerTopRightMarker;

    private Marker centerBottomLeftMarker;
    private Marker centerBottomLeftMiddleMarker;
    private Marker centerBottomRightMiddleMarker;
    private Marker centerBottomRightMarker;

    private Marker bottomLeftMarker;
    private Marker bottomLeftMiddleMarker;
    private Marker bottomRightMiddleMarker;
    private Marker bottomRightMarker;

    private void setUpClusterMarkers() {

        topLeftMarker = new Marker(mMapView);
        topLeftMarker.setIcon(refreshClusterIconCanvas("0"));
        topLeftMarker.setInfoWindow(null);

        topLeftMiddleMarker = new Marker(mMapView);
        topLeftMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        topLeftMiddleMarker.setInfoWindow(null);

        topRightMiddleMarker = new Marker(mMapView);
        topRightMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        topRightMiddleMarker.setInfoWindow(null);

        topRightMarker = new Marker(mMapView);
        topRightMarker.setIcon(refreshClusterIconCanvas("0"));
        topRightMarker.setInfoWindow(null);

        centerTopLeftMarker = new Marker(mMapView);
        centerTopLeftMarker.setIcon(refreshClusterIconCanvas("0"));
        centerTopLeftMarker.setInfoWindow(null);

        centerTopLeftMiddleMarker = new Marker(mMapView);
        centerTopLeftMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        centerTopLeftMiddleMarker.setInfoWindow(null);

        centerTopRightMiddleMarker = new Marker(mMapView);
        centerTopRightMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        centerTopRightMiddleMarker.setInfoWindow(null);

        centerTopRightMarker = new Marker(mMapView);
        centerTopRightMarker.setIcon(refreshClusterIconCanvas("0"));
        centerTopRightMarker.setInfoWindow(null);

        centerBottomLeftMarker = new Marker(mMapView);
        centerBottomLeftMarker.setIcon(refreshClusterIconCanvas("0"));
        centerBottomLeftMarker.setInfoWindow(null);

        centerBottomLeftMiddleMarker = new Marker(mMapView);
        centerBottomLeftMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        centerBottomLeftMiddleMarker.setInfoWindow(null);

        centerBottomRightMiddleMarker = new Marker(mMapView);
        centerBottomRightMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        centerBottomRightMiddleMarker.setInfoWindow(null);

        centerBottomRightMarker = new Marker(mMapView);
        centerBottomRightMarker.setIcon(refreshClusterIconCanvas("0"));
        centerBottomRightMarker.setInfoWindow(null);

        bottomLeftMarker = new Marker(mMapView);
        bottomLeftMarker.setIcon(refreshClusterIconCanvas("0"));
        bottomLeftMarker.setInfoWindow(null);

        bottomLeftMiddleMarker = new Marker(mMapView);
        bottomLeftMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        bottomLeftMiddleMarker.setInfoWindow(null);

        bottomRightMiddleMarker = new Marker(mMapView);
        bottomRightMiddleMarker.setIcon(refreshClusterIconCanvas("0"));
        bottomRightMiddleMarker.setInfoWindow(null);

        bottomRightMarker = new Marker(mMapView);
        bottomRightMarker.setIcon(refreshClusterIconCanvas("0"));
        bottomRightMarker.setInfoWindow(null);


        clusterMarkers.add(topLeftMarker);
        clusterMarkers.add(topLeftMiddleMarker);
        clusterMarkers.add(topRightMiddleMarker);
        clusterMarkers.add(topRightMarker);
        clusterMarkers.add(centerTopLeftMarker);
        clusterMarkers.add(centerTopLeftMiddleMarker);
        clusterMarkers.add(centerTopRightMiddleMarker);
        clusterMarkers.add(centerTopRightMarker);
        clusterMarkers.add(centerBottomLeftMarker);
        clusterMarkers.add(centerBottomLeftMiddleMarker);
        clusterMarkers.add(centerBottomRightMiddleMarker);
        clusterMarkers.add(centerBottomRightMarker);
        clusterMarkers.add(bottomLeftMarker);
        clusterMarkers.add(bottomLeftMiddleMarker);
        clusterMarkers.add(bottomRightMiddleMarker);
        clusterMarkers.add(bottomRightMarker);


    }

    private BitmapDrawable refreshClusterIconCanvas(String text) {
        Drawable clusterIconD = getResources().getDrawable(R.drawable.marker_cluster);
        Bitmap clusterIcon = ((BitmapDrawable) clusterIconD).getBitmap();
        Bitmap finalIcon =
                Bitmap.createBitmap(clusterIcon.getWidth(), clusterIcon.getHeight(), clusterIcon.getConfig());
        Canvas iconCanvas = new Canvas(finalIcon);
        iconCanvas.drawBitmap(clusterIcon, 0, 0, null);
        Paint mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(20.0f);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);
        int textHeight = (int) (mTextPaint.descent() + mTextPaint.ascent());
        float mTextAnchorU = Marker.ANCHOR_CENTER, mTextAnchorV = Marker.ANCHOR_CENTER;
        iconCanvas.drawText(text, mTextAnchorU * clusterIcon.getWidth(),
                mTextAnchorV * clusterIcon.getHeight() - textHeight / 2, mTextPaint);

        BitmapDrawable bd = new BitmapDrawable(mMapView.getContext().getResources(), finalIcon);
        return bd;
    }


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("ACTION_LOGOUT"));
        activeOpenStreetMap();
        checkGpsAvailability();
        System.out.println("Size of mmapView overlay array on created: " + mMapView.getOverlays().size());
        locationManager.requestLocationUpdates(bestProvider, ParameterSetting.getLocationUpdateTime(),
                ParameterSetting.getLocationUpdateDistance(), this);
        UserInfo.addListener(this);
        formatMenuStatus();


        GeoPoint myLoc = new GeoPoint(myCurrentLocation);
        mMapController.setCenter(myLoc);
        StartSync.getInstance(getApplicationContext()).start();

        // NOTE(Torgrim): Added for tesing, might need to change this.
        mMapView.setMapListener(new MapListener() {
            @Override public boolean onScroll(ScrollEvent scrollEvent) {
                calculateMarkers();
                return true;
            }

            @Override public boolean onZoom(ZoomEvent zoomEvent) {
                calculateMarkers();
                return true;
            }

        });

        setUpClusterMarkers();

        //Used for creating menu from long click event
        mapEventsOverlay = new MapEventsOverlay(this, this);
        mMapView.getOverlays().add(0, mapEventsOverlay);

    }

    @Override protected void onResume() {

        super.onResume();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> MapActivity view Resumed");
    }


    /**
     * check if GPS enabled and if not send user to the GSP settings
     */
    private void checkGpsAvailability() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    /**
     * start open street map
     */
    private void activeOpenStreetMap() {
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setTileSource(new XYTileSource("MapQuest", ResourceProxy.string.mapquest_osm, 0, 18, 256, ".jpg",
                new String[]{"http://otile1.mqcdn.com/tiles/1.0.0/map/", "http://otile2.mqcdn.com/tiles/1.0.0/map/",
                        "http://otile3.mqcdn.com/tiles/1.0.0/map/", "http://otile4.mqcdn.com/tiles/1.0.0/map/"}));


        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

        /*
        If true: will automatically download maps online.
        If false: will only download map tiles through the cache map tiles command
         */
        mMapView.setUseDataConnection(true);

        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(32);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        updatePhotoMarkers();
        updateLocation();
        mMapController.setCenter(new GeoPoint(myCurrentLocation.getLatitude(), myCurrentLocation.getLongitude()));

    }


    private void updateLocation() {
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        bestProvider = locationManager.getBestProvider(criteria, false);
        // Get Current Location

        /*
        It will first try to find the location using the device's GPS functionality.
        If that is not possible it will instead use networks to determine the users location, or if
        that it also not possible it will just select a default position.

        If there exists a GPS location that is not older than 10 minutes, use this as the position
         */
        if (locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER) != null && System.currentTimeMillis() -
                locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER).getTime() < 600000) {
            myCurrentLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
            Toast.makeText(getApplicationContext(), "Updated position with gps", Toast.LENGTH_LONG).show();
            System.out.println("Updated with GPS");
            Log.i("gps", "Updated using recent GPS position from:" + Long.toString(myCurrentLocation.getTime()) +
                    " Current time is: " + System.currentTimeMillis());
            /*
            If the GPS location is old, it will try to use the network for determining location instead if possible
             */
        } else if (locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER) != null) {
            myCurrentLocation = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

            Toast.makeText(getApplicationContext(), "Updated position with Network location", Toast.LENGTH_LONG).show();
            System.out.println("Updated with Network");
            Log.i("gps", "Updated using Network location from:" + Long.toString(myCurrentLocation.getTime()) +
                    " Current time is: " + System.currentTimeMillis());

        }
           /*
           If the gps location is old, but there is no network location, it will still use the old GPS location.
            */
        else if (locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER) != null) {
            Toast.makeText(getApplicationContext(), "Updated position with old gps", Toast.LENGTH_LONG).show();
            System.out.println("Updated with old GPS");
            Log.i("gps", "Updated using Old GPS position from:" + Long.toString(myCurrentLocation.getTime()) +
                    " Current time is: " + System.currentTimeMillis());

            /*
            If there is no way of determining the location, it will just use a default lat long location(Here
            trondheim city centre)
             */
        } else {
            Toast.makeText(getApplicationContext(), "Could not find location, Using default", Toast.LENGTH_LONG).show();
            myCurrentLocation = new Location("default");
            myCurrentLocation.setLatitude(63.4305939);
            myCurrentLocation.setLongitude(10.3921571);

            System.out.println("No location found");
            Log.i("gps", "Could not find any locations stored on device");
        }
        if (startMarker == null) {
            startMarker = new Marker(mMapView);
        }

        //        Reporting.reportMyLocation(myCurrentLocation);
        newReportLocation = new Location(myCurrentLocation);
        onLocationChanged(myCurrentLocation);

    }

    /**
     * get coworkers location and add the to map view
     *
     * @return
     */
    private ArrayList<OverlayItem> getCoworkersLocation() {
        new Thread(queryThread).start();
        return null;
    }

    /**
     * use thread to run the server request
     */
    private Runnable queryThread = new Runnable() {
        @Override public void run() {
            Looper.prepare();
            //            JSONArray jsonArray =
            //                    new RequestService().getLocationsByTeam(UserInfo.getUSERNAME(),
            // UserInfo.getMYANDROIDID());
            //            System.out.println(jsonArray);
            //            if (jsonArray != null) {
            //                addAllMarkers(jsonArray);
            //
            //            }

            Looper.loop();
        }
    };


    public void refreshOnCLicked(View view) {
        updateLocation();
        System.out.println("Refreshed");
        Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_LONG).show();

        //TODO Add timer to not flood app if pressed many times in a row
    }

    // NOTE(Torgrim): Helper method to help with timeing of events..


    public static void getTimeSinceLastLocationDownload()
    {
        timeSinceLastLocationDownload = (System.currentTimeMillis() - timeOfLastLocationDownload);
        timeOfLastLocationDownload = System.currentTimeMillis();

        System.out.println("Time since last location Download = " + timeSinceLastLocationDownload + " ms");
    }

    public static void getTimeSinceLastLocationUpload()
    {

        timeSinceLastLocationUpload = (System.currentTimeMillis() - timeOfLastLocationUpload);
        timeOfLastLocationUpload = System.currentTimeMillis();

        System.out.println("Time since last location Upload = " + timeSinceLastLocationUpload + " ms");
    }

    public static void getTimeSinceLastTextUpload()
    {

        timeSinceLastTextUpload = (System.currentTimeMillis() - timeOfLastTextUpload);
        timeOfLastTextUpload = System.currentTimeMillis();

        System.out.println("Time since last text report upload = " + timeSinceLastTextUpload + " ms");
    }

    public static void getTimeSinceLastTextDownload()
    {

        timeSinceLastTextDownload = (System.currentTimeMillis() - timeOfLastTextDownload);
        timeOfLastTextDownload = System.currentTimeMillis();

        System.out.println("Time since last text report download = " + timeSinceLastTextDownload + " ms");
    }

    public static void getTimeSinceLastPhotoUpload()
    {

        timeSinceLastPhotoUpload = (System.currentTimeMillis() - timeOfLastPhotoUpload);
        timeOfLastPhotoUpload = System.currentTimeMillis();

        System.out.println("Time since last photo report upload = " + timeSinceLastPhotoUpload + " ms");
    }

    public static void getTimeSinceLastPhotoDownload()
    {

        timeSinceLastPhotoDownload = (System.currentTimeMillis() - timeOfLastPhotoDownload);
        timeOfLastPhotoDownload = System.currentTimeMillis();

        System.out.println("Time since last photo report download = " + timeSinceLastPhotoDownload + " ms");
    }



    @Override public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_view, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_app_settings:
                startActivity(new Intent(this, AppSettings.class));
                return true;
            case R.id.menu_item_report:
                newReportLocation = new Location(myCurrentLocation);
                startActivity(new Intent(this, Report.class));
                return true;
            case R.id.menu_item_status:
                startActivity(new Intent(this, Status.class));
                return true;
            case R.id.menu_item_all_reports:
                startActivity(new Intent(this, AllReportsView.class));
                return true;
            case R.id.menu_item_logout:
                logout();
                return true;
            case R.id.menu_item_cacheTiles:
                cacheTiles();
                return true;
            case R.id.menu_item_status_and_send_button:
                statusAndSendButtonClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu_map_view, menu);
    }

    @Override public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.contextmenu_item_add_marker:
                Toast.makeText(this, "Marker added", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.contextmenu_item_report_observation:
                startActivity(new Intent(this, Report.class));
                return true;
            case R.id.contextmenu_item_cancel:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    /**
     * status and send button at the top of each menu bar
     */
    private void statusAndSendButtonClicked() {
        Toast.makeText(this, "status send button clicked", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new PerformBackgroundTask(getApplicationContext()).execute();
            }
        });
    }

    @Override public void onLocationChanged(Location location) {
        UserInfo.setCurrentLatitude(location.getLatitude());
        UserInfo.setCurrentLongitude(location.getLongitude());
        updateMyPositionMarker(location);
        updateCoworkersPositionMarker();
        addMyNewPositionToDB();
        mMapView.invalidate();
    }

    /**
     * add my new position to local database
     */
    private void addMyNewPositionToDB() {
        long timeSinceLastPositionWasAdded = (System.currentTimeMillis() - timeOfLastNewPositionAdded);
        timeOfLastNewPositionAdded = System.currentTimeMillis();

        System.out.println("Time since last my position was added to the local database... " + timeSinceLastPositionWasAdded + " ms");
        DAOlocation daOlocation = new DAOlocation(getApplicationContext());
        try {
            daOlocation.addLocation(new LocationReport(true));
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            daOlocation.close();
        }
    }

    /**
     * update coworkers position marker
     */
    public void updateCoworkersPositionMarker() {
        new Thread(getCoWorkerMarkerThread).start();
    }


    private Runnable getCoWorkerMarkerThread = new Runnable() {
        @Override public void run() {

            long startTime = System.currentTimeMillis();
            OSMmap osmMap = new OSMmap();
            ArrayList<LocationReport> coworkerLocationReportsList =
                    osmMap.getAllCoworkersLocationReports(getApplicationContext());
            for (LocationReport report : coworkerLocationReportsList) {
                String reportID = (report.getDatetimeLong() + report.getUserid());
                if (!coworkersLocationReportsIDPresent.contains(reportID)) {
                    Marker coworkerMarker = new Marker(mMapView);
                    coworkerMarker.setPosition(new GeoPoint(report.getLatitude(), report.getLongitude()));
                    coworkerMarker.setIcon(getResources().getDrawable(R.drawable.teammembericon));

                    coworkerMarker.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble_black, mMapView) {
                        @Override public void onOpen(Object o) {
                            System.out.println(
                                    "==================== Info window should be open now!! ========================");
                        }

                        @Override public void onClose() {
                            System.out.println(
                                    "==================== Info window should be closed now!! ========================");
                        }
                    });
                    String info = "User: " + report.getName() + "\n";
                    info += "Latitude: " + coworkerMarker.getPosition().getLatitude() + "\n";
                    info += "Longitude:" + coworkerMarker.getPosition().getLongitude() + "\n";
                    ((TextView) coworkerMarker.getInfoWindow().getView().findViewById(R.id.black_bubble_title))
                            .setText("This is the title of a location report");
                    ((TextView) coworkerMarker.getInfoWindow().getView().findViewById(R.id.black_bubble_description))
                            .setText(info);
                    allCoworkersMarkers.add(coworkerMarker);
                    coworkersLocationReportsPresent.add(report);
                    coworkersLocationReportsIDPresent.add(reportID);
                    mMapView.getOverlays().add(coworkerMarker);


                } else {
                    System.out.println(
                            " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>This location report has already been " +
                                    "added<<<<<<<<<<<<<<<<");
                }
            }


            // NOTE(Torgrim): Get all team members text reports
            List<TextReport> coworkersTextReportsList = osmMap.getAllCoworkersTextReports(getApplicationContext());
            for (TextReport report : coworkersTextReportsList) {
                String reportID = (report.getDatetimeLong() + report.getUserid());
                if (!coworkersTextReportsIDPresent.contains(reportID)) {

                    Marker marker = new Marker(mMapView);
                    marker.setIcon(getResources().getDrawable(R.drawable.observationicon));
                    marker.setPosition(new GeoPoint(report.getLatitude(), report.getLongitude()));
                    marker.setInfoWindow(new MarkerInfoWindow(R.layout.bonuspack_bubble_black, mMapView) {
                        @Override public void onOpen(Object o) {
                            System.out.println(
                                    "========================TextReport Info window should now be open " +
                                            "======================");
                        }

                        @Override public void onClose() {
                            System.out.println(
                                    "======================== TextReport Info Window should now be closed " +
                                            "=====================");
                        }
                    });
                    String info = "User: " + report.getName() + "\n";
                    info += "Latitude: " + report.getLatitude() + "\n";
                    info += "Longitude:" + report.getLongitude() + "\n";
                    info += "-----------------------------------------\n";
                    info += "Acutal report: " + report.getReport();
                    ((TextView) marker.getInfoWindow().getView().findViewById(R.id.black_bubble_title))
                            .setText("This is the title of a Text report");
                    ((TextView) marker.getInfoWindow().getView().findViewById(R.id.black_bubble_description))
                            .setText(info);

                    coworkerTextReportsPresent.add(report);
                    coworkersTextReportsIDPresent.add(reportID);
                    allCoworkersMarkers.add(marker);
                    mMapView.getOverlays().add(marker);
                } else {
                    System.out.println(
                            ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>This text report is already " +
                                    "present<<<<<<<<<<<<<<<<<<<<<<<<<");
                }


            }


            System.out.println("Current number of location report markers: " + coworkersLocationReportsPresent.size());
            System.out.println("Current number of text report markers: " + coworkerTextReportsPresent.size());
            System.out.println("Current number of photo report markers: " + currentPhotoReportsPresent.size());
            System.out.println("Current number of All report markers: " + allCoworkersMarkers.size());


            double finalTime = ((double) System.currentTimeMillis() - (double) startTime) / 1000;
            System.out.println("The time it took to get coworker reports: " + finalTime + " Seconds");

        }
    };

    // NOTE(Torgrim): Function to update photo reports on map,
    // Called for every zoom and drag event on the map
    // TODO(Torgrim): Check and clean up this method
    public void updatePhotoMarkers() {

        List<PhotoReport> photoReports = new OSMmap().getAllPhotoReports(getApplicationContext());
        for(final PhotoReport report : photoReports)
        {
            if (report.getPath() != null && !currentPhotoReportsPresent.contains(report.getPicId()))
            {
                currentPhotoReportsPresent.add(report.getPicId());

                final Marker marker = new Marker(mMapView);
                marker.setIcon(getResources().getDrawable(R.drawable.teampositionicon));
                marker.setPosition(new GeoPoint(report.getLatitude(), report.getLongitude()));
                marker.setInfoWindow(new MarkerInfoWindow(R.layout.black_bubble_photo_report, mMapView) {

                    Bitmap bitmap = null;
                    @Override
                    public void onOpen(Object o)
                    {
                        ImageView view = ((ImageView) marker.getInfoWindow().getView().findViewById(R.id.black_bubble_photo_content));
                        marker.getInfoWindow().getView().findViewById(R.id.black_bubble_photo_content).setEnabled(true);
                        Bitmap pl = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ic_launcher);
                        Bitmap placeHolderBitmap = Bitmap.createScaledBitmap(pl, 256, 256, true);
                        if (AsyncDrawable.cancelPotentialWork(report.getPath(), view)) {
                            final BitmapWorkerTask task = new BitmapWorkerTask(view);
                            final AsyncDrawable asyncDrawable =
                                    new AsyncDrawable(getApplicationContext().getResources(), placeHolderBitmap, task);
                            view.setImageDrawable(asyncDrawable);
                            task.execute(report.getPath());
                        }

                    }

                    @Override
                    public void onClose()
                    {

                        if(bitmap != null)
                        {
                            bitmap.recycle();
                        }

                        System.out.println("======================== PhotoReport Info Window should now be closed =====================");
                    }
                });

                // TODO(Torgrim): Fix that only one photo from the current user shows up???
                String info = "User: " + report.getName() + "\n";
                info += "Latitude: " + report.getLatitude() + "\n";
                info += "Longitude:" + report.getLongitude() + "\n";
                info += "-----------------------------------------\n";
                info += "Title: " + report.getTitle() + "\n";
                info += "Description: " + report.getDescription() + "\n\n";

                ((TextView) marker.getInfoWindow().getView().findViewById(R.id.black_bubble_title))
                        .setText("This is the title of a PhotoReport report");
                ((TextView) marker.getInfoWindow().getView().findViewById(R.id.black_bubble_description)).setText(info);
                marker.setRelatedObject(report);
                coworkersPhotoReportsPresent.add(report);
                allCoworkersMarkers.add(marker);
                coworkersPhotoReportMarkers.add(marker);
                mMapView.getOverlays().add(marker);
            }
        }

    }

    // NOTE(Torgrim): Added by Torgrim for testing purposes
    // to calculate the marker view status, meaning that
    // we're trying to calculate whether to cluster or single markers
    private boolean calculateMarkers() {

        double startTime = System.currentTimeMillis();
        int count = 0;
        updatePhotoMarkers();
        if (allCoworkersMarkers.size() > 0) {
            for (Marker marker : allCoworkersMarkers) {
                if (mMapView.getBoundingBox().contains(marker.getPosition())) {
                    if (count <= 1000 || mMapView.getZoomLevel() == mMapView.getMaxZoomLevel()) {
                        marker.setEnabled(true);
                        count++;
                    } else {
                        break;
                    }
                } else {
                    marker.setEnabled(false);
                }
            }
        }
        if (count > 100 && mMapView.getZoomLevel() != mMapView.getMaxZoomLevel()) {
            System.out.println("Ready for Clustering");
            topLeftMarker.setEnabled(true);
            topLeftMiddleMarker.setEnabled(true);
            topRightMiddleMarker.setEnabled(true);
            topRightMarker.setEnabled(true);
            centerTopLeftMarker.setEnabled(true);
            centerTopLeftMiddleMarker.setEnabled(true);
            centerTopRightMiddleMarker.setEnabled(true);
            centerTopRightMarker.setEnabled(true);
            centerBottomLeftMarker.setEnabled(true);
            centerBottomLeftMiddleMarker.setEnabled(true);
            centerBottomRightMiddleMarker.setEnabled(true);
            centerBottomRightMarker.setEnabled(true);
            bottomLeftMarker.setEnabled(true);
            bottomLeftMiddleMarker.setEnabled(true);
            bottomRightMiddleMarker.setEnabled(true);
            bottomRightMarker.setEnabled(true);
            calculateCluster();
        } else {
            topLeftMarker.setEnabled(false);
            topLeftMiddleMarker.setEnabled(false);
            topRightMiddleMarker.setEnabled(false);
            topRightMarker.setEnabled(false);
            centerTopLeftMarker.setEnabled(false);
            centerTopLeftMiddleMarker.setEnabled(false);
            centerTopRightMiddleMarker.setEnabled(false);
            centerTopRightMarker.setEnabled(false);
            centerBottomLeftMarker.setEnabled(false);
            centerBottomLeftMiddleMarker.setEnabled(false);
            centerBottomRightMiddleMarker.setEnabled(false);
            centerBottomRightMarker.setEnabled(false);
            bottomLeftMarker.setEnabled(false);
            bottomLeftMiddleMarker.setEnabled(false);
            bottomRightMiddleMarker.setEnabled(false);
            bottomRightMarker.setEnabled(false);
            //System.out.println("==================== Current screen does not contain more than 100 markers");
        }
        //System.out.println("Current Number of Markers Disabled: " + (allCoworkersMarkers.size() - count));
        //System.out.println("Current Number of Markers Enabled: " + count);
        double finalTime = ((double) System.currentTimeMillis() - (double) startTime) / 1000;
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>The time it took to calculate markers:  " + finalTime + "
        // Seconds");
        return false;
    }

    private void setAllMarkersToEnabled() {
        // TODO(Torgrim): Remember to change the list so that all markers are taken into account
        for (Marker marker : coworkersLocationMarkers) {
            marker.setEnabled(true);
        }
    }

    private void setAllMarkersToDisabled() {
        // TODO(Torgrim): Remember to change the list so that all markers are taken into account
        for (Marker marker : coworkersLocationMarkers) {
            marker.setEnabled(false);
        }
    }

    private void calculateCluster() {
        final float DIVIDER = 1000000.000000f;
        float latitudeSouth = (float) mMapView.getBoundingBox().getLatSouthE6() / DIVIDER;
        float latitudeNorth = (float) mMapView.getBoundingBox().getLatNorthE6() / DIVIDER;
        float longitudeWest = (float) mMapView.getBoundingBox().getLonWestE6() / DIVIDER;
        float longitudeEast = (float) mMapView.getBoundingBox().getLonEastE6() / DIVIDER;
        float latitudeSpan = (float) mMapView.getLatitudeSpan() / DIVIDER;
        float longitudeSpan = (float) mMapView.getLongitudeSpan() / DIVIDER;

        float latitudeSpanQuarter = (latitudeSpan / 4.0f);
        float longitudeSpanQuarter = (longitudeSpan / 4.0f);

        float latitudeFirstMiddle = (latitudeNorth - latitudeSpanQuarter);
        float latitudeSecondMiddle = (latitudeFirstMiddle - latitudeSpanQuarter);
        float latitudeThirdMiddle = (latitudeSecondMiddle - latitudeSpanQuarter);

        float longitudeFirstMiddle = (longitudeWest + longitudeSpanQuarter);
        float longitudeSecondMiddle = (longitudeFirstMiddle + longitudeSpanQuarter);
        float longitudeThirdMiddle = (longitudeSecondMiddle + longitudeSpanQuarter);


        int topLeftCount = 0;
        int topLeftMiddleCount = 0;
        int topRightMiddleCount = 0;
        int topRightCount = 0;

        int centerTopLeftCount = 0;
        int centerTopLeftMiddleCount = 0;
        int centerTopRightMiddleCount = 0;
        int centerTopRightCount = 0;

        int centerBottomLeftCount = 0;
        int centerBottomLeftMiddleCount = 0;
        int centerBottomRightMiddleCount = 0;
        int centerBottomRightCount = 0;

        int bottomLeftCount = 0;
        int bottomLeftMiddleCount = 0;
        int bottomRightMiddleCount = 0;
        int bottomRightCount = 0;


        System.out.println("---------Latitude South-----------------        " + latitudeSouth);
        System.out.println("---------Latitude North-----------------        " + latitudeNorth);
        System.out.println("---------Longitude West-----------------        " + longitudeWest);
        System.out.println("---------Longitude East-----------------        " + longitudeEast);
        System.out.println("---------Latitude Span-----------------         " + latitudeSpan);
        System.out.println("---------Longitude Span-----------------        " + longitudeSpan);
        System.out.println("---------Latitude Half Span-----------------    " + latitudeSpanQuarter);
        System.out.println("---------Longitude Half Span-----------------   " + longitudeSpanQuarter);
        System.out.println("---------Latitude First Middle-----------------       " + latitudeFirstMiddle);
        System.out.println("---------Latitude Second Middle-----------------      " + latitudeSecondMiddle);
        System.out.println("---------Latitude Third Middle-----------------      " + latitudeThirdMiddle);
        System.out.println("---------Longitude First Middle-----------------      " + longitudeFirstMiddle);
        System.out.println("---------Longitude Second Middle-----------------      " + longitudeSecondMiddle);
        System.out.println("---------Longitude Third Middle-----------------      " + longitudeThirdMiddle);


        for (Marker marker : allCoworkersMarkers) {
            marker.setEnabled(false);
            marker.getInfoWindow().close();

            GeoPoint position = marker.getPosition();

            // NOTE(Torgrim): First Column to scan
            if (position.getLatitude() < latitudeNorth && position.getLatitude() > latitudeFirstMiddle &&
                    position.getLongitude() > longitudeWest && position.getLongitude() < longitudeFirstMiddle) {
                topLeftCount++;
            }
            if (position.getLatitude() < latitudeFirstMiddle && position.getLatitude() > latitudeSecondMiddle &&
                    position.getLongitude() > longitudeWest && position.getLongitude() < longitudeFirstMiddle) {
                centerTopLeftCount++;
            }
            if (position.getLatitude() < latitudeSecondMiddle && position.getLatitude() > latitudeThirdMiddle &&
                    position.getLongitude() > longitudeWest && position.getLongitude() < longitudeFirstMiddle) {
                centerBottomLeftCount++;
            }
            if (position.getLatitude() < latitudeThirdMiddle && position.getLatitude() > latitudeSouth &&
                    position.getLongitude() > longitudeWest && position.getLongitude() < longitudeFirstMiddle) {
                bottomLeftCount++;
            }

            // NOTE(Torgrim): Second Column to scan
            if (position.getLatitude() < latitudeNorth && position.getLatitude() > latitudeFirstMiddle &&
                    position.getLongitude() > longitudeFirstMiddle && position.getLongitude() < longitudeSecondMiddle) {
                topLeftMiddleCount++;
            }
            if (position.getLatitude() < latitudeFirstMiddle && position.getLatitude() > latitudeSecondMiddle &&
                    position.getLongitude() > longitudeFirstMiddle && position.getLongitude() < longitudeSecondMiddle) {
                centerTopLeftMiddleCount++;
            }
            if (position.getLatitude() < latitudeSecondMiddle && position.getLatitude() > latitudeThirdMiddle &&
                    position.getLongitude() > longitudeFirstMiddle && position.getLongitude() < longitudeSecondMiddle) {
                centerBottomLeftMiddleCount++;
            }
            if (position.getLatitude() < latitudeThirdMiddle && position.getLatitude() > latitudeSouth &&
                    position.getLongitude() > longitudeFirstMiddle && position.getLongitude() < longitudeSecondMiddle) {
                bottomLeftMiddleCount++;
            }


            // NOTE(Torgrim)Third Column to scan
            if (position.getLatitude() < latitudeNorth && position.getLatitude() > latitudeFirstMiddle &&
                    position.getLongitude() > longitudeSecondMiddle && position.getLongitude() < longitudeThirdMiddle) {
                topRightMiddleCount++;
            }
            if (position.getLatitude() < latitudeFirstMiddle && position.getLatitude() > latitudeSecondMiddle &&
                    position.getLongitude() > longitudeSecondMiddle && position.getLongitude() < longitudeThirdMiddle) {
                centerTopRightMiddleCount++;
            }
            if (position.getLatitude() < latitudeSecondMiddle && position.getLatitude() > latitudeThirdMiddle &&
                    position.getLongitude() > longitudeSecondMiddle && position.getLongitude() < longitudeThirdMiddle) {
                centerBottomRightMiddleCount++;
            }
            if (position.getLatitude() < latitudeThirdMiddle && position.getLatitude() > latitudeSouth &&
                    position.getLongitude() > longitudeSecondMiddle && position.getLongitude() < longitudeThirdMiddle) {
                bottomRightMiddleCount++;
            }


            // NOTE(Torgrim): Fourth Column to scan
            if (position.getLatitude() < latitudeNorth && position.getLatitude() > latitudeFirstMiddle &&
                    position.getLongitude() > longitudeThirdMiddle && position.getLongitude() < longitudeEast) {
                topRightCount++;
            }
            if (position.getLatitude() < latitudeFirstMiddle && position.getLatitude() > latitudeSecondMiddle &&
                    position.getLongitude() > longitudeThirdMiddle && position.getLongitude() < longitudeEast) {
                centerTopRightCount++;
            }
            if (position.getLatitude() < latitudeSecondMiddle && position.getLatitude() > latitudeThirdMiddle &&
                    position.getLongitude() > longitudeThirdMiddle && position.getLongitude() < longitudeEast) {
                centerBottomRightCount++;
            }
            if (position.getLatitude() < latitudeThirdMiddle && position.getLatitude() > latitudeSouth &&
                    position.getLongitude() > longitudeThirdMiddle && position.getLongitude() < longitudeEast) {
                bottomRightCount++;
            }


        }


        mMapView.getOverlayManager().removeAll(clusterMarkers);
        // First
        if (topLeftCount > 0) {

            topLeftMarker.setPosition(new GeoPoint((latitudeNorth - (latitudeSpanQuarter / 2.0f)),
                    longitudeWest + (longitudeSpanQuarter / 2.0f)));
            topLeftMarker.setIcon(refreshClusterIconCanvas(((Integer) topLeftCount).toString()));
            mMapView.getOverlays().add(topLeftMarker);
        }
        if (topLeftMiddleCount > 0) {

            topLeftMiddleMarker.setPosition(new GeoPoint((latitudeNorth - (latitudeSpanQuarter / 2.0f)),
                    longitudeFirstMiddle + (longitudeSpanQuarter / 2.0f)));
            topLeftMiddleMarker.setIcon(refreshClusterIconCanvas(((Integer) topLeftMiddleCount).toString()));

            mMapView.getOverlays().add(topLeftMiddleMarker);
        }
        if (topRightMiddleCount > 0) {

            topRightMiddleMarker.setPosition(new GeoPoint((latitudeNorth - (latitudeSpanQuarter / 2.0f)),
                    longitudeSecondMiddle + (longitudeSpanQuarter / 2.0f)));
            topRightMiddleMarker.setIcon(refreshClusterIconCanvas(((Integer) topRightMiddleCount).toString()));
            mMapView.getOverlays().add(topRightMiddleMarker);
        }
        if (topRightCount > 0) {
            topRightMarker.setPosition(new GeoPoint((latitudeNorth - (latitudeSpanQuarter / 2.0f)),
                    longitudeThirdMiddle + (longitudeSpanQuarter / 2.0f)));
            topRightMarker.setIcon(refreshClusterIconCanvas(((Integer) topRightCount).toString()));
            mMapView.getOverlays().add(topRightMarker);
        }


        // Second
        if (centerTopLeftCount > 0) {
            centerTopLeftMarker.setPosition(new GeoPoint((latitudeFirstMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeWest + (longitudeSpanQuarter / 2.0f)));
            centerTopLeftMarker.setIcon(refreshClusterIconCanvas(((Integer) centerTopLeftCount).toString()));
            mMapView.getOverlays().add(centerTopLeftMarker);
        }
        if (centerTopLeftMiddleCount > 0) {
            centerTopLeftMiddleMarker.setPosition(new GeoPoint((latitudeFirstMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeFirstMiddle + (longitudeSpanQuarter / 2.0f)));
            centerTopLeftMiddleMarker
                    .setIcon(refreshClusterIconCanvas(((Integer) centerTopLeftMiddleCount).toString()));
            mMapView.getOverlays().add(centerTopLeftMiddleMarker);
        }
        if (centerTopRightMiddleCount > 0) {
            centerTopRightMiddleMarker.setPosition(new GeoPoint((latitudeFirstMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeSecondMiddle + (longitudeSpanQuarter / 2.0f)));
            centerTopRightMiddleMarker
                    .setIcon(refreshClusterIconCanvas(((Integer) centerTopRightMiddleCount).toString()));
            mMapView.getOverlays().add(centerTopRightMiddleMarker);
        }
        if (centerTopRightCount > 0) {
            centerTopRightMarker.setPosition(new GeoPoint((latitudeFirstMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeThirdMiddle + (longitudeSpanQuarter / 2.0f)));
            centerTopRightMarker.setIcon(refreshClusterIconCanvas(((Integer) centerTopRightCount).toString()));
            mMapView.getOverlays().add(centerTopRightMarker);
        }


        //Third
        if (centerBottomLeftCount > 0) {
            centerBottomLeftMarker.setPosition(new GeoPoint((latitudeSecondMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeWest + (longitudeSpanQuarter / 2.0f)));
            centerBottomLeftMarker.setIcon(refreshClusterIconCanvas(((Integer) centerBottomLeftCount).toString()));
            mMapView.getOverlays().add(centerBottomLeftMarker);
        }
        if (centerBottomLeftMiddleCount > 0) {
            centerBottomLeftMiddleMarker.setPosition(new GeoPoint((latitudeSecondMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeFirstMiddle + (longitudeSpanQuarter / 2.0f)));
            centerBottomLeftMiddleMarker
                    .setIcon(refreshClusterIconCanvas(((Integer) centerBottomLeftMiddleCount).toString()));
            mMapView.getOverlays().add(centerBottomLeftMiddleMarker);
        }
        if (centerBottomRightMiddleCount > 0) {
            centerBottomRightMiddleMarker.setPosition(
                    new GeoPoint((latitudeSecondMiddle - (latitudeSpanQuarter / 2.0f)),
                            longitudeSecondMiddle + (longitudeSpanQuarter / 2.0f)));
            centerBottomRightMiddleMarker
                    .setIcon(refreshClusterIconCanvas(((Integer) centerBottomRightMiddleCount).toString()));
            mMapView.getOverlays().add(centerBottomRightMiddleMarker);
        }
        if (centerBottomRightCount > 0) {
            centerBottomRightMarker.setPosition(new GeoPoint((latitudeSecondMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeThirdMiddle + (longitudeSpanQuarter / 2.0f)));
            centerBottomRightMarker.setIcon(refreshClusterIconCanvas(((Integer) centerBottomRightCount).toString()));
            mMapView.getOverlays().add(centerBottomRightMarker);
        }


        // Fourth
        if (bottomLeftCount > 0) {
            bottomLeftMarker.setPosition(new GeoPoint((latitudeThirdMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeWest + (longitudeSpanQuarter / 2.0f)));
            bottomLeftMarker.setIcon(refreshClusterIconCanvas(((Integer) bottomLeftCount).toString()));
            mMapView.getOverlays().add(bottomLeftMarker);
        }

        if (bottomLeftMiddleCount > 0) {
            bottomLeftMiddleMarker.setPosition(new GeoPoint((latitudeThirdMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeFirstMiddle + (longitudeSpanQuarter / 2.0f)));
            bottomLeftMiddleMarker.setIcon(refreshClusterIconCanvas(((Integer) bottomLeftMiddleCount).toString()));
            mMapView.getOverlays().add(bottomLeftMiddleMarker);
        }

        if (bottomRightMiddleCount > 0) {
            bottomRightMiddleMarker.setPosition(new GeoPoint((latitudeThirdMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeSecondMiddle + (longitudeSpanQuarter / 2.0f)));
            bottomRightMiddleMarker.setIcon(refreshClusterIconCanvas(((Integer) bottomRightMiddleCount).toString()));
            mMapView.getOverlays().add(bottomRightMiddleMarker);
        }

        if (bottomRightCount > 0) {
            bottomRightMarker.setPosition(new GeoPoint((latitudeThirdMiddle - (latitudeSpanQuarter / 2.0f)),
                    longitudeThirdMiddle + (longitudeSpanQuarter / 2.0f)));
            bottomRightMarker.setIcon(refreshClusterIconCanvas(((Integer) bottomRightCount).toString()));
            mMapView.getOverlays().add(bottomRightMarker);
        }


        System.out.println("Top Left Count: " + topLeftCount);
        System.out.println("Top Right Count: " + topRightCount);
        System.out.println("Bottom Left Count: " + bottomLeftCount);
        System.out.println("Bottom Right Count: " + bottomRightCount);

    }


    private void cacheTiles() {


        cacheManager = new CacheManager(mMapView);
        int zoomMin = 11;
        int zoomMax = 17;
        cacheManager.downloadAreaAsync(this, mMapView.getBoundingBox(), zoomMin, zoomMax);

    }

    /**
     * my position marker
     *
     * @param location
     */
    private void updateMyPositionMarker(Location location) {
        mMapView.getOverlays().remove(startMarker);
        startMarker.setPosition(new GeoPoint(location.getLatitude(), location.getLongitude()));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        mMapView.getOverlays().add(startMarker);
        mMapView.invalidate();
        startMarker.setIcon(getResources().getDrawable(R.drawable.mypositionicon));
        startMarker.setTitle("My point");


        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override public boolean onMarkerClick(Marker marker, MapView mapView) {
                System.out.println(">>>>>>>I guess you just touched yourself!!>>>>>>>>>>>>");
                return true;
            }
        });

    }


    @Override public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override public void onProviderEnabled(String provider) {

    }

    @Override public void onProviderDisabled(String provider) {

    }


    @Override public boolean singleTapConfirmedHelper(GeoPoint p) {

        System.out.println("Tapped on (" + p.getLatitude() + "," + p.getLongitude() + ")");
        return true;
    }


    @Override
    /*
    If user presses long on the map it opens a menu where
    it can go to submit an observation about the particular point
    on the map through the report page
    // TODO(Torgrim): Fix so that the latitude and longitude always have E6
     */ public boolean longPressHelper(GeoPoint p) {

        registerForContextMenu(mMapView);
        newReportLocation = new Location("");
        newReportLocation.setTime(System.currentTimeMillis());
        newReportLocation.setLatitude((p.getLatitudeE6() / 1000000.000000f));
        newReportLocation.setLongitude((p.getLongitudeE6() / 1000000.000000f));
        openContextMenu(mMapView);


        Toast.makeText(this, "Long press on (" + (p.getLatitudeE6() / 1000000.000000f) + "," + (p.getLongitudeE6() / 1000000.000000f) + ")", Toast.LENGTH_SHORT)
        .show();
        return false;
    }

    /**
     * logout function for logout
     */
    public void logout() {
        rememberMeDelete();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ACTION_LOGOUT");
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
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
            LocalBroadcastManager.getInstance(context).unregisterReceiver(mMessageReceiver);
            startActivity(new Intent(context, Login.class));
            finish();
        }
    };


    @Override
    protected void onPause()
    {
        super.onPause();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> MapActivity view Paused");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> MapActivity view Restarted");
    }
    @Override
    protected void onStop()
    {
        super.onStop();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> MapActivity view Stopped");
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> MapActivity view started");
    }




    @Override protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Map Activity Destroyed");
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
        //        int count = UserInfo.getTotalUnReportedItemsCount();
        //        if (count == 0) {
        //            menuStatus = "NO un-reported items !";
        //        } else {
        //            menuStatus = count + " items not reported! send now?";
        //        }
        // new methods as customer wishes: detailed count by category
        menuStatus = UserInfo.getReportDetails();
    }


    @Override public void menuStatusChanged() {
        formatMenuStatus();
    }

    @Override public void locationStatusChanged() {

    }

    @Override public void textStatusChanged() {

    }

    @Override public void photoStatusChanged() {

    }

    @Override public void lastReportStatusChanged() {
    }


}
