package com.example.rangoappoffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;


import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.SystemServiceNetWork;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.databinding.ActivityMainBinding;
import com.example.rangoappoffice.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity implements HomeFragment.Listener {

    private BottomNavigationView navigationView ;
    public static int CONSTANT_SEARCH =22 ;

   public static final String currentUser = UserHelper.getCurrentUser() ;

   private  ActivityMainBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
       navigationView =binding.bottomNavigationMain ;
       bottomnavigationmanager(navigationView);
       msearView(binding.mainSearch);
      SystemServiceNetWork.NetWorkStatut(this ,binding.bottomNavigationMain);


    }


    private void  bottomnavigationmanager(BottomNavigationView navigationView){
            navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                 case R.id.navigation_dashboard :
                    startActivity(new Intent(MainActivity.this , dashboardActivity.class));
                  return  true ;
                 case R.id.bottom_nav_search :
                    startActivity(new Intent(MainActivity.this ,  HomeActivity.class));
                    return true;
           }
         return false;
        });
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void msearView(SearchView searchView){

   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
       @Override
       public boolean onQueryTextSubmit(String query) {
           gotToLookFor(query);
           return false;
       }

       @Override
       public boolean onQueryTextChange(String newText) {
           return false;
       }
   });

   searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
       if (hasFocus){

           binding.bottomNavigationMain.setVisibility(View.GONE);
       }else {
           binding.bottomNavigationMain.setVisibility(View.VISIBLE);
       }

   });
    }

    private void gotToLookFor(String s){
        Intent  i = new Intent(this ,CategoriSearchActivity.class) ;
        i.putExtra("searching" ,s) ;
        i.putExtra("CONSTANT_SEARCH",CONSTANT_SEARCH ) ;
        startActivity(i);
    }


    @Override
    public String getQuerySearch() {
        return getIntent().getStringExtra("query_searching") ;
    }
}


