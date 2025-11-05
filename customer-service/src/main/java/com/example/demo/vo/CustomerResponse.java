package com.example.demo.vo;

import java.util.Date;

public class CustomerResponse {

    private String customerId;
    private String firstName;
    private String sirName;
    private Date dob;
    private String title;

    public CustomerResponse(String customerId, String firstName, String sirName, Date dob, String title) {
        super();
        this.customerId = customerId;
        this.firstName = firstName;
        this.sirName = sirName;
        this.dob = dob;
        this.title = title;
    }

    public CustomerResponse() {
        super();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CustomerResponse [customerId=");
        builder.append(customerId);
        builder.append(", firstName=");
        builder.append(firstName);
        builder.append(", sirName=");
        builder.append(sirName);
        builder.append(", dob=");
        builder.append(dob);
        builder.append(", title=");
        builder.append(title);
        builder.append("]");
        return builder.toString();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSirName() {
        return sirName;
    }

    public void setSirName(String sirName) {
        this.sirName = sirName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

