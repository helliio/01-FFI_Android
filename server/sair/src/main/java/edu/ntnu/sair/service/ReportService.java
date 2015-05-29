package edu.ntnu.sair.service;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by Chun on 2/16/15.
 * <br>
 * ReportService: Offer the interfaces for the client to report the reports from the server.
 */

@WebService
public interface ReportService {

        /**
         * Offer the interface for the client to send location report to the server
         *
         * @param username username for the user
         * @param uuid unique id for the user together with device id
         * @param sendingTime timestamp when sent from client
         * @param latitude latitude of the location
         * @param longitude longitude of the location
         *
         * @return result with string type as a json or a xml format
         */
    public String sendLocationReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "longitude") String longitude);

    /**
     * Offer the interface for the client to send location report list to the server
     *
     * @param username    username for the user
     * @param uuid        unique id for the user together with device id
     * @param sendingTime timestamp when sent from client
     * @param list        list of the location reports
     * @return result with string type as a json or a xml format
     */
    public String sendLocationReportList(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "list") String list);

        /**
         * Offer the interface for the client to send text report to the server
         *
         * @param username username for the user
         * @param uuid unique id for the user together with device id
         * @param sendingTime timestamp when sent from client
         * @param latitude latitude of the location
         * @param longitude longitude of the location
         * @param content content of the text report
         *
         * @return result with string type as a json or a xml format
         */
    public String sendTextReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "longitude") String longitude,
            @WebParam(name = "content") String content);

        /**
         * Offer the interface for the client to send text report list to the server
         *
         * @param username username for the user
         * @param uuid unique id for the user together with device id
         * @param sendingTime timestamp when sent from client
         * @param list list of the text reports
         *
         * @return result with string type as a json or a xml format
         */
    public String sendTextReportList(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "list") String list);

        /**
         * Offer the interface for the client to send photo report to the server
         *
         * @param username username for the user
         * @param uuid unique id for the user together with device id
         * @param sendingTime timestamp when sent from client
         * @param latitude latitude of the location
         * @param longitude longitude of the location
         * @param direction direction of the user
         * @param file photo file
         * @param extension extension of the photo file
         * @param title title of the report
         * @param description description of the report
         *
         * @return result with string type as a json or a xml format
         */
    public String sendPhotoReport(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "latitude") String latitude,
            @WebParam(name = "longitude") String longitude,
            @WebParam(name = "direction") String direction,
            @WebParam(name = "file") String file,
            @WebParam(name = "extension") String extension,
            @WebParam(name = "title") String title,
            @WebParam(name = "description") String description);

    // Added by Torgrim for testing
    public String getAllReports();
}
