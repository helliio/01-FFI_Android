package ffiandroid.situationawareness.model;

/**
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on March 23, 2015.
 */
public class PhotoReport extends LocationReport {
    private String title, description, path, extension;

    // NOTE(Torgrim): added to use with downloadOnPhoto...
    private String picId;

    public PhotoReport(String title, String description, String path, long datetimeLong) {
        super(true);
        this.title = title;
        this.description = description;
        this.path = path;
        setDatetime(datetimeLong);
    }

    public PhotoReport(String title, String description, String path, long datetimeLong, boolean reported) {
        super(true);
        this.title = title;
        this.description = description;
        this.path = path;
        setDatetime(datetimeLong);
        setIsreported(reported);
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public PhotoReport() {
        super();
    }

    /**
     * Gets title.
     *
     * @return Value of title.
     */
    public String getTitle() { return title; }

    /**
     * Gets description.
     *
     * @return Value of description.
     */
    public String getDescription() { return description; }

    /**
     * Sets new title.
     *
     * @param title New value of title.
     */
    public void setTitle(String title) { this.title = title; }

    /**
     * Sets new description.
     *
     * @param description New value of description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets new path.
     *
     * @param path New value of path.
     */
    public void setPath(String path) { this.path = path; }

    /**
     * Gets path.
     *
     * @return Value of path.
     */
    public String getPath() { return path; }


    public String getPicId(){ return picId;}
    public void setPicId(String picId){ this.picId = picId;}

    @Override public String toString() {
        return "Title:" + title + "   " + df.format(getDatetime().getTime()) + " Reported: " + isIsreported() +
                "\nDescription:" + description;
    }
}