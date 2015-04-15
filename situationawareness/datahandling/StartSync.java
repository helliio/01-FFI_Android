package ffiandroid.situationawareness.datahandling;

import android.content.Context;

/**
 * This file is part of Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 10/03/15.
 */
public class StartSync {
    private static ClientServerSync instance = null;

    private StartSync() {
    }

    public static ClientServerSync getInstance(Context context) {
        if (instance == null) {
            instance = new ClientServerSync(context);
        }
        return instance;
    }
}
