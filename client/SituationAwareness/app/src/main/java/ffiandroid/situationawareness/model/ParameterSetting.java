package ffiandroid.situationawareness.model;

/**
 * This AppSetting File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class ParameterSetting {
    /**
     * <p/>
     * minimum time interval between GPS location updates, in seconds
     */
    private static int locationUpdateTime = 20000;

    /**
     * minimum distance between location updates, in meters
     */
    private static int locationUpdateDistance = 5;

    /**
     * do sync Task in every given second
     */
    private  static int autoSyncTime = 60000;


    /**
     * Sets new minimum distance between location updates, in meters.
     *
     * @param updateDistance New value of minimum distance between location updates, in meters.
     */
    public static void setLocationUpdateDistance(int updateDistance) {
        locationUpdateDistance = updateDistance;
    }

    /**
     * Gets <p> minimum time interval between GPS location updates, in milliseconds.
     *
     * @return Value of <p> minimum time interval between GPS location updates, in milliseconds.
     */
    public static int getLocationUpdateTime() { return locationUpdateTime; }

    /**
     * Gets minimum distance between location updates, in meters.
     *
     * @return Value of minimum distance between location updates, in meters.
     */
    public static int getLocationUpdateDistance() { return locationUpdateDistance; }

    /**
     * Sets new <p> minimum time interval between GPS location updates, in seconds.
     *
     * @param updateTime New value of <p> minimum time interval between GPS location updates, in seconds.
     */
    public static void setLocationUpdateTime(int updateTime) { locationUpdateTime = updateTime * 1000; }

    /**
     * Sets new do sync Task in every given minutes.
     *
     * @param syncTime New value of do sync Task in every given minutes.
     */
    public static void setAutoSyncTime(int syncTime) { autoSyncTime = syncTime * 60000; }

    /**
     * Gets do sync Task in every given millisecond.
     *
     * @return Value of do sync Task in every given millisecond.
     */
    public static int getAutoSyncTime() { return autoSyncTime; }


}
