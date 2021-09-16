package com.example.rangoappoffice.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import  com.example.rangoappoffice.R ;
import com.example.rangoappoffice.app.Objets.HouseModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class OwnerDialog extends DialogFragment {
    private final Context context ;
    private final HouseModel model ;
    private  getItemListener listener ;
    public interface getItemListener{
        void getItemFromClickEvent(HouseModel model ,int position) ;
    }
    public OwnerDialog(Context context, HouseModel model) {
        this.context =context ;
        this.model =model ;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("que voulez faire ")
                .setItems(R.array.ownerActionOnHouse, (dialog, which) -> {
                     listener.getItemFromClickEvent(model ,which);
                }) ;

        return builder.create() ;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
       try {
           listener = (getItemListener) context;
       }catch (ClassCastException e){
           throw  new ClassCastException(context.toString()+"must implement ") ;
       }
    }
}
