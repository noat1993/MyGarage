package com.example.gabys.mygarage.models;

/**
 * Created by evyatartoledano on 13/01/2018.
 */

public class Customers {

    public String fname;
    public String lname;
    public String city;
    public String id_number;
    public String phone;
    public String email;


    public Customers(){

    }

    public Customers(String fname, String lname, String city, String id_number, String phone, String email){

        this.fname = fname;
        this.lname = lname;
        this.city = city;
        this.id_number = id_number;
        this.phone = phone;
        this.email = email;
    }

}