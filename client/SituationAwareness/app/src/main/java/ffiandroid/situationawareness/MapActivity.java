package ffiandroid.situationawareness;


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.osmdroid.ResourceProxy;
import org.osmdroid.bonuspack.cachemanager.CacheManager;
import org.osmdroid.bonuspack.overlays.InfoWindow;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.datahandling.PerformBackgroundTask;
import ffiandroid.situationawareness.datahandling.StartSync;
import ffiandroid.situationawareness.localdb.DAOlocation;
import ffiandroid.situationawareness.model.LocationReport;
import ffiandroid.situationawareness.model.OSMmap;
import ffiandroid.situationawareness.model.ParameterSetting;
import ffiandroid.situationawareness.model.TextReport;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/15.
 * <p/>
 * responsible for this file: GuoJunjun & Simen
 */
public class MapActivity extends ActionBarActivity implements LocationListener  {
    private MapView mMapView;
    private MapController mMapController;
    private LocationManager locationManager;
    private Location myCurrentLocation;
    private String bestProvider;
    private Marker startMarker;
    private CacheManager cacheManager;


    // NOTE(Torgrim): Added for testing purpose
    private ArrayList<Marker> coworkersLocationMarkers = new ArrayList();
    private ArrayList<Marker> coworkersTextReportMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);

        activeOpenStreetMap();
        checkGpsAvailability();
        locationManager.requestLocationUpdates(bestProvider, ParameterSetting.getLocationUpdateTime(),
                ParameterSetting.getLocationUpdateDistance(), this);

        StartSync.getInstance(getApplicationContext()).start();

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

        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(32);
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


            // NOTE(Torgrim): Added for testing purposes the get all coworkers location reports,
            // add markers to them and add them to the map...
            // Will probably do this in the OSMmap class later
            ArrayList<OverlayItem> coworkerLocationReportsList =
                    new OSMmap().getAllCoworkersLocationReports(getApplicationContext());
            for(OverlayItem item : coworkerLocationReportsList)
            {
                item.setMarker(getResources().getDrawable(R.drawable.mypositionicon));
                Marker coworkerMarker = new Marker(mMapView);
                coworkerMarker.setPosition(item.getPoint());
                coworkerMarker.setIcon(getResources().getDrawable(R.drawable.mypositionicon));
                coworkerMarker.setTitle(item.getTitle());
                coworkerMarker.setInfoWindow(new InfoWindow(R.layout.mapinfowindow, mMapView) {
                    @Override
                    public void onOpen(Object o) {
                        System.out.println("==================== Info window should be open now!! ========================");
                    }

                    @Override
                    public void onClose() {
                        System.out.println("==================== Info window should be closed now!! ========================");
                    }
                });
                String info = "User: " + coworkerMarker.getTitle() + "\n";
                info += "Latitude: " + coworkerMarker.getPosition().getLatitude() + "\n";
                info += "Longitude:" + coworkerMarker.getPosition().getLongitude() + "\n";
                setTextForPopup(coworkerMarker.getInfoWindow().getView(), info );
                coworkerMarker.setInfoWindowAnchor((float)coworkerMarker.getPosition().getLatitude(),(float) coworkerMarker.getPosition().getLongitude());
                coworkerMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> You just touched a coworker >>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        if(!marker.getInfoWindow().isOpen())
                        {

                            marker.getInfoWindow().open(marker, marker.getPosition(), 0, -100);
                        }
                        else
                        {
                            marker.getInfoWindow().close();
                        }
                        return true;
                    }
                });
                coworkersLocationMarkers.add(coworkerMarker);




            }

            //TODO(Torgrim): Check to see the difference between localdb doa and server doa...
            // LocationDoa in local db seems to only take locations from locationReport table, while
            // locationDoa in server db seems to use both textreport location and locationreport location????

            // NOTE(Torgrim): Get all team members text reports
            List<TextReport> coworkersTextReportsList = new OSMmap().getAllCoworkersTextReports(getApplicationContext());
            for(TextReport report : coworkersTextReportsList)
            {
                Marker marker = new Marker(mMapView);
                marker.setIcon(getResources().getDrawable(R.drawable.mypositionicon));
                marker.setPosition(new GeoPoint(report.getLatitude(), report.getLongitude()));
                marker.setInfoWindow(new InfoWindow(R.layout.mapinfowindow, mMapView) {
                    @Override
                    public void onOpen(Object o)
                    {
                        System.out.println("========================TextReport Info window should now be open ======================");
                    }

                    @Override
                    public void onClose()
                    {
                        System.out.println("======================== TextReport Info Window should now be closed =====================");
                    }
                });


                String info = "User: " + report.getUserid() + "\n";
                info += "Latitude: " + report.getLatitude() + "\n";
                info += "Longitude:" + report.getLongitude() + "\n";
                info += "-----------------------------------------\n";
                info += "Acutal report: " + report.getReport();
                setTextForPopup(marker.getInfoWindow().getView(), info );

                marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker, MapView mapView) {
                        if(!marker.getInfoWindow().isOpen())
                        {

                            marker.getInfoWindow().open(marker, marker.getPosition(), 0, -100);
                        }
                        else
                        {
                            marker.getInfoWindow().close();
                        }
                        return true;
                    }
                });
                coworkersTextReportMarkers.add(marker);


            }

            if (coworkerLocationReportsList.size() > 0) {
                addCoWorkerMarkers(coworkerLocationReportsList);
            }
            if(coworkersTextReportsList.size() > 0)
            {
                addCoworkersTextReportMarkers(coworkersTextReportMarkers);
            }
        }
    };

    private void addCoWorkerMarkers(ArrayList<OverlayItem> markersOverlayItemArray) {
        /*


        ItemizedIconOverlay<OverlayItem> markerItemizedIconOverlay =
                new ItemizedIconOverlay(this, markersOverlayItemArray, null);
        mMapView.getOverlays().add(markerItemizedIconOverlay);
        */
        for(Marker marker : coworkersLocationMarkers)
        {

            mMapView.getOverlays().add(marker);
        }
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mMapView.getOverlays().add(myScaleBarOverlay);
    }

    private void addCoworkersTextReportMarkers(ArrayList<Marker> markerList)
    {
        for(Marker marker : markerList)
        {

            mMapView.getOverlays().add(marker);
        }
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
        mMapView.getOverlays().add(startMarker);
        mMapView.invalidate();
        startMarker.setIcon(getResources().getDrawable(R.drawable.mypositionicon));
        startMarker.setTitle("My point");
        startMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>I guess you just touched yourself!!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
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

    // NOTE(Torgrim): Added to be used for testing the popup window on map...
    public void setTextForPopup(View view, String text)
    {
        ((TextView)((LinearLayout)view).getChildAt(0)).setText(text);
    }
}
