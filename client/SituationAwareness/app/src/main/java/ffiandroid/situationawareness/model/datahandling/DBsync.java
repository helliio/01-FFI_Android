package ffiandroid.situationawareness.model.datahandling;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ffiandroid.situationawareness.model.service.ReportService;
import ffiandroid.situationawareness.model.service.RequestService;
import ffiandroid.situationawareness.model.service.impl.SoapReportService;
import ffiandroid.situationawareness.model.service.impl.SoapRequestService;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 19, 2015.
 */
public abstract class DBsync {

    protected Context context;
    protected ReportService reportService = new SoapReportService();
    protected RequestService requestService = new SoapRequestService();

    public DBsync(Context context) {
        this.context = context;
    }

    /**
     * upload from server to local database
     */
    public abstract void upload();

    /**
     * down load from server to local database
     */
    public abstract void download();

    /**
     * convert string to json array
     *
     * @param message
     * @return en JSONArray or null
     */
    public JSONArray stringToJsonArray(String message) {
        try {
            JSONObject job = new JSONObject(message);
            return new JSONArray(job.getString("obj"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
