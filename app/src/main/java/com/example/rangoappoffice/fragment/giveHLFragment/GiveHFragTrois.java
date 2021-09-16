package com.example.rangoappoffice.fragment.giveHLFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rangoappoffice.activity.MainActivity;
import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.FragmentGiveHFragTroisBinding;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import adpter.spinerHouseAdapter;
import adpter.spinnerLoyerAdapter;


public class GiveHFragTrois extends Fragment implements AdapterView.OnItemSelectedListener {

    FragmentGiveHFragTroisBinding binding ;

    spinerHouseAdapter adapterHouse ;
    spinnerLoyerAdapter adapterLoyer ;

    ArrayList<String> listCat ;
    ArrayList<String> listLoyer;
    HouseModel house,house2;

     private  Listenter listenter ;
    public interface  Listenter{
        void succes(HouseModel house);
        void setImg(Uri uri) ;
        HouseModel getHouseToModified() ;
        int getConstantFromIntent() ;
    }
    public GiveHFragTrois() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGiveHFragTroisBinding.inflate(getLayoutInflater(),container,false) ;
         View v = binding.getRoot();
        house = new HouseModel();
          house2 =listenter.getHouseToModified() ;
         if (house2!=null){ fullInput(house2); }
         binding.spinerHouse.setOnItemSelectedListener(this);
        binding.spinnerLoyer.setOnItemSelectedListener(selectLoyer);

        binding.BtnSend.setOnClickListener(v1 -> chekInputIsFull());
      setSpinnerLoyer();
        setSpinnerHouse();
         return v ;
    }



    private  void chekInputIsFull(){
        if (binding.descriptonSend.getText().toString().isEmpty()){
            binding.descriptonSend.setError("");
        }else if (binding.adressSend.getText().toString().isEmpty()){
            binding.adressSend.setError("");
        }else if (binding.lieuIndi.getText().toString().isEmpty()){
            binding.lieuIndi.setError("");
        }else {
        onHouseBuildGeneral(house);
        }
 }

    private final AdapterView.OnItemSelectedListener selectLoyer = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String loyer =parent.getItemAtPosition(position).toString() ;
            house.setLoyer(loyer);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    } ;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       String houseItem =  parent.getItemAtPosition(position).toString();
       house.setCategori(houseItem);
 }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private String buidIdHouse(){
        String IdHouse ;
        Long time = System.currentTimeMillis() ;
        if (house2!= null  && !house2.getIdHouse().isEmpty()){
            IdHouse = house2.getIdHouse() ; }
        else{
            IdHouse =MainActivity.currentUser + time.toString();
        }
        return IdHouse ;
    }
    public void onHouseBuildGeneral(HouseModel house) {
        this.house =house;
        String IdHouse= buidIdHouse();
        house.setDescription(getDescription());
        house.setAdresse(getAdress());
        house.setIdHouse(IdHouse);
        house.setLieuReference(getLieuIndik());
        house.setOwnUID(MainActivity.currentUser);
        listenter.succes(house);
    }



    private String getDescription(){
        return binding.descriptonSend.getText().toString();
    }
    private String getAdress(){
        return binding.adressSend.getText().toString();
    }
    private String getLieuIndik(){
        return binding.lieuIndi.getText().toString() ;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  Listenter){
            listenter= (Listenter)context ;
        }else{
            throw new RuntimeException(context.toString()+"implement fail");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listenter = null ;
    }

    private void fullInput(HouseModel house){
        binding.adressSend.setText(house.getAdresse()) ;
        binding.descriptonSend.setText(house.getDescription()) ;
        binding.lieuIndi.setText(house.getLieuReference());
    }

    private void setSpinnerLoyer(){
        listLoyer = getLoyer() ;
        adapterLoyer = new spinnerLoyerAdapter(getContext() ,listLoyer);
        binding.spinnerLoyer.setAdapter(adapterLoyer);
    }

    private void setSpinnerHouse(){
        listCat = getCatList() ;
        adapterHouse = new spinerHouseAdapter(getContext() ,listCat);
        binding.spinerHouse.setAdapter(adapterHouse);
    }

    private ArrayList<String> getLoyer() {
        listLoyer = new ArrayList<>();
        AdminHelper.getLoyer().orderBy("prix" , Query.Direction.ASCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                    String loyer = documentSnapshot.getString("prix") ;
                    listLoyer.add(loyer);
                }
                adapterLoyer.notifyDataSetChanged();
            }
        }) ;
        return listLoyer;
    }


    private ArrayList<String> getCatList() {
        listCat = new ArrayList<>();
        AdminHelper.getCategoriMaison().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                for (QueryDocumentSnapshot documentSnapshot :task.getResult()){
                    String house =documentSnapshot.getString("categori");
                    listCat.add(house); }
                adapterHouse.notifyDataSetChanged();
            }
        }) ;
        return listCat ;
    }



}