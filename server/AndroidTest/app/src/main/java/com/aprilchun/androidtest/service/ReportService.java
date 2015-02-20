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
public class ReportService {
    public String sendLocationReport(String username, String deviceId, double longitude, double latitude) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendLocationReport");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Code.encryptMD5(username + deviceId));
            // arg2: longitude
            soapObject.addProperty("longitude", String.valueOf(longitude));
            // arg3: latitude
            soapObject.addProperty("latitude", String.valueOf(latitude));
            // arg4: sendingTime
            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.bodyOut = soapObject;

            HttpTransportSE ht = new HttpTransportSE(Constant.SERVICE_URL + "ReportService?wsdl");
            ht.call(null, envelope);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
