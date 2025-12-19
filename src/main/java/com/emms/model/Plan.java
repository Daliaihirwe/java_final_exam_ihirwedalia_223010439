package com.emms.model;

import java.math.BigDecimal;


public class Plan {

    private int planId;
    private String name;
    private BigDecimal ratePerKwh;
    private String description;

    public Plan() {
    }

    public Plan(int planId, String name, BigDecimal ratePerKwh, String description) {
        this.planId = planId;
        this.name = name;
        this.ratePerKwh = ratePerKwh;
        this.description = description;
    }


    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRatePerKwh() {
        return ratePerKwh;
    }

    public void setRatePerKwh(BigDecimal ratePerKwh) {
        this.ratePerKwh = ratePerKwh;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Plan{"
                + "planId=" + planId + ", "
                + "name='" + name + '\'' + ", "
                + "ratePerKwh=" + ratePerKwh + ", "
                + "description='" + description + '\'' + 
                '}';
    }
}
