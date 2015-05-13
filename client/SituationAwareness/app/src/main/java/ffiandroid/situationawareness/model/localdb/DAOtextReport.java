package ffiandroid.situationawareness.model.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.model.TextReport;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.util.Coder;


/**
 * This DAOtextReport File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class DAOtextReport {
    private SQLiteDatabase database;
    private DBhelper dbHelper;

    public DAOtextReport(Context context) {
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
     * insert a text report item to the textreport database table
     *
     * @param textReport
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    public long addReport(TextReport textReport) {
        System.out.println("Added Text report to DB with user ID " + textReport.getUserid());
        ContentValues cv = new ContentValues();
        cv.put(DBtables.TextReportTB.COLUMN_USER_ID, textReport.getUserid());
        cv.put(DBtables.TextReportTB.COLUMN_NAME, textReport.getName());
        cv.put(DBtables.TextReportTB.COLUMN_REPORT, textReport.getReport());
        cv.put(DBtables.TextReportTB.COLUMN_ISREPORTED, textReport.isIsreported());
        cv.put(DBtables.TextReportTB.COLUMN_LONGITUDE, textReport.getLongitude());
        cv.put(DBtables.TextReportTB.COLUMN_LATITUDE, textReport.getLatitude());
        if(textReport.getDatetime() == null)
        {
            cv.put(DBtables.TextReportTB.COLUMN_DATETIME, System.currentTimeMillis());
        }
        else
        {
            cv.put(DBtables.TextReportTB.COLUMN_DATETIME, textReport.getDatetimeLong());
        }
        long result = database.insert(DBtables.TextReportTB.TABLE_NAME, null, cv);

        if (result >= 0) {
            statusChanged();
        }
        return result;
    }

    /**
     * update given text reports isReported value to isReported
     *
     * @param textReport
     * @return the number of rows affected
     */
    public long updateIsReported(TextReport textReport) {
        ContentValues cv = new ContentValues();
        cv.put(DBtables.TextReportTB.COLUMN_ISREPORTED, true);
        String where = DBtables.TextReportTB.COLUMN_USER_ID + "=?" + " AND " +
                DBtables.TextReportTB.COLUMN_DATETIME + "=?";

        long result = database.update(DBtables.TextReportTB.TABLE_NAME, cv, where,
                new String[]{textReport.getUserid(), String.valueOf(textReport.getDatetime().getTimeInMillis())});

        if (result >= 0) {
            statusChanged();
        }
        return result;
    }

    /**
     * set new un-reported text report value to UserInfo when it changed
     */
    private void statusChanged() {
        UserInfo.setUnReportedText(getMyNOTReportedItemCount(UserInfo.getUserID()));
    }

    /**
     * @return all text reports as a List
     */
    public List<TextReport> getAllTextReports() {
        List<TextReport> textReports = new ArrayList<>();
        // NOTE(Torgrim): testing ...
        Cursor cursor =
                database.query(DBtables.TextReportTB.TABLE_NAME, DBtables.TextReportTB.ALL_COLUMNS, null, null, null,
                        null, DBtables.TextReportTB.COLUMN_DATETIME + " DESC");
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                TextReport textReport = cursorToTextReport(cursor);
                textReports.add(textReport);
                cursor.moveToNext();
            }
            cursor.close();
            Log.i("DATABASE: ", "database read");
        } else {
            Log.i("DATABASE: ", "database empty");
        }
        return textReports;
    }

    /**
     * @param myUserID
     * @return all my unsent report
     */
    public List<TextReport> getMyUnsentReports(String myUserID) {
        List<TextReport> textReports = new ArrayList<>();

        Cursor cursor = database.query(DBtables.TextReportTB.TABLE_NAME, DBtables.TextReportTB.ALL_COLUMNS,
                DBtables.TextReportTB.COLUMN_USER_ID + " = ? AND " + DBtables.TextReportTB.COLUMN_ISREPORTED + " =?",
                new String[]{Coder.encryptMD5(myUserID), "0"}, null, null, DBtables.LocationTB.COLUMN_DATETIME + " DESC");
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                TextReport textReport = cursorToTextReport(cursor);
                textReports.add(textReport);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return textReports;
    }

    /**
     * @return total row count of the table
     */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + DBtables.TextReportTB.TABLE_NAME;
        Cursor cursor = database.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * @return total row count of the not reported items in the table
     */
    public int getMyNOTReportedItemCount(String myUserID) {
        Cursor cursor = database.query(DBtables.TextReportTB.TABLE_NAME, DBtables.TextReportTB.ALL_COLUMNS,
                DBtables.TextReportTB.COLUMN_USER_ID + " = ? AND " + DBtables.TextReportTB.COLUMN_ISREPORTED + " =?",
                new String[]{Coder.encryptMD5(myUserID), "0"}, null, null, DBtables.LocationTB.COLUMN_DATETIME + " DESC");
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * *
     *
     * @param cursor the cursor row
     * @return a TextReport
     */
    private TextReport cursorToTextReport(Cursor cursor) {
        TextReport tr = new TextReport();
        tr.setUserid(cursor.getString(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_USER_ID)));
        tr.setName(cursor.getString(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_NAME)));
        tr.setReport(cursor.getString(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_REPORT)));
        tr.setDatetime(cursor.getLong(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_DATETIME)));
        tr.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_LONGITUDE)));
        tr.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_LATITUDE)));
        tr.setIsreported(cursor.getInt(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_ISREPORTED)) > 0);
        return tr;
    }
}
