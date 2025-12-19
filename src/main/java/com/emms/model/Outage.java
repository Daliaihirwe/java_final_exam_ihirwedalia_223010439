package com.emms.model;

import java.sql.Timestamp;


public class Outage {

    private int outageId;
    private int meterId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;

    public Outage() {
    }

    public Outage(int outageId, int meterId, Timestamp startTime, Timestamp endTime, String description) {
        this.outageId = outageId;
        this.meterId = meterId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }


    public int getOutageId() {
        return outageId;
    }

    public void setOutageId(int outageId) {
        this.outageId = outageId;
    }

    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Outage{"
                + "outageId=" + outageId + 
                ", meterId=" + meterId + 
                ", startTime=" + startTime + 
                ", endTime=" + endTime + 
                ", description='" + description + '\'' + 
                '}';
    }
}
