package ffiandroid.situationawareness.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.model.TextReport;


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
        ContentValues cv = new ContentValues();
        cv.put(DBtables.TextReportTB.COLUMN_USER_ID, textReport.getUserid());
        cv.put(DBtables.TextReportTB.COLUMN_REPORT, textReport.getReport());
        cv.put(DBtables.TextReportTB.COLUMN_ISREPORTED, textReport.isIsreported());
        cv.put(DBtables.TextReportTB.COLUMN_LONGITUDE, textReport.getLongitude());
        cv.put(DBtables.TextReportTB.COLUMN_LATITUDE, textReport.getLatitude());
        cv.put(DBtables.TextReportTB.COLUMN_DATETIME, System.currentTimeMillis());
        return database.insert(DBtables.TextReportTB.TABLE_NAME, null, cv);
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

        return database.update(DBtables.TextReportTB.TABLE_NAME, cv, where,
                new String[]{textReport.getUserid(), String.valueOf(textReport.getDatetime().getTimeInMillis())});
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
                new String[]{myUserID, "0"}, null, null, DBtables.LocationTB.COLUMN_DATETIME + " DESC");
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
     * *
     *
     * @param cursor the cursor row
     * @return a TextReport
     */
    private TextReport cursorToTextReport(Cursor cursor) {
        TextReport tr = new TextReport();
        tr.setUserid(cursor.getString(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_USER_ID)));
        tr.setReport(cursor.getString(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_REPORT)));
        tr.setDatetime(cursor.getLong(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_DATETIME)));
        tr.setLongitude(cursor.getDouble(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_LONGITUDE)));
        tr.setLatitude(cursor.getDouble(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_LATITUDE)));
        tr.setIsreported(cursor.getInt(cursor.getColumnIndex(DBtables.TextReportTB.COLUMN_ISREPORTED)) > 0);
        return tr;
    }
}
