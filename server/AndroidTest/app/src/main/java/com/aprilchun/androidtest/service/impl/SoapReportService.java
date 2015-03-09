package com.aprilchun.androidtest.service.impl;

import com.aprilchun.androidtest.service.ReportService;
import com.aprilchun.androidtest.util.Coder;
import com.aprilchun.androidtest.util.Constant;
import com.aprilchun.androidtest.util.HttpTransport;
import com.aprilchun.androidtest.util.Sender;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chun on 2/18/15.
 */
public class SoapReportService implements ReportService {
    public String sendLocationReport(String username, String deviceId, Calendar sendingTime, double latitude, double longitude) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendLocationReport");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", sendingTime.getTimeInMillis());
        // arg3: latitude
        soapObject.addProperty("latitude", String.valueOf(latitude));
        // arg4: longitude
        soapObject.addProperty("longitude", String.valueOf(longitude));

        return Sender.send(soapObject, "ReportService?wsdl");
    }

    @Override
    public String sendLocationReportList(String username, String deviceId, Calendar sendingTime, String list) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendLocationReportList");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", sendingTime.getTimeInMillis());
        // arg3: list
        soapObject.addProperty("list", list);

        return Sender.send(soapObject, "ReportService?wsdl");
    }

    public String sendTextReport(String username, String deviceId, Calendar sendingTime, double latitude, double longitude, String content) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendTextReport");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", sendingTime.getTimeInMillis());
        // arg3: latitude
        soapObject.addProperty("latitude", String.valueOf(latitude));
        // arg4: longitude
        soapObject.addProperty("longitude", String.valueOf(longitude));
        // arg5: content
        soapObject.addProperty("content", content);

        return Sender.send(soapObject, "ReportService?wsdl");
    }

    @Override
    public String sendTextReportList(String username, String deviceId, Calendar sendingTime, double latitude, double longitude, String list) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendTextReport");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", sendingTime.getTimeInMillis());
        // arg3: list
        soapObject.addProperty("list", list);

        return Sender.send(soapObject, "ReportService?wsdl");
    }

    public String sendPhotoReport(String username, String deviceId, Calendar sendingTime, double latitude, double longitude, int direction, File file, String description) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendPhotoReport");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", sendingTime.getTimeInMillis());
        // arg3: latitude
        soapObject.addProperty("latitude", String.valueOf(latitude));
        // arg4: longitude
        soapObject.addProperty("longitude", String.valueOf(longitude));
        // arg5: direction
        soapObject.addProperty("direction", String.valueOf(direction));
        // arg6: file
        soapObject.addProperty("file", Coder.encryptBASE64(file));
        // arg7: extension
        soapObject.addProperty("extension", file.getName().substring(file.getName().lastIndexOf(".") + 1));
        // arg8: description
        soapObject.addProperty("description", description);

        return Sender.send(soapObject, "ReportService?wsdl");
    }


}
