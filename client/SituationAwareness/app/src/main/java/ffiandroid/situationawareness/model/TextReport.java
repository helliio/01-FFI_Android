package ffiandroid.situationawareness.model;

/**
 * This TextReport File is part of project: Situation Awareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on 3/8/2015.
 * <p/>
 * Responsible for this file: GuoJunjun
 */
public class TextReport extends LocationReport {
    private String report;

    /**
     * location are user current location
     * <p/>
     * current data time will be generated when insert to database; isreported by default is set to false;
     *
     * @param report
     */
    public TextReport(String report) {
        super(true);

        this.report = report;
    }

    /**
     * current data time will be generated when insert to database; isreported by default is set to false;
     *
     * @param report
     */
    public TextReport(String report, long time, double latitude, double longitude) {
        super(latitude, longitude);
        this.report = report;
        this.datetimeLong = time;
    }

    public TextReport() {
        super();
    }


    /**
     * Sets new report.gi
     *
     * @param report New value of report.
     */
    public void setReport(String report) { this.report = report; }

    /**
     * Gets report.
     *
     * @return Value of report.
     */
    public String getReport() { return report; }


    @Override
    public String toString() {
        return "name='" + name +
                ",      isreported=" + isreported +
                "\nreport='" + report +
                "\ndatetime=" + df.format(datetime.getTime()) +
                "\nlatitude=" + latitude +
                ", longitude=" + longitude;
    }

}
