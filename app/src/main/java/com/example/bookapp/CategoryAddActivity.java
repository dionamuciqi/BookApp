package com.example.bookapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CategoryAddActivity extends AppCompatActivity {

    //view binding
    private ActivityCategoryAddBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //configure progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //handle click, go back
        binding.backBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                onBackPressed();
            }
        });

        //handle click, begin upload category
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            public void OnClick(View v) {
                validateData();
            }
        });


    }
}
private String category ="";

private void validateData(){
    /*Before adding validate data*/

    //get data
    category = binding.categoryEt.getText().toString().trim();

    if(TextUtils.isEmpty(category)) {
        Toast.makeText(this, "Please enter category...!", Toast.LENGTH_SHORT).show();

    }
    else{
        addCategoryFirebase();
    }
}

private void addCategoryFirebase(){

    progressDialog.setMessage("Adding category...");
    progressDialog.show();

    //get timestamp
    long timestamp = System.currentTimeMillis();

    //setup info to add in firebase db
    HashMap<String, Object> hasMap = new HashMap<>();
    hashMap.put("id", ""+timestamp);
    hashMap.put("category", ""+category);
    hashMap.put("timestamp" , ""+timestamp);
    hashMap.put("uid" , ""+firebaseAuth.getUid());

    //add to firebase db... Database Root > Categories >categoryId> category info
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Categories");
    ref.child(""+timestamp)
            .setValue(hashMap)
            .addOnSuccessListener(new OnSuccessListener<Void>{
                public void onSuccess(Void unused){
                    //category add success
                   progressDialog.dismiss();
                   Toast.makeText(CategoryAddActivity.this , "Category added successfully!", Toast.LENGTH_SHORT).show();
        }
    })
    .add onFailureListener(new OnFailureListener(){
        public void onFailure( Exception e) {
            //category add failed
            progressDialog.dismiss();
            Toast.makeText(CategoryAddActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    })
}




