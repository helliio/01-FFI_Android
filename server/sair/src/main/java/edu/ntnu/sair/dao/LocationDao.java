package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.Location;

import java.util.List;

public interface LocationDao {
	public void add(Location location);

	public void delete(Location location);

	public void update(Location location);

	public Location getById(long id);

	public List<Location> getAll();
}
