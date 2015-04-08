package ffiandroid.situationawareness.localdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This DBhelper File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class DBhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sair.db";
    public static final int DB_VERSION = 1;

    private static final String TEXT_TYPE = " TEXT";
    private static final String REAL_TYPE = " REAL";
    private static final String NUMERIC_TYPE = " NUMERIC";
    private static final String COMMA_SEP = ",";

    private static final String CREATE_TEXTREPORT_TABLE = "CREATE TABLE " + DBtables.TextReportTB.TABLE_NAME + " (" +
            DBtables.TextReportTB.COLUMN_USER_ID + TEXT_TYPE + COMMA_SEP +
            DBtables.TextReportTB.COLUMN_REPORT + TEXT_TYPE + COMMA_SEP +
            DBtables.TextReportTB.COLUMN_DATETIME + NUMERIC_TYPE + COMMA_SEP +
            DBtables.TextReportTB.COLUMN_ISREPORTED + NUMERIC_TYPE + COMMA_SEP +
            DBtables.TextReportTB.COLUMN_LONGITUDE + REAL_TYPE + COMMA_SEP +
            DBtables.TextReportTB.COLUMN_LATITUDE + REAL_TYPE + COMMA_SEP +
            DBtables.TextReportTB.PRIMARY_KEY +
            " )";

    private static final String CREATE_LOCATION_TABLE = "CREATE TABLE " + DBtables.LocationTB.TABLE_NAME + "(" +
            DBtables.LocationTB.COLUMN_USER_ID + TEXT_TYPE + COMMA_SEP +
            DBtables.LocationTB.COLUMN_DATETIME + NUMERIC_TYPE + COMMA_SEP +
            DBtables.LocationTB.COLUMN_ISREPORTED + NUMERIC_TYPE + COMMA_SEP +
            DBtables.LocationTB.COLUMN_LONGITUDE + REAL_TYPE + COMMA_SEP +
            DBtables.LocationTB.COLUMN_LATITUDE + REAL_TYPE + COMMA_SEP +
            DBtables.LocationTB.PRIMARY_KEY +
            " )";

    private static final String CREATE_PHOTO_TABLE = "CREATE TABLE " + DBtables.PhotoTB.TABLE_NAME + " (" +
            DBtables.PhotoTB.COLUMN_USER_ID + TEXT_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_DATETIME + NUMERIC_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_ISREPORTED + NUMERIC_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_EXTENSION + TEXT_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_LONGITUDE + REAL_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_LATITUDE + REAL_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_TITLE + TEXT_TYPE + COMMA_SEP +
            DBtables.PhotoTB.COLUMN_PATH + TEXT_TYPE + COMMA_SEP +
            DBtables.PhotoTB.PRIMARY_KEY +
            " )";

    private static final String DELETE_TEXTREPORT_TABLE = "DROP TABLE IF EXISTS " + DBtables.TextReportTB.TABLE_NAME;
    private static final String DELETE_LOCATION_TABLE = "DROP TABLE IF EXISTS " + DBtables.LocationTB.TABLE_NAME;
    private static final String DELETE_PHOTO_TABLE = "DROP TABLE IF EXISTS " + DBtables.PhotoTB.TABLE_NAME;

    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TEXTREPORT_TABLE);
        db.execSQL(CREATE_LOCATION_TABLE);
        db.execSQL(CREATE_PHOTO_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TEXTREPORT_TABLE);
        db.execSQL(DELETE_LOCATION_TABLE);
        db.execSQL(DELETE_PHOTO_TABLE);
        onCreate(db);
    }
}
