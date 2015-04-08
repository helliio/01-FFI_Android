package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import ffiandroid.situationawareness.localdb.DAOphoto;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 09/03/15.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class PerformBackgroundTask extends AsyncTask {
    private Context context;
    private DBsyncPhoto photo;
    private DBsync report;
    private DBsync location;

    public PerformBackgroundTask(Context context) {
        this.context = context;
        photo = new DBsyncPhoto(context);
        report = new DBsyncTextReport(context);
        location = new DBsyncLocation(context);
    }

    @Override protected Object doInBackground(Object[] params) {
        if (isOnline()) {

            report.upload();
            location.upload();
            //            reportUnsendPhotos();
            report.download();
            location.download();
            downloadPhotoHandling();
        }
        return null;
    }

    /**
     * @return true is there is a network connection
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * check if there is network connection and un-send photos, if true send one
     */
    public void reportUnsendPhotos() {
        DAOphoto daOphoto = new DAOphoto(context);
        Log.i(this.getClass().getSimpleName(),
                "----un-sent photos: called---: " + daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()));
        while (isOnline() && daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()) != null) {
            Log.i(this.getClass().getSimpleName(), "----upload un-send photo ///");
            photo.upload();
        }
        daOphoto.close();
    }

    /**
     * handle download photos: <li>first download latest photo list</li> <li>then if has network connection and
     * un-downloaded photo from list, download one photo</li>
     */
    private void downloadPhotoHandling() {
        photo.download();
        downloadOnePhoto();
    }

    /**
     * if has network connection and un-downloaded photo from list, call download one photo
     */
    public void downloadOnePhoto() {
        DAOphoto daOphoto = new DAOphoto(context);
        PhotoReport photoReport = daOphoto.getOneNotDownloadedPhoto(UserInfo.getUserID());
        if (isOnline() && photoReport != null) {
            photo.downloadOnePhoto(photoReport);
        }
        daOphoto.close();
    }
}