package com.example.rangoappoffice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.data.DataForDesign;
import com.example.rangoappoffice.databinding.ActivityDashboardBinding;
import com.example.rangoappoffice.model.CurrentUsertStatu;
import com.example.rangoappoffice.service.GuideService;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class dashboardActivity extends BaseActivity{
    private Switch isGuide ;
    private ProgressDialog dialog ,dialog1;
    public static int CONSTANT_GUIDE= 5 ;
    public static int CONSTANT_LOCATAIRE=6 ;
    public static int CONSTANT_CONDITION=7 ;

    ActivityDashboardBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        isGuide =binding.isGuideSwi ;

        dialog = new ProgressDialog(this) ;
        dialog.setCancelable(false);
        dialog.setMessage("wait...");

        dialog1= new ProgressDialog(this);
        dialog1.setCancelable(false);
        dialog1.setMessage("wait...");
        getSupportActionBar().show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CurrentUsertStatu usertStatu = DataForDesign.getUserStatuFromPreference(this) ;


      Boolean guideVail =  usertStatu.getGuideVailable() ;
      Boolean declarGuide = usertStatu.getGuide();
      Boolean isdemarcheur =usertStatu.getDemarcheur();
      Boolean isProprietaire =usertStatu.getPropritaire();
      if (declarGuide){
          updateDesignGuide();
      }

      if (isProprietaire || isdemarcheur){
          updateDesignPropritaire() ;
      }
      isGuide.setChecked(guideVail);
      isGuide.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){ saveGuideSta();}
            else{ disaveGuide() ; }
      });

      binding.businGroup.setOnClickListener(v -> startActivity(new Intent(dashboardActivity.this ,BusinessActivity.class)));
      binding.gHGroup.setOnClickListener(v -> startActivity(new Intent(dashboardActivity.this ,GiveLocationActivity.class)));
      binding.cardMaisonLouer.setOnClickListener(v -> startActivity(new Intent(dashboardActivity.this ,HouseAdminActivity.class)));
      binding.cardGuider.setOnClickListener(v -> configIntent(CONSTANT_GUIDE));
      binding.cardLocataire.setOnClickListener(v -> {Toast.makeText(getApplicationContext() ,"oooo" ,Toast.LENGTH_LONG).show(); });
      binding.readCondition.setOnClickListener(v -> configIntent(CONSTANT_CONDITION));

    }


    private  void configIntent(int constant){
        Intent i  = new Intent(getApplicationContext() ,showInfoRecieverActivity.class) ;
        i.putExtra("CONSTANT_NAME" ,constant) ;
        startActivity(i);
    }

    private void saveGuideSta(){
            dialog.show();
            UserHelper.offcialGuide().document(getCurrentUser().getUid()).update("isVailable",true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            DataForDesign.setGuideVailable(this ,true);
                            startServiceGuide();
                        }else{
                            dialog.dismiss();
                        }
                    });
        }

      private void startServiceGuide(){
          Intent intent =new Intent(getApplicationContext() , GuideService.class) ;
          if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
              startForegroundService(intent);
           }else{
              startService(intent );
          }dialog.dismiss();
      }
      private void stopService(){
        stopService(new Intent(getApplicationContext() , GuideService.class)) ;
          dialog1.dismiss();
      }
       private void disaveGuide(){
           dialog1.show();
           UserHelper.getUserCollection().document("businessUser")
                   .collection("guide")
                   .document(getCurrentUser().getUid()).update("isVailable",false)
                   .addOnCompleteListener(task -> {
                       if (task.isSuccessful()){
                           DataForDesign.setGuideVailable(this ,false);
                       }
              stopService();
           }) ;
       }

       private void updateDesignGuide(){
           binding.guideBox.setVisibility(View.VISIBLE);
       }

       private void updateDesignPropritaire(){
           Toast.makeText(this ,"i am proprietaire" ,Toast.LENGTH_LONG).show();

       }

       @Override
       public void onBackPressed() {
          startActivity(new Intent(this ,MainActivity.class));
          finish();
    }
}