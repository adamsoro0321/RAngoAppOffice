package com.example.rangoappoffice.fragment.giveHLFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rangoappoffice.activity.AddHouseCompasant;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.FragmentGiveHFragFourBinding;
import com.example.rangoappoffice.service.LoadService;
import com.example.rangoappoffice.R ;

import org.jetbrains.annotations.NotNull;

public class GiveHFragFour extends Fragment  {
    private Listener listenter ;
    private Uri selectImg ;

   private FragmentGiveHFragFourBinding binding ;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    binding.imgId.setImageURI(result);
                    selectImg =result ;

                }
            }) ;
  public  interface Listener{
      HouseModel getHouse();
      Uri getImg();
      void setHouse(HouseModel house);
      int getConstantFromIntent() ;
  }

    public GiveHFragFour() {

    }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
   }

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      binding = FragmentGiveHFragFourBinding.inflate(getLayoutInflater() ,container ,false);
       View view = binding.getRoot() ;
      HouseModel house = listenter.getHouse() ;
      Uri img = listenter.getImg() ;
     design(house ,img);
      binding.btnAddImg.setOnClickListener(v ->goToupLoadHouse(house));

      binding.btnMdf.setOnClickListener(v -> listenter.setHouse(house));
      binding.addMainImg.setOnClickListener(v -> mGetContent.launch("image/*"));
       return view;
    }

  private  void goToupLoadHouse(HouseModel house){
      house.setLoyer(house.getLoyer().replaceAll("\\s+",""));
      if (selectImg!=null){
          Intent intent = new Intent(getActivity() , LoadService.class) ;
          intent.putExtra("house",house);
          intent.putExtra("selectImg" ,selectImg.toString()) ;
          getActivity().startService(intent);
       addHouseComposant(house);
      }else{
          binding.addMainImg.setBackgroundColor(getResources().getColor(R.color.red));
      }
  }


    private void addHouseComposant(HouseModel house){
        Intent i =new Intent(getContext(), AddHouseCompasant.class);
        i.putExtra("idHouse" ,house.getIdHouse()) ;
        i.putExtra("CONSTANT_FROM" ,listenter.getConstantFromIntent());
      getActivity().startActivity(i);
    }

    private void design(HouseModel house ,Uri uri){
      binding.imgId.setImageURI(uri);
      binding.loyerId.setText(house.getLoyer());
      binding.categoriId.setText(house.getCategori());
      binding.adressId.setText(house.getAdresse());
      binding.descId.setText(house.getDescription());
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener){
            listenter= (Listener) context ;
        }else{
            throw new RuntimeException(context.toString()+"implement fail");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenter =null ;
    }

}