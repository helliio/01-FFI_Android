package ffiandroid.situationawareness.model.datahandling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.net.URL;

import ffiandroid.situationawareness.model.localdb.DAOlocation;
import ffiandroid.situationawareness.model.localdb.DAOphoto;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.UserInfo;
import ffiandroid.situationawareness.model.localdb.DAOtextReport;

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
            reportUnsendPhotos();
            report.download();
            location.download();
            downloadPhotoHandling();
        }
        updateReportStatus();
        return null;
    }

    /**
     * update report status
     */
    private void updateReportStatus() {
        DAOlocation daOlocation = new DAOlocation(context);
        DAOphoto daOphoto = new DAOphoto(context);
        DAOtextReport daOtextReport = new DAOtextReport(context);

        int locationCount = daOlocation.getMyNOTReportedItemCount(UserInfo.getUserID());
        if (locationCount != UserInfo.getUnReportedLocations()) {
            UserInfo.setUnReportedLocations(locationCount);
        }
        int textCount = daOtextReport.getMyNOTReportedItemCount(UserInfo.getUserID());
        if (textCount != UserInfo.getUnReportedText()) {
            UserInfo.setUnReportedText(textCount);
        }
        int photoCount = daOphoto.getMyNOTReportedItemCount(UserInfo.getUserID());
        if (photoCount != UserInfo.getUnReportedPhotos()) {
            UserInfo.setUnReportedPhotos(photoCount);
        }
        daOlocation.close();
        daOphoto.close();
        daOtextReport.close();
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
        while (isOnline() && daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()) != null) {
            photo.upload();
        }
        daOphoto.close();
    }

    /**
     * handle download photos: <li>first download latest photo list</li> <li>then if has network connection and
     * not-downloaded photos from list, download one photo</li>
     */
    private void downloadPhotoHandling() {
        // NOTE(Torgrim): Added for debugging
        photo.download();
        new DownloadFilesTask().doInBackground();
    }

    /**
     * An inner AsyncTask class used to handle download and upload photo files from and to server:
     * <p/>
     * <li>download one file at a time, repeat the process until there is no un-downloaded photos from list or no
     * network connection</li>
     * <p/>
     * <li>upload one photo file at a time, repeat the process until there is no un-uploaded photos  or no network
     * connection</li>
     */
    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            DAOphoto daOphoto = new DAOphoto(context);
            PhotoReport photoReport = daOphoto.getOneNotDownloadedPhoto(UserInfo.getUserID());
            if (isOnline()) {
                if (photoReport != null) {
                    photo.downloadOnePhoto(photoReport);
                } else if (daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()) != null) {
                    photo.upload();
                }
            }
            daOphoto.close();
            return null;
        }

        protected void onPostExecute(Long result) {
            DAOphoto daOphoto = new DAOphoto(context);
            PhotoReport photoReport = daOphoto.getOneNotDownloadedPhoto(UserInfo.getUserID());
            if (isOnline()) {
                if (photoReport != null || daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()) != null) {
                    doInBackground();
                }
            }
            daOphoto.close();
        }
    }

}