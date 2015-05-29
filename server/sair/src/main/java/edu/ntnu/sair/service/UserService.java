package edu.ntnu.sair.service;

import org.apache.cxf.annotations.GZIP;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by Chun on 2/16/15.
 * <br>
 * UserService: Offer the interfaces for the client to authenticate the user.
 */

@WebService
public interface UserService {
    /**
     * Offer the interface for the client to register a new user
     *
     * @param username username for the user
     * @param password password for the user
     * @param name     name for the user
     * @param teamId   teamId for the user
     * @return result with string type as a json or a xml format
     */
    public String register(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "name") String name,
            @WebParam(name = "teamId") String teamId);

    /**
     * Offer the interface for the client to login a  user
     *
     * @param username  username for the user
     * @param password  password for the user
     * @param uuid      unique id for the user together with the device id
     * @param loginTime timestamp when the user login
     * @return result with string type as a json or a xml format
     */
    public String login(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "password") String password,
            @WebParam(name = "loginTime") String loginTime);

    /**
     * Offer the interface for the client to check a user if logged in
     *
     * @param username username for the user
     * @param uuid     unique id for the user together with the device id
     * @return result with string type as a json or a xml format
     */
    public String checkLogin(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid);

    public String getAllMembers();

}
