package edu.ntnu.sair.util;

import java.util.TimeZone;

/**
 * Created by chun on 2/19/15.
 */
public class Constant {

    // Key for encryption
    protected static final String AES_KEY = "01FFIAndroid2015";

    // Timezone for time
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Oslo");

    // Time format
    public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    // Unit is hour
    public static final int LOGIN_PERIOD = 48;

    // Path of photos
    // Real photo path(Torgrim): "/Users/chun/database/sair/photos/"
    // Linux path
    //public static final String PHOTO_PATH = "/home/tbstbs/Documents/BachelorNTNU/photos/";
    // Windows path
    public static final String PHOTO_PATH = "p:/ProjectFFI/photos/";

    public static final int TRUE = 1;
    public static final int FALSE = 0;
}
