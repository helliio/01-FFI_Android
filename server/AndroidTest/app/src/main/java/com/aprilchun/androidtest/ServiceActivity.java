package com.aprilchun.androidtest;

import android.util.Log;

import com.aprilchun.androidtest.com.aprilchun.androidtest.util.Constant;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Calendar;

/**
 * Created by chun on 2/18/15.
 */
public class ServiceActivity {
    public String register(String username, String password, String name, String teamId) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "register");
            // arg0: username
            soapObject.addProperty("arg0", username);
            // arg1: password
            soapObject.addProperty("arg1", password);
            // arg2: name
            soapObject.addProperty("arg2", name);
            // arg3: teamId
            soapObject.addProperty("arg3", teamId);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = soapObject;

            HttpTransportSE ht = new HttpTransportSE(Constant.SERVICE_URL + "UserService?wsdl");
            ht.call(null, envelope);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String login(String username, String androidId, String password) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "login");
            // arg0: username
            soapObject.addProperty("arg0", username);
            // arg1: uuid
            soapObject.addProperty("arg1", androidId);
            // arg2: password
            soapObject.addProperty("arg2", password);
            // arg3: loginTime
            soapObject.addProperty("arg3", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
