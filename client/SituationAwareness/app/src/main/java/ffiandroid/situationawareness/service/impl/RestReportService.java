package ffiandroid.situationawareness.service.impl;


import org.json.JSONArray;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;

import ffiandroid.situationawareness.service.ReportService;
import ffiandroid.situationawareness.util.Coder;
import ffiandroid.situationawareness.util.Sender;

/**
 * Created by chun on 2/18/15.
 */
public class RestReportService implements ReportService {
    public String sendLocationReport(String username, String deviceId, long sendingTime, double latitude, double longitude) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(sendingTime));
        // arg3: latitude
        requestData.add("latitude", String.valueOf(latitude));
        // arg4: longitude
        requestData.add("longitude", String.valueOf(longitude));

        return Sender.sendRESTRequest(requestData, "report/sendLocationReport");
    }

    @Override
    public String sendLocationReportList(String username, String deviceId, long sendingTime, JSONArray list) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(sendingTime));
        // arg3: list
        requestData.add("list", list.toString());

        return Sender.sendRESTRequest(requestData, "report/sendLocationReportList");
    }

    public String sendTextReport(String username, String deviceId, long sendingTime, double latitude, double longitude, String content) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(sendingTime));
        // arg3: latitude
        requestData.add("latitude", String.valueOf(latitude));
        // arg4: longitude
        requestData.add("longitude", String.valueOf(longitude));
        // arg5: content
        requestData.add("content", content);

        return Sender.sendRESTRequest(requestData, "report/sendTextReport");
    }

    @Override
    public String sendTextReportList(String username, String deviceId, long sendingTime, JSONArray list) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(sendingTime));
        // arg3: list
        requestData.add("list", list.toString());

        return Sender.sendRESTRequest(requestData, "report/sendTextReportList");
    }

    public String sendPhotoReport(String username, String deviceId, long sendingTime, double latitude, double longitude, int direction, File file, String title, String description) {
        MultiValueMap requestData = new LinkedMultiValueMap<String, Object>();
        // arg0: username
        requestData.add("username", Coder.encryptMD5(username));
        // arg1: uuid
        requestData.add("uuid", Coder.encryptMD5(username + deviceId));
        // arg2: sendingTime
        requestData.add("sendingTime", String.valueOf(sendingTime));
        // arg3: latitude
        requestData.add("latitude", String.valueOf(latitude));
        // arg4: longitude
        requestData.add("longitude", String.valueOf(longitude));
        // arg5: direction
        requestData.add("direction", String.valueOf(direction));
        // arg6: file
        requestData.add("file", Coder.encryptBASE64(file));
        // arg7: extension
        requestData.add("extension", file.getName().substring(file.getName().lastIndexOf(".") + 1));

        // NOTE(Torgrim): added title..
        requestData.add("title", title);
        // arg8: description
        requestData.add("description", description);

        return Sender.sendRESTRequest(requestData, "report/sendPhotoReport");
    }

}
