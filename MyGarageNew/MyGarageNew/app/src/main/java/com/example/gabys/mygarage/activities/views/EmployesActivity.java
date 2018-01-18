package com.example.gabys.mygarage.activities.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.gabys.mygarage.activities.adapters.EmployesList;
import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.activities.adds.NewEmployeeActivity;
import com.example.gabys.mygarage.models.Employes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployesActivity extends AppCompatActivity {

//listViewEmployees

    private Button buttonAddEmployee;
    private ListView listVeiwEmployes;

    List<Employes> employesList;

    DatabaseReference databaseEmployes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employes);

        //getting the reference of artists node
        databaseEmployes = FirebaseDatabase.getInstance().getReference("Employes");

        //getting views

        listVeiwEmployes = (ListView) findViewById(R.id.listViewEmployees);

        buttonAddEmployee = (Button) findViewById(R.id.buttonAddEmployee);

        //list to store artists
        employesList = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view == buttonAddEmployee) {

                    startActivity(new Intent(getApplicationContext(), NewEmployeeActivity.class));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseEmployes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                employesList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting employes
                    Employes employee = postSnapshot.getValue(Employes.class);
                    //adding artist to the list
                    employesList.add(employee);
                }

                //creating adapter
                EmployesList employesAdapter = new EmployesList(EmployesActivity.this, employesList);
                //attaching adapter to the listview
                listVeiwEmployes.setAdapter(employesAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




}
