package com.aiub.knowlegebookstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.MediaRouteButton;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.jar.Attributes;

public class historyBook extends AppCompatActivity implements View.OnClickListener {

    protected ImageView imageView;
    protected EditText Type,Pname,Price,Quantity,author,description;
    protected Button chooseImg,upload;
    protected Uri imageUri= Uri.parse("android.resource://com.aiub.knowlegebookstore/drawable/logo1");
    protected ProgressBar progressBarUpload;


    DatabaseReference databaseReference;
    StorageReference storageReference;
    StorageTask uploadTask;

    private static  final  int IMAGE_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_book);
        databaseReference = FirebaseDatabase.getInstance().getReference("Books");
        storageReference = FirebaseStorage.getInstance().getReference("Books_image");

        Type = findViewById(R.id.productType);
        Pname = findViewById(R.id.ProductName);
        Price = findViewById(R.id.productPrice);
        Quantity = findViewById(R.id.productQuantity);
        author = findViewById(R.id.author_name);
        description = findViewById(R.id.description);
        upload = (Button)findViewById(R.id.button);
        chooseImg = (Button)findViewById(R.id.chooseImageButton);
        imageView = findViewById(R.id.imageView11);
        progressBarUpload = findViewById(R.id.progressBar2);


        chooseImg.setOnClickListener(this);
        upload.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.chooseImageButton:
                openGallery();

                break;
            case R.id.button:
                uploadData();

                break;

        }


    }

    void openGallery(){
        progressBarUpload.setVisibility(View.GONE);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && data!=null){

            imageUri = data.getData();
            Picasso.with(this).load(imageUri).fit().centerCrop().into(imageView);

        }

    }

    //getting image extension

    public String getFileExtension(Uri imageUri1){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri1));

    }

    private void uploadData()
    {
        final String imageType = Type.getText().toString().trim();
        final String imageName = Pname.getText().toString().trim();
        final String productPrice = Price.getText().toString().trim();
        final String productQuantity = Quantity.getText().toString().trim();
        final String authorName = author.getText().toString().trim();
        final String productDescription = description.getText().toString().trim();




        if(imageType.isEmpty()){

            Type.setError("Please Set Product Type");
            Type.requestFocus();

        }
        else if(imageName.isEmpty()){

            Pname.setError("Please Set Product Name");
            Pname.requestFocus();

        }
        else if(authorName.isEmpty())
        {
            author.setError("Please Enter Author Name");
            author.requestFocus();
        }
        else if(productPrice.isEmpty()){
            Price.setError("Please Set Product Price");
            Price.requestFocus();

        }
        else if(productQuantity.isEmpty()) {

            Quantity.setError("Please Set Product Quantity");
            Quantity.requestFocus();

        }
        else if(productDescription.isEmpty()){

            description.setError("Enter a description");
            description.requestFocus();


        }

        else {

            final double valuePrice = Double.parseDouble(productPrice);
            final int valueQuantity =Integer.parseInt(productQuantity);
            progressBarUpload.setIndeterminate(false);

            StorageReference ref = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            progressBarUpload.setVisibility(View.VISIBLE);

            ref.putFile(imageUri)


                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressBarUpload.setProgress((int)progress);

                        }
                    })


                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            Toast.makeText(getApplicationContext(), "Product Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            Task<Uri> uriTask= taskSnapshot.getStorage().getDownloadUrl();

                            while(!uriTask.isSuccessful());

                            Uri downloadUri = uriTask.getResult();
                            upload load = new upload(imageType,imageName,authorName,valuePrice, valueQuantity,productDescription,downloadUri.toString());

                            String productId = databaseReference.push().getKey();
                            databaseReference.child(imageType).child(productId).setValue(load);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressBarUpload.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });




        }

    }
}