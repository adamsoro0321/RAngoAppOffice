package com.example.rangoappoffice.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.data.DataForDesign;
import com.example.rangoappoffice.data.LocalData;
import com.example.rangoappoffice.databinding.ActivityBusinessBinding;
import com.example.rangoappoffice.model.CurrentUsertStatu;
import com.example.rangoappoffice.model.User;
import com.example.rangoappoffice.model.guideModel;

public class BusinessActivity extends BaseActivity {

    private ProgressDialog dialog ;
    private  ActivityBusinessBinding binding ;
    private CurrentUsertStatu usertStatu ;
    private guideModel userIsguide ;
    private User user ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBusinessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Enregistrement...");

        usertStatu = DataForDesign.getUserStatuFromPreference(this) ;
        user= LocalData.getUserFromPreference(this);


         AlertDialog.Builder alertDialog = new AlertDialog.Builder(this );
         alertDialog.setTitle("RANGO.COM");
         alertDialog.setMessage("Voulez vous arreter d'ètre un GUIDE ???");
         alertDialog.setView(binding.layoutDemar) ;
         binding.isDemarcheur.setChecked(usertStatu.getDemarcheur());
         binding.isGuide.setChecked(usertStatu.getGuide());
         binding.knowMorGuid.setOnClickListener(v ->{binding.readMeContain.setVisibility(View.VISIBLE) ;binding.knowMorGuid.setVisibility(View.GONE);} );
         binding.knowMorDemar.setOnClickListener(v ->{binding.readMeContainDemar.setVisibility(View.VISIBLE);binding.knowMorDemar.setVisibility(View.GONE);} );
         loadguideCaller();
         loadBusinessCaller();
         loadDemarche();
         listenSwitchGuide();
         listenSwitchDemar();
    }


    private  void listenSwitchDemar(){
        binding.isDemarcheur.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                demarchTru();
            }else{
             demarcheFalse();
            }
        });
    }
    private void listenSwitchGuide(){
        binding.isGuide.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                dialog.show();
               saveStatuGuideTrue();
            }else{
             saveStatuGuideFalse();
            }
        });
    }
    private void saveStatuGuideTrue(){
        dialog.show();
        userIsguide = new guideModel(true ,false , getCurrentUser().getUid()) ;
        userIsguide.setImg(user.getImageProfil());
        userIsguide.setName(user.getName());
        UserHelper.offcialGuide().document(getCurrentUser().getUid())
                .set(userIsguide).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        usertStatu.setGuide(true);
                        DataForDesign.setCurrentUserStatu(this ,usertStatu);
                        dialog.dismiss();
                       }else{
                        dialog.dismiss();
                    }
                });
    }


    private  void saveStatuGuideFalse(){
        dialog.show();
        UserHelper.offcialGuide().document(getCurrentUser().getUid()).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                usertStatu.setGuide(false);
               DataForDesign.setCurrentUserStatu(getApplicationContext(),usertStatu);
               dialog.dismiss();
            }else{
               dialog.dismiss();
            }
        }) ;
    }

    private void loadBusinessCaller(){
        AdminHelper.getBusinessCaller().get()
                .addOnSuccessListener(documentSnapshot -> binding.businessManCaller.setText(documentSnapshot.get("message").toString())) ;
    }
    private void loadguideCaller(){
        AdminHelper.guideCaller().get().addOnSuccessListener(documentSnapshot -> {
            binding.guideCaller.setText(documentSnapshot.get("message").toString()) ;
          binding.readMe.setText(documentSnapshot.get("cantrat").toString());
        }) ;
    }
    private  void loadDemarche(){
        AdminHelper.getDemarcheur().get().addOnSuccessListener(documentSnapshot -> {
            binding.demarCaller.setText(documentSnapshot.get("message").toString()) ;
           binding.readMeDemar.setText(documentSnapshot.get("cantrat").toString());
        }) ;
    }

    private void demarchTru(){
          dialog.show();
        UserHelper.getBusinessUserDemarch().document(MainActivity.currentUser)
                .set(user).addOnSuccessListener(aVoid ->{
                    usertStatu.setDemarcheur(true);
                    DataForDesign.setCurrentUserStatu(getApplicationContext(),usertStatu);
                    dialog.dismiss();
        }) ;
    }

    private  void  demarcheFalse(){
        dialog.show();
        UserHelper.getBusinessUserDemarch()
                .document(MainActivity.currentUser).delete().addOnSuccessListener(aVoid -> {
                    usertStatu.setDemarcheur(false);
                    DataForDesign.setCurrentUserStatu(getApplicationContext() ,usertStatu);
                    dialog.dismiss();
                }) ;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true ;
    }
}

