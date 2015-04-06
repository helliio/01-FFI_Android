package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;

import ffiandroid.situationawareness.localdb.DAOphoto;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 19, 2015.
 */
public class DBsyncPhoto extends DBsync {

    private PhotoReport photoReport;

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
                photoReport = daOphoto.getOneNotReportedPhoto(UserInfo.getUserID());
                File file = new File(photoReport.getPath());
                String message = reportService
                        .sendPhotoReport(UserInfo.getUserID(), UserInfo.getMyAndroidID(), System.currentTimeMillis(),
                                photoReport.getLatitude(), photoReport.getLongitude(), 0, file,
                                photoReport.getDescription());
                Log.i(this.getClass().getSimpleName(), "--- " + message);
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
                daOphoto.updateIsReported(photoReport);
                UserInfo.setLastSyncSucceed(true);
            } else {
                UserInfo.setLastSyncSucceed(false);
            }
        }
    };

    /**
     * down load photo from server to local database
     */
    @Override public void download() {

    }
}
