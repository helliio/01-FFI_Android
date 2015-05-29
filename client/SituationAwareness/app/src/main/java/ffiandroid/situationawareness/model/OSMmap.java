package ffiandroid.situationawareness.model;

import android.content.Context;


import java.util.ArrayList;
import java.util.List;

import ffiandroid.situationawareness.model.localdb.DAOlocation;
import ffiandroid.situationawareness.model.localdb.DAOphoto;
import ffiandroid.situationawareness.model.localdb.DAOtextReport;

/**
 * This OSMmap File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class OSMmap {



    /*
    // Created by Torgrim for testing purpose
    // return all coworkers location reports in the server database.
     */

    public ArrayList<LocationReport> getAllCoworkersLocationReports(Context context)
    {
        DAOlocation daoLocation = null;
        ArrayList<LocationReport> locationReports = null;
        try
        {
            daoLocation = new DAOlocation(context);
            locationReports = (ArrayList)daoLocation.getCoWorkerLocations(UserInfo.getUserID());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            daoLocation.close();
        }
        return locationReports;

    }

    // Created by Torgrim
    // gets all coworkersTextReports
    public List<TextReport> getAllCoworkersTextReports(Context context)
    {
        DAOtextReport doaTextReport = null;
        List<TextReport> textReports = null;
        try
        {
            doaTextReport = new DAOtextReport(context);
            textReports = doaTextReport.getAllTextReports();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            doaTextReport.close();
        }
        return textReports;

    }


    public List<PhotoReport> getAllPhotoReports(Context context)
    {
        DAOphoto daoPhoto = null;
        List<PhotoReport> photoReports = null;
        try
        {
            daoPhoto = new DAOphoto(context);
            photoReports = daoPhoto.getAllPhotos();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if(daoPhoto != null) {
                daoPhoto.close();
            }
        }
        return photoReports;
    }

}
