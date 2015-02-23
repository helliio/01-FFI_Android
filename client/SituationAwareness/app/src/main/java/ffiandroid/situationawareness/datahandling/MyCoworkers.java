package ffiandroid.situationawareness.datahandling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

/**
 * information from my coworkers
 * <p/>
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 22/02/15.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class MyCoworkers {
    public ArrayList<OverlayItem> getCoworkerMarkersOverlay(JSONArray jsonArray) {

        ArrayList<OverlayItem> markersOverlayItemArray = new ArrayList();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                CoworkerLocation cl = getCoworkerLocation(jsonArray.getJSONObject(i));
                System.out.println("cl: - - - - - -" + cl.getName() + " " + cl.getLatitude() + " " + cl.getLongitude());
                markersOverlayItemArray.add(new OverlayItem(cl.getName(), cl.getName(),
                        new GeoPoint(cl.getLatitude(), cl.getLongitude())));
            }
            return markersOverlayItemArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param jsonObject
     * @return CoworkerLocation Object
     */
    public CoworkerLocation getCoworkerLocation(JSONObject jsonObject) {
        try {
            return new CoworkerLocation(jsonObject.getString("name"), jsonObject.getDouble("latitude"),
                    jsonObject.getDouble("longitude"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}