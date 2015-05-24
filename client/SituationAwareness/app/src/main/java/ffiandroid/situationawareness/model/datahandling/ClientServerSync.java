package ffiandroid.situationawareness.model.datahandling;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

import ffiandroid.situationawareness.model.ParameterSetting;

/**
 * loop handle: repeat missions: temporary method, going to use google :Transferring Data Using Sync Adapters
 * <p/>
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 05/03/15.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class ClientServerSync {
    private Context context;

    public ClientServerSync(Context context) {
        this.context = context;
    }

    public void start() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {

            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            PerformBackgroundTask performBackgroundTask = new PerformBackgroundTask(context);
                            if(Build.VERSION.SDK_INT >= 11)
                                performBackgroundTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else {
                                performBackgroundTask.execute();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 2000, ParameterSetting.getAutoSyncTime());
    }
}
