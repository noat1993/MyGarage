package com.example.gabys.mygarage.activities.adds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.models.Customers;
import com.example.gabys.mygarage.models.Treatments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import android.database.Cursor;

public class NewTreatmentActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordRepet;
    private Button buttonAddTreat;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private Customers user;
    //defining firebase objects


    private EditText editTextCarNumber;
    private EditText editTextDate;
    private EditText editTextEmployeeID;
    private EditText editTextIssue;
    private EditText editTextPrice;
    private EditText editTextCustomerID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_treatment);

        //initializing firebase objects
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //initializing views

        editTextCarNumber = (EditText) findViewById(R.id.carNumber);
        editTextDate = (EditText) findViewById(R.id.date);
        editTextEmployeeID = (EditText) findViewById(R.id.EmployeeID);
        editTextIssue = (EditText) findViewById(R.id.issue);
        editTextPrice = (EditText) findViewById(R.id.price);
        editTextCustomerID = (EditText) findViewById(R.id.customerID);

        buttonAddTreat = (Button) findViewById(R.id.createButt);
        progressDialog = new ProgressDialog(this);
        //attaching listener to button
        buttonAddTreat.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if(view == buttonAddTreat){

                Treatments treat = new Treatments(editTextCarNumber.getText().toString(), editTextDate.getText().toString(),
                        editTextEmployeeID.getText().toString(), editTextIssue.getText().toString(), editTextPrice.getText().toString(),editTextCustomerID.getText().toString());


                DatabaseReference usersRef = mDatabase.child("Treatments");

                usersRef.push().setValue(treat);

            Toast.makeText(NewTreatmentActivity.this, "Create New Treatment successfully", Toast.LENGTH_SHORT).show();

            finish();
            finishActivity(RESULT_OK);


            }


        }

/////----////
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode != RESULT_CANCELED && data != null) {

        if (resultCode == RESULT_OK) {

        }
    }
}

}
