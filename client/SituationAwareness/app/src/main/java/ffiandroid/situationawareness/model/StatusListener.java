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
     void menuStatusChanged();

    /**
     * location status changed
     */
     void locationStatusChanged();

    /**
     * text status changed
     */
     void textStatusChanged();

    /**
     * photo status changed
     */
     void photoStatusChanged();

    /**
     * last time report succeed or not
     */
     void lastReportStatusChanged();
}
