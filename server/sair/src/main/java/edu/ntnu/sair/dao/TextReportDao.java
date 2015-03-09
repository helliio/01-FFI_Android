package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Member;
import edu.ntnu.sair.model.TextReport;

import java.util.List;

/**
 * Created by chun on 2/10/15.
 */

public interface TextReportDao {
    public void add(TextReport textReport);

    public void delete(TextReport textReport);

    public void update(TextReport textReport);

    public TextReport getById(long id);

    public List<TextReport> getAll();

    public List<TextReport> getByMember(Member member);

    public TextReport getByMemberLatest(Member member);

    public List<TextReport> getByTeam(String teamId);

    public List<TextReport> getByTeamLatest(String teamId);

    public List<TextReport> getByTeamPeriod(String teamId, long startTime, long endTime);
}
