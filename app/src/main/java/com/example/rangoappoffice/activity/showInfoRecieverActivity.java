package com.example.rangoappoffice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.databinding.ActivityShowInfoRecieverBinding;
import com.example.rangoappoffice.model.People;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import adpter.SampleAdapter;
import adpter.adminHouseAdapter;

public class showInfoRecieverActivity extends AppCompatActivity {
       private ActivityShowInfoRecieverBinding binding ;
      private adminHouseAdapter adapter ;
      private SampleAdapter adapter2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowInfoRecieverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int constant =getIntent().getIntExtra("CONSTANT_NAME",0);

        if (constant==HomeActivity.CONSTANT_M_L){

        }if (constant ==HomeActivity.CONSTANT_G_C){
            Query query =  UserHelper.getGuideContactCollection(MainActivity.currentUser) ;
            showPeople(query) ;
        }if (constant==HomeActivity.CONSTANT_COMMENTAIRE){
            designComment();
        }
        if (constant ==dashboardActivity.CONSTANT_GUIDE){
            Query query = UserHelper.getGensGuide(MainActivity.currentUser);
            showPeople(query) ;

        }if (constant == dashboardActivity.CONSTANT_CONDITION){
            Toast.makeText(this ," condition" ,Toast.LENGTH_LONG).show();

        }

    }

    private void showPeople(Query query){
        FirestoreRecyclerOptions<People> options = new FirestoreRecyclerOptions.Builder<People>()
                .setQuery(query, People.class)
                .setLifecycleOwner(this)
                .build();

        adapter2 = new SampleAdapter(options ,this ) ;
        binding.rcvHouse.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvHouse.setAdapter(adapter2);
    }

    private void designComment(){
        binding.commentContain.setVisibility(View.VISIBLE);
        Map<String,String> comment =new HashMap<>() ;
         comment.put("user" ,MainActivity.currentUser) ;
        binding.sentBtn.setOnClickListener(v -> {
            String s = binding.editText.getText().toString() ;
            if (!s.isEmpty()){
                comment.put("commentaire" ,s) ;
                AdminHelper.commentRoom().add(comment).addOnSuccessListener(reference -> {
                    binding.editText.setBackgroundColor(getResources().getColor(R.color.green));
                    binding.editText.setText("");
                    binding.editText.setHint("Merci");
                    Toast.makeText(getApplicationContext() ,"merci beaucoup" ,Toast.LENGTH_LONG).show();
                }) ;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true ;
    }
}