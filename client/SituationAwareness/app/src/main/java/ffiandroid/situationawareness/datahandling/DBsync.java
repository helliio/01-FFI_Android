package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ffiandroid.situationawareness.localdb.DAOlocation;
import ffiandroid.situationawareness.model.LocationReport;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.service.ReportService;
import ffiandroid.situationawareness.service.impl.SoapReportService;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 15, 2015.
 */
public class DBsync {
    private Context context;
    private List<LocationReport> locationReports;

    public DBsync(Context context) {
        this.context = context;
    }

    private ReportService reportService = new SoapReportService();


    /**
     * upload report from local to server
     *
     * @return true if succeed, false otherwise
     */
    public boolean uploadReport() {

        return false;
    }

    /**
     * down load report from server to local database
     *
     * @return true if succeed, false otherwise
     */
    public boolean downloadReport() {

        return false;
    }

    /**
     * upload location from local to server
     *
     * @return true if succeed, false otherwise
     */

    public void uploadLocation() {
        new Thread(serviceThread).start();
    }

    Runnable serviceThread = new Runnable() {
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
     * down load location from server to local database
     *
     * @return true if succeed, false otherwise
     */
    public boolean downloadLocation() {
        //        String message = new RequestService().getAllTeamLocations();


        return false;
    }

    /**
     * upload photo from local to server
     *
     * @return true if succeed, false otherwise
     */
    public boolean uploadPhoto() {

        return false;
    }

    /**
     * down load photo from server to local database
     *
     * @return true if succeed, false otherwise
     */
    public boolean downloadPhoto() {

        return false;
    }
}
