package com.example.furniture;

public class Shipping {

    public Shipping() {
    }

    String id,name,email,phone,address,city,zipcode,time;

    public Shipping(String id, String name, String email, String phone, String address, String city,String zipcode,String time) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.zipcode=zipcode;
        this.time=time;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    } //getting id

    public void setId(String id) {
        this.id = id;
    }//setting id

    public String getName() {
        return name;
    }//getting id

    public void setName(String name) {
        this.name = name;
    }//setting name

    public String getEmail() {
        return email;
    }//getting email

    public void setEmail(String email) {
        this.email = email;
    }//setting email

    public String getPhone() {
        return phone;
    }//getting phone

    public void setPhone(String phone) {
        this.phone = phone;
    }//setting phone

    public String getAddress() {
        return address;
    }//getting address

    public void setAddress(String address) {
        this.address = address;
    }//setting address

    public String getCity() {
        return city;
    }//country

    public void setCity(String city) {
        this.city = city;
    }//getting country
}