package com.example.rangoappoffice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.ArchiveHelsptore;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.ActivityHouseAdminBinding;
import com.example.rangoappoffice.model.HouseLocataire;
import com.example.rangoappoffice.model.OwnerDialog;
import com.example.rangoappoffice.model.PayData;
import com.example.rangoappoffice.model.ownerDialogRetrieve;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import adpter.HouseLocataireAdapter;
import adpter.adminHouseAdapter;

public class HouseAdminActivity extends AppCompatActivity implements OwnerDialog.getItemListener, ownerDialogRetrieve.getItemListenerToSet {
    public static final int CHANGE_RUL = 29;
    private ActivityHouseAdminBinding binding;
    private ArrayList<HouseModel> list ;
    private adminHouseAdapter adapter ;
    private HouseLocataireAdapter adapter2 ;
    public  static int CONSTANT_ID_ADAPTER =10 ;
    private ProgressDialog dialog ;
    private  Query query ;
    private String IdHouse ;
    public static  final int SHOW_ALL_HOUSE=011111 ;
    public  static  final int SHOW_TO_SET=022222 ;
  public static final int ADD_GUIDE_CONSTANT =00003 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHouseAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog=new ProgressDialog(this) ;
        dialog.setCancelable(true);
        dialog.setMessage("wait...");


      query=  HouseHelpStore.getAllHouse().whereEqualTo("ownUID",MainActivity.currentUser);
      update(query,SHOW_ALL_HOUSE);
      binding.sendDSetting.setOnClickListener(v -> sendSettingDemarcheur());
  }

  private void sendSettingDemarcheur(){
      if ( binding.addCaution.getText().toString().isEmpty()){
          binding.addCaution.setError("");
      }
      else{
          String setting =binding.addCaution.getText().toString() ;
          String atherSetting = binding.addOtherCondi.getText().toString() ;
          PayData data =new PayData(setting,atherSetting) ;
          HouseHelpStore.getDemarchSetting(IdHouse).document("conditionDemarcheur").set(data) ;
      }
  }
  private  void update(Query query,int i){
   FirestoreRecyclerOptions<HouseModel> options = new FirestoreRecyclerOptions.Builder<HouseModel>()
              .setQuery(query, HouseModel.class)
              .setLifecycleOwner(this)
              .build();
     adapter =new adminHouseAdapter(options,i,this) ;
     GridLayoutManager layoutManager =new GridLayoutManager(getApplicationContext(),2,RecyclerView.HORIZONTAL,false) ;
     binding.rcvListH.setAdapter(adapter);
     binding.rcvListH.setLayoutManager(layoutManager);
  }

  private void updateLocataire(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_proprietaire ,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_house:
                gotoAddHouse();
                break;
            case R.id.retire_house:
                maisonRetire();
                break;
            case R.id.louer_house:
                showHouseLouer();
                break;
            case R.id.locataire :
                listLocataire();
                break;
            case R.id.add_guide :
                addGuide() ;
                break ;
            default:
             break;
        }
        return true ;
    }
  private  void addGuide(){
       Intent intent = new Intent(this,showInfoRecieverActivity.class) ;
       intent.putExtra("constant",ADD_GUIDE_CONSTANT) ;
        startActivity(intent);
  }
    private void gotoAddHouse(){
        startActivity(new Intent(this ,GiveLocationActivity.class));
    }
    private  void listLocataire(){
        Query query = UserHelper.MyLocataire(MainActivity.currentUser) ;
        showPeople(query);
    }
    private  void maisonRetire(){
     query=  ArchiveHelsptore.getAchiveHouse().whereEqualTo("ownUID",MainActivity.currentUser) ;
     update(query,SHOW_TO_SET) ;
    }
    private  void setSettingDemarcheur(){
        Toast.makeText(this,"parametre demarcheur" ,Toast.LENGTH_LONG).show();
    }
    private  void showHouseLouer(){

    }

    private void showPeople(Query query){
         FirestoreRecyclerOptions<HouseLocataire> options = new FirestoreRecyclerOptions.Builder<HouseLocataire>()
                .setQuery(query,HouseLocataire.class)
                .setLifecycleOwner(this)
                .build();
        adapter2 = new HouseLocataireAdapter(options ,this) ;
        binding.rcvListH.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvListH.setAdapter(adapter2);
    }
    @Override
    public boolean onSupportNavigateUp() {

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this ,dashboardActivity.class));
    }

    @Override
    public void getItemFromClickEvent(HouseModel model, int position) {
        this.IdHouse = model.getIdHouse() ;
        if (position==0){
              deleteHouse(model);
           }if (position==1){
         deleteHouse(model);
        }if (position==2){
            goToMidofoed(model);
        }if (position==3){
            goToChangeSetting();
        }
   }
  private void goToChangeSetting(){
       binding.containSetting.setVisibility(View.VISIBLE);
       binding.containRecv.setVisibility(View.GONE);

  }

    private  void deleteHouse(HouseModel house) {
        Toast.makeText(this ,house.toString() ,Toast.LENGTH_LONG).show();
        dialog.show();
           ArchiveHelsptore.getAchiveHouse().document(house.getIdHouse())
               .set(house).addOnSuccessListener(aVoid ->
                 HouseHelpStore.getCollection().document("dispo").collection("house")
               .document(house.getIdHouse()).delete().addOnSuccessListener(aVoid1 -> dialog.dismiss())) ;
    }
    private void goToMidofoed(HouseModel  house){
        Intent intent = new Intent(this ,GiveLocationActivity.class) ;
        intent.putExtra("house" ,house) ;
        startActivity(intent);
    }

    @Override
    public void getItemTosetFromClickEvent(HouseModel model, int position) {
        retreive(model);
    }
    private void  retreive(HouseModel model){
        dialog.show();
        HouseHelpStore.getCollection().document("dispo").collection("house")
                .document(model.getIdHouse()).set(model).addOnSuccessListener(aVoid -> {
                    ArchiveHelsptore.getAchiveHouse().document(model.getIdHouse()).delete() ;
                    Toast.makeText(getApplicationContext() ," maison récuperer avec succes" ,Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }) ;
    }


}