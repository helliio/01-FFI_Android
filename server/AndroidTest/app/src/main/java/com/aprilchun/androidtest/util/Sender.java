package com.aprilchun.androidtest.util;

import android.os.Handler;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chun on 3/4/15.
 */
public class Sender extends Thread {

    public static String send(SoapObject soapObject, String wsdl) {
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            envelope.bodyOut = soapObject;

            List<HeaderProperty> headers = new ArrayList<>();
            HeaderProperty headerProperty;
            headerProperty = new HeaderProperty("Accept-Encoding", "gzip");
            headers.add(headerProperty);
            headerProperty = new HeaderProperty("Content-Encoding", "gzip");
            headers.add(headerProperty);

            HttpTransport ht = new HttpTransport(Constant.SERVICE_URL + wsdl);
            ht.call(null, envelope, headers);

            return envelope.getResponse().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
