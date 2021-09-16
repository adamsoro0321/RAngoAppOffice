package com.example.rangoappoffice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.rangoappoffice.databinding.ActivityChatBoxBinding;

import java.util.Objects;

public class ChatBoxActivity extends AppCompatActivity {
  private ActivityChatBoxBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBoxBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        String ownerUId = getIntent().getStringExtra("ownerUId") ;

    }

    private  void SetAdapter(){

    }
    private void sendSms(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}