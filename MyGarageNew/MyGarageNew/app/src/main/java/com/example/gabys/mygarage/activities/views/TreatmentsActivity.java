package com.example.gabys.mygarage.activities.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.UserHolderService;
import com.example.gabys.mygarage.activities.adapters.TreatmentsList;
import com.example.gabys.mygarage.activities.adds.NewTreatmentActivity;
import com.example.gabys.mygarage.models.Treatments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TreatmentsActivity extends AppCompatActivity {

//listViewEmployees

    private Button buttonAddTreatment;
    private ListView listVeiwTreatments;

    List<Treatments> treatmentsList;

    DatabaseReference databaseTreatment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatments);

        //getting the reference of artists node
        databaseTreatment = FirebaseDatabase.getInstance().getReference("Treatments");

        //getting views

        listVeiwTreatments = (ListView) findViewById(R.id.listViewTreatments);

        buttonAddTreatment = (Button) findViewById(R.id.buttonAddTreatment);


        //list to store artists
        treatmentsList = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                //addArtist();
                startActivity(new Intent(getApplicationContext(), NewTreatmentActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseTreatment.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                treatmentsList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting employes
                    Treatments treatment = postSnapshot.getValue(Treatments.class);
                    //adding artist to the list
                    if(UserHolderService.isAdmin) {
                        treatmentsList.add(treatment);
                    }
                    else if(treatment.employee_id.equals(UserHolderService.employee.employee_id))
                    {
                        treatmentsList.add(treatment);
                    }

                }

                //creating adapter
                TreatmentsList treatmentsAdapter = new TreatmentsList(TreatmentsActivity.this, treatmentsList);

                //attaching adapter to the listview
                listVeiwTreatments.setAdapter(treatmentsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
