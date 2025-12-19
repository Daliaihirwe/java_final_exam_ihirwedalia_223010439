package com.emms.model;


public class Customer {

    private int customerId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private int planId;

    public Customer() {
    }

    public Customer(int customerId, String name, String address, String phone, String email, int planId) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.planId = planId;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + "'" +
                ", address='" + address + "'" +
                ", phone='" + phone + "'" +
                ", email='" + email + "'" +
                ", planId=" + planId +
                '}';
    }
}
