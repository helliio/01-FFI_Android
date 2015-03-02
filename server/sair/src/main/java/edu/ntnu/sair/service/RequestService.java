package edu.ntnu.sair.service;

import org.apache.cxf.annotations.GZIP;
import org.apache.cxf.interceptor.OutInterceptors;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface RequestService {

    public String getTeamMembers(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getTeamLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getMemberLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getPeriodLocations(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime);
}
