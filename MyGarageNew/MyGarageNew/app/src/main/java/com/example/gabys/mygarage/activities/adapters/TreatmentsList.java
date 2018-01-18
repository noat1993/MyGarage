package com.example.gabys.mygarage.activities.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.models.Treatments;

import java.util.List;

public class TreatmentsList extends ArrayAdapter<Treatments> {
    private Activity context;
    List<Treatments> treatments;

    public TreatmentsList(Activity context, List<Treatments> treat) {
        super(context, R.layout.activity_treatments, treat);
        this.context = context;
        this.treatments = treat;
    }
    public String car_number;
    public String date;
    public String employee_id;
    public String issue;
    public String price;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_treatments_list, null, true);

        TextView textViewCarNumber = (TextView) listViewItem.findViewById(R.id.textViewCar);
        TextView textViewDate = (TextView) listViewItem.findViewById(R.id.textViewDate);
        TextView textViewIssue = (TextView) listViewItem.findViewById(R.id.textViewDetails);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);

        Treatments treatment = treatments.get(position);

        textViewCarNumber.setText("Car Number: " + treatment.car_number);
        textViewDate.setText("Date: " + treatment.date);
        textViewIssue.setText("Details/Issues: " + treatment.issue);
        textViewPrice.setText("Price: " + treatment.price);

        return listViewItem;
    }
}