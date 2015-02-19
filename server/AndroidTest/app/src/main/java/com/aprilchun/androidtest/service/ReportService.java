package com.aprilchun.androidtest.service;

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
    public String sendLocationReport(String memberId, String deviceId, double longitude, double latitude) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendLocationReport");
            // arg0: memberId
            soapObject.addProperty("arg0", memberId);
            // arg1: deviceId
            soapObject.addProperty("arg1", deviceId);
            // arg2: longitude
            soapObject.addProperty("arg2", longitude);
            // arg3: latitude
            soapObject.addProperty("arg3", latitude);
            // arg3: latitude
            soapObject.addProperty("arg4", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

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
