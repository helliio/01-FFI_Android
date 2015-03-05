package com.aprilchun.androidtest.service.impl;

import com.aprilchun.androidtest.service.RequestService;
import com.aprilchun.androidtest.util.Coder;
import com.aprilchun.androidtest.util.Constant;
import com.aprilchun.androidtest.util.Sender;

import org.json.JSONArray;
import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;

/**
 * Created by chun on 2/18/15.
 */
public class SoapRequestService implements RequestService {
//    public String getMemberLocations(String username, String deviceId) {
//        try {
//            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getMemberLocations");
//            // arg0: username
//            soapObject.addProperty("username", Coder.encryptMD5(username));
//            // arg1: uuid
//            soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
//            // arg2: sendingTime
//            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.bodyOut = soapObject;
//
//            HttpTransportSE ht = new HttpTransportSE(Constant.SERVICE_URL + "RequestService?wsdl");
//            ht.call(null, envelope);
//
//
//            return null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    public JSONArray getAllTeamLocations(String username, String deviceId) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getTeamLocations");
            // arg0: username
            soapObject.addProperty("username", Coder.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
            // arg2: sendingTime
            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

            String response = Sender.send(soapObject, "RequestService?wsdl");
            JSONArray array = new JSONArray(response);
//            JSONArray array = new JSONArray(response.getProperty(0).toString());
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
