package ffiandroid.situationawareness.datahandling;

/**
 * Constant information of android id & user name
 * <p/>
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 20/02/15.
 * <p/>
 * responsible for this file: GuoJunjun
 */
public class UserInfo {
    private static String MYANDROIDID, USERNAME;

    public static String getMYANDROIDID() {
        return MYANDROIDID;
    }

    public static void setMYANDROIDID(String MYANDROIDID) {
        UserInfo.MYANDROIDID = MYANDROIDID;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static void setUSERNAME(String USERNAME) {
        UserInfo.USERNAME = USERNAME;
    }
}
