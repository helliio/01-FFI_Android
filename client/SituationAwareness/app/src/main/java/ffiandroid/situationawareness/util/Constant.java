package ffiandroid.situationawareness.util;

import java.util.TimeZone;

/**
 * Created by chun on 2/19/15.
 */
public class Constant {
    // Key for encryption
    public static final String AES_KEY = "01FFIAndroid2015";

    // URL of the server
    public static final String SERVICE_URL = "http://chun.no:8080";

    // Timezone for time
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Oslo");

    // Time format
    public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    // Gzip buffer size
    public static final int GZIP_BUFFER_SIZE = 1024;

}
