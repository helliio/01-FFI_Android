package edu.ntnu.sair.service;

import edu.ntnu.sair.model.Location;

/**
 * Created by chun on 2/16/15.
 */

public interface ReportService {

    public default String sendLocationReport(Location location) {
        return null;
    }

    public String sendTextReport();

    public String sendPhotoReport();
}
