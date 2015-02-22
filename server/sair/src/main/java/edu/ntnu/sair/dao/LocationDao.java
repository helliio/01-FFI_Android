package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Location;
import edu.ntnu.sair.model.Member;

import java.util.List;

/**
 * Created by chun on 2/10/15.
 */

public interface LocationDao {
	public void add(Location location);

	public void delete(Location location);

	public void update(Location location);

	public Location getById(long id);

    public List<Location> getAll();

    public List<Location> getByMember(Member member);

    public List<Location> getByTeam(String teamId);
}
