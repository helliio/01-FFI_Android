package ffiandroid.situationawareness.datahandling;

import android.os.Handler;

/**
 * loop handle: repeat missions: temporary method, going to use google :Transferring Data Using Sync Adapters
 * <p/>
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 05/03/15.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class LoopHandle {
    private int mInterval;
    private Handler mHandler;

    public LoopHandle() {
        this.mInterval = 50000; //50 seconds
        mHandler = new Handler();
    }


    Runnable runLoopHandle = new Runnable() {
        @Override
        public void run() {
            
            mHandler.postDelayed(runLoopHandle, mInterval);
        }
    };

    public void startRepeatingTask() {
        runLoopHandle.run();
    }

    public void stopRepeatingTask() {
        mHandler.removeCallbacks(runLoopHandle);
    }
    
    
    
}
