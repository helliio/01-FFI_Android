package edu.ntnu.sair.service;

import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface ReportService {

    public String sendLocationReport(String memberId, String deviceId, String longitude, String latitude, String sendingTime);

    public String sendTextReport(String memberId, String deviceId, String longitude, String latitude, String content, String sendingTime);

    public String sendPhotoReport(String memberId, String deviceId, String longitude, String latitude, String extension, String description, String direction, String sendingTime);
}
