package edu.ntnu.sair.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface ReportService {

    public String sendLocationReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "longitude") String longitude);

    public String sendLocationReportList(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "list") String list);

    public String sendTextReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "longitude") String longitude,
            @WebParam(name = "content") String content);

    public String sendTextReportList(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "list") String list);

    public String sendPhotoReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "longitude") String longitude,
            @WebParam(name = "direction") String direction,
            @WebParam(name = "file") String file,
            @WebParam(name = "extension") String extension,
            @WebParam(name = "description") String description);
}
