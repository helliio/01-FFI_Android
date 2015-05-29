package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.model.PhotoReport;

import java.util.List;

/**
 * Created by chun on 2/10/15.
 */

public interface PhotoReportDao {
    public void add(PhotoReport photoReport);

    public void delete(PhotoReport photoReport);

    public void update(PhotoReport photoReport);

    public PhotoReport getById(long id);

    public List<PhotoReport> getAll();

    public List<PhotoReport> getByMember(Member member);

    public PhotoReport getByMemberLatest(Member member);

    public List<PhotoReport> getByTeam(String teamId);

    public List<PhotoReport> getByTeamLatest(String teamId);

    public List<PhotoReport> getByTeamPeriod(String teamId, String username, long startTime, long endTime);

    public List<PhotoReport> getByUsernamePeriod(String username, long startTime, long endTime);
}
