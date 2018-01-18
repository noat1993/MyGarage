package com.example.gabys.mygarage.models;

/**
 * Created by shaul on 17/01/2018.
 */

public class Treatments {

    public String car_number;
    public String date;
    public String employee_id;
    public String issue;
    public String price;
    public String customerID;

    public Treatments()
    {

    }

    public Treatments (String car_number, String date, String employee_id, String issue, String price,String customerID)
    {
        this.car_number = car_number;
        this.date = date;
        this.employee_id = employee_id;
        this.issue = issue;
        this.price = price;
        this.customerID = customerID;
    }


}
