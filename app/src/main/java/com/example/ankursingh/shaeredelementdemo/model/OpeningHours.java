
package com.example.ankursingh.shaeredelementdemo.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OpeningHours {

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;
    @SerializedName("periods")
    @Expose
    private List<Period> periods = new ArrayList<Period>();
    @SerializedName("weekday_text")
    @Expose
    private List<String> weekdayText = new ArrayList<String>();

    /**
     * 
     * @return
     *     The openNow
     */
    public Boolean getOpenNow() {
        return openNow;
    }

    /**
     * 
     * @param openNow
     *     The open_now
     */
    public void setOpenNow(Boolean openNow) {
        this.openNow = openNow;
    }

    /**
     * 
     * @return
     *     The periods
     */
    public List<Period> getPeriods() {
        return periods;
    }

    /**
     * 
     * @param periods
     *     The periods
     */
    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    /**
     * 
     * @return
     *     The weekdayText
     */
    public List<String> getWeekdayText() {
        return weekdayText;
    }

    /**
     * 
     * @param weekdayText
     *     The weekday_text
     */
    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }

}
