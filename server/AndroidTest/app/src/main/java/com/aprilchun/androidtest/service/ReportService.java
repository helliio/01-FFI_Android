package com.aprilchun.androidtest.service;

import java.io.File;

/**
 * Created by chun on 3/4/15.
 */
public interface ReportService {
    public String sendLocationReport(String username, String deviceId, double latitude, double longitude);

    public String sendTextReport(String username, String deviceId, double latitude, double longitude, String content);

    public String sendPhotoReport(String username, String deviceId, double latitude, double longitude, int direction, File file, String description);
}
