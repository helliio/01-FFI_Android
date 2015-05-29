package ffiandroid.situationawareness.model.datahandling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;

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
public class PerformBackgroundTask extends AsyncTask<Void, Void, String> {
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

    protected String doInBackground(Void... params) {
        if (isOnline()) {
            report.upload();
            location.upload();
            reportUnsentPhotos();
            report.download();
            location.download();
            downloadPhotoHandling();
        }
        updateReportStatus();
        return "test";
    }

    /**
     * update report status
     */
    private void updateReportStatus() {
        DAOlocation daOlocation = null;
        DAOphoto daOphoto = null;
        DAOtextReport daOtextReport = null;
        try {

            daOlocation = new DAOlocation(context);
            daOphoto = new DAOphoto(context);
            daOtextReport = new DAOtextReport(context);

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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(daOlocation != null) {
                daOlocation.close();
            }
            if(daOphoto != null) {
                daOphoto.close();
            }
            if(daOtextReport != null) {
                daOtextReport.close();
            }
        }
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
     * check if there is network connection and un-sent photos, if true send one
     */
    public void reportUnsentPhotos() {
        DAOphoto daOphoto = null;
        try {
            daOphoto = new DAOphoto(context);
            while (isOnline() && daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()) != null) {
                photo.upload();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(daOphoto != null) {
                daOphoto.close();
            }
        }
    }

    /**
     * handle download photos: <li>first download latest photo list</li> <li>then if has network connection and
     * not-downloaded photos from list, download one photo</li>
     */
    private void downloadPhotoHandling() {
        // NOTE(Torgrim): Added for debugging
        photo.download();
        if(Build.VERSION.SDK_INT >= 11)
            new DownloadFilesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
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
        @Override protected Long doInBackground(URL... urls) {
            DAOphoto daOphoto = null;
            try {
                daOphoto = new DAOphoto(context);
                PhotoReport photoReport = daOphoto.getOneNotDownloadedPhoto(UserInfo.getUserID());
                if (isOnline()) {
                    if (photoReport != null) {
                        photo.downloadOnePhoto(photoReport);
                    }
                    else if (daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()) != null) {
                        photo.upload();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                if(daOphoto != null) {
                    daOphoto.close();
                }
            }
            return null;
        }

        protected void onPostExecute(Long result) {
            DAOphoto daOphoto = null;
            try {
                daOphoto = new DAOphoto(context);
                PhotoReport photoReport = daOphoto.getOneNotDownloadedPhoto(UserInfo.getUserID());
                if (isOnline()) {
                    if (photoReport != null || daOphoto.getOneNotReportedPhoto(UserInfo.getUserID()) != null) {
                        //doInBackground();
                        System.out.println("This is where we would send a request for a second photo...");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                if(daOphoto != null) {
                    daOphoto.close();
                }
            }
        }
    }

}