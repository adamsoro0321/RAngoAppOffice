package com.example.rangoappoffice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.rangoappoffice.databinding.ActivityHomeBinding;
import com.example.rangoappoffice.ui.login.LoginActivity ;
import com.bumptech.glide.Glide;
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.api.UserHelper;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding ;

     protected   static  final int CONSTANT_M_L=1 ;
       protected static final int CONSTANT_G_C =2 ;
      public static  final int CONSTANT_D = 9;
      public static  final int CONSTANT_COMMENTAIRE=24 ;
    public static  final int CONSTANT_ASSISTANT=25;
    public static  final int CONSTANT_SINGN_OUT=25;
    private SharedPreferences preferences ;
    SharedPreferences.Editor editor ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    getSupportActionBar().hide() ;
   preferences =getSharedPreferences("app" ,MODE_PRIVATE) ;
   editor =preferences.edit() ;

        downloadNumber();
        downloadInfoHouseLouer() ;
        getUserInfo(MainActivity.currentUser);
        downloadInfoGuide(MainActivity.currentUser);


        binding.expandIcon.setOnClickListener(v -> expandLayout());
       binding.modifiInfo.setOnClickListener(v -> startActivity(new Intent(getApplicationContext() ,SetupActivity.class)));
         binding.linMaisonLouer.setOnClickListener(v -> configIntent(CONSTANT_M_L));
        binding.linGuide.setOnClickListener(v -> configIntent(CONSTANT_G_C));
        binding.dConect.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext() , LoginActivity.class);
            i.putExtra("constant",CONSTANT_D) ;
        });

        binding.btnComment.setOnClickListener(v -> configIntent(CONSTANT_COMMENTAIRE));
             binding.btnAssitant.setOnClickListener(v -> goToAssistant(CONSTANT_ASSISTANT));
      binding.btnMjr.setOnClickListener(v -> Toast.makeText(getApplicationContext() ," update " ,Toast.LENGTH_LONG).show());
    binding.dConect.setOnClickListener(v -> signOut());
    }
   private  void configIntent(int constant){
       Intent i  = new Intent(getApplicationContext() ,showInfoRecieverActivity.class) ;
       i.putExtra("CONSTANT_NAME" ,constant) ;
       startActivity(i);
   }
    private  void goToAssistant(int constant){
        Intent i  = new Intent(getApplicationContext() ,GlobalChatActivity.class) ;
        i.putExtra("CONSTANT_NAME" ,constant) ;
        startActivity(i);
    }
    private  void getUserInfo(String currentUser){
        UserHelper.getUserDoc(currentUser).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.get("name").toString()!=null){
                        binding.userProfilName.setText(documentSnapshot.getString("name"));
                        binding.userNumer.setText(documentSnapshot.getString("phone"));
                        Glide.with(getApplicationContext()).load(documentSnapshot.get("imageProfil"))
                                .placeholder(R.drawable.account)
                                .into(binding.imgProfilUser) ;
                    }
                }) ;
       }


    private void downloadNumber(){
        AdminHelper.getNumber().get().addOnSuccessListener(documentSnapshot -> {
            binding.num1.setText(documentSnapshot.get("numero1").toString());
            binding.num2.setText(documentSnapshot.get("numero2").toString());
            binding.num3.setText(documentSnapshot.get("numero3").toString());
        }) ;
    }


    private void expandLayout(){
      if (binding.containNum.getVisibility()==View.VISIBLE){
            binding.containNum.setVisibility(View.GONE);
             binding.expandIcon.setImageResource(R.drawable.ic_baseline_expand_more_24);
     }else{
         binding.containNum.setVisibility(View.VISIBLE);
         binding.expandIcon.setImageResource(R.drawable.ic_baseline_expand_less_24);
     }
    }

   private void downloadInfoHouseLouer(){
       UserHelper.getHouseLouerCollection(MainActivity.currentUser)
               .limit(1)
               .get().addOnCompleteListener(task -> {
                   for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                       HouseHelpStore.getHouseRoom(documentSnapshot.getId())
                               .document(documentSnapshot.getId()).get()
                               .addOnSuccessListener(documentSnapshot1 ->
                               binding.maisonLoue.setText(documentSnapshot1.getString("categori"))) ;
                   }
       });
   }
   private void downloadInfoGuide(String user){
            UserHelper.getGuideContactCollection(user).limit(1)
                    .get().addOnCompleteListener(task -> {
                        for (DocumentSnapshot documentSnapshot : task.getResult()){

                            binding.guideCon.setText(documentSnapshot.get("name").toString() );
                        }
                    }) ;
   }
private void signOut(){
        AuthUI.getInstance().signOut(this).addOnSuccessListener(aVoid -> {
            editor.putBoolean("isSign",false) ;
            editor.putString("Uid" ,null) ;
            editor.commit() ;
            Intent  i=new Intent(getApplicationContext() ,LoginActivity.class) ;
            i.putExtra("CONSTANT" ,CONSTANT_SINGN_OUT) ;
            startActivity(i);
            finish();
        }) ;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            return super.onOptionsItemSelected(item);
    }
}

