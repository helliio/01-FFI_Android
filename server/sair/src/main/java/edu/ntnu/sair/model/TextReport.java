package edu.ntnu.sair.model;

import javax.persistence.*;

/**
 * Created by chun on 2/6/15.
 * <br>
 * TextReport: TextReport model is used to store the detail of the text report.
 */

@Entity
@Table(name = "textreport")
public class TextReport {
    private long id;
    private Location location;
    private String content;


    @Id

    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "locationid")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
