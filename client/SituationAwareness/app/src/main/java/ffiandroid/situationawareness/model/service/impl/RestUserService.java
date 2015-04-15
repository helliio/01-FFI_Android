package ffiandroid.situationawareness.model.service.impl;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Calendar;

import ffiandroid.situationawareness.model.service.UserService;
import ffiandroid.situationawareness.model.util.Coder;
import ffiandroid.situationawareness.model.util.Constant;
import ffiandroid.situationawareness.model.util.Sender;

/**
 * Created by chun on 2/18/15.
 */
public class RestUserService implements UserService {
    public String register(String username, String password, String name, String teamId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: password
        requestData.add("password", Coder.encryptMD5(password));
        // arg2: name
        requestData.add("name", name);
        // arg3: teamId
        requestData.add("teamId", teamId);

        return Sender.sendRESTRequest(requestData, "user/register");
    }

    public String login(String username, String deviceId, String password) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: password
        requestData.add("password", Coder.encryptMD5(password));
        // arg3: loginTime
        requestData.add("loginTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));

        return Sender.sendRESTRequest(requestData, "user/login");
    }

}
