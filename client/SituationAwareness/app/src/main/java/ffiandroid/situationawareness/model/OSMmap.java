package ffiandroid.situationawareness.model;

import android.content.Context;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.localdb.DAOlocation;

/**
 * This OSMmap File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class OSMmap {
    /**
     * @param context
     * @return a markers Overlay Item Array
     */
    public ArrayList<OverlayItem> getCoworkerMarkersOverlay(Context context) {
        ArrayList<OverlayItem> markersOverlayItemArray = new ArrayList();
        DAOlocation daOlocation = new DAOlocation(context);
        List<LocationReport> locationReports = daOlocation.getCoWorkerLocations(UserInfo.getUSERID());

        for (LocationReport lr : locationReports) {
            markersOverlayItemArray.add(new OverlayItem(lr.getUserid(), lr.getUserid(),
                    new GeoPoint(lr.getLatitude(), lr.getLongitude())));
        }
        daOlocation.close();
        return markersOverlayItemArray;
    }

}
