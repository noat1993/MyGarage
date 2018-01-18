package com.example.gabys.mygarage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.UserHolderService;
import com.example.gabys.mygarage.activities.adds.NewEmployeeActivity;
import com.example.gabys.mygarage.activities.adds.NewTreatmentActivity;
import com.example.gabys.mygarage.activities.adds.NewUserActivity;
import com.example.gabys.mygarage.activities.views.CustomersActivity;
import com.example.gabys.mygarage.activities.views.EmployesActivity;
import com.example.gabys.mygarage.activities.views.TreatmentsActivity;
import com.example.gabys.mygarage.models.Employes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInsideActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttoninsideLogout;
    private Button buttonAddCustomer;
    private Button buttonAddTreatment;
    private Button buttonViewCustomers;
    private Button buttonViewTreatments;
    // for admin
    private Button buttonAddEmployee;
    private Button buttonViewEmployees;

    //firebase auth object
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_inside);
        buttoninsideLogout = (Button) findViewById(R.id.logMeOutBtn);
        buttonAddCustomer = (Button) findViewById(R.id.buttonAddCustomer);
        buttonAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsideActivity.this, NewUserActivity.class));
            }
        });
        buttonAddTreatment = (Button) findViewById(R.id.buttonAddTreatment);
        buttonAddTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsideActivity.this, NewTreatmentActivity.class));
            }
        });
        buttonViewCustomers = (Button) findViewById(R.id.buttonCustomersView);
        buttonViewCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsideActivity.this, CustomersActivity.class));
            }
        });
        buttonViewTreatments = (Button) findViewById(R.id.buttonTreatmentsView);
        buttonViewTreatments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsideActivity.this, TreatmentsActivity.class));
            }
        });

        // only for admin
        buttonViewEmployees = (Button) findViewById(R.id.buttonViewEmployees);
        buttonViewEmployees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsideActivity.this, EmployesActivity.class));
            }
        });
        buttonAddEmployee = (Button) findViewById(R.id.buttonAddEmployee);
        buttonAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInsideActivity.this, NewEmployeeActivity.class));
            }
        });
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Employes").child(UserHolderService.uuid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Employes e = dataSnapshot.getValue(Employes.class);
                UserHolderService.employee = e;
                if(e.is_admin.equals("true"))
                {
                    UserHolderService.isAdmin=true;
                    buttonAddEmployee.setVisibility(View.VISIBLE);
                    buttonViewEmployees.setVisibility(View.VISIBLE);
                    Toast.makeText(UserInsideActivity.this,"Welcome admin",Toast.LENGTH_LONG).show();
                }else
                {
                    UserHolderService.isAdmin=false;
                    Toast.makeText(UserInsideActivity.this,"Welcome: "+ UserHolderService.employee.fname,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        //if(firebaseAuth.getCurrentUser() == null){
            //finish();
            //startActivity(new Intent(this, LoginActivity.class));
        //}

        // getting current user
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Object a = FirebaseDatabase.getInstance().getReference();




        //adding listener to button
        buttoninsideLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if(view == buttoninsideLogout){
            //closing activity
            finish();
            //starting logout activity
            startActivity(new Intent(this, LogoutActivity.class));
        }
    }
}
