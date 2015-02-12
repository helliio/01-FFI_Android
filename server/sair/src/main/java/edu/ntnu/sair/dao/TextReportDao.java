package edu.ntnu.sair.dao;

import edu.ntnu.sair.model.TextReport;

import java.util.List;

public interface TextReportDao {
    public void add(TextReport textReport);

    public void delete(TextReport textReport);

    public void update(TextReport textReport);

    public TextReport getById(String id);

    public List<TextReport> getAll();
}
