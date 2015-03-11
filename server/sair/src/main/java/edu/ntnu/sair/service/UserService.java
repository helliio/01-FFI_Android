package edu.ntnu.sair.service;

import org.apache.cxf.annotations.GZIP;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface UserService {

    public String register(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "name") String name,
            @WebParam(name = "teamId") String teamId);

    public String login(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid,
            @WebParam(name = "password") String password,
            @WebParam(name = "loginTime") String loginTime);

    public String checkLogin(
            @WebParam(name = "username") String username,
            @WebParam(name = "uuid") String uuid);

}
