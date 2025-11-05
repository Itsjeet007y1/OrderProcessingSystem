package com.example.demo.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document("customer")
public class Customer {

    @Id
    @NotNull
    @NotEmpty
    @NotBlank
    private String customerId;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String sirName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @NotNull
    @NotEmpty
    private String title;

    public Customer(@NotNull @NotEmpty @NotBlank String customerId, @NotNull @NotEmpty String firstName,
            @NotNull @NotEmpty String sirName, Date dob, @NotNull @NotEmpty String title) {
        super();
        this.customerId = customerId;
        this.firstName = firstName;
        this.sirName = sirName;
        this.dob = dob;
        this.title = title;
    }

    public Customer() {
        super();
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Customer [customerId=");
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

}

