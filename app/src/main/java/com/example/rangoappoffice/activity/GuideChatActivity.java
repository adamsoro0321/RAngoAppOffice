package com.example.rangoappoffice.activity;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.data.LocalData;
import com.example.rangoappoffice.databinding.ActivityGuideChatBinding;

import com.example.rangoappoffice.model.LocataireInfo;
import com.example.rangoappoffice.model.MyaDialogFragment;
import com.example.rangoappoffice.model.OffreGuiddeModel;
import com.example.rangoappoffice.model.User;
import com.example.rangoappoffice.model.messageChat;
import com.example.rangoappoffice.model.showOffreDialog;
import com.example.rangoappoffice.operation.visitOperation;
import com.example.rangoappoffice.service.GuideChooseService;
import com.example.rangoappoffice.service.GuideService;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.Query;
import com.like.IconType;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashMap;
import java.util.Map;

import adpter.GuideAndLocataireChatAdapter;

public class GuideChatActivity extends BaseActivity implements MyaDialogFragment.YourConditionDialogListener,showOffreDialog.ListenerOffre {
    Query query ;
    ProgressDialog dialog ;
    private GuideAndLocataireChatAdapter adapter ;
    private ActivityGuideChatBinding binding ;
    private   HouseModel houseModel ;
    private  String locataireUId,houseId ;
    public  static final String CONSTANT_SUCCED="SUCCES" ;
    public  static final String CONSTANT_UNSUCCED="UN_SUCCES" ;
    private setDataToDialogListener listener ;


    public interface  setDataToDialogListener{
       void getData(HouseModel house) ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuideChatBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this) ;
        dialog.setCancelable(false);
        dialog.setMessage("Chargement ...");
        getSupportActionBar().hide();

       houseModel = (HouseModel) getIntent().getSerializableExtra("house");
       LocataireInfo locataireInfo = (LocataireInfo) getIntent().getSerializableExtra("locatairInfo");
        int CONSTANT= getIntent().getIntExtra("requestGuide",0) ;
        locataireUId =locataireInfo.getLocataireUId() ;
        houseId=locataireInfo.getHouse();
        if (CONSTANT == GuideService.CONSTANT_REQUEST){ showOffre(); }
       if (locataireInfo!=null){
          intiOperation(locataireInfo.getOperationId());
          likeButonListen(locataireInfo.getOperationId());
          configAdpter(locataireInfo.getLocataireUId() ,MainActivity.currentUser);
      }

        binding.likeButton.setIcon(IconType.Thumb);
      binding.sendBtnGuide.setOnClickListener(v -> {
            if (!binding.writTxtGuide.getText().toString().isEmpty() && locataireInfo!=null){
                loadMsg(locataireInfo.getLocataireUId());
            }else{{
                Toast.makeText(getApplicationContext() ,"ecrire quelque chose" ,Toast.LENGTH_LONG).show(); }}
        });

        SaveMeInLOcataireRoom() ;
    }
    private  void SaveMeInLOcataireRoom(){
       User me = LocalData.getUserFromPreference(this) ;
        UserHelper.getGuideContactCollection(locataireUId).document(MainActivity.currentUser).set(me) ;
    }

 private void gotoDistract(){
        startActivity(new Intent(this ,DistactActivity.class));
        finish();
 }



    private void configAdpter(String locataire ,String currentId){
        Query query = UserHelper.chatWithGuide(locataire ,currentId).orderBy("dateCreated" ,Query.Direction.ASCENDING).limit(40) ;
        FirestoreRecyclerOptions<messageChat> options = new FirestoreRecyclerOptions.Builder<messageChat>()
                .setQuery(query, messageChat.class)
                .setLifecycleOwner(this)
                .build();
        adapter =new GuideAndLocataireChatAdapter(options ,MainActivity.currentUser ,this) ;
        binding.chatWithGuideRcv.setLayoutManager(new LinearLayoutManager(this));
        binding.chatWithGuideRcv.setAdapter(adapter);
    }
  private  void loadMsg(String locataireUId){
        String message = binding.writTxtGuide.getText().toString() ;
        messageChat msg = new messageChat(message ,MainActivity.currentUser) ;
        UserHelper.chatWithGuide(locataireUId ,MainActivity.currentUser).add(msg)
                .addOnSuccessListener(documentReference -> binding.writTxtGuide.setText(" ")) ;
  }
private void intiOperation(String operation){
    Map<String, Object> opUdate = new HashMap<>();
    opUdate.put("guide" , MainActivity.currentUser);
    opUdate.put("dateGuideCome" ,FieldValue.serverTimestamp()) ;
    visitOperation.getGuideOperation(operation).update(opUdate) ;
}

private void likeButonListen(String operation){
        binding.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Toast.makeText(getApplicationContext() ," succes operation" ,Toast.LENGTH_LONG).show();
                    succedOperation(operation,CONSTANT_SUCCED);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                succedOperation(operation ,CONSTANT_UNSUCCED);
            }
        });
}
private  void  succedOperation(String operation,String operationState){
        visitOperation.getGuideOperation(operation).update("succedOperation" ,operationState) ;
}



  public  void showDialog(){
        DialogFragment dialogFragment =new MyaDialogFragment(this) ;
        dialogFragment.show(getSupportFragmentManager() ,"give your condition");
  }
private  void showOffre(){
        DialogFragment dialogFragment = new showOffreDialog(this) ;
        dialogFragment.show(getSupportFragmentManager() ,"offre");
}

    @Override
    public void onpositiveClick(String tarif, String condi) {
        OffreGuiddeModel offre = new OffreGuiddeModel(tarif ,condi ,MainActivity.currentUser) ;
     UserHelper.waitingGuideProposition(locataireUId,houseId).document(MainActivity.currentUser).set(offre)
             .addOnSuccessListener(aVoid -> {
                 Toast.makeText(getApplicationContext() ,"Offre postuler" ,Toast.LENGTH_LONG).show();
                 Intent i =new Intent(getApplicationContext() , GuideChooseService.class) ;
                 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                     startService(i) ;
                 }else{
                     startService(i) ;
                 }

                 gotoDistract(); }) ;
    }

    @Override
    public void onNegativeClick(DialogFragment dialogFragment) {
        gotoDistract();
    }

    @Override
    public void onDismiss(String tarif, String condition) {
  gotoDistract();
    }
//
@Override
public void positivClic() { showDialog(); }

    @Override
    public void NegativClic() {
        gotoDistract();
    }

    @Override
    public HouseModel getHouse() {
        return this.houseModel ;
    }


}