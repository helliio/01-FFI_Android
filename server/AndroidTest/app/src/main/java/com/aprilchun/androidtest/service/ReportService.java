package com.aprilchun.androidtest.service;

import android.media.Image;

import com.aprilchun.androidtest.util.Code;
import com.aprilchun.androidtest.util.Constant;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chun on 2/18/15.
 */
public class ReportService {
    public String sendLocationReport(String username, String deviceId, double latitude, double longitude) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendLocationReport");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Code.encryptMD5(username + deviceId));
            // arg2: sendingTime
            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
            // arg3: latitude
            soapObject.addProperty("latitude", String.valueOf(latitude));
            // arg4: longitude
            soapObject.addProperty("longitude", String.valueOf(longitude));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            List<HeaderProperty> headers = new ArrayList<>();
            HeaderProperty headerProperty = new HeaderProperty("Accept-Encoding", "gzip");
            headers.add(headerProperty);
            headerProperty = new HeaderProperty("Content-Encoding", "gzip");
            headers.add(headerProperty);

            HttpTransportService ht = new HttpTransportService(Constant.SERVICE_URL + "ReportService?wsdl");
            ht.call(null, envelope, headers);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String sendTextReport(String username, String deviceId, double latitude, double longitude, String content) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendTextReport");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Code.encryptMD5(username + deviceId));
            // arg2: sendingTime
            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
            // arg3: latitude
            soapObject.addProperty("latitude", String.valueOf(latitude));
            // arg4: longitude
            soapObject.addProperty("longitude", String.valueOf(longitude));
            // arg5: content
            soapObject.addProperty("content", content);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            List<HeaderProperty> headers = new ArrayList<>();
            HeaderProperty headerProperty = new HeaderProperty("Accept-Encoding", "gzip");
            headers.add(headerProperty);
            headerProperty = new HeaderProperty("Content-Encoding", "gzip");
            headers.add(headerProperty);

            HttpTransportService ht = new HttpTransportService(Constant.SERVICE_URL + "ReportService?wsdl");
            ht.call(null, envelope, headers);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String sendPhotoReport(String username, String deviceId, double latitude, double longitude, int direction, File file, String description) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "sendPhotoReport");
            // arg0: username
            soapObject.addProperty("username", Code.encryptMD5(username));
            // arg1: uuid
            soapObject.addProperty("uuid", Code.encryptMD5(username + deviceId));
            // arg2: sendingTime
            soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
            // arg3: latitude
            soapObject.addProperty("latitude", String.valueOf(latitude));
            // arg4: longitude
            soapObject.addProperty("longitude", String.valueOf(longitude));
            // arg5: direction
            soapObject.addProperty("direction", String.valueOf(direction));
            // arg6: file
            soapObject.addProperty("file", Code.encryptBASE64(file));
            // arg7: extension
            soapObject.addProperty("extension", file.getName().substring(file.getName().lastIndexOf(".") + 1));
            // arg8: description
            soapObject.addProperty("description", description);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            List<HeaderProperty> headers = new ArrayList<>();
            HeaderProperty headerProperty = new HeaderProperty("Accept-Encoding", "gzip");
            headers.add(headerProperty);
            headerProperty = new HeaderProperty("Content-Encoding", "gzip");
            headers.add(headerProperty);

            HttpTransportService ht = new HttpTransportService(Constant.SERVICE_URL + "ReportService?wsdl");
            ht.call(null, envelope, headers);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
