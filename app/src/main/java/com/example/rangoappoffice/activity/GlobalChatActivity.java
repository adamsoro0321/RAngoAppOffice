package com.example.rangoappoffice.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.databinding.ActivityGlobalChatBinding;
import com.example.rangoappoffice.model.Alert;
import com.example.rangoappoffice.model.messageChat;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import adpter.GuideAndLocataireChatAdapter;

public class GlobalChatActivity extends AppCompatActivity {
  private ActivityGlobalChatBinding binding ;
  private GuideAndLocataireChatAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityGlobalChatBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alertAdmin();
        binding.sendMsg.setOnClickListener(v -> endMgs());
       loadAdmin() ;
        configAdpter();
      }
   private void alertAdmin(){
        Alert a =new Alert(MainActivity.currentUser) ;
        AdminHelper.ListenUser().document(MainActivity.currentUser).set(a);
    }
    private void loadAdmin(){
        AdminHelper.assistant()
                .get().addOnSuccessListener(doc -> {
                    getSupportActionBar().setTitle("en ligne ..."+doc.get("name").toString());
                });
    }
    private void configAdpter(){
        Query query = AdminHelper.chatRoom(MainActivity.currentUser) ;
        FirestoreRecyclerOptions<messageChat> options = new FirestoreRecyclerOptions.Builder<messageChat>()
                .setQuery(query, messageChat.class)
                .setLifecycleOwner(this)
                .build();
        adapter =new GuideAndLocataireChatAdapter(options ,MainActivity.currentUser ,this) ;
        binding.rcvAssist.setLayoutManager(new LinearLayoutManager(this));
        binding.rcvAssist.setAdapter(adapter);
    }
    private void endMgs(){
        String s = binding.editextMgs.getText().toString() ;
        messageChat message  ;
        if (!s.isEmpty()){
            message =new messageChat(s,MainActivity.currentUser);
            AdminHelper.chatRoom(MainActivity.currentUser).add(message)
                    .addOnSuccessListener(reference -> binding.editextMgs.setText(""));
        }else{
            binding.editextMgs.setBackgroundColor(getResources().getColor(R.color.red));
            binding.editextMgs.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true ;
    }

}