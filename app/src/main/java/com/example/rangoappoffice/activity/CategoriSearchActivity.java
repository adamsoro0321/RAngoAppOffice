package com.example.rangoappoffice.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.ActivityCategoriSearchBinding;
import com.example.rangoappoffice.engine.SearchEngine;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import adpter.SearchEnginAdapter;
import adpter.firestoreHouseAdapter;

public class CategoriSearchActivity extends BaseActivity {

    private RecyclerView rcv_ctg ;
    private SearchEnginAdapter adapter ;
    private ActivityCategoriSearchBinding binding ;
    public static final int CONsTANT_CONFIG =18 ;
   private  ArrayList<HouseModel> houseModelList ;
   private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriSearchBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());

         dialog = new ProgressDialog(this) ;
         dialog.setMessage("wait..");
        houseModelList =new ArrayList<>() ;

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().show();
        String searchObjet = getIntent().getStringExtra("searching");
        int constant=getIntent().getIntExtra("CONSTANT_SEARCH",0) ;


        if (constant==MainActivity.CONSTANT_SEARCH){
            getData(searchObjet) ;
        }else{
          configMainAdpater(searchObjet);
        }
  }
    private  void configAdapterSeach( List<HouseModel> houses ){
        if (houses.isEmpty()){
            Toast.makeText(this ,"aucun resultat" ,Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }else{
            dialog.dismiss();
        }
        rcv_ctg =binding.rcvCtg ;
        adapter = new SearchEnginAdapter(houses,this) ;
         adapter.notifyDataSetChanged();
        rcv_ctg.setAdapter(adapter);
        rcv_ctg.setLayoutManager(new LinearLayoutManager(this));
    }
    private void configMainAdpater(String s){
        rcv_ctg =binding.rcvCtg ;
        FirestoreRecyclerOptions<HouseModel> options = new FirestoreRecyclerOptions.Builder<HouseModel>()
                .setQuery(HouseHelpStore.getAllHouse().whereEqualTo("categori" ,s), HouseModel.class)
                .setLifecycleOwner(this)
                .build();
        firestoreHouseAdapter adapter2 = new firestoreHouseAdapter(options ,this ,CONsTANT_CONFIG) ;
        rcv_ctg.setLayoutManager(new LinearLayoutManager(this));
        rcv_ctg.setAdapter(adapter2);
    }
   private void getData(String s){
        dialog.show();
        SearchEngine.searchCategori(s).addSnapshotListener((value, error) -> {
            if (error==null){
                for (DocumentSnapshot doc :value ) {
                houseModelList.add(onBuildHouse(doc)) ;

                }
                configAdapterSeach(houseModelList);

            }
        }) ;
   }

    public static HouseModel onBuildHouse(DocumentSnapshot doc){
        HouseModel house=new HouseModel() ;

        if (doc.get("idHouse")!=null){
            house.setIdHouse(doc.get("idHouse").toString());
        }
        if (doc.get("image")!=null){
            house.setImage(doc.get("image").toString());
        }

        if (doc.get("loyer")!=null){
            house.setLoyer(doc.get("loyer").toString());
        }

        if (doc.get("categori")!=null){
            house.setCategori(doc.get("categori").toString());
        }

        if (doc.get("adresse")!=null){
            house.setAdresse(doc.get("adresse").toString());
        }

        if (doc.get("yetAdmin")!=null){
            house.setYetAdmin(doc.get("yetAdmin").toString());
        }
        if (doc.get("description")!=null){
            house.setDescription(doc.get("description").toString());
        }
        if (doc.get("lieuReference")!=null){
            house.setLieuReference(doc.get("lieuReference").toString());
        }
        if(doc.get("ownUID")!=null){
            house.setOwnUID(doc.get("ownUID").toString());
        }

        if (doc.get("idLocataire")!=null){
            house.setIdLocataire(doc.get("idLocataire").toString());
        }

        return house ;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}