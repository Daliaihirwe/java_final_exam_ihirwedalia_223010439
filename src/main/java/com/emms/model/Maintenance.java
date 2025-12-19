package com.emms.model;

import java.sql.Timestamp;


public class Maintenance {

    private int maintenanceId;
    private int meterId;
    private Timestamp maintenanceDate;
    private String technicianName;
    private String notes;

    public Maintenance() {
    }

    public Maintenance(int maintenanceId, int meterId, Timestamp maintenanceDate, String technicianName, String notes) {
        this.maintenanceId = maintenanceId;
        this.meterId = meterId;
        this.maintenanceDate = maintenanceDate;
        this.technicianName = technicianName;
        this.notes = notes;
    }


    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public int getMeterId() {
        return meterId;
    }

    public void setMeterId(int meterId) {
        this.meterId = meterId;
    }

    public Timestamp getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Timestamp maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Maintenance{"
                + "maintenanceId=" + maintenanceId +
                ", meterId=" + meterId +
                ", maintenanceDate=" + maintenanceDate +
                ", technicianName='" + technicianName + "'"
                + ", notes='" + notes + "'"
                + '}'
                ;
    }
}
