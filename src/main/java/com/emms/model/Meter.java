package com.emms.model;

import java.sql.Timestamp;


public class Meter {

    private int meterId;
    private String serialNumber;
    private Timestamp installationDate;
    private int customerId;

    public Meter() {
    }

    public Meter(int meterId, String serialNumber, Timestamp installationDate, int customerId) {
        this.meterId = meterId;
        this.serialNumber = serialNumber;
        this.installationDate = installationDate;
        this.customerId = customerId;
    }


    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Timestamp getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Timestamp installationDate) {
        this.installationDate = installationDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Meter{" +
                "meterId=" + meterId +
                ", serialNumber='" + serialNumber + "'" +
                ", installationDate=" + installationDate +
                ", customerId=" + customerId +
                '}';
    }
}
