package ffiandroid.situationawareness.model.service;

import org.json.JSONArray;

import java.io.File;

/**
 * Created by chun on 3/4/15.
 */
public interface ReportService {
    public String sendLocationReport(String username, String deviceId, long sendingTime, double latitude, double longitude);

    public String sendLocationReportList(String username, String deviceId, long sendingTime, JSONArray list);

    public String sendTextReport(String username, String deviceId, long sendingTime, double latitude, double longitude, String content);

    public String sendTextReportList(String username, String deviceId, long sendingTime, JSONArray list);

    // NOTE(Torgrim): added title as a parameter..
    public String sendPhotoReport(String username, String deviceId, long sendingTime, double latitude, double longitude, int direction, File file, String title, String description);
}
