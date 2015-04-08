package ffiandroid.situationawareness.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.model.PhotoReport;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 19, 2015.
 */
public class DAOphoto {
    private SQLiteDatabase database;
    private DBhelper dbHelper;

    public DAOphoto(Context context) {
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
     * insert a photo report item to the photo database table
     *
     * @param photoReport
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addPhoto(PhotoReport photoReport) {
        ContentValues cv = new ContentValues();
        cv.put(DBtables.PhotoTB.COLUMN_DESCRIPTION, photoReport.getDescription());
        cv.put(DBtables.PhotoTB.COLUMN_DATETIME, photoReport.getDatetimeLong());
        cv.put(DBtables.PhotoTB.COLUMN_ISREPORTED, photoReport.isIsreported());
        cv.put(DBtables.PhotoTB.COLUMN_EXTENSION, photoReport.getExtension());
        cv.put(DBtables.PhotoTB.COLUMN_LONGITUDE, photoReport.getLongitude());
        cv.put(DBtables.PhotoTB.COLUMN_LATITUDE, photoReport.getLatitude());
        cv.put(DBtables.PhotoTB.COLUMN_USER_ID, photoReport.getUserid());
        cv.put(DBtables.PhotoTB.COLUMN_TITLE, photoReport.getTitle());
        cv.put(DBtables.PhotoTB.COLUMN_PATH, photoReport.getPath());
        return database.insert(DBtables.PhotoTB.TABLE_NAME, null, cv);
    }

    /**
     * update given photo reports isReported value to true
     *
     * @param photoReport
     * @return the number of rows affected
     */
    public long updateIsReported(PhotoReport photoReport) {
        ContentValues cv = new ContentValues();
        cv.put(DBtables.PhotoTB.COLUMN_ISREPORTED, true);

        String where = DBtables.PhotoTB.COLUMN_USER_ID + "=?" + " AND " +
                DBtables.PhotoTB.COLUMN_DATETIME + "=?";
        return database.update(DBtables.PhotoTB.TABLE_NAME, cv, where,
                new String[]{photoReport.getUserid(), String.valueOf(photoReport.getDatetimeLong())});
    }

    /**
     * update given photo reports path
     *
     * @param photoReport
     * @return the number of rows affected
     */
    public long updatePhotoPath(PhotoReport photoReport) {
        ContentValues cv = new ContentValues();
        cv.put(DBtables.PhotoTB.COLUMN_PATH, photoReport.getPath());

        String where = DBtables.PhotoTB.COLUMN_USER_ID + "=?" + " AND " +
                DBtables.PhotoTB.COLUMN_DATETIME + "=?";
        return database.update(DBtables.PhotoTB.TABLE_NAME, cv, where,
                new String[]{photoReport.getUserid(), String.valueOf(photoReport.getDatetimeLong())});
    }

    /**
     * @return all photo reports as a List
     */
    public List<PhotoReport> getAllPhotos() {
        List<PhotoReport> photoReports = new ArrayList<>();
        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, null, null, null, null, null,
                DBtables.PhotoTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhotoReport photoReport = cursorToPhotoReport(cursor);
            photoReports.add(photoReport);
            cursor.moveToNext();
        }
        cursor.close();
        return photoReports;
    }

    /**
     * @param myUserID
     * @return all team members photo except the given user id' photo (my photo report)
     */
    public List<PhotoReport> getCoWorkerPhotos(String myUserID) {
        List<PhotoReport> photoReports = new ArrayList<>();
        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, DBtables.PhotoTB.ALL_COLUMNS,
                DBtables.PhotoTB.COLUMN_USER_ID + " != ?", new String[]{myUserID}, null, null,
                DBtables.PhotoTB.COLUMN_DATETIME + " DESC");
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PhotoReport photoReport = cursorToPhotoReport(cursor);
                photoReports.add(photoReport);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return photoReports;
    }

    /**
     * @param myUserID
     * @return all my photos (photo reports by me)
     */
    public List<PhotoReport> getMyPhotos(String myUserID) {
        List<PhotoReport> photoReports = new ArrayList<>();
        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, DBtables.PhotoTB.ALL_COLUMNS,
                DBtables.PhotoTB.COLUMN_USER_ID + " = ?", new String[]{myUserID}, null, null,
                DBtables.PhotoTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhotoReport photoReport = cursorToPhotoReport(cursor);
            photoReports.add(photoReport);
            cursor.moveToNext();
        }
        cursor.close();
        return photoReports;
    }

    /**
     * @param myUserID
     * @return one of my not reported photo
     */
    public PhotoReport getOneNotReportedPhoto(String myUserID) {
        PhotoReport photoReport;
        //        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, null,
        //                DBtables.PhotoTB.COLUMN_USER_ID + " = ? AND " + DBtables.PhotoTB.COLUMN_ISREPORTED + " =?",
        //                new String[]{myUserID, "0"}, null, null, DBtables.PhotoTB.COLUMN_DATETIME + " DESC");


        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, null, null, null, null, null,
                DBtables.PhotoTB.COLUMN_DATETIME + " DESC");

