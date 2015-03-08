package ffiandroid.situationawareness;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;

import ffiandroid.situationawareness.model.OSMmap;
import ffiandroid.situationawareness.model.ParameterSetting;

/**
 * This file is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 2/20/15.
 * <p/>
 * responsible for this file: GuoJunjun & Simen
 */
public class MapActivity extends ActionBarActivity implements LocationListener {
    private MapView mMapView;
    private MapController mMapController;
    private LocationManager locationManager;
    private Location myCurrentLocation;
    private String bestProvider;
    private Marker startMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_view);
        activeOpenStreetMap();
        checkGpsAvailability();
    }

    @Override protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(bestProvider, ParameterSetting.LOCATION_UPDATE_TIME,
                ParameterSetting.LOCATION_UPDATE_DISTANCE, this);
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
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(16);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Get the name of the best provider
        bestProvider = locationManager.getBestProvider(criteria, false);
        // Get Current Location

        if (myCurrentLocation != null) {
            myCurrentLocation = locationManager.getLastKnownLocation(bestProvider);
            Toast.makeText(getApplicationContext(), "Using Location from your GPS!", Toast.LENGTH_LONG).show();
        } else {
            myCurrentLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Toast.makeText(getApplicationContext(), "No GPS Single, getting Location form your Network Provider!",
                    Toast.LENGTH_LONG).show();
        }
        startMarker = new Marker(mMapView);
        onLocationChanged(myCurrentLocation);
        updateCoworkersPositionMarker();
        //        Reporting.reportMyLocation(myCurrentLocation);
    }

    //    /**
    //     * get coworkers location and add the to map view
    //     *
    //     * @return
    //     */
    //    private ArrayList<OverlayItem> getCoworkersLocation() {
    //        new Thread(queryThread).start();
    //        return null;
    //    }
    //
    //    /**
    //     * use thread to run the server request
    //     */
    //    private Runnable queryThread = new Runnable() {
    //        @Override public void run() {
    //            Looper.prepare();
    //            JSONArray jsonArray =
    //                    new RequestService().getLocationsByTeam(UserInfo.getUSERID(), UserInfo.getMYANDROIDID());
    //            System.out.println(jsonArray);
    //            if (jsonArray != null) {
    //                addAllMarkers(jsonArray);
    //
    //            }
    //            Looper.loop();
    //        }
    //    };
    //
    //    /**
    //     * add markers to map view
    //     *
    //     * @param jsonArray
    //     */
    //    private void addAllMarkers(JSONArray jsonArray) {
    //        ArrayList<OverlayItem> markersOverlayItemArray = new MyCoworkers().getCoworkerMarkersOverlay(jsonArray);
    //        //                printteammarkers(markersOverlayItemArray);
    //        ItemizedIconOverlay<OverlayItem> markerItemizedIconOverlay =
    //                new ItemizedIconOverlay(this, markersOverlayItemArray, null);
    //        mMapView.getOverlays().add(markerItemizedIconOverlay);
    //        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
    //        mMapView.getOverlays().add(myScaleBarOverlay);
    //
    //    }
    //
    //    /**
    //     * temporary function to print team markers
    //     *
    //     * @param a
    //     */
    //    private void printteammarkers(ArrayList<OverlayItem> a) {
    //        for (OverlayItem oi : a) {
    //            System.out.println(">>>>>>>>>>>:" + oi.getTitle().toString() + " La: " + oi.getPoint().getLatitude
    // () + " " +
    //                    "Lo: " + oi.getPoint().getLongitude());
    //        }
    //
    //    }

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
                finish();
                startActivity(new Intent(this, AppSettings.class));
                return true;
            case R.id.menu_item_report:
                finish();
                startActivity(new Intent(this, Report.class));
                return true;
            case R.id.menu_item_status:
                finish();
                startActivity(new Intent(this, Status.class));
                return true;
            case R.id.menu_item_report_view:
                finish();
                startActivity(new Intent(this, ReportView.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override public void onLocationChanged(Location location) {
        mMapController.setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
        updateMyPositionMarker(location);
    }

    /**
     * update coworkers position marker
     */
    public void updateCoworkersPositionMarker() {
        ArrayList<OverlayItem> markersOverlayItemArray =
                new OSMmap().getCoworkerMarkersOverlay(getApplicationContext());
        ItemizedIconOverlay<OverlayItem> markerItemizedIconOverlay =
                new ItemizedIconOverlay(this, markersOverlayItemArray, null);
        mMapView.getOverlays().add(markerItemizedIconOverlay);
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(this);
        mMapView.getOverlays().add(myScaleBarOverlay);
    }

    /**
     * my position marker
     *
     * @param location
     */
    private void updateMyPositionMarker(Location location) {
        startMarker.setPosition(new GeoPoint(location.getLatitude(), location.getLongitude()));
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mMapView.getOverlays().add(startMarker);
        mMapView.invalidate();
        startMarker.setIcon(getResources().getDrawable(R.drawable.mypositionicon));
        startMarker.setTitle("My point");
    }

    @Override public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override public void onProviderEnabled(String provider) {

    }

    @Override public void onProviderDisabled(String provider) {

    }
}
