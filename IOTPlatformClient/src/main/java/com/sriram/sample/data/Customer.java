package com.sriram.sample.data;

import javax.persistence.*;

/**
 * Created by srirkumar on 10/18/2015.
 */
@Entity
@Table(name = "customer", schema = "world")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customerId")
    private Long customerId;

    private String firstname;

    private String lastname;

    private String email;

    private String phonenumber;

    private String accountnumber;

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("FirstName: " + this.firstname)
                .append(" LastName: " + this.lastname)
                .append(" Email: " + this.email)
                .append(" PhoneNumber: " + this.phonenumber)
                .append(" AccountNumber: " + this.accountnumber)
                .append(" CustomerID: " + this.customerId);
        return sb.toString();
    }
}
