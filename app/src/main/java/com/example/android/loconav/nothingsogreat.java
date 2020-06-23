package com.example.android.loconav;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import static android.content.ContentValues.TAG;

public class nothingsogreat extends AppCompatActivity {



    String imageName;
    String text1;
    String text2;
    String text4;
    String text5;
    String text6;
    String Id;
    String spinner;
    static int PICK_IMAGE_REQUEST = 1;

    Button mChooseButton;
    Button mUploadButton;
    Button msubmitButton;
    EditText mEditTextFileName;
    EditText mtext1;
    EditText mtext2;
    Spinner spinnerCity;
    EditText mtext5;
    EditText mtext6;
    private ImageView mimageview;
    private ProgressBar mprogressbar;
    private String name;
    private Uri mimageuri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    public FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nothingsogreat);


        mChooseButton = findViewById(R.id.chooseImage);
        mUploadButton = findViewById(R.id.uploadButton);
        mEditTextFileName = findViewById(R.id.picturename);
        mtext1= findViewById(R.id.editName1);
        mtext2= findViewById(R.id.editName2);
        spinnerCity=findViewById(R.id.spinnercity);
        mtext5= findViewById(R.id.editName5);
        mtext6= findViewById(R.id.editName6);

        msubmitButton=findViewById(R.id.submitbutton);
        imageName=mEditTextFileName.getText().toString().trim();
        text1=mtext1.getText().toString().trim();
        text2=mtext2.getText().toString().trim();
        spinner=spinnerCity.getSelectedItem().toString();
        text5=mtext5.getText().toString().trim();
        text6=mtext6.getText().toString().trim();

        mprogressbar = findViewById(R.id.progressbar);
        mimageview = findViewById(R.id.image_view);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("UserData");
        mStorageRef = FirebaseStorage.getInstance().getReference("UserData");

        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();



        mChooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });


        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadfile();

            }
        });


        msubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddata();

            }
        });






    }



    private void uploaddata(){

        userinformation user= new userinformation(imageName,text1,text2,spinner,text5,text6);
        mDatabaseRef.setValue(user);



    }


    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            mimageuri = data.getData();
            mimageview.setImageURI(mimageuri);
        }
    }

    private String getFileExtention(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadfile(){

        if(mimageuri != null){

            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtention(mimageuri));

            fileReference.putFile(mimageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mprogressbar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(nothingsogreat.this, "Upload Successful", Toast.LENGTH_LONG).show();

                            userinformation userinformation = new userinformation(imageName,text1,text2,spinner,text5,text6);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadurl = uri;
                                }
                            });


                            //String uploadId = mDatabaseRef.push().getKey();

//                            String uid =mAuth.getCurrentUser().getUid();
//                            DatabaseReference reference = mDatabase.getReference("Users").child(uid);
//                            reference.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    userinformation user = dataSnapshot.getValue(userinformation.class);
//                                    mEditTextFileName.setText(user.mImagename);
//                                    mtext1.setText(user.mText1);
//                                    mtext2.setText(user.mText2);
//                                    mtext4.setText(user.mText4);
//                                    mtext5.setText(user.mText5);
//                                    mtext6.setText(user.mText6);
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//                                    Log.e(TAG,"Error",databaseError.toException());
//                                }
//                            });




   //                         String uid = mAuth.getCurrentUser().getUid();
     //                       mDatabase.getReference("Users").child(uid).setValue(userinformation).addOnCompleteListener(new OnCompleteListener<Void>() {
   //                             @Override
   //                            public void onComplete(@NonNull Task<Void> task) {
    //                                if(task.isSuccessful()){
     //                                   Toast.makeText(nothingsogreat.this,"Values Updated",Toast.LENGTH_LONG).show();
     //                               }
     //                               else{
      //                                  Toast.makeText(nothingsogreat.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
      //                              }
      //                          }
       //                     });






                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(nothingsogreat.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            mprogressbar.setProgress((int) progress);

                        }
                    });



        } else{
            Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
        }

    }



}