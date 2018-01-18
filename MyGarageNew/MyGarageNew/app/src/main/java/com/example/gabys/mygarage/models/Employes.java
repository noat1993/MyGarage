package com.example.gabys.mygarage.models;

/**
 * Created by evyatartoledano on 17/01/2018.
 */


public class Employes {

    public String fname;
    public String lname;
    public String city;
    public String id_number;
    public String is_admin;
    public String phone;
    public String email;
    public String employee_id;

    public Employes(){

    }

    public Employes(String city, String email, String employee_id, String fname, String id_number, String lname, String phone){
        this.city = city;
        this.email = email;
        this.employee_id = employee_id;
        this.fname = fname;
        this.id_number = id_number;
        this.lname = lname;
        this.phone = phone;
        this.is_admin="false";
    }
}
