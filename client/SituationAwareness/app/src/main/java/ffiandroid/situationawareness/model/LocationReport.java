package ffiandroid.situationawareness.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ffiandroid.situationawareness.model.util.Coder;

/**
 * This LocationReport File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class LocationReport {
    protected String userid;
    protected boolean isreported;
    protected Calendar datetime;
    protected long datetimeLong;
    protected double latitude, longitude;
    protected SimpleDateFormat df = new SimpleDateFormat("MMMM d, yy  h:mm");

    /**
     * If autoFill True
     * <p/>
     * auto fill up: user id, isreported set to false, latitude, and longitude get from UserInfo
     *
     * @param autoFill
     */
    public LocationReport(boolean autoFill) {
        if (autoFill) {
            this.longitude = UserInfo.getCurrentLongitude();
            this.latitude = UserInfo.getCurrentLatitude();
            this.userid = Coder.encryptMD5(UserInfo.getUserID());
            this.isreported = false;
        }
    }

    /**
     * auto set userid, isreported: false, current time stamp;
     *
     * @param latitude
     * @param longitude
     */
    public LocationReport(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.userid = Coder.encryptMD5(UserInfo.getUserID());
        this.isreported = false;
    }

    public LocationReport() {

    }

    /**
     * Sets new longitude.
     *
     * @param longitude New value of longitude.
     */
    public void setLongitude(double longitude) { this.longitude = longitude; }

    /**
     * Gets latitude.
     *
     * @return Value of latitude.
     */
    public double getLatitude() { return latitude; }

    /**
     * Gets isreported.
     *
     * @return Value of isreported.
     */
    public boolean isIsreported() { return isreported; }

    /**
     * Gets longitude.
     *
     * @return Value of longitude.
     */
    public double getLongitude() { return longitude; }

    /**
     * Sets new datetime.
     *
     * @param datetime New value of datetime.
     */
    public void setDatetime(Calendar datetime) { this.datetime = datetime; }

    /**
     * Sets new datetime.
     *
     * @param datetime New value of datetime.
     */
    public void setDatetime(long datetime) {
        this.datetimeLong = datetime;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(datetime);
        this.datetime = cal;
    }

    /**
     * Sets new latitude.
     *
     * @param latitude New value of latitude.
     */
    public void setLatitude(double latitude) { this.latitude = latitude; }

    /**
     * Sets new userid.
     *
     * @param userid New value of userid.
     */
    public void setUserid(String userid) { this.userid = userid; }

    /**
     * Gets datetime.
     *
     * @return Value of datetime.
     */
    public Calendar getDatetime() { return datetime; }

    /**
     * Gets datetimeLong.
     *
     * @return Value of datetimeLong.
     */
    public long getDatetimeLong() { return datetimeLong; }


    /**
     * Gets userid.
     *
     * @return Value of userid.
     */
    public String getUserid() { return userid; }

    /**
     * Sets new isreported.
     *
     * @param isreported New value of isreported.
     */
    public void setIsreported(boolean isreported) { this.isreported = isreported; }

    @Override public String toString() {
        return "id:" + userid +
                ", reported:" + isreported +
                ", " + df.format(datetime.getTime()) +
                ", " + latitude +
                ", " + longitude;
    }

}