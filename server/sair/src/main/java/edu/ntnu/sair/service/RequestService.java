package edu.ntnu.sair.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by Chun on 2/16/15.
 * <br>
 * RequestService: Offer the interfaces for the client to request the reports from the server.
 */

@WebService
public interface RequestService {
    /**
     * Offer the interface for the client to request all location reports from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     *
     * @return result with string type as a json or a xml format
     */
    public String getAllTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);
    /**
     * Offer the interface for the client to request latest location reports from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     *
     * @return result with string type as a json or a xml format
     */
    public String getLatestTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    /**
     * Offer the interface for the client to request location reports in a period time from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     * @param startTime start of the period
     * @param endTime end of the period
     *
     * @return result with string type as a json or a xml format
     */
    public String getPeriodTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);
    /**
     * Offer the interface for the client to request all text reports from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     *
     * @return result with string type as a json or a xml format
     */
    public String getAllTeamTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);
    /**
     * Offer the interface for the client to request latest text reports from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     *
     * @return result with string type as a json or a xml format
     */
    public String getLatestTeamTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    /**
     * Offer the interface for the client to request text reports in a period time from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     * @param startTime start of the period
     * @param endTime end of the period
     *
     * @return result with string type as a json or a xml format
     */
    public String getPeriodTeamTextReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);
    /**
     * Offer the interface for the client to request all photo reports from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     *
     * @return result with string type as a json or a xml format
     */
    public String getAllTeamPhotoReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);
    /**
     * Offer the interface for the client to request latest photo reports from the server
     *
     * @param username username for the user
     * @param uuid unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     *
     * @return result with string type as a json or a xml format
     */
    public String getLatestTeamPhotoReports(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    /**
     * Offer the interface for the client to request photo reports in a period time from the server
     *
     * @param username    username for the user
     * @param uuid        unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     * @param startTime   start of the period
     * @param endTime     end of the period
     * @return result with string type as a json or a xml format
     */
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

}
