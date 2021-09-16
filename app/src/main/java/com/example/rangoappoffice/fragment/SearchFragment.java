package com.example.rangoappoffice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import adpter.firestoreHouseAdapter;


public class SearchFragment extends Fragment {
    RecyclerView recyclerView ;
   public static final int CONSTANT_CONFIG =19 ;

    private firestoreHouseAdapter adapter ;

    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    private void configMainAdpater(View view){
        recyclerView = view.findViewById(R.id.recyclerview_search);
        Query query = HouseHelpStore.getAllHouse();
        FirestoreRecyclerOptions<HouseModel> options = new FirestoreRecyclerOptions.Builder<HouseModel>()
                .setQuery(query, HouseModel.class)
                .build();
        adapter = new firestoreHouseAdapter(options ,getContext() ,CONSTANT_CONFIG) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()) ;
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}