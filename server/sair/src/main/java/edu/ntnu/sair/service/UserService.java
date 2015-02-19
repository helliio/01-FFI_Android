package edu.ntnu.sair.service;

import javax.jws.WebService;
import java.util.Calendar;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface UserService {

    public String register(String username, String password, String name, String teamId);

    public String login(String username, String uuid, String password, String loginTime);

    public String checkLogin(String memberId, String uuid);

}
