package ffiandroid.situationawareness.test;

import android.location.Location;
import android.os.Looper;

import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.service.ReportService;

/**
 * Send report: text, image & location to server
 * <p/>
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 20/02/15.
 * <p/>
 * responsible for this file: GuoJunjun
 */
public class Reporting {
    private static Location location;

    public static Location getLocation() {
        return location;
    }

    public static void setLocation(Location location) {
        Reporting.location = location;
    }

    /**
     * *
     *
     * @param location
     * @return feedback message from server: "success" if succeed, otherwise error information
     */
    public static void reportMyLocation(Location location) {
        setLocation(location);
        new Thread(reportThread).start();
    }

    /**
     *
     */
    private static Runnable reportThread = new Runnable() {
        @Override public void run() {
            Looper.prepare();
            String feedback = new ReportService()
                    .sendLocationReport(UserInfo.getUserID(), UserInfo.getMyAndroidID(), getLocation().getLatitude(),
                            getLocation().getLongitude());
            if (feedback != null && feedback.equals("success")) {
            } else {
            }
            Looper.loop();
        }
    };

}
