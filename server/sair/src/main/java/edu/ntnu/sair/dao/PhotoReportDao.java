package edu.ntnu.sair.dao;

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
}
