package com.example.rangoappoffice.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.app.NavUtils;

import com.example.rangoappoffice.R;
import com.example.rangoappoffice.app.Objets.HouseModel;
import com.example.rangoappoffice.fragment.giveHLFragment.GiveHFragFour;
import com.example.rangoappoffice.fragment.giveHLFragment.GiveHFragTrois;

public class GiveLocationActivity extends BaseActivity  implements GiveHFragTrois.Listenter , GiveHFragFour.Listener {
    private HouseModel house ,house2 ;
    private Uri uri ;
    private  int CONSTANT_FROM_INTENT ;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_give_location);


            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().show();
             CONSTANT_FROM_INTENT = getIntent().getIntExtra("CONSTANT_NAME" ,0) ;
        checkModifiedFromOwn();
       }
      @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private  void checkModifiedFromOwn(){
      HouseModel house3 = (HouseModel) getIntent().getSerializableExtra("house");
      if (house3!=null){
          this.house2 = house3 ;
      }
    }
    @Override
    public void succes(HouseModel house) {
          this.house = house ;
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_give_location ,new GiveHFragFour() )
                    .addToBackStack(null)
                    .commit();
    }

    @Override
    public void setImg(Uri uri) {
        this.uri=uri ;
    }
    @Override
    public HouseModel getHouse() { return this.house ; }

    @Override
    public Uri getImg() {
        return this.uri ;
    }

    @Override
    public void setHouse(HouseModel house) {
      this.house2 = house ;
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_give_location ,new GiveHFragTrois() )
                .addToBackStack(null)
                .commit();
    }
    @Override
    public HouseModel getHouseToModified() {
        return this.house2;
    }

    @Override
    public int getConstantFromIntent() {
        return this.CONSTANT_FROM_INTENT ;
    }

    @Override
    public void onBackPressed() {
        alertDialog(this);
    }

    public static void alertDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context) ;
        builder.setTitle("R-ANGO").setMessage("Voulez vous abandonner le processus?") ;

        builder.setCancelable(true) ;

        builder.setPositiveButton("Continuer", (dialog, which) -> {

        }) ;

        builder.setNegativeButton("Abandonner", (dialog, which) -> context.startActivity(new Intent(context ,dashboardActivity.class))) ;

        AlertDialog alertDialog =builder.create() ;
        alertDialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homeAsUp) {
            if (getParentActivityIntent() == null) {
                onBackPressed();
            } else {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}