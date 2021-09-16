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

public class ownerDialogRetrieve extends DialogFragment {
    private final Context context ;
    private final HouseModel model ;
    private  getItemListenerToSet listener ;
    public interface getItemListenerToSet {
        void getItemTosetFromClickEvent(HouseModel model ,int position) ;
    }
    public  ownerDialogRetrieve(Context context, HouseModel model) {
        this.context =context ;
        this.model =model ;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("que voulez faire ")
                .setItems(R.array.optionItem, (dialog, which) -> {
                    listener.getItemTosetFromClickEvent(model ,which);
                }) ;

        return builder.create() ;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (getItemListenerToSet) context;
        }catch (ClassCastException e){
            throw  new ClassCastException(context.toString()+"must implement ") ;
        }
    }
}
