package ffiandroid.situationawareness.model.datahandling;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ffiandroid.situationawareness.model.localdb.DAOlocation;
import ffiandroid.situationawareness.model.LocationReport;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 15, 2015.
 */
public class DBsyncLocation extends DBsync {
    public DBsyncLocation(Context context) {
        super(context);
    }

    private List<LocationReport> locationReports;

    /**
     * upload location from local to server
     */
    public void upload() {
        new Thread(uplocadLocationThread).start();
    }

    Runnable uplocadLocationThread = new Runnable() {
        @Override
        public void run() {
            DAOlocation daOlocation = new DAOlocation(context);
            Looper.prepare();
            try {
                locationReports = daOlocation.getMyNOTReportedLocations(UserInfo.getUserID());
                String message = reportService.sendLocationReportList(UserInfo.getUserID(), UserInfo.getMyAndroidID(),
                        System.currentTimeMillis(), getWaitingLocations(locationReports));
                Message msg = handlerUploadLocation.obtainMessage();
                JSONObject jsonObject = new JSONObject(message);
                msg.obj = jsonObject.get("desc");
                handlerUploadLocation.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                daOlocation.close();
            }
            Looper.loop();
        }
    };

    /**
     * handler the message from server
     */
    private Handler handlerUploadLocation = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.toString().equalsIgnoreCase("success")) {
                myLocationStatusIsReported(locationReports);
                UserInfo.setLastSyncSucceed(true);
            } else {
                UserInfo.setLastSyncSucceed(false);
            }
        }
    };

    /**
     * change my location status to is reported
     *
     * @param locationReports
     */
    private void myLocationStatusIsReported(List<LocationReport> locationReports) {
        if (locationReports.size() > 0) {
            DAOlocation daOlocation = new DAOlocation(context);
            for (LocationReport locationReport : locationReports) {
                daOlocation.updateIsReported(locationReport);
                System.out.println("update location report status number of rows affected: " +
                        daOlocation.updateIsReported(locationReport));
            }
            daOlocation.close();
        }
    }

    /**
     * @param locationReports
     * @return json array list with location reports attached
     */
    private JSONArray getWaitingLocations(List<LocationReport> locationReports) {
        JSONArray jsonArray = new JSONArray();
        for (LocationReport locationReport : locationReports) {
            jsonArray.put(LocationReport2JSONOb(locationReport));
        }
        return jsonArray;
    }


    /**
     * @param locationReport
     * @return sjon object with location report attached
     */
    private JSONObject LocationReport2JSONOb(LocationReport locationReport) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("longitude", locationReport.getLongitude());
            jsonObject.put("latitude", locationReport.getLatitude());
            jsonObject.put("sendingTime", locationReport.getDatetimeLong());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * download location from server to local database
     */
    public void download() {
        new Thread(downloadLocationThread).start();
    }

    Runnable downloadLocationThread = new Runnable() {
        @Override public void run() {
            try {
                String message = requestService.getLatestTeamLocations(UserInfo.getUserID(), UserInfo.getMyAndroidID());
                saveLocationToLocalDB(stringToJsonArray(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * save locations to local database
     *
     * @param jsonArray
     */
    private void saveLocationToLocalDB(JSONArray jsonArray) {
        if (jsonArray != null) {
            DAOlocation daOlocation = new DAOlocation(context);
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject job = jsonArray.getJSONObject(i);
                    LocationReport lr = new LocationReport();
                    lr.setIsreported(true);
                    lr.setUserid(job.getString("username"));
                    lr.setName(job.getString("name"));
                    lr.setDatetime(job.getLong("timestamp"));
                    lr.setLatitude(job.getDouble("latitude"));
                    lr.setLongitude(job.getDouble("longitude"));
                    daOlocation.addLocation(lr);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                daOlocation.close();
            }
        }
    }
}