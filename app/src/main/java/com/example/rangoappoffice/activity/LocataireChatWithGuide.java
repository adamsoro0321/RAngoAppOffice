package com.example.rangoappoffice.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.data.LocalData;
import com.example.rangoappoffice.databinding.ActivityLocataireChatWithGuideBinding;

import com.example.rangoappoffice.model.LocataireInfo;
import com.example.rangoappoffice.model.User;
import com.example.rangoappoffice.model.messageChat;
import com.example.rangoappoffice.operation.visitOperation;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import java.util.Objects;

import adpter.GuideAndLocataireChatAdapter;

public class LocataireChatWithGuide extends BaseActivity {
   String guideUId ,currentUser ;
   private ProgressDialog dialog ;
   private  GuideAndLocataireChatAdapter adapter ;
     private ActivityLocataireChatWithGuideBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocataireChatWithGuideBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();
        dialog = new ProgressDialog(this) ;
        dialog.setCancelable(false);

        LocataireInfo locataireInfo = (LocataireInfo) getIntent().getSerializableExtra("locataireInfo");

        if (locataireInfo!=null){
            String operationId  =locataireInfo.getOperationId() ;
            guideUId  = locataireInfo.getLocataireUId();
           ratingListener(operationId);
          if (guideUId !=null){
               Query query = UserHelper.chatWithGuide(MainActivity.currentUser ,guideUId)
                      .orderBy("dateCreated" ,Query.Direction.ASCENDING).limit(40) ;
              configAdpter(query);
              SaveMeInGuideRoom();

            }
        }
   binding.sendBtn.setOnClickListener(v -> {
           if (!binding.writTxt.getText().toString().isEmpty() && guideUId !=null){
                 sendMsg(); }else{
                  Toast.makeText(getApplicationContext() ," ecrire quelque chose" ,Toast.LENGTH_LONG).show();
                }
   });

    }
   private void  SaveMeInGuideRoom(){
        User user = LocalData.getUserFromPreference(this);
       UserHelper.getGensGuide(guideUId).document(MainActivity.currentUser).set(user) ;
   }



    private void configAdpter(Query query){
        FirestoreRecyclerOptions<messageChat> options = new FirestoreRecyclerOptions.Builder<messageChat>()
                .setQuery(query, messageChat.class)
                .setLifecycleOwner(this)
                .build();
        adapter =new GuideAndLocataireChatAdapter(options ,MainActivity.currentUser ,this) ;
        binding.chatWithLocatRcv.setLayoutManager(new LinearLayoutManager(this));
        binding.chatWithLocatRcv.setAdapter(adapter);
    }

   private void sendMsg(){
        dialog.show();
        String text = binding.writTxt.getText().toString() ;
        messageChat message = new messageChat(text ,MainActivity.currentUser);
        UserHelper.chatWithGuide(MainActivity.currentUser ,guideUId).add(message).addOnSuccessListener(documentReference -> {
            dialog.dismiss();
            binding.writTxt.setText(" ") ;
        });
    }
    private void ratingListener(String operationId){
        binding.ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
      loadNote(operationId ,rating);
      Toast.makeText(getApplicationContext() ,"vous avez noté"+rating ,Toast.LENGTH_LONG).show();
        });
    }

    private void loadNote(String operationId ,float rate ){
        visitOperation.getGuideOperation(operationId).update("rateGuide" ,rate) ;
    }

}