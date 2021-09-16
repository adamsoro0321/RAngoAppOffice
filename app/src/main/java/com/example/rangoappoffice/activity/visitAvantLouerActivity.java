
package com.example.rangoappoffice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.api.PublicRegisterHelper;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.databinding.ActivityVisitAvantLouerBinding;
import com.example.rangoappoffice.model.LocataireInfo;
import com.example.rangoappoffice.model.OffreGuiddeModel;
import com.example.rangoappoffice.model.operationVisit;
import com.example.rangoappoffice.operation.visitOperation;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import adpter.ListeOffreAdapter;

public class visitAvantLouerActivity extends BaseActivity {
  public static  int CONSTANT_SHOW_GUIDE =22 ;
 private ActivityVisitAvantLouerBinding binding ;
    private LocataireInfo locataire ;
    private String ownerHouse,houseId ;
    private Query query ;
private Button accepted ;
private String operationId ;


 private ListeOffreAdapter adapter ;
  private  ListenerRegistration listenerRegistration ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityVisitAvantLouerBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        accepted =binding.acceptedCondition ;

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       loadCondition() ;

        houseId = getIntent().getStringExtra("IdHouse") ;
        ownerHouse = getIntent().getStringExtra("ownerHouse");
        locataire =new LocataireInfo(MainActivity.currentUser,houseId) ;
         query =UserHelper.waitingGuideProposition(MainActivity.currentUser,houseId).orderBy("date" , Query.Direction.DESCENDING) ;
          accepted.setOnClickListener(v -> {
               //recherche de guide disponible
          initOpertaion();
          AlertOwner();
          setUpDesign(); });
          }

   private void initOpertaion(){
   operationVisit operationVisit =new operationVisit(MainActivity.currentUser) ;
               visitOperation.initOperation().add(operationVisit).addOnSuccessListener(documentReference -> {
                    operationId = documentReference.getId() ;
                   locataire.setOperationId(operationId);
                   locataire.setOperationId(operationId);
                   if (query!=null){
                       showGuide(query,locataire);
                   }
               });
   }


    private void loadCondition(){
        TextView condition =binding.conditionTxt ;
        AdminHelper.getVisiteCondition().get().addOnSuccessListener(documentSnapshot ->
                condition.setText(documentSnapshot.get("conditions").toString())) ;
    }
   private void setUpDesign(){
        binding.containerIntCon.setVisibility(View.GONE);
        binding.shwoGuideConatain.setVisibility(View.VISIBLE);
    }


  private void showGuide(Query query,LocataireInfo locataireInfo){
       FirestoreRecyclerOptions<OffreGuiddeModel> options = new FirestoreRecyclerOptions.Builder<OffreGuiddeModel>()
                .setQuery(query, OffreGuiddeModel.class)
                .setLifecycleOwner(this)
                .build();
        adapter =new ListeOffreAdapter(options ,locataireInfo,this ) ;
        binding.shwoGuide.setLayoutManager(new LinearLayoutManager(this));
        binding.shwoGuide.setAdapter(adapter);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed() ;
        return true ;}



    private  void AlertOwner(){
        UserHelper.IamBusinessGuide(ownerHouse).document(MainActivity.currentUser)
                .set(locataire).addOnSuccessListener(aVoid -> lookForGuide()) ;
    }
    private void lookForGuide(){
        PublicRegisterHelper.getPublicRegister().add(locataire)
                .addOnSuccessListener(reference -> {

                }) ;
    }
}
