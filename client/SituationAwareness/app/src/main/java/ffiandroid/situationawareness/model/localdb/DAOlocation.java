package ffiandroid.situationawareness.model.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.model.LocationReport;
import ffiandroid.situationawareness.model.util.Coder;

/**
 * This DAOlocation File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class DAOlocation {
    private SQLiteDatabase database;
    private DBhelper dbHelper;

    public DAOlocation(Context context) {
        dbHelper = new DBhelper(context);
        database = dbHelper.getWritableDatabase();
    }

    /**
     * close any database object
     */
    public void close() {
        dbHelper.close();
        database.close();
    }

    /**
     * insert a text report item to the location database table
     *
     * @param locationReport
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addLocation(LocationReport locationReport) {
        ContentValues cv = new ContentValues();
        cv.put(DBtables.LocationTB.COLUMN_USER_ID, Coder.encryptMD5(locationReport.getUserid()));
        cv.put(DBtables.LocationTB.COLUMN_ISREPORTED, locationReport.isIsreported());
        cv.put(DBtables.LocationTB.COLUMN_LONGITUDE, locationReport.getLongitude());
        cv.put(DBtables.LocationTB.COLUMN_LATITUDE, locationReport.getLatitude());
        cv.put(DBtables.LocationTB.COLUMN_DATETIME, System.currentTimeMillis());
        return database.insert(DBtables.LocationTB.TABLE_NAME, null, cv);
    }

    // NOTE(Torgrim): Created a update location in database so
    // that your own location will only be on report
    // instead of getting the path of the user..
    public long updateLocation(LocationReport locationReport)
    {
        ContentValues cv = new ContentValues();
        cv.put(DBtables.LocationTB.COLUMN_USER_ID, locationReport.getUserid());
        cv.put(DBtables.LocationTB.COLUMN_ISREPORTED, locationReport.isIsreported());
        cv.put(DBtables.LocationTB.COLUMN_LONGITUDE, locationReport.getLongitude());
        cv.put(DBtables.LocationTB.COLUMN_LATITUDE, locationReport.getLatitude());
        cv.put(DBtables.LocationTB.COLUMN_DATETIME, System.currentTimeMillis());
        String where = DBtables.LocationTB.COLUMN_USER_ID + "=?";
        return database.update(DBtables.LocationTB.TABLE_NAME, cv, where, new String[]{locationReport.getUserid()} );
    }

    /**
     * update given location reports isReported value to true
     *
     * @param locationReport
     * @return the number of rows affected
     */
    public long updateIsReported(LocationReport locationReport) {
        ContentValues cv = new ContentValues();
        cv.put(DBtables.LocationTB.COLUMN_ISREPORTED, true);

        String where = DBtables.LocationTB.COLUMN_USER_ID + "=?" + " AND " +
                DBtables.LocationTB.COLUMN_DATETIME + "=?";
        return database.update(DBtables.LocationTB.TABLE_NAME, cv, where, new String[]{locationReport.getUserid(),
                String.valueOf(locationReport.getDatetime().getTimeInMillis())});
    }

    /**
     * @return all location reports as a List
     */
    public List<LocationReport> getAllLocations() {
        List<LocationReport> locationReports = new ArrayList<>();
        Cursor cursor =
                database.query(DBtables.LocationTB.TABLE_NAME, DBtables.LocationTB.ALL_COLUMNS, null, null, null, null,
                        DBtables.LocationTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationReport locationReport = cursorToTextReport(cursor);
            locationReports.add(locationReport);
            cursor.moveToNext();
        }
        cursor.close();
        return locationReports;
    }

    /**
     * @param myUserID
     * @return all team members location except the given user id' location (my location)
     */
    public List<LocationReport> getCoWorkerLocations(String myUserID) {
        String userid = Coder.encryptMD5(myUserID);
        List<LocationReport> locationReports = new ArrayList<>();
        Cursor cursor = database.query(DBtables.LocationTB.TABLE_NAME, DBtables.LocationTB.ALL_COLUMNS,
                DBtables.LocationTB.COLUMN_USER_ID + " != ?", new String[]{userid}, null, null,
                DBtables.LocationTB.COLUMN_DATETIME + " DESC");
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LocationReport locationReport = cursorToTextReport(cursor);
                System.out.println("Current user id: " + Coder.encryptMD5(myUserID) + "LocationReport userid: " + locationReport.getUserid());
                locationReports.add(locationReport);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return locationReports;
    }


    /**
     * @param myUserID
     * @return all team members location except the given user id' location (my location)
     */
    public List<LocationReport> getLatestCoWorkerLocations(String myUserID) {
        List<LocationReport> locationReports = new ArrayList<>();
        String lwQuery =
                "SELECT *, MAX(" + DBtables.LocationTB.COLUMN_DATETIME + ") FROM " + DBtables.LocationTB.TABLE_NAME +
                        " GROUP BY '" + DBtables.LocationTB.COLUMN_USER_ID + "' AND " +
                        DBtables.LocationTB.COLUMN_USER_ID + " !=?";
        Cursor cursor = database.rawQuery(lwQuery, new String[]{myUserID});
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                LocationReport locationReport = cursorToTextReport(cursor);
                locationReports.add(locationReport);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return locationReports;
    }


    /**
     * @param myUserID
     * @return all my locations
     */
    public List<LocationReport> getMyLocations(String myUserID) {
        List<LocationReport> locationReports = new ArrayList<>();
        Cursor cursor = database.query(DBtables.LocationTB.TABLE_NAME, DBtables.LocationTB.ALL_COLUMNS,
                DBtables.LocationTB.COLUMN_USER_ID + " = ?", new String[]{myUserID}, null, null,
                DBtables.LocationTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationReport locationReport = cursorToTextReport(cursor);
            locationReports.add(locationReport);
            cursor.moveToNext();
        }
        cursor.close();
        return locationReports;
    }

    /**
     * @param myUserID
     * @return all my not reported locations
     */
    public List<LocationReport> getMyNOTReportedLocations(String myUserID) {
        List<LocationReport> locationReports = new ArrayList<>();
        Cursor cursor = database.query(DBtables.LocationTB.TABLE_NAME, DBtables.LocationTB.ALL_COLUMNS,
                DBtables.LocationTB.COLUMN_USER_ID + " = ?" + " AND " + DBtables.LocationTB.COLUMN_ISREPORTED + " =?",
                new String[]{myUserID, "0"}, null, null, DBtables.LocationTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LocationReport locationReport = cursorToTextReport(cursor);
            locationReports.add(locationReport);
            cursor.moveToNext();
        }
        cursor.close();
        return locationReports;
    }

    /**
     * @return total row count of the table
     */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + DBtables.LocationTB.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * @return total row count of the not reported items in the table
     */
    public int getMyNOTReportedItemCount(String myUserID) {
        Cursor cursor = database.query(DBtables.LocationTB.TABLE_NAME, DBtables.LocationTB.ALL_COLUMNS,
                DBtables.LocationTB.COLUMN_USER_ID + " = ?" + " AND " + DBtables.LocationTB.COLUMN_ISREPORTED + " =?",
                new String[]{myUserID, "0"}, null, null, DBtables.LocationTB.COLUMN_DATETIME + " DESC");
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * *
     *
     * @param cursor the cursor row
     * @return a LocationReport
     */
    private LocationReport cursorToTextReport(Cursor cursor) {
        LocationReport lr = new LocationReport();
        lr.setUserid(cursor.getString(cursor.getColumnIndex(DBtables.LocationTB.COLUMN_USER_ID)));
        lr.setDatetime(cursor.getLong(cursor.getColumnIndex(DBtables.LocationTB.COLUMN_DATETIME)));
        lr.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBtables.LocationTB.COLUMN_LONGITUDE)));
        lr.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBtables.LocationTB.COLUMN_LATITUDE)));
        lr.setIsreported(cursor.getInt(cursor.getColumnIndex(DBtables.LocationTB.COLUMN_ISREPORTED)) > 0);
        return lr;
    }
}
