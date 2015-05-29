package ffiandroid.situationawareness.model.util;

import ffiandroid.situationawareness.model.PhotoReport;

/**
 * Created by Torgrim on 13/05/2015.
 *
 * A class that is used to hold information
 * about a report that is to be sent to the server,
 * only used in {@link ffiandroid.situationawareness.controller.AllReportsView}
 *
 */
public class AdapterContentHolder
{
    public String report = null;
    public PhotoReport photoReport = null;

    public AdapterContentHolder(PhotoReport photoReport, String report)
    {
        this.report = report;
        this.photoReport = photoReport;
    }
}
