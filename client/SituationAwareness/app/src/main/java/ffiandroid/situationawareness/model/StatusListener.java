package ffiandroid.situationawareness.model;

/** Used to observer status changes and UI can do the respond changes accordingly.
 * This file is part of SituationAwareness
 * <p/>
 * Created by GuoJunjun <junjunguo.com> on April 16, 2015.
 */
public interface StatusListener {
    /**
     * menu status changed
     */
    public void menuStatusChanged();

    /**
     * location status changed
     */
    public void locationStatusChanged();

    /**
     * text status changed
     */
    public void textStatusChanged();

    /**
     * photo status changed
     */
    public void photoStatusChanged();

    /**
     * last time report succeed or not
     */
    public void lastReportStatusChanged();
}
