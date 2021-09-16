package com.example.rangoappoffice.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.CommonMethode;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.app.Objets.houseComposant;
import com.example.rangoappoffice.databinding.ActivityHouseDetailBinding;
import com.example.rangoappoffice.model.RuleDoc;
import com.example.rangoappoffice.ui.home.HomeFragment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import adpter.firestoreHouseDetailAdpter;

public class HouseDetailActivity extends AppCompatActivity {
    private static final int CONSTANT_CHAT_OWNER =29 ;
    private ActivityHouseDetailBinding binding ;

   private  firestoreHouseDetailAdpter adapter;
   private  HouseModel house ;

   private  String IdOwnerHouse;
    private String IdHouse ;
   private int CONSTANT_COMING ;
    private HouseModel houseI ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = ActivityHouseDetailBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        houseI = (HouseModel) getIntent().getSerializableExtra("house");
        CONSTANT_COMING=getIntent().getIntExtra("CONSTANT_COMING",0) ;
        if (houseI!=null){ comFromMain(); }
         if (MainActivity.currentUser==null){
             binding.dLouer.setOnClickListener(clic);
             binding.dVisite.setOnClickListener(clic);
             binding.chatOwner.setOnClickListener(clic);
         }else{
             binding.dLouer.setOnClickListener(v -> goToLouerActivity(IdHouse));
             binding.dVisite.setOnClickListener(v -> {
                 if (IdOwnerHouse!=null){ goTovisitAvantLouerActivity(IdHouse); } });
             binding.shareHouse.setOnClickListener(v -> generateContentLink(IdHouse));
         }
        getRul(IdHouse);
    }
    private void comFromMain(){
         IdHouse = houseI.getIdHouse() ;
        IdOwnerHouse = houseI.getOwnUID() ;
        setUpDesignHouse(houseI);
      configAdapterImg(HouseHelpStore.getImageRoom(IdHouse));
    }

    private  void goToLouerActivity(String IdHouse){
        Intent  intent = new Intent(HouseDetailActivity.this ,LouerActivity.class) ;
        intent.putExtra("idHouse" ,IdHouse);
        startActivity(intent);
    }
    private  void goTovisitAvantLouerActivity(String IdHouse){
        Intent i = new Intent(HouseDetailActivity.this ,visitAvantLouerActivity.class) ;
        i.putExtra("IdHouse" ,IdHouse);
        i.putExtra("ownerHouse" , IdOwnerHouse) ;
        startActivity(i);
    }

    private void setUpDesignHouse( HouseModel house ){
              IdOwnerHouse =house.getOwnUID() ;
        binding.description.setText(house.getDescription());
                    binding.loyer.setText(CommonMethode.ConvertAnFormat(house.getLoyer())+" FCFA");
                    binding.adress.setText(house.getAdresse());
                    binding.categori.setText(house.getCategori());
                    Glide.with(getApplicationContext()).load(house.getImage())
                            .placeholder(R.drawable.image)
                            .into(binding.imageSlider) ;
  }
    private  void configAdapterImg( Query query){
        FirestoreRecyclerOptions<houseComposant> options = new FirestoreRecyclerOptions.Builder<houseComposant>()
                .setQuery(query, houseComposant.class)
                .setLifecycleOwner(this)
                .build();
        adapter = new firestoreHouseDetailAdpter(options ,this) ;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this) ;
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recyclerview.setLayoutManager(layoutManager);
        binding.recyclerview.setAdapter(adapter);
    }
 private void getRul(String IdHouse){
        HouseHelpStore.getHouseRules(IdHouse).document(IdHouse)
                .get().addOnSuccessListener(documentSnapshot -> {
                   if(documentSnapshot.getData()!=null){
                        RuleDoc ruleDoc = documentSnapshot.toObject(RuleDoc.class) ;
                      if (documentSnapshot.getBoolean("isChater")){
                            binding.chatOwner.setVisibility(View.VISIBLE);
                        }
                        if (documentSnapshot.get("caution")!=null){
                            String s = documentSnapshot.get("caution").toString() ;
                            binding.caution.setText("Caution :\n"+s +"FCFA");
                        }if (documentSnapshot.get("rule")!=null){
                            String d =documentSnapshot.get("rule").toString() ;
                            binding.contra.setText("Contrat : \n"+d);
                        }
                        if (documentSnapshot.get("uidOwner")!=null){
                            binding.chatOwner.setOnClickListener(v -> gotoChat(documentSnapshot.get("uidOwner").toString()));
                        } }else{ } }) ;
           }
   private  void gotoChat(String owner){
        Intent i = new Intent(this ,ChatBoxActivity.class);
        i.putExtra("CONSTANT" ,CONSTANT_CHAT_OWNER) ;
        startActivity(i);
        }
    private void onShareClicked(Uri link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link.toString());
        startActivity(Intent.createChooser(intent, "Share Link"));
    }
    public  void generateContentLink(String IdHouse) {
        Uri baseUrl = Uri.parse("https://www.rangoapp.com/?idhouse="+IdHouse);
        String domain = "https://rangoappe.page.link";
       FirebaseDynamicLinks.getInstance()
                .createDynamicLink()
                .setLink(baseUrl)
                .setDomainUriPrefix(domain)
                .setIosParameters(new DynamicLink.IosParameters.Builder("com.your.bundleid").build())
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder("com.example.rangoappoffice").build())
                .buildShortDynamicLink().addOnSuccessListener(shortDynamicLink -> {
                    Uri link =shortDynamicLink.getShortLink() ;
                    onShareClicked(link);
                }) ;

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    @Override
    public void onBackPressed() {
        if (CONSTANT_COMING==DynamicLInkActivity.CONSTANT_GO){
            startActivity(new Intent(this ,MainActivity.class));
        }else {
            super.onBackPressed();
        }
        }

   View.OnClickListener clic = v -> CommonMethode.gotoLogin(getApplicationContext());


}