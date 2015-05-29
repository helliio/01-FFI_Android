package ffiandroid.situationawareness.model.util;

import java.util.TimeZone;

/**
 * Created by chun on 2/19/15.
 */
public class Constant {
    // Key for encryption
    public static final String AES_KEY = "01FFIAndroid2015";

    // Default server address
    public static final String DEFAULT_SERVICE_URL = "http://sair.chun.no";
    // URL of the server
    // Public Server
    public static String SERVICE_URL = "http://sair.chun.no";

    // Timezone for time
    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("Europe/Oslo");

    // Time format
    public static final String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    // Timeout in ms
    public static final int TIMEOUT = 360000;

}
