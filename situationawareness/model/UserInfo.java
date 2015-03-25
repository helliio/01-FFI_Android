package ffiandroid.situationawareness.model;

/**
 * Constant information of android id & user name + current location
 * <p/>
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 20/02/15.
 * <p/>
 * responsible for this file: GuoJunjun
 */
public class UserInfo {
    private static String myAndroidID, userID;
    public static double currentLatitude, currentLongitude;

    public static double getCurrentLatitude() {
        return currentLatitude;
    }

    public static void setCurrentLatitude(double currentLatitude) {
        UserInfo.currentLatitude = currentLatitude;
    }

    public static double getCurrentLongitude() {
        return currentLongitude;
    }

    public static void setCurrentLongitude(double currentLongitude) {
        UserInfo.currentLongitude = currentLongitude;
    }

    public static String getMyAndroidID() {
        return myAndroidID;
    }

    public static void setMyAndroidID(String myAndroidID) {
        UserInfo.myAndroidID = myAndroidID;
    }

    public static String getUserID() {
        return userID;
    }

    public static void setUserID(String userID) {
        UserInfo.userID = userID;
    }
}
