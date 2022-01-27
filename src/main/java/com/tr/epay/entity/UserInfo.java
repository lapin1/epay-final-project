package com.tr.epay.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String street;
    private String state;
    private String zip;
    private String phone;
    private String email;

    public UserInfo() {

    }

    public UserInfo(int id, String firstName, String lastName, String city, String street, String state, String zip, String phone, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    public UserInfo(String firstName, String lastName, String city, String street, String state, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.street = street;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id && firstName.equals(userInfo.firstName) && lastName.equals(userInfo.lastName) && city.equals(userInfo.city) && street.equals(userInfo.street) && state.equals(userInfo.state) && zip.equals(userInfo.zip) && phone.equals(userInfo.phone) && email.equals(userInfo.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, city, street, state, zip, phone, email);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}


