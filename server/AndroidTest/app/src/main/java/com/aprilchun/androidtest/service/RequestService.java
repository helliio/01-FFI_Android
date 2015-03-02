package com.aprilchun.androidtest.service;

import com.aprilchun.androidtest.util.Code;
import com.aprilchun.androidtest.util.Constant;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chun on 2/18/15.
 */
public class RequestService {
    public String getMemberLocations(String username, String deviceId) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getMemberLocations");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Code.encryptMD5(username + deviceId));
            // arg2: sendingTime
            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = soapObject;

            HttpTransportSE ht = new HttpTransportSE(Constant.SERVICE_URL + "RequestService?wsdl");
            ht.call(null, envelope);


            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public JSONArray getTeamLocations(String username, String deviceId) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getTeamLocations");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Code.encryptMD5(username + deviceId));
            // arg2: sendingTime
            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            List<HeaderProperty> headers = new ArrayList<>();
            HeaderProperty headerProperty = new HeaderProperty("Accept-Encoding", "gzip");
            headers.add(headerProperty);
            headerProperty = new HeaderProperty("Content-Encoding", "gzip");
            headers.add(headerProperty);

            HttpTransportService ht = new HttpTransportService(Constant.SERVICE_URL + "RequestService?wsdl");
            ht.call(null, envelope, headers);
            SoapObject response = (SoapObject) envelope.bodyIn;

            JSONArray array = new JSONArray(response.getProperty(0).toString());
            for (int i = 0; i < array.length(); i++) {
                System.out.println(array.get(i));
            }
            return array;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
