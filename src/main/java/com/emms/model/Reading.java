package com.emms.model;

import java.math.BigDecimal;
import java.sql.Timestamp;


public class Reading {

    private long readingId;
    private int meterId;
    private Timestamp readingTimestamp;
    private BigDecimal kwhConsumed;

    public Reading() {
    }

    public Reading(long readingId, int meterId, Timestamp readingTimestamp, BigDecimal kwhConsumed) {
        this.readingId = readingId;
        this.meterId = meterId;
        this.readingTimestamp = readingTimestamp;
        this.kwhConsumed = kwhConsumed;
    }


    public long getReadingId() {
        return readingId;
    }

    public void setReadingId(long readingId) {
        this.readingId = readingId;
    }

    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    public Timestamp getReadingTimestamp() {
        return readingTimestamp;
    }

    public void setReadingTimestamp(Timestamp readingTimestamp) {
        this.readingTimestamp = readingTimestamp;
    }

    public BigDecimal getKwhConsumed() {
        return kwhConsumed;
    }

    public void setKwhConsumed(BigDecimal kwhConsumed) {
        this.kwhConsumed = kwhConsumed;
    }

    @Override
    public String toString() {
        return "Reading{" +
                "readingId=" + readingId +
                ", meterId=" + meterId +
                ", readingTimestamp=" + readingTimestamp +
                ", kwhConsumed=" + kwhConsumed +
                '}';
    }
}
