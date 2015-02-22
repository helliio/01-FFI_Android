package ffiandroid.situationawareness.service;


import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Calendar;

import ffiandroid.situationawareness.util.Code;
import ffiandroid.situationawareness.util.Constant;


/**
 * Created by chun on 2/18/15.
 */
public class RequestService {
    public String getLocationsByMember(String username, String deviceId) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getLocationsByMember");
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

    public JSONArray getLocationsByTeam(String username, String deviceId) {
        try {
            SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getTeamLocations");
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

            SoapObject response = (SoapObject) envelope.bodyIn;

            return new JSONArray(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}

