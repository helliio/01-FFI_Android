package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.dao.MemberDao;
import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.model.Result;
import edu.ntnu.sair.service.UserService;
import edu.ntnu.sair.util.Constant;
import org.apache.cxf.annotations.GZIP;
import org.apache.cxf.interceptor.OutInterceptors;
import org.apache.cxf.transport.common.gzip.GZIPOutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.Calendar;
import java.util.List;

/**
 * Created by chun on 2/16/15.
 */

@GZIP(force = true, threshold = 0)
@BindingType(value = SOAPBinding.SOAP12HTTP_BINDING)
@WebService(endpointInterface = "edu.ntnu.sair.service.UserService", targetNamespace = "http://service.sair.ntnu.edu/")
@Service("userService")
public class UserServiceImpl implements UserService {
    private MemberDao memberDao;


    @Transactional
    @Override
    public String register(String username, String password, String name, String teamId) {
        Member member = this.memberDao.getByUsername(username);
        if (member != null) {
            return new Result("register", "Username exists").toString();
        }
        member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        member.setName(name);
        member.setTeamId(teamId);
        this.memberDao.add(member);
        return new Result("register", "success").toString();
    }

    @Transactional
    @Override
    public String login(String username, String uuid, String password, String loginTime) {
        Member member = this.memberDao.getByUsername(username);
        if (member == null) {
            return new Result("login", "Incorrect username or password").toString();
        }
        if (member.getPassword() == null || !member.getPassword().equals(password)) {
            return new Result("login", "Incorrect username or password").toString();
        }
        member.setUuid(uuid);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(loginTime));
        calendar.add(Calendar.HOUR_OF_DAY, Constant.LOGIN_PERIOD);
        member.setValidTime(calendar);
        this.memberDao.update(member);
        return new Result("login", "success").toString();
    }

    @Transactional
    @Override
    public String checkLogin(String username, String uuid) {
        Member member = this.memberDao.getByUsername(username);
        if (member == null) {
            return new Result("login", "Invalid request: member does not exist").toString();
        }
        if (member.getUuid() == null || !member.getUuid().equals(uuid)) {
            return new Result("login", "Invalid request: member has not logged in").toString();
        }
        Calendar calendar = Calendar.getInstance(Constant.TIME_ZONE);
        if (calendar.after(member.getValidTime())) {
            return new Result("login", "Invalid request: login timeout").toString();
        }
        calendar.add(Calendar.HOUR_OF_DAY, Constant.LOGIN_PERIOD);
        member.setValidTime(calendar);
        this.memberDao.update(member);
        return "success";
    }


    // Added by Torgrim to show the all the current members
    // of the local database as a string.
    @Transactional
    @Override
    public String getAllMembers()
    {
        List<Member> members = memberDao.getAll();
        String result = "";
        for(Member member : members)
        {
            result += "Name                             >>>>    " +  member.getName() + "<br>";
            result += "Username                         >>>>    " + member.getUsername() + "<br>";
            result += "ID                               >>>>    " + member.getId() + "<br>";
            result += "UUID                             >>>>    " + member.getUuid() + "<br>";
            result += "Team ID                          >>>>    " + member.getTeamId() + "<br>";
            result += "Valid time                       >>>>    " + member.getValidTime() + "<br><br><br>";
        }

        return result;
    }

    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

}
