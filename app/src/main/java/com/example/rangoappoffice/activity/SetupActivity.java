package com.example.rangoappoffice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.databinding.ActivitySetupBinding;
import com.example.rangoappoffice.model.User;
import com.example.rangoappoffice.service.LoadService;
import com.example.rangoappoffice.ui.login.LoginActivity;

import java.util.Objects;

public class SetupActivity extends BaseActivity {
    public static final int CONSTANT_TO_SAVE = 0123;
  private   ActivitySetupBinding binding ;
  private   Uri selectImage ;
   private User user ;
   private  int constant ;
    private  ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
        getSupportActionBar().hide();
        user = (User) getIntent().getSerializableExtra("user");
        constant = getIntent().getIntExtra("constant",0) ;

  if(user==null){ getMyInfo();
  }else {
      fullNumber();
  }


        binding.profilImg.setOnClickListener(v -> mGetContent.launch("image/*"));

        binding.login.setOnClickListener(v -> {
            if (selectImage != null){
                checkIfInputVoid(); }else{
                Toast.makeText(this ,"ajouter une image de profil",Toast.LENGTH_LONG).show();
            }
        });
    }

    private  void getMyInfo(){
        UserHelper.getUserDoc(MainActivity.currentUser).get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.get("phone").toString().isEmpty()) {
                documentSnapshot.get("phone").toString();
                if (documentSnapshot.get("phone").toString().equals(" ")) {
                    binding.numUser.setText(documentSnapshot.get("phone").toString());
                }
            }
            if (!documentSnapshot.get("town").toString().isEmpty()) {
                documentSnapshot.get("town").toString();
                if (documentSnapshot.get("town").toString().equals(" ")) {
                    binding.townUser.setText(documentSnapshot.get("town").toString());
                }
            }
            if (!documentSnapshot.get("name").toString().isEmpty()) {
                Objects.requireNonNull(documentSnapshot.get("name")).toString();
                if (documentSnapshot.get("name").toString().equals(" ")) {
                    binding.nom.setText(documentSnapshot.get("name").toString());
                }
            }
       }) ;
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                   selectImage = uri ;
                   binding.profilImg.setImageURI(uri);
                }
            });


    private void uploadInStorage(){
       String nom =getName()+" "+getPrenom() ;
        user.setName(nom);
        user.setTown(getTown());

    Intent intent = new Intent(this , LoadService.class) ;
    intent.putExtra("user" ,user) ;
    intent.putExtra("selectImg" ,selectImage.toString()) ;
    startService(intent) ;

        if (constant== LoginActivity.CONSATNT_NEW){
            startActivity(new Intent(getApplicationContext() ,MainActivity.class));
            finish();
        }else{
            startActivity(new Intent(getApplicationContext() , HomeActivity.class));
        }
    }



    private  String getName(){
        return   binding.nom.getText().toString(); }
  private  String getPrenom(){
        return   binding.prenom.getText().toString(); }
   private String getTown(){
       return binding.townUser.getText().toString() ; }
     private  String getNumer(){
        return binding.numUser.getText().toString() ; }
     private  void fullNumber(){
        binding.numUser.setText(user.getPhone());
     }

    private void checkIfInputVoid(){
        if (binding.prenom.getText().toString().isEmpty()){
            binding.prenom.setError("Entrez votre prenom");
        }
        if (binding.numUser.getText().toString().isEmpty()){
            binding.numUser.setError("");
        }
        if (binding.nom.getText().toString().isEmpty() ){
            binding.nom.setError("Entrez votre nom");
        }
       if (binding.townUser.getText().toString().isEmpty()){
           binding.townUser.setError("");
       }
        else{
    uploadInStorage();
        }
    }
}