package com.aprilchun.androidtest.service;

import com.aprilchun.androidtest.util.Code;
import com.aprilchun.androidtest.util.Constant;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Calendar;

/**
 * Created by chun on 2/18/15.
 */
public class UserService {
    public String register(String username, String password, String name, String teamId) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "register");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: password
            soapObject.addProperty("password", Code.encryptMD5(password));
            // arg2: name
            soapObject.addProperty("name", name);
            // arg3: teamId
            soapObject.addProperty("teamId", teamId);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            HttpTransportSE ht = new HttpTransportSE(Constant.SERVICE_URL + "UserService?wsdl");
            ht.call(null, envelope);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String login(String username, String deviceId, String password) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "login");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Code.encryptMD5(username + deviceId));
            // arg2: password
            soapObject.addProperty("password", Code.encryptMD5(password));
            // arg3: loginTime
            soapObject.addProperty("loginTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            HttpTransportSE ht = new HttpTransportSE(Constant.SERVICE_URL + "UserService?wsdl");
            ht.call(null, envelope);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
