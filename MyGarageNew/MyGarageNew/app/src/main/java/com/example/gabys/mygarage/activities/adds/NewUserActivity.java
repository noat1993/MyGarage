package com.example.gabys.mygarage.activities.adds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gabys.mygarage.BuildConfig;
import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.activities.LoginActivity;
import com.example.gabys.mygarage.models.Customers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

//import android.database.Cursor;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordRepet;
    private Button buttonSignup;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    private Customers user;
    //defining firebase objects
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageRef;


    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextCity;
    private EditText editTextID;
    private EditText editTextPhone;
    private String mCurrentPhotoPath;

    private Uri filePath;
    private String UID;
    private static final int PHOTO_CAMERA = 1;
    private static final int PHOTO_GALLERY = 2;
    private Uri uriSavedImage = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //initializing firebase objects
        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageRef = firebaseStorage.getInstance().getReference();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null) {
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        //initializing views

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.newPassword);
        editTextPasswordRepet = (EditText) findViewById(R.id.repetPassword);
        editTextFirstName = (EditText) findViewById(R.id.firstName);
        editTextLastName = (EditText) findViewById(R.id.lastName);
        editTextCity = (EditText) findViewById(R.id.city);
        editTextID = (EditText) findViewById(R.id.customerID);
        editTextPhone = (EditText) findViewById(R.id.aptNum);
        buttonSignup = (Button) findViewById(R.id.createButt);
        progressDialog = new ProgressDialog(this);
        //attaching listener to button
        buttonSignup.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignup){
            if(!editTextPassword.getText().toString().equals(editTextPasswordRepet.getText().toString())){
                //display some message here
                Toast.makeText(NewUserActivity.this, "your password isn't match", Toast.LENGTH_LONG).show();
            }
            else{

                Customers user = new Customers(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                        editTextCity.getText().toString(), editTextID.getText().toString(), editTextPhone.getText().toString(),editTextEmail.getText().toString());
                registerUser();

                //Log.d("firebaseAuth.getUid-str", firebaseAuth.getUid());
                //Log.d("firebaseAuth.getUid()", firebaseAuth.getUid());
                //Log.d("firebaseAuth.getUid-cur", firebaseAuth.getCurrentUser().toString());
                //UID = firebaseAuth.getCurrentUser().getUid().toString();
                DatabaseReference usersRef = mDatabase.child("Customers");

                usersRef.child(user.id_number).setValue(user);



                if(filePath != null) {
                    StorageReference imagesRef = storageRef.child("images/" + UUID.randomUUID().toString()).child(filePath.getLastPathSegment());
                    imagesRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(NewUserActivity.this, "Picture upload success", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }


        }

    }

    private void registerUser(){

        //getting email and password from edit texts
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){


                            finish();
                            //startActivity(new Intent(getApplicationContext(), CustomersActivity.class));
                            finishActivity(RESULT_OK);
                        }else{
                            //display FireBase Error message here
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            Toast.makeText(NewUserActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.w("Failed Registration:",e.getMessage());


                            //Toast.makeText(NewUserActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });



    }

    public void uploadProfilePict(View view) {
        //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
        registerForContextMenu(view);
        openContextMenu(view);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select picture");
        menu.add(menu.NONE, PHOTO_GALLERY, menu.NONE, "Gallery");
        menu.add(menu.NONE, PHOTO_CAMERA, menu.NONE, "Camera");
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case PHOTO_GALLERY:
                Intent pickPictureIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPictureIntent, PHOTO_GALLERY);

                //onBackPressed();
                break;
            case PHOTO_CAMERA:
                Log.d("start camera activity", "fff");

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                //File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
                //imagesFolder.mkdirs();

                //File image = new File(imagesFolder, "QR_" + timeStamp + ".png");



                //Uri uriSavedImage = Uri.fromFile(image);


                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    try {
                        uriSavedImage = FileProvider.getUriForFile(NewUserActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider",
                                createImageFile());
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("uri data",uriSavedImage.toString());

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                    startActivityForResult(takePictureIntent, PHOTO_CAMERA);

                }

                break;
        }
        return super.onContextItemSelected(item);
    }
///start from here////
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
/////----////
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    ImageButton imgButt;
    imgButt = (ImageButton) findViewById(R.id.profileImgButt);
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != RESULT_CANCELED && data != null)
    {
        Uri selectedImage = data.getData();

        Bitmap bitmap = null;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_GALLERY:
                    if (resultCode == RESULT_OK) {
                        if (resultCode == RESULT_OK) {
                            try {
                                //
                                String[] filePathColumn = {MediaStore.Images.Media.DATA,
                                        MediaStore.Images.Media.DISPLAY_NAME};
                                Cursor cursor =
                                        getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                                if (cursor.moveToFirst()) {
                                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                    String filePath = cursor.getString(columnIndex);

                                    int fileNameIndex = cursor.getColumnIndex(filePathColumn[1]);
                                    String fileName = cursor.getString(fileNameIndex);
                                    // Here we get the extension you want
                                    String extension = fileName.replaceAll("^.*\\.", "");
                                    if (extension.equalsIgnoreCase("jpg")|| extension.equalsIgnoreCase("png"))
                                    {
                                        Toast.makeText(this, "Your Profile Image is selected with JPG or PNG Image",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                                cursor.close();
                                //

                                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case PHOTO_CAMERA:
                    Log.d("1",uriSavedImage.toString());
                    Bundle extras = data.getExtras();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriSavedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //bitmap = (Bitmap) extras.get(loadBitmap(uriSavedImage.toString()));
                        //bitmap = (Bitmap) data.getExtras().get("data");
                        //bitmap = (Bitmap) loadBitmap(uriSavedImage.toString());



                    Log.d("3",uriSavedImage.toString());
                    //filePath = uriSavedImage;
                    break;
            }
        }
        if(selectedImage != null){
            filePath = selectedImage;
        }
        else filePath = uriSavedImage;
            //file = new File(filePath.getPath());
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
        imgButt.setBackgroundDrawable(bitmapDrawable);
        //Detects request codes
        /*if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            ImageButton imgButt = (ImageButton) findViewById(R.id.profileImgButt);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                imgButt.setBackgroundDrawable(bitmapDrawable);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }*/
    }
    }





}
