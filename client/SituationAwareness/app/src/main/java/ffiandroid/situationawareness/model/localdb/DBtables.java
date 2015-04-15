package ffiandroid.situationawareness.model.localdb;

import android.provider.BaseColumns;

/**
 * This DBtables File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class DBtables {
    // To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
    public DBtables() {}

    /* Inner class that defines the table contents */
    public static abstract class TextReportTB implements BaseColumns {
        public static final String TABLE_NAME = "textreport";

        public static final String COLUMN_USER_ID = "userid";
        public static final String COLUMN_REPORT = "report";
        public static final String COLUMN_ISREPORTED = "isreported";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String PRIMARY_KEY = "PRIMARY KEY (" + COLUMN_USER_ID + "," + COLUMN_DATETIME + ")";
        public static final String[] ALL_COLUMNS =
                {COLUMN_USER_ID, COLUMN_REPORT, COLUMN_ISREPORTED, COLUMN_DATETIME, COLUMN_LATITUDE, COLUMN_LONGITUDE};
    }

    public static abstract class LocationTB implements BaseColumns {
        public static final String TABLE_NAME = "locationreport";

        public static final String COLUMN_USER_ID = "userid";
        public static final String COLUMN_ISREPORTED = "isreported";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String PRIMARY_KEY = "PRIMARY KEY (" + COLUMN_USER_ID + "," + COLUMN_DATETIME + ")";
        public static final String[] ALL_COLUMNS =
                {COLUMN_USER_ID, COLUMN_ISREPORTED, COLUMN_DATETIME, COLUMN_LATITUDE, COLUMN_LONGITUDE};
    }

    public static abstract class PhotoTB implements BaseColumns {
        public static final String TABLE_NAME = "photoreport";

        public static final String COLUMN_USER_ID = "userid";
        public static final String COLUMN_ISREPORTED = "isreported";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_EXTENSION = "extension";
        public static final String PRIMARY_KEY = "PRIMARY KEY (" + COLUMN_USER_ID + "," + COLUMN_DATETIME + ")";
        public static final String[] ALL_COLUMNS =
                {COLUMN_USER_ID, COLUMN_ISREPORTED, COLUMN_DATETIME, COLUMN_LATITUDE, COLUMN_LONGITUDE,
                        COLUMN_DESCRIPTION, COLUMN_TITLE, COLUMN_PATH, COLUMN_EXTENSION};
    }
}
