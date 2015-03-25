package ffiandroid.situationawareness.service.impl;


import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Calendar;

import ffiandroid.situationawareness.service.RequestService;
import ffiandroid.situationawareness.util.Coder;
import ffiandroid.situationawareness.util.Constant;
import ffiandroid.situationawareness.util.Sender;

/**
 * Created by chun on 2/18/15.
 */
public class RestRequestService implements RequestService {

    public String getAllTeamLocations(String username, String deviceId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));

        return Sender.sendRESTRequest(requestData, "request/getAllTeamLocations");
    }

    @Override
    public String getLatestTeamLocations(String username, String deviceId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));

        return Sender.sendRESTRequest(requestData, "request/getLatestTeamLocations");
    }

    @Override
    public String getPeriodTeamLocations(String username, String deviceId, String startTime, String endTime) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));
        // arg3: startTime
        requestData.add("startTime", startTime);
        // arg4: endTime
        requestData.add("endTime", endTime);

        return Sender.sendRESTRequest(requestData, "request/getPeriodTeamLocations");
    }

    @Override
    public String getAllTeamTextReports(String username, String deviceId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));

        return Sender.sendRESTRequest(requestData, "request/getAllTeamTextReports");
    }

    @Override
    public String getLatestTeamTextReports(String username, String deviceId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));

        return Sender.sendRESTRequest(requestData, "request/getLatestTeamTextReports");
    }

    @Override
    public String getPeriodTeamTextReports(String username, String deviceId, String startTime, String endTime) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));
        // arg3: startTime
        requestData.add("startTime", startTime);
        // arg4: endTime
        requestData.add("endTime", endTime);

        return Sender.sendRESTRequest(requestData, "request/getPeriodTeamTextReports");
    }

    @Override
    public String getAllTeamPhotoReports(String username, String deviceId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));

        return Sender.sendRESTRequest(requestData, "request/getAllTeamPhotoReports");
    }

    @Override
    public String getLatestTeamPhotoReports(String username, String deviceId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));

        return Sender.sendRESTRequest(requestData, "request/getLatestTeamPhotoReports");
    }

    @Override
    public String getPeriodTeamPhotoReports(String username, String deviceId, String startTime, String endTime) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));
        // arg3: startTime
        requestData.add("startTime", startTime);
        // arg4: endTime
        requestData.add("endTime", endTime);

        return Sender.sendRESTRequest(requestData, "request/getPeriodTeamPhotoReports");
    }


    @Override
    public String getPhoto(String username, String deviceId, String picId) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(Calendar.getInstance(Constant.TIME_ZONE).getTimeInMillis()));
        // arg3: picId
        requestData.add("picId", picId);

        return Sender.sendRESTRequest(requestData, "request/getPhoto");
    }
}
