package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

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
public class DBsyncTextPeport extends DBsync {

    private List<TextReport> reports;

    public DBsyncTextPeport(Context context) {
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
                Log.i("test report", "------------" + message);
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

    private JSONArray getWaitingList(List<TextReport> reports) {
        JSONArray jsonArray = new JSONArray();
        for (TextReport textReport : reports) {
            jsonArray.put(report2JSONOb(textReport));
        }
        return jsonArray;
    }

    private JSONObject report2JSONOb(TextReport textReport) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("longitude", textReport.getLongitude());
            jsonObject.put("latitude", textReport.getLatitude());
            jsonObject.put("sendingTime", textReport.getDatetimeLong());
            jsonObject.put("content", textReport.getReport());
            Log.i("test report", "------------");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

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
        }
    }

    /**
     * down load report from server to local database
     */
    @Override public void download() {

    }
}

