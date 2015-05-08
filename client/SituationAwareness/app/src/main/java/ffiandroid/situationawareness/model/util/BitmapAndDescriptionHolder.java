package ffiandroid.situationawareness.model.util;

import android.graphics.Bitmap;

import ffiandroid.situationawareness.model.PhotoReport;

/**
 * Created by Torgrim on 08/05/2015.
 */
public class BitmapAndDescriptionHolder {

    public Bitmap bitmap;
    public String description;
    public PhotoReport report;

    public BitmapAndDescriptionHolder(Bitmap bitmap, String description, PhotoReport photoReport)
    {
        this.bitmap = bitmap;
        this.description = description;
        this.report = photoReport;
    }

    public String getPath()
    {
        return report.getPath();
    }

}
