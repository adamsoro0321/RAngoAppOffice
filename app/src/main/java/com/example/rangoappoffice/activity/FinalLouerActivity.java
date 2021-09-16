package com.example.rangoappoffice.activity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.databinding.ActivityFinalLouerBinding;
import com.example.rangoappoffice.model.PayData;
import com.example.rangoappoffice.operation.LouerOperation;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class FinalLouerActivity extends AppCompatActivity {
 private ActivityFinalLouerBinding binding ;
 private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinalLouerBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog =new ProgressDialog(this) ;
        dialog.setMessage("wait...");

        registerForContextMenu(binding.codePay);
         String IdHouse =getIntent().getStringExtra("IdHouse") ;
        initOperation(IdHouse);
         loadHouse(IdHouse);
         loadRessource();

        binding.switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                loadHouseInfo(IdHouse);
            }else{
            }
        });
    }
    private void initOperation(String house){
        Map<String, Object> w = new HashMap<>();
        w.put("locataire" ,MainActivity.currentUser);
        w.put("houseId" ,house);
        w.put("date" , FieldValue.serverTimestamp()) ;
        LouerOperation.initLouer().document(MainActivity.currentUser).set(w) ; }


    private void loadHouse(String h){
       HouseHelpStore.getHousePay(h).document(h).get().addOnSuccessListener(documentSnapshot -> {
           PayData payData =documentSnapshot.toObject(PayData.class) ;
          if (payData!=null){
              String code ="*144*2*1*"+payData.getNumero()+"*"+payData.getSomme()+"*"+0000+"#" ;
              binding.codePay.setText(code);
          } }) ;
    }
    private void loadHouseInfo(String house){
        HouseHelpStore.getHouseRoom(house).document(house).get()
                .addOnSuccessListener(documentSnapshot -> {
              if(documentSnapshot.exists() &&   documentSnapshot!=null ){
                  HouseModel houseModel =documentSnapshot.toObject(HouseModel.class) ;
                       if (houseModel!=null){
                           String owner =houseModel.getOwnUID() ;
                           AlertOwner(owner);
                           SaveMyOwner(owner ,house);
                           SaveHouse(houseModel);
                       } }else{
                        Toast.makeText(getApplicationContext() ,"operation echouer" ,Toast.LENGTH_LONG).show();
                    } }) ; }
    private  void AlertOwner(String owner){
        Map<String, Object> w = new HashMap<>();
        w.put("locataire" ,MainActivity.currentUser);
        w.put("date" , FieldValue.serverTimestamp()) ;
        UserHelper.AlertFromLocataitre(owner).document(MainActivity.currentUser).set(w) ;
    }

    private void SaveMyOwner(String owner ,String house){
        Map<String ,Object> m =new HashMap<>();
        m.put("owner" ,owner);
        m.put("IdHouse",house);
        m.put("date" ,FieldValue.serverTimestamp());
        UserHelper.MyOwner(MainActivity.currentUser).add(m) ;
    }
    private  void SaveHouse(HouseModel house){dialog.show();
        UserHelper.getHouseLouerCollection(MainActivity.currentUser).document(house.getIdHouse())
                .set(house).addOnSuccessListener(aVoid -> updateLocataireId(house.getIdHouse())) ; }

    private void updateLocataireId(String IdHouse){
        HouseHelpStore.getCollection().document("dispo")
                .collection("house").document(IdHouse)
                .update("dLocataire" ,MainActivity.currentUser)
                .addOnSuccessListener(aVoid -> dialog.dismiss()); }
     private void loadRessource(){
         AdminHelper.finalLouerString().get().addOnSuccessListener(doc -> {
            binding.warning.setText(doc.get("warning").toString());
             binding.guideText.setText(doc.get("guideText").toString());
         }) ;
     }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(0,v.getId(),0,"Copy");
        TextView textView = (TextView) v ;
        ClipboardManager  clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE) ;
        ClipData data = ClipData.newPlainText("text",textView.getText()) ;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true ;
    }
}