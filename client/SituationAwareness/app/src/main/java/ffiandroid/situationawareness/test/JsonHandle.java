package ffiandroid.situationawareness.test;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * handle Json
 * <p/>
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 22/02/15.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class JsonHandle {

    /**
     * convert a json string array list to CoworkerLocation array list
     *
     * @param s : json string array list
     * @return : array list contains CoworkerLocations
     */
    public static ArrayList<CoworkerLocation> getCoworkerLocations(String s) {
        ArrayList<CoworkerLocation> cwls = new ArrayList<>();
        try {
            JSONObject jlist = new JSONObject(s);
            for (int i = 0; i < jlist.length(); i++) {
                //                cwls.add(getCoworkerLocation(jlist))
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * convert a json string object to a coworkerLocation object
     *
     * @param s : a Json string
     * @return CoworkerLocation object
     */
    public static CoworkerLocation getCoworkerLocation(String s) {
        try {
            return new CoworkerLocation(new JSONObject(s).getString("name"), new JSONObject(s).getDouble("latitude"),
                    new JSONObject(s).getDouble("longitude"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
