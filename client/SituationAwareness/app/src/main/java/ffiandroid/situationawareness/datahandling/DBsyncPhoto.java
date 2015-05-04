package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import ffiandroid.situationawareness.MapActivity;
import ffiandroid.situationawareness.localdb.DAOphoto;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.util.Coder;

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
        //        new Thread(uploadThread).start();
        //    }
        //
        //    Runnable uploadThread = new Runnable() {
        //        @Override public void run() {
        DAOphoto daOphoto = new DAOphoto(context);
        Looper.prepare();
        try {
            photoReportR = daOphoto.getOneNotReportedPhoto(UserInfo.getUserID());
            File file = new File(photoReportR.getPath());
            String message = reportService
                    .sendPhotoReport(UserInfo.getUserID(), UserInfo.getMyAndroidID(), photoReportR.getDatetimeLong(),
                                    photoReportR.getLatitude(), photoReportR.getLongitude(), 0, file,
                                    photoReportR.getTitle(), photoReportR.getDescription());
            Log.i(this.getClass().getSimpleName(), "--upload one photo -- " + message);
            if (message != null) {
                Message msg = handlerUploadPhoto.obtainMessage();
                JSONObject jsonObject = new JSONObject(message);
                msg.obj = jsonObject.get("desc");
                handlerUploadPhoto.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            daOphoto.close();
        }
        Looper.loop();
    }
    //    };

    private Handler handlerUploadPhoto = new Handler() {
        @Override public void handleMessage(Message msg) {
            if (msg.obj.toString().equalsIgnoreCase("success")) {
                DAOphoto daOphoto = new DAOphoto(context);
                daOphoto.updateIsReported(photoReportR);
                UserInfo.setLastSyncSucceed(true);
                daOphoto.close();
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

            DAOphoto daoPhoto = null;
            try {
                daoPhoto = new DAOphoto(context);
                String message = requestService
                        .getPeriodTeamPhotoReports(UserInfo.getUserID(), UserInfo.getMyAndroidID(),
                                String.valueOf(daoPhoto.getLastDownloadedPhotoReportTime(UserInfo.getUserID())),
                                String.valueOf(System.currentTimeMillis()));
                savePhotoListToLocalDB(daoPhoto, stringToJsonArray(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally
            {
                    // NOTE(Torgrim): Is this necessary, added for testing..
                   daoPhoto.close();
            }
        }
    };

    /**
     * Save downloaded photo list and save them as items in local database
     *
     * @param daoPhoto
     * @param jsonArray
     */
    private void savePhotoListToLocalDB(DAOphoto daoPhoto, JSONArray jsonArray) {
        if (jsonArray != null) {
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject job = jsonArray.getJSONObject(i);
                    PhotoReport pr = new PhotoReport();
                    pr.setIsreported(false);
                    pr.setUserid(job.getString("username"));
                    pr.setDatetime(job.getLong("timestamp"));
                    pr.setLatitude(Double.parseDouble(job.getString("latitude")));
                    pr.setLongitude(Double.parseDouble(job.getString("longitude")));

                    pr.setExtension(job.getString("extension"));

                    // NOTE(Torgrim): added title and id...
                    pr.setTitle(job.getString("title"));
                    pr.setPicId(job.getString("id"));


                    pr.setDescription(job.getString("description"));
                    daoPhoto.addPhoto(pr);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                daoPhoto.close();
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
                String message =
                        requestService.getPhoto(UserInfo.getUserID(), UserInfo.getMyAndroidID(), photoReportD.getPicId());
                savePhoto(new JSONObject(message).getString("obj"), photoReportD);

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
        String imgname = photoReport.getUserid() + photoReport.getDatetimeLong() + "." + photoReport.getExtension();
        DAOphoto daOphoto = null;

        try {
            if (isExternalStorageWritable()) {
                File imgDir = getAlbumStorageDir(context, "sair");
                String path = imgDir.getPath();
                File file = new File(imgDir, imgname);
                Coder.decryptBASE64(file, photofile);
                path += "/" + imgname;
                photoReport.setPath(path);
                daOphoto = new DAOphoto(context);
                daOphoto.updatePhotoPath(photoReport);
                daOphoto.updateIsReported(photoReport);
                daOphoto.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            // NOTE(Torgrim): Added for testing fix of leaked database
            daOphoto.close();
        }
    }

    /**
     * Get the directory for the app's private pictures directory : User this method to save pictures private to the
     * application
     */
    public File getAlbumStorageDir(Context context, String albumName) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(this.getClass().getSimpleName(), "Directory not created");
        }
        return file;
    }

    /**
     * Get the directory for the user's public pictures directory. User this method to save pictures publicly
     */
    public File getAlbumStorageDir(String albumName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(this.getClass().getSimpleName(), "Directory not created");
        }
        return file;
    }

    /**
     * Checks if external storage is available for read and write
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if external storage is available to at least read
     */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
