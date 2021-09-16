package com.example.rangoappoffice.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.rangoappoffice.R;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.ShowOffreBinding;

public class showOffreDialog  extends DialogFragment  {
    public interface ListenerOffre  {
        void positivClic() ;
        void NegativClic() ;
        HouseModel getHouse() ;
    }
    ListenerOffre listenerOffre ;
    private  HouseModel housed ;
  private   ShowOffreBinding binding ;
    final Context context ;
    public showOffreDialog(Context context) {
        super(R.layout.show_offre);
        this.context=context ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context) ;
        LayoutInflater inflater = requireActivity().getLayoutInflater() ;
        binding =ShowOffreBinding.inflate(getLayoutInflater()) ;
        HouseModel houseModel =listenerOffre.getHouse() ;
        View v =binding.getRoot() ;


        if (houseModel!=null){
            Glide.with(context).load(houseModel.getImage()).into(binding.houseImg) ;
            binding.lieuHouse.setText(houseModel.getLieuReference());
           binding.refHouse.setText(houseModel.getAdresse());
        }


             builder.setView(v)
                .setPositiveButton("postuler", (dialog, which) -> {
                       listenerOffre.positivClic();
                  }).setNegativeButton("Non", (dialog, which) ->{
                     listenerOffre.NegativClic();
              }) ;
        return builder.create() ;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
             listenerOffre = (ListenerOffre) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+" must implement dialog") ;
        }
    }
}
