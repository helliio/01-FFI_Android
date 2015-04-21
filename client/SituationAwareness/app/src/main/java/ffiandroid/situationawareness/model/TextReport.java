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
     * current data time will be generated when insert to database; isreported by default is set to false;
     *
     * @param report
     */
    public TextReport(String report) {
        super(true);
        this.report = report;
    }

    public TextReport() {
    }


    /**
     * Sets new report.
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

    /*
    @Override
    public String toString() {
        return "userid='" + userid +
                ",      isreported=" + isreported +
                "\nreport='" + report +
                "\ndatetime=" + df.format(datetime.getTime()) +
                "\nlatitude=" + latitude +
                ", longitude=" + longitude;
    }
    */
}
