package com.example.rangoappoffice.model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.rangoappoffice.R;

public class MyaDialogFragment extends DialogFragment {
    public  interface  YourConditionDialogListener{
         void onpositiveClick(String tarif ,String condi);
         void onNegativeClick(DialogFragment dialogFragment);
        void onDismiss(String tarif ,String condition) ;
    }
    final Context context ;
    YourConditionDialogListener  dialogListener ;

    public MyaDialogFragment(Context context) {
        super(R.layout.request_guide_condition_layout);
        this.context = context ;
    }
  @NonNull
    @Override
 public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context) ;
        LayoutInflater inflater = requireActivity().getLayoutInflater() ;
        View v =inflater.inflate(R.layout.request_guide_condition_layout,null) ;
       TextView tarif = v.findViewById(R.id.editTextNumber) ;
       TextView cond =v.findViewById(R.id.editTextTextPersonName);

        builder.setView(v)
                .setPositiveButton("valide", (dialog, which) -> {
                    String t =tarif.getText().toString();
                    String con = cond.getText().toString() ;
           dialogListener.onpositiveClick(t ,con);
                }).setNegativeButton("Non", (dialog, which) -> dialogListener.onNegativeClick(MyaDialogFragment.this)) ;
        return builder.create() ;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener =(YourConditionDialogListener) context ;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"must implement dialog") ;
        }
    }
}
