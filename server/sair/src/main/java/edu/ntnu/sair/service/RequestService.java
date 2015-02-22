package edu.ntnu.sair.service;

import edu.ntnu.sair.model.Location;

import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 * Created by chun on 2/16/15.
 */

@WebService(endpointInterface = "edu.ntnu.sair.service.RequestService", targetNamespace = "http://service.sair.ntnu.edu/")
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
