package com.example.gabys.mygarage.activities.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.gabys.mygarage.activities.adapters.CustomersList;
import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.activities.adds.NewUserActivity;
import com.example.gabys.mygarage.models.Customers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CustomersActivity extends AppCompatActivity {

//listViewEmployees

    private Button buttonAddCustomer;
    private ListView listVeiwCustomers;

    List<Customers> customersList;

    DatabaseReference databaseCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        //getting the reference of artists node
        databaseCustomers = FirebaseDatabase.getInstance().getReference("Customers");

        //getting views

        listVeiwCustomers = (ListView) findViewById(R.id.listViewCustomers);

        buttonAddCustomer = (Button) findViewById(R.id.buttonAddCustomer);

        //list to store artists
        customersList = new ArrayList<>();


        //adding an onclicklistener to button
        buttonAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewUserActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseCustomers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                customersList.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting employes
                    Customers customer = postSnapshot.getValue(Customers.class);
                    //adding artist to the list
                    customersList.add(customer);
                }

                //creating adapter
                CustomersList customerAdapter = new CustomersList(CustomersActivity.this, customersList);

                //attaching adapter to the listview
                listVeiwCustomers.setAdapter(customerAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });
    }




}
