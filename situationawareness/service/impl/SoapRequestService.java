package ffiandroid.situationawareness.service.impl;


import org.ksoap2.serialization.SoapObject;

import java.util.Calendar;

import ffiandroid.situationawareness.service.RequestService;
import ffiandroid.situationawareness.util.Coder;
import ffiandroid.situationawareness.util.Constant;
import ffiandroid.situationawareness.util.Sender;

/**
 * Created by chun on 2/18/15.
 */
public class SoapRequestService implements RequestService {

    public String getAllTeamLocations(String username, String deviceId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getAllTeamLocations");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getLatestTeamLocations(String username, String deviceId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getLatestTeamLocations");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getPeriodTeamLocations(String username, String deviceId, String startTime, String endTime) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getPeriodTeamLocations");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
        // arg3: startTime
        soapObject.addProperty("startTime", startTime);
        // arg4: endTime
        soapObject.addProperty("endTime", endTime);

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getAllTeamTextReports(String username, String deviceId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getAllTeamTextReports");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getLatestTeamTextReports(String username, String deviceId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getLatestTeamTextReports");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getPeriodTeamTextReports(String username, String deviceId, String startTime, String endTime) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getPeriodTeamTextReports");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
        // arg3: startTime
        soapObject.addProperty("startTime", startTime);
        // arg4: endTime
        soapObject.addProperty("endTime", endTime);

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getAllTeamPhotoReports(String username, String deviceId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getAllTeamPhotoReports");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getLatestTeamPhotoReports(String username, String deviceId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getLatestTeamPhotoReports");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }

    @Override
    public String getPeriodTeamPhotoReports(String username, String deviceId, String startTime, String endTime) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getPeriodTeamPhotoReports");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
        // arg3: startTime
        soapObject.addProperty("startTime", startTime);
        // arg4: endTime
        soapObject.addProperty("endTime", endTime);

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }


    @Override
    public String getPhoto(String username, String deviceId, String picId) {
        SoapObject soapObject = new SoapObject("http://service.sair.ntnu.edu/", "getPhoto");
        // arg0: username
        soapObject.addProperty("username", Coder.encryptMD5(username));
        // arg1: uuid
        soapObject.addProperty("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        soapObject.addProperty("sendingTime", Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis());
        // arg3: picId
        soapObject.addProperty("picId", picId);

        return Sender.sendSOAPRequest(soapObject, "RequestService?wsdl");
    }
}
