package edu.ntnu.sair.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface RequestService {

    public String getAllTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getLatestTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);


    public String getPeriodTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);

    public String getAllTeamTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getLatestTeamTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);


    public String getPeriodTeamTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);

    public String getAllTeamPhotoReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getLatestTeamPhotoReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);


    public String getPeriodTeamPhotoReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);

    public String getAllSelfLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getLatestSelfLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);


    public String getPeriodSelfLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);

    public String getAllSelfTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getLatestSelfTextReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);


    public String getPeriodSelfTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);

    public String getAllSelfPhotoReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getLatestSelfPhotoReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);


    public String getPeriodSelfPhotoReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);

    public String getPhoto(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "picId") String picId);





    // NOTE(Torgrim): Added for testing distinct location reports
    public String getDistinctPeriodTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);

}
