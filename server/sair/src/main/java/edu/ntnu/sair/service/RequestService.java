package edu.ntnu.sair.service;

import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface RequestService {

    public String getTeamMembers(String memberId, String uuid, String timestamp);

    public String getTeamLocations(String memberId, String uuid, String timestamp);

    public String getLocationsByMember(String memberId, String uuid, String timestamp);

    public String getLocationsByPeriod(String memberId, String uuid, String startTime, String endTime, String timestamp);
}
