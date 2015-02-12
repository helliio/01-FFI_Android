package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.EventLog;

import java.util.List;

public interface EventLogDao {
    public void add(EventLog eventLog);

    public void delete(EventLog eventLog);

    public void update(EventLog eventLog);

    public EventLog getById(long id);

    public List<EventLog> getAll();
}
