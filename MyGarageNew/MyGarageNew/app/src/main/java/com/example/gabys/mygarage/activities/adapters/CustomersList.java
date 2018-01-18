package com.example.gabys.mygarage.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.models.Customers;

import java.util.List;

/**
 * Created by evyatartoledano on 17/01/2018.
 */

public class CustomersList extends ArrayAdapter<Customers> {
    private Activity context;
    List<Customers> customers;

    public CustomersList(Activity context, List<Customers> custom) {
        super(context, R.layout.activity_customers, custom);
        this.context = context;
        this.customers = custom;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_customers_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewID = (TextView) listViewItem.findViewById(R.id.textViewIDNumber);
        TextView textViewPhone = (TextView) listViewItem.findViewById(R.id.textViewPhone);

        Customers customer = customers.get(position);

        textViewName.setText("Name: " + customer.fname + " " + customer.lname);
        textViewID.setText("ID Number: " + customer.id_number);
        textViewPhone.setText("Phone: " + customer.phone);

        return listViewItem;
    }
}