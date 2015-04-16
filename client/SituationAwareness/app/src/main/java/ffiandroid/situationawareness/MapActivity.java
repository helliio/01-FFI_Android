package ffiandroid.situationawareness;


import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.json.JSONArray;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.bonuspack.cachemanager.CacheManager;
import org.osmdroid.bonuspack.overlays.InfoWindow;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.sql.SQLOutput;
import java.util.ArrayList;

import ffiandroid.situationawareness.datahandling.PerformBackgroundTask;
import ffiandroid.situationawareness.datahandling.StartSync;
import ffiandroid.situationawareness.localdb.DAOlocation;
import ffiandroid.situationawareness.model.LocationReport;
import ffiandroid.situationawareness.model.OSMmap;
import ffiandroid.situationawareness.model.ParameterSetting;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/15.
 * <p/>
 * responsible for this file: GuoJunjun & Simen
 */
public class MapActivity extends ActionBarActivity implements LocationListener, MapEventsReceiver {
    private MapView mMapView;
    private MapController mMapController;
    private LocationManager locationManager;
    private Location myCurrentLocation;
    public static Location newReportLocation;
    private String bestProvider;
    private Marker startMarker;
    private CacheManager cacheManager;
    private MapEventsOverlay mapEventsOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        activeOpenStreetMap();
        checkGpsAvailability();
        locationManager.requestLocationUpdates(bestProvider, ParameterSetting.getLocationUpdateTime(),
                ParameterSetting.getLocationUpdateDistance(), this);

        StartSync.getInstance(getApplicationContext()).start();


        mapEventsOverlay = new MapEventsOverlay(this, this);

        mMapView.getOverlays().add(0, mapEventsOverlay);


    }


    @Override
    protected void onResume() {

        super.onResume();
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

        mMapView.setUseDataConnection(true);
        //If true: will automatically download maps online.
        //If false: will only download map tiles through the cache map tiles command

        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(15);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        updateLocation();

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
            myCurrentLocation = new Location("none");
            myCurrentLocation.setLatitude(63.4305939);
            myCurrentLocation.setLongitude(10.3921571);

            System.out.println("No location found");
            Log.i("gps", "Could not find any locations stored on device");
        }

        startMarker = new Marker(mMapView);


        //        Reporting.reportMyLocation(myCurrentLocation);
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

    /**
     * add markers to map view
     *
     * @param jsonArray
     */
    private void addAllMarkers(JSONArray jsonArray) {
        //        ArrayList<OverlayItem> markersOverlayItemArray = new MyCoworkers().getCoworkerMarkersOverlay
        // (jsonArray);
        //                printteammarkers(markersOverlayItemArray);
        //        ItemizedIconOverlay<OverlayItem> markerItemizedIconOverlay =
        //                new ItemizedIconOverlay(this, markersOverlayItemArray, null);
        //        mMapView.getOverlays().add(markerItemizedIconOverlay);
        //        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        //        mMapView.getOverlays().add(myScaleBarOverlay);

    }

    /**
     * temporary function to print team markers
     *
     * @param a
     */
    private void printteammarkers(ArrayList<OverlayItem> a) {
        for (OverlayItem oi : a) {
            System.out.println(">>>>>>>>>>>:" + oi.getTitle().toString() + " La: " + oi.getPoint().getLatitude() + " " +
                    "Lo: " + oi.getPoint().getLongitude());
        }


    }

    //    public void textReportOnClicked(View view) {
    //        String report = textReport.getText().toString();
    //        if (validTextInput(report)) {
    //            Toast.makeText(this, "connecting database ...", Toast.LENGTH_SHORT).show();
    //            sendTextReportToServer(report);
    //        } else {
    //            Toast.makeText(this, "input text not valid !", Toast.LENGTH_LONG).show();
    //        }
    //    }
    public void refreshOnCLicked(View view) {
        updateLocation();
        System.out.println("Refreshed");
        Toast.makeText(getApplicationContext(), "Refreshing", Toast.LENGTH_LONG).show();

        //TODO Add timer to not flood app if pressed many times in a row
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map_view, menu);
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
            case R.id.menu_item_status:
                startActivity(new Intent(this, Status.class));
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
            case R.id.menu_item_cacheTiles:
                cacheTiles();
            case R.id.menu_item_status_and_send_button:
                statusAndSendButtonClicked();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contextmenu_map_view, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
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
            @Override public void run() {
                new PerformBackgroundTask(getApplicationContext()).execute();
            }
        });
    }

    @Override public void onLocationChanged(Location location) {
        UserInfo.setCurrentLatitude(location.getLatitude());
        UserInfo.setCurrentLongitude(location.getLongitude());
        mMapController.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
        updateMyPositionMarker(location);
        updateCoworkersPositionMarker();
        addMyNewPositionToDB();
    }

    /**
     * add my new position to local database
     */
    private void addMyNewPositionToDB() {
        new DAOlocation(getApplicationContext()).addLocation(new LocationReport(true));
    }

    /**
     * update coworkers position marker
     */
    public void updateCoworkersPositionMarker() {
        new Thread(getCoWorkerMarkerThread).start();
    }


    private Runnable getCoWorkerMarkerThread = new Runnable() {
        @Override public void run() {
            ArrayList<OverlayItem> markersOverlayItemArray =
                    new OSMmap().getCoworkerMarkersOverlay(getApplicationContext());
            if (markersOverlayItemArray.size() > 0) {
                addCoWorkerMarkers(markersOverlayItemArray);
            }
        }
    };

    private void addCoWorkerMarkers(ArrayList<OverlayItem> markersOverlayItemArray) {
        ItemizedIconOverlay<OverlayItem> markerItemizedIconOverlay =
                new ItemizedIconOverlay(this, markersOverlayItemArray, null);
        mMapView.getOverlays().add(markerItemizedIconOverlay);
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mMapView.getOverlays().add(myScaleBarOverlay);
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
        startMarker.setPosition(new GeoPoint(location.getLatitude(), location.getLongitude()));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        startMarker.setIcon(getResources().getDrawable(R.drawable.mypositionicon));
        startMarker.setTitle("My point");

        mMapView.getOverlays().add(startMarker);
        mMapView.invalidate();

    }




    @Override public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override public void onProviderEnabled(String provider) {

    }

    @Override public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {

        Toast.makeText(this, "Tapped on ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public boolean longPressHelper(GeoPoint p) {

        registerForContextMenu(mMapView);
        newReportLocation = new Location("");
        newReportLocation.setLatitude(p.getLatitude());
        newReportLocation.setLongitude(p.getLongitude());

        openContextMenu(mMapView);



        Toast.makeText(this, "Long press on ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();
        return false;
    }


}
