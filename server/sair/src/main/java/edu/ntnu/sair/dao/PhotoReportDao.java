package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.PhotoReport;

import java.util.List;

public interface PhotoReportDao {
    public void add(PhotoReport photoReport);

    public void delete(PhotoReport photoReport);

    public void update(PhotoReport photoReport);

    public PhotoReport getById(String id);

    public List<PhotoReport> getAll();
}
