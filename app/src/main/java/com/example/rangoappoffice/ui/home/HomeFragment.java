package com.example.rangoappoffice.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.FragmentHomeBinding;
import com.example.rangoappoffice.model.CategoriHome;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import adpter.CategoriAdapter;
import adpter.firestoreHouseAdapter;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView ,recyclerView2 ;
    FirebaseRemoteConfig mFirebaseRemoteConfig ;
    private firestoreHouseAdapter adapter,adapter4 ,adapter3 ;
    private CategoriAdapter adapter2 ;
    private  Query query ;
    FragmentHomeBinding binding ;
   public static final int RENTING_CONTANT=15 ;
   private  Listener listener ;
   public interface Listener{
       String getQuerySearch() ;
   }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater() ,container ,false);
       View v = binding.getRoot() ;
       String searchQuery =this.listener.getQuerySearch() ;
        if(searchQuery!=null){
            query = HouseHelpStore.getAllHouse().whereEqualTo("categori" ,searchQuery) ;
        }else{
             query = HouseHelpStore.getAllHouse();
        }

         configCategoriAdapter();
         configMainAdpater(query);

        return v;
    }
   private void configCategoriAdapter(){
        Query query1 = AdminHelper.getCategoriMaison() ;
        recyclerView2 = binding.classForcategorie ;
        FirestoreRecyclerOptions<CategoriHome> options1 = new FirestoreRecyclerOptions.Builder<CategoriHome>()
                .setQuery(query1, CategoriHome.class)
                .setLifecycleOwner(getActivity())
                .build();
        adapter2 = new CategoriAdapter(options1,getContext());
        LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setAdapter(adapter2);
        recyclerView2.setLayoutManager(layoutManager);

    }
   private void configMainAdpater(Query query){
       recyclerView =binding.recyclerviewHome ;
       FirestoreRecyclerOptions<HouseModel> options = new FirestoreRecyclerOptions.Builder<HouseModel>()
               .setQuery(query, HouseModel.class)
               .setLifecycleOwner(getActivity())
               .build();
       adapter = new firestoreHouseAdapter(options ,getContext() ,RENTING_CONTANT) ;
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) ;
       linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
       recyclerView.setLayoutManager(linearLayoutManager);
       recyclerView.setAdapter(adapter);
   }


    @Override
    public void onStart() {
        super.onStart();
 }

    @Override
    public void onStop() {
        super.onStop();
   }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        }catch (ClassCastException exception){
            throw  new ClassCastException(context.toString()+"must implement ") ;
        }
    }
}