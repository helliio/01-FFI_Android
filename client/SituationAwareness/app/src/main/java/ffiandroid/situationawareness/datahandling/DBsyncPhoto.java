package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import ffiandroid.situationawareness.localdb.DAOphoto;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 19, 2015.
 */
public class DBsyncPhoto extends DBsync {

    private PhotoReport photoReportR;
    private PhotoReport photoReportD;

    public DBsyncPhoto(Context context) {
        super(context);
    }

    /**
     * upload photo from local to server
     */
    @Override public void upload() {
        new Thread(uploadThread).start();
    }

    Runnable uploadThread = new Runnable() {
        @Override public void run() {
            DAOphoto daOphoto = new DAOphoto(context);
            Looper.prepare();
            try {
                photoReportR = daOphoto.getOneNotReportedPhoto(UserInfo.getUserID());
                File file = new File(photoReportR.getPath());
                String message = reportService
                        .sendPhotoReport(UserInfo.getUserID(), UserInfo.getMyAndroidID(), System.currentTimeMillis(),
                                photoReportR.getLatitude(), photoReportR.getLongitude(), 0, file,
                                photoReportR.getDescription());
                Log.i(this.getClass().getSimpleName(), "--upload one photo -- " + message);
                Message msg = handlerUploadPhoto.obtainMessage();
                JSONObject jsonObject = new JSONObject(message);
                msg.obj = jsonObject.get("desc");
                handlerUploadPhoto.sendMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                daOphoto.close();
            }
            Looper.loop();
        }
    };

    private Handler handlerUploadPhoto = new Handler() {
        @Override public void handleMessage(Message msg) {
            if (msg.obj.toString().equalsIgnoreCase("success")) {
                DAOphoto daOphoto = new DAOphoto(context);
                daOphoto.updateIsReported(photoReportR);
                UserInfo.setLastSyncSucceed(true);
            } else {
                UserInfo.setLastSyncSucceed(false);
            }
        }
    };

    /**
     * download latest photo list from server to local database
     */
    @Override public void download() {
        new Thread(downloadPhotoListThread).start();
    }

    Runnable downloadPhotoListThread = new Runnable() {
        @Override public void run() {
            try {
                DAOphoto daOphoto = new DAOphoto(context);
                String message = requestService
                        .getPeriodTeamPhotoReports(UserInfo.getUserID(), UserInfo.getMyAndroidID(),
                                String.valueOf(daOphoto.getLastDownloadedPhotoReportTime(UserInfo.getUserID())),
                                String.valueOf(System.currentTimeMillis()));
                savePhotoListToLocalDB(daOphoto, stringToJsonArray(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * Save downloaded photo list and save them as items in local database
     *
     * @param daOphoto
     * @param jsonArray
     */
    private void savePhotoListToLocalDB(DAOphoto daOphoto, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject job = jsonArray.getJSONObject(i);
                    Log.i(this.getClass().getSimpleName(), "=====save photo list===== " + job.toString());
                    PhotoReport pr = new PhotoReport();
                    pr.setIsreported(false);
                    pr.setPath(job.getString("filename"));
                    pr.setUserid(job.getString("username"));
                    pr.setDatetime(job.getLong("timestamp"));
                    pr.setLatitude(job.getDouble("latitude"));
                    pr.setLongitude(job.getDouble("longitude"));
                    pr.setDescription(job.getString("description"));
                    daOphoto.addPhoto(pr);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                daOphoto.close();
            }
        }
    }

    /**
     * download one photo
     */
    public void downloadOnePhoto(PhotoReport photoReport) {
        photoReportD = photoReport;
        new Thread(downloadOnePhotoThread).start();
    }

    Runnable downloadOnePhotoThread = new Runnable() {
        @Override public void run() {
            try {
                String message = requestService
                        .getPhoto(UserInfo.getUserID(), UserInfo.getMyAndroidID(), photoReportD.getPath());
                Log.i(this.getClass().getSimpleName(),
                        "----download one photo: ---- " + photoReportD.toString() + photoReportD.getPath());
                Log.i(this.getClass().getSimpleName(), "----download one photo---- " + message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * save photo to internal storage & update photo reports photo path
     *
     * @param photofile
     * @param photoReport
     */
    public void savePhoto(String photofile, PhotoReport photoReport) {
        String filename = photoReport.getUserid() + photoReport.getDatetimeLong();
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(photofile.getBytes());
            outputStream.close();
            String path = context.getFilesDir().getAbsolutePath();
            path += filename;
            photoReport.setPath(path);
            DAOphoto daOphoto = new DAOphoto(context);
            daOphoto.updatePhotoPath(photoReport);
            daOphoto.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
