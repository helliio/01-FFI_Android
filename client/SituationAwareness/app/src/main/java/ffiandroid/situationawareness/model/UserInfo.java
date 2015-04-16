package ffiandroid.situationawareness.model;

import java.util.ArrayList;

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
    private static double currentLatitude = 0;
    private static double currentLongitude = 0;
    private static boolean lastSyncSucceed = false;
    private static int unReportedLocations = 0;
    private static int unReportedPhotos = 0;
    private static int unReportedText = 0;
    private static ArrayList<StatusListener> listeners = new ArrayList<StatusListener>();

    /**
     * add interest listener to list
     *
     * @param statusListener
     */
    public static void addListener(StatusListener statusListener) {
        listeners.add(statusListener);
    }

    /**
     * Gets unReportedPhotos.
     *
     * @return Value of unReportedPhotos.
     */
    public static int getUnReportedPhotos() { return unReportedPhotos; }

    /**
     * Gets unReportedText.
     *
     * @return Value of unReportedText.
     */
    public static int getUnReportedText() { return unReportedText; }

    /**
     * Sets new unReportedPhotos.
     *
     * @param unReportedPhotos New value of unReportedPhotos.
     */
    public static void setUnReportedPhotos(int unReportedPhotos) {
        UserInfo.unReportedPhotos = unReportedPhotos;
        for (StatusListener listener : listeners) {
            listener.photoStatusChanged();
            listener.menuStatusChanged();
        }
    }

    /**
     * Gets myAndroidID.
     *
     * @return Value of myAndroidID.
     */
    public static String getMyAndroidID() { return myAndroidID; }

    /**
     * Gets lastSyncSucceed.
     *
     * @return Value of lastSyncSucceed.
     */
    public static boolean isLastSyncSucceed() { return lastSyncSucceed; }

    /**
     * Sets new lastSyncSucceed.
     *
     * @param lastSyncSucceed New value of lastSyncSucceed.
     */
    public static void setLastSyncSucceed(boolean lastSyncSucceed) {
        UserInfo.lastSyncSucceed = lastSyncSucceed;
        for (StatusListener listener : listeners) {
            listener.lastReportStatusChanged();
        }
    }

    /**
     * Sets new unReportedText.
     *
     * @param unReportedText New value of unReportedText.
     */
    public static void setUnReportedText(int unReportedText) {
        UserInfo.unReportedText = unReportedText;
        for (StatusListener listener : listeners) {
            listener.textStatusChanged();
            listener.menuStatusChanged();
        }
    }

    /**
     * Gets currentLongitude.
     *
     * @return Value of currentLongitude.
     */
    public static double getCurrentLongitude() { return currentLongitude; }

    /**
     * Gets currentLatitude.
     *
     * @return Value of currentLatitude.
     */
    public static double getCurrentLatitude() { return currentLatitude; }

    /**
     * Gets unReportedLocations.
     *
     * @return Value of unReportedLocations.
     */
    public static int getUnReportedLocations() { return unReportedLocations; }

    /**
     * Sets new currentLatitude.
     *
     * @param currentLatitude New value of currentLatitude.
     */
    public static void setCurrentLatitude(double currentLatitude) { UserInfo.currentLatitude = currentLatitude; }

    /**
     * Sets new unReportedLocations.
     *
     * @param unReportedLocations New value of unReportedLocations.
     */
    public static void setUnReportedLocations(int unReportedLocations) {
        UserInfo.unReportedLocations = unReportedLocations;
        for (StatusListener listener : listeners) {
            listener.locationStatusChanged();
            listener.menuStatusChanged();
        }
    }

    /**
     * Sets new userID.
     *
     * @param userID New value of userID.
     */
    public static void setUserID(String userID) { UserInfo.userID = userID; }

    /**
     * Sets new myAndroidID.
     *
     * @param myAndroidID New value of myAndroidID.
     */
    public static void setMyAndroidID(String myAndroidID) { UserInfo.myAndroidID = myAndroidID; }

    /**
     * Sets new currentLongitude.
     *
     * @param currentLongitude New value of currentLongitude.
     */
    public static void setCurrentLongitude(double currentLongitude) { UserInfo.currentLongitude = currentLongitude; }

    /**
     * Gets userID.
     *
     * @return Value of userID.
     */
    public static String getUserID() { return userID; }

    public static int getTotalUnReportedItemsCout() {
        return getUnReportedLocations() + getUnReportedPhotos() + getUnReportedText();
    }
}
