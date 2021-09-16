package com.example.rangoappoffice.service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.example.rangoappoffice.activity.MainActivity;
import com.example.rangoappoffice.api.HouseHelpStore;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.api.storageHelper;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.data.DataForDesign;
import com.example.rangoappoffice.data.LocalData;
import com.example.rangoappoffice.model.CurrentUsertStatu;
import com.example.rangoappoffice.model.User;
import com.google.firebase.storage.StorageReference;

public class LoadService extends Service {
    private CurrentUsertStatu usertStatu ;
    public LoadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        HouseModel house = (HouseModel) intent.getSerializableExtra("house");
        if (house!=null){
            Uri uri =Uri.parse(intent.getStringExtra("selectImg")) ;
            upLoadHouse(house ,uri);
        }
        User user = (User) intent.getSerializableExtra("user");
        if (user!=null){
            Uri uri =Uri.parse(intent.getStringExtra("selectImg")) ;
            UploadUser(user,uri);
        }
        return START_REDELIVER_INTENT ;
    }
    private void upLoadHouse(HouseModel house,Uri selectImg){
           usertStatu = new CurrentUsertStatu() ;
            String  idHouse =house.getIdHouse() ;
            StorageReference ref = storageHelper.getStorageRef(idHouse) ;
            ref.putFile(selectImg).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    ref.getDownloadUrl().addOnSuccessListener(uri -> {
                        house.setImage(uri.toString());
                        saveHouse(house ,idHouse);
                        saveHouseInOwner(house,idHouse);
                        usertStatu.setPropritaire(true);
                        DataForDesign.setCurrentUserStatu(getApplicationContext() ,usertStatu);
                    });
                }
            }) ;

    }
    private void saveHouse(HouseModel  house ,String idhouse){
          HouseHelpStore.getHouseRoom(idhouse).document(idhouse).set(house).addOnSuccessListener(aVoid -> stopSelf()) ;
    }
  private void saveHouseInOwner(HouseModel  house ,String idhouse){
        UserHelper.MesMaison().document(idhouse).set(house) ;
  }

    private void UploadUser(User user ,Uri selectImage){
        StorageReference reference = storageHelper.updateProfil(MainActivity.currentUser) ;
        reference.putFile(selectImage).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                reference.getDownloadUrl().addOnSuccessListener(uri -> {
                    user.setImageProfil(uri.toString());
                  saveWithoutImg(user);
                    LocalData.SaveUserInPrefence(user,this);
                    stopSelf();
                });
            }
        });
    }

    private  void saveWithoutImg(User user){
        UserHelper.getUserDoc(MainActivity.currentUser)
                .set(user).addOnSuccessListener(aVoid -> {

        }) ;
    }
}