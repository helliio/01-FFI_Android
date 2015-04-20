package ffiandroid.situationawareness.model.service.impl;

import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;

import ffiandroid.situationawareness.model.service.UserService;
import ffiandroid.situationawareness.model.util.Coder;
import ffiandroid.situationawareness.model.util.Constant;
import ffiandroid.situationawareness.model.util.Sender;

/**
 * Created by chun on 2/18/15.
 */
public class SoapUserService implements UserService {
    public String register(String username, String password, String name, String teamId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "register");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: password
        soapObject.addProperty("password", Coder.encryptMD5(password));
        // arg2: name
        soapObject.addProperty("name", name);
        // arg3: teamId
        soapObject.addProperty("teamId", teamId);

        return Sender.sendSOAPRequest(soapObject, "UserService?wsdl");
    }

    public String login(String username, String deviceId, String password) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "login");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: password
        soapObject.addProperty("password", Coder.encryptMD5(password));
        // arg3: loginTime
        soapObject.addProperty("loginTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

        return Sender.sendSOAPRequest(soapObject, "UserService?wsdl");
    }

}
