package ffiandroid.situationawareness.datahandling;

import android.content.Context;

import java.util.List;

import ffiandroid.situationawareness.localdb.DAOphoto;
import ffiandroid.situationawareness.model.PhotoReport;
import ffiandroid.situationawareness.model.UserInfo;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 19, 2015.
 */
public class DBsyncPhoto extends DBsync {

    private List<PhotoReport> photoReports;

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
            photoReports = daOphoto.getMyNOTReportedPhotos(UserInfo.getUserID());
//            String message = reportService.sendPhotoReport(UserInfo.getUserID(),UserInfo.getMyAndroidID(),
//                    System.currentTimeMillis(),0,)

        }
    };

    /**
     * down load photo from server to local database
     */
    @Override public void download() {

    }
}
