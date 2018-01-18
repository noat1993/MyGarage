package com.example.gabys.mygarage.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.models.Employes;

import java.util.List;

/**
 * Created by evyatartoledano on 17/01/2018.
 */

public class EmployesList extends ArrayAdapter<Employes> {
    private Activity context;
    List<Employes> employes;

    public EmployesList(Activity context, List<Employes> employes) {
        super(context, R.layout.activity_employes, employes);
        this.context = context;
        this.employes = employes;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_employes_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewID = (TextView) listViewItem.findViewById(R.id.textViewID);
        TextView textViewPhone = (TextView) listViewItem.findViewById(R.id.textViewPhone);

        Employes employee = employes.get(position);

        textViewName.setText("Name: " + employee.fname + " " + employee.lname);
        textViewID.setText("Employee ID: " + employee.employee_id);
        textViewPhone.setText("Phone: " + employee.phone);

        return listViewItem;
    }
}