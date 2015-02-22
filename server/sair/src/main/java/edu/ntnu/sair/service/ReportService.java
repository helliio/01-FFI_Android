package edu.ntnu.sair.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService(endpointInterface = "edu.ntnu.sair.service.ReportService", targetNamespace = "http://service.sair.ntnu.edu/")
public interface ReportService {

    public String sendLocationReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "longitude") String longitude,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "sendingTime") String sendingTime);

    public String sendTextReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "longitude") String longitude,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "content") String content,
            @WebParam(name = "sendingTime") String sendingTime);

    public String sendPhotoReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "longitude") String longitude,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "extension") String extension,
            @WebParam(name = "description") String description,
            @WebParam(name = "direction") String direction,
            @WebParam(name = "sendingTime") String sendingTime);
}
