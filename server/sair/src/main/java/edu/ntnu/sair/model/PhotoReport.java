package edu.ntnu.sair.model;

import javax.persistence.*;

/**
 * Created by chun on 2/6/15.
 */

@Entity
@Table(name = "photoreport")
public class PhotoReport {
    private long id;
    private Location location;
    private String name;
    private String extension;
    private String path;
    // NOTE(Torgrim): Title to match local database on mobile
    private String title;
    private String description;
    private int direction;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // NOTE(Torgrim): Set title to match localdb on android phone...
    public String getTitle(){ return title;}
    public void setTitle(String title){this.title = title;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