//        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, null, DBtables.PhotoTB.COLUMN_ISREPORTED + " =?",
//                new String[]{"0"}, null, null, DBtables.PhotoTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            photoReport = cursorToPhotoReport(cursor);
        } else {
            photoReport = null;
        }
        cursor.close();
        return photoReport;
    }


    /**
     * @param myUserID
     * @return one of the not downloaded photo from local database list
     */
    public PhotoReport getOneNotDownloadedPhoto(String myUserID) {
        PhotoReport photoReport;
        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, DBtables.PhotoTB.ALL_COLUMNS,
                DBtables.PhotoTB.COLUMN_USER_ID + " != ? AND " + DBtables.PhotoTB.COLUMN_ISREPORTED + " =?",
                new String[]{myUserID, "0"}, null, null, DBtables.PhotoTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            photoReport = cursorToPhotoReport(cursor);
        } else {
            photoReport = null;
        }
        cursor.close();
        return photoReport;
    }

    /**
     * @param myUserID
     * @return latest downloaded photo report item time as long; return 0 if there is none;
     */
    public long getLastDownloadedPhotoReportTime(String myUserID) {
        long lastTime;
        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, DBtables.PhotoTB.ALL_COLUMNS,
                DBtables.PhotoTB.COLUMN_USER_ID + " != ?", new String[]{myUserID}, null, null,
                DBtables.PhotoTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {

            lastTime = cursor.getLong(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_DATETIME));
        } else {
            lastTime = 0;
        }
        cursor.close();
        return lastTime;
    }

    /**
     * @param myUserID
     * @return all my not reported photos
     */
    public List<PhotoReport> getMyNOTReportedPhotos(String myUserID) {
        List<PhotoReport> photoReports = new ArrayList<>();
        Cursor cursor = database.query(DBtables.PhotoTB.TABLE_NAME, DBtables.PhotoTB.ALL_COLUMNS,
                DBtables.PhotoTB.COLUMN_USER_ID + " = ?" + " AND " + DBtables.PhotoTB.COLUMN_ISREPORTED + " =?",
                new String[]{myUserID, "0"}, null, null, DBtables.PhotoTB.COLUMN_DATETIME + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            PhotoReport photoReport = cursorToPhotoReport(cursor);
            photoReports.add(photoReport);
            cursor.moveToNext();
        }
        cursor.close();
        return photoReports;
    }

    /**
     * delete the given photo report from database
     *
     * @param photoReport
     */
    public void deleteImage(PhotoReport photoReport) {
        String whereClause = DBtables.PhotoTB.COLUMN_USER_ID + "=? AND " + DBtables.PhotoTB.COLUMN_DATETIME +
                "=?";
        String[] whereArgs = new String[]{photoReport.getUserid(), String.valueOf(photoReport.getDatetimeLong())};
        database.delete(DBtables.PhotoTB.TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * @return total row count of the table
     */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + DBtables.PhotoTB.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * *
     *
     * @param cursor the cursor row
     * @return a PhotoReport
     */
    private PhotoReport cursorToPhotoReport(Cursor cursor) {
        PhotoReport photoReport = new PhotoReport();
        photoReport.setUserid(cursor.getString(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_USER_ID)));
        photoReport.setDatetime(cursor.getLong(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_DATETIME)));
        photoReport.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_LONGITUDE)));
        photoReport.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_LATITUDE)));
        photoReport.setIsreported(cursor.getInt(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_ISREPORTED)) > 0);
        photoReport.setTitle(cursor.getString(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_TITLE)));
        photoReport.setDescription(cursor.getString(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_DESCRIPTION)));
        photoReport.setPath(cursor.getString(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_PATH)));
        photoReport.setExtension(cursor.getString(cursor.getColumnIndex(DBtables.PhotoTB.COLUMN_EXTENSION)));
        return photoReport;
    }
}
