package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.dao.MemberDao;
import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.service.RequestService;
import edu.ntnu.sair.service.UserService;
import edu.ntnu.sair.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import java.util.Calendar;

/**
 * Created by chun on 2/16/15.
 */

@WebService(endpointInterface = "edu.ntnu.sair.service.RequestService", targetNamespace = "http://service.sair.ntnu.edu/")
@Service("requestService")
public class RequestServiceImpl implements RequestService {
    private MemberDao memberDao;

    @Transactional
    @Override
    public String getTeamMembers(String username, String uuid, String sendingTime) {
        return null;
    }

    @Transactional
    @Override
    public String getTeamLocations(String username, String uuid, String sendingTime) {
        return null;
    }

    @Transactional
    @Override
    public String getLocationsByMember(String username, String uuid, String sendingTime) {
        return null;
    }

    @Transactional
    @Override
    public String getLocationsByPeriod(String username, String uuid, String startTime, String endTime, String sendingTime) {
        return null;
    }


    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
}
