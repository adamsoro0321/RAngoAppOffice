package com.example.rangoappoffice.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.databinding.ActivityAddHouseCompasantBinding;
import com.example.rangoappoffice.api.storageHelper ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.rangoappoffice.R ;
import com.example.rangoappoffice.model.PayData;
import com.example.rangoappoffice.model.RuleDoc;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

public class AddHouseCompasant extends BaseActivity {

    ActivityAddHouseCompasantBinding binding ;

      private ImageSwitcher imageSwitcher ;
      private ArrayList<Uri> imaUri ;
      Button btnUpload;

    private   ProgressDialog dialog;
      private static final int PICK_IMG_CODE = 0 ;
      int position =0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddHouseCompasantBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
       btnUpload = binding.btnUpload ;
        imageSwitcher =binding.imageSwitcher ;
        imaUri = new ArrayList<>() ;
        String idHouse =getIntent().getStringExtra("idHouse");
        int CONSTANT = getIntent().getIntExtra("CONSTANT_FROM",0) ;


        dialog = new ProgressDialog(this);
        dialog.setMessage("chargement ...");
        dialog.setCancelable(false);

       Objects.requireNonNull(getSupportActionBar()).hide();

      if(CONSTANT==HouseAdminActivity.CHANGE_RUL){
          binding.containerImg.setVisibility(View.GONE);
          binding.texContain.setVisibility(View.VISIBLE);
      }

        imageSwitcher.setFactory(() -> {

            return new ImageView(getApplicationContext());
        });
        loadStringToFull() ;
   imageSwitcher.setOnClickListener(v -> PickImgIntent());

    btnUpload.setOnClickListener(v -> { uploadHouse(idHouse); });
   binding.btnSendCdt.setOnClickListener(v -> {
       if (binding.somEntiere.getText().toString().isEmpty()){
           binding.somEntiere.setError("");
       }
       if (binding.payNum.getText().toString().isEmpty()){
           binding.payNum.setError("");
       }
           else{
           rul(idHouse);
           pay(idHouse);
       }
   });
    }

   private void uploadHouse(String idHouse){
       if (!imaUri.isEmpty()){
           CollectionReference ref=  HouseHelpStore.getImageRoom(idHouse) ;
           for (int i=0 ;i<imaUri.size() ;i++){
               UploadImage(idHouse ,i ,imaUri.get(i),ref); }
       }else{imgNoSelected(); }
   }
   private void imgNoSelected(){
        Toast.makeText(this ," selectionner au moins deux images" ,Toast.LENGTH_LONG).show();
   }
    private  void UploadImage(String idHouse , int i,Uri uri,CollectionReference ref) {
        dialog.show();
        StorageReference Ref =storageHelper.getImageRoom(idHouse ,i) ;
               Ref.putFile(uri).addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       Ref.getDownloadUrl().addOnSuccessListener(uri1 -> saveImg(uri1 ,i,ref)) ;
                   }
               }) ;
        }
        private  void  saveImg( Uri uri , int i, CollectionReference Ref){
            Map<String, Object> user = new HashMap<>();
            user.put("uri", uri.toString());
                 Ref.add(user).addOnSuccessListener(documentReference -> endUpload(i)) ;
        }
        private void endUpload(int i){
            if (i  ==imaUri.size()-1){
                dialog.dismiss();
                Toast.makeText(getApplicationContext() ,"images télécharger avec succès " ,Toast.LENGTH_LONG).show();
                binding.containerImg.setVisibility(View.GONE);
                binding.texContain.setVisibility(View.VISIBLE);
            }
        }
     private void PickImgIntent(){
        Intent intent = new Intent() ;
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE ,true) ;
        intent.setAction(Intent.ACTION_GET_CONTENT) ;
        startActivityForResult(Intent.createChooser(intent ,"selection Images"),PICK_IMG_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== PICK_IMG_CODE && resultCode == Activity.RESULT_OK){
            assert data != null;
            if (data.getClipData() != null){
               int count  = data.getClipData().getItemCount() ;
               for (int i=0 ;i<count ; i++){
                    Uri uri =data.getClipData().getItemAt(i).getUri();
                    imaUri.add(uri) ;
               }
              imageSwitcher.setImageURI(imaUri.get(0));
              position=0 ;
              }else if (data.getData() != null){
               Uri imageUri = data.getData() ;
               position= 0 ;
             }else{
            }
        }
    }


    private  void previous(){
        if (position > 0){
            position-- ;
            imageSwitcher.setImageURI(imaUri.get(position));
        }
        else{
            Toast.makeText(getApplicationContext() ,"non previous image" ,Toast.LENGTH_LONG).show();
        }
    }
    private  void Next(){
        if (position < imaUri.size()-1){
            position++ ;
            imageSwitcher.setImageURI(imaUri.get(position));
        }else{
            Toast.makeText(getApplicationContext() ,"no next image" ,Toast.LENGTH_LONG).show();
        }
    }
    public void swichImage(View view) {
        switch (view.getId()){
            case R.id.previous :
                previous();
                break;
            case R.id.next :
                Next();
                break;
        }
    }


    private void pay(String IdHouse){
        String som = binding.somEntiere.getText().toString() ;
        String num= binding.payNum.getText().toString() ;
        PayData payData = new PayData(som ,num) ;
         HouseHelpStore.getHousePay(IdHouse).document(IdHouse).set(payData);
    }
    private void rul(String IdHouse){
        String caution = binding.caution.getText().toString() ;
        String  rul = binding.condAutr.getText().toString() ;
        Boolean chat = binding.rpdDirect.isChecked() ;
        RuleDoc ruleDoc = new RuleDoc(rul ,caution ,chat,MainActivity.currentUser) ;
        HouseHelpStore.getHouseRules(IdHouse).document(IdHouse)
                .set(ruleDoc).addOnSuccessListener(aVoid -> {
                    startActivity(new Intent(getApplicationContext() ,HouseAdminActivity.class));
                     finish();
           })  ;
    }
    private void loadStringToFull(){
        AdminHelper.getPayAndRulString().get()
                .addOnSuccessListener(doc -> {
                    if (doc!=null){
                        binding.somEntierTitle.setText(doc.get("sommeentiere").toString());
                        binding.cautionTitle.setText(doc.get("cautiontitle").toString());
                        binding.chatLocataireTitle.setText(doc.get("chatlocataire").toString());
                        binding.numberTitle.setText(doc.get("numbertitle").toString());
                        binding.autreCondition.setText(doc.get("autrecondition").toString());
                    }
                }) ;
    }
    @Override
    public void onBackPressed() {
       GiveLocationActivity.alertDialog(this);
    }
}