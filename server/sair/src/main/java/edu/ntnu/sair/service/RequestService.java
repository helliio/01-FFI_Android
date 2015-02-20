package edu.ntnu.sair.service;

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

    public String getLocationsByMember(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "sendingTime") String sendingTime);

    public String getLocationsByPeriod(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "startTime") String startTime,
            @WebParam(name = "endTime") String endTime,
            @WebParam(name = "sendingTime") String sendingTime);
}
