package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.model.Member;

import java.util.Calendar;
import java.util.List;

/**
 * Created by chun on 2/10/15.
 */

public interface LocationDao {
    public void add(Location location);

    public void add(List<Location> list);

    public void delete(Location location);

    public void update(Location location);

    public Location getById(long id);

    public List<Location> getAll();

    public List<Location> getByMember(Member member);

    public Location getByMemberLatest(Member member);

    public List<Location> getByTeam(String teamId);

    public List<Location> getByTeamLatest(String teamId, long timeOfLastRequest);

    public List<Location> getByTeamPeriod(String teamId, long startTime, long endTime);
}
