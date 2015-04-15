package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ffiandroid.situationawareness.localdb.DAOtextReport;
import ffiandroid.situationawareness.model.TextReport;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 19, 2015.
 */
public class DBsyncTextReport extends DBsync {

    private List<TextReport> reports;

    public DBsyncTextReport(Context context) {
        super(context);
    }

    /**
     * upload report from local to server
     */
    @Override public void upload() {
        new Thread(uplocadThread).start();
    }

    Runnable uplocadThread = new Runnable() {
        @Override
        public void run() {
            DAOtextReport daOreport = new DAOtextReport(context);
            Looper.prepare();
            try {
                reports = daOreport.getMyUnsentReports(UserInfo.getUserID());
                String message = reportService
                        .sendTextReportList(UserInfo.getUserID(), UserInfo.getMyAndroidID(), System.currentTimeMillis(),
                                getWaitingList(reports));
                Message msg = handlerUploadLocation.obtainMessage();
                JSONObject jsonObject = new JSONObject(message);
                msg.obj = jsonObject.get("desc");
                handlerUploadLocation.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                daOreport.close();
            }
            Looper.loop();
        }
    };

    /**
     * convert a TextReport list to a json array list
     *
     * @param reports
     * @return json array
     */
    private JSONArray getWaitingList(List<TextReport> reports) {
        JSONArray jsonArray = new JSONArray();
        for (TextReport textReport : reports) {
            jsonArray.put(report2JSONOb(textReport));
        }
        return jsonArray;
    }

    /**
     * convert TextReport to json object
     *
     * @param textReport
     * @return a json object
     */
    private JSONObject report2JSONOb(TextReport textReport) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("longitude", textReport.getLongitude());
            jsonObject.put("latitude", textReport.getLatitude());
            jsonObject.put("sendingTime", textReport.getDatetimeLong());
            jsonObject.put("content", textReport.getReport());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * an Handler to handler feedback message from server
     */
    private Handler handlerUploadLocation = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj.toString().equalsIgnoreCase("success")) {
                myReportStatusIssent(reports);
                UserInfo.setLastSyncSucceed(true);
            } else {
                UserInfo.setLastSyncSucceed(false);
            }
        }
    };

    /**
     * change my report status to is sent
     *
     * @param textReports
     */
    private void myReportStatusIssent(List<TextReport> textReports) {
        if (textReports.size() > 0) {
            DAOtextReport daOtextReport = new DAOtextReport(context);
            for (TextReport textReport : textReports) {
                daOtextReport.updateIsReported(textReport);
                System.out.println("update text report status number of rows affected: " +
                        daOtextReport.updateIsReported(textReport));
            }
            daOtextReport.close();
        }
    }

    /**
     * down load report from server to local database
     */
    @Override public void download() {
        new Thread(downloadThread).start();
    }

    Runnable downloadThread = new Runnable() {
        @Override public void run() {
            try {
                String message = requestService.getAllTeamTextReports(UserInfo.getUserID(), UserInfo.getMyAndroidID());
                saveTextReportToLocalDB(stringToJsonArray(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * save text report to local database
     *
     * @param jsonArray
     */
    private void saveTextReportToLocalDB(JSONArray jsonArray) {
        if (jsonArray != null) {
            DAOtextReport daOtextReport = new DAOtextReport(context);
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject job = jsonArray.getJSONObject(i);
                    TextReport textReport = new TextReport();
                    textReport.setIsreported(true);
                    textReport.setUserid(job.getString("username"));
                    textReport.setDatetime(job.getLong("timestamp"));
                    textReport.setLatitude(job.getDouble("latitude"));
                    textReport.setLongitude(job.getDouble("longitude"));
                    daOtextReport.addReport(textReport);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                daOtextReport.close();
            }
        }
    }
}

