package com.example.rangoappoffice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.ActivityDynamicLInkBinding;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;

public class DynamicLInkActivity extends AppCompatActivity {
   private ActivityDynamicLInkBinding binding ;
   private ProgressDialog dialog ;
   public  static int CONSTANT_GO =22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityDynamicLInkBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this );
        dialog.setMessage("wait...");
        dialog.setCancelable(true);
        binding.progressBar.setProgress(0);
        DynamicLInk();
    }
    private void DynamicLInk(){
        dialog.show();
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                    }
                    if (deepLink!=null){
                     String idhouse =  deepLink.getQueryParameter("idhouse");
                    loadHouse(idhouse);
                    }

                })
                .addOnFailureListener(this, e -> {
                });}

                private void loadHouse(String idhouse){
                    HouseHelpStore.getHouseRoom(idhouse).document(idhouse).get()
                            .addOnSuccessListener(documentSnapshot -> {
                                HouseModel houseModel =documentSnapshot.toObject(HouseModel.class) ;
                               starActivity(houseModel);
                            }) ; }
                private  void starActivity(HouseModel Model){
             dialog.dismiss();
                    Intent intent = new Intent(this ,HouseDetailActivity.class) ;
                    intent.putExtra("CONSTANT_COMING",CONSTANT_GO) ;
                    intent.putExtra("house" ,Model) ;
                   startActivity(intent);
                }
}