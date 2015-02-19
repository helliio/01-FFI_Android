package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.service.ReportService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService(endpointInterface = "edu.ntnu.sair.service.ReportService", targetNamespace = "http://service.sair.ntnu.edu/")
@Service("reportService")
public class ReportServiceImpl implements ReportService {


    @Override
    public String sendLocationReport(String memberId, String uuid, String longitude, String latitude, String sendingTime) {
        return null;
    }

    @Override
    public String sendTextReport(String memberId, String uuid, String longitude, String latitude, String content, String sendingTime) {
        return null;
    }

    @Override
    public String sendPhotoReport(String memberId, String uuid, String longitude, String latitude, String extension, String description, String direction, String sendingTime) {
        return null;
    }
}
