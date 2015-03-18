package ffiandroid.situationawareness.datahandling;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

/**
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 09/03/15.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class PerformBackgroundTask extends AsyncTask {
    private Context context;
    private DBsync dBsync;

    public PerformBackgroundTask(Context context) {
        this.context = context;
        dBsync = new DBsync(context);
    }

    @Override protected Object doInBackground(Object[] params) {
        if (isOnline()) {
            dBsync.uploadLocation();
        }
        return null;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}