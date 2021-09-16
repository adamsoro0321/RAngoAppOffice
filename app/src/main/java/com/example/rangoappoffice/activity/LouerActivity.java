 package com.example.rangoappoffice.activity;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rangoappoffice.R ;
import com.example.rangoappoffice.api.AdminHelper;
import com.example.rangoappoffice.databinding.ActivityLouerBinding;

import info.lapieuvre.pay_android_gateway.activity.pay_payment_activity;
import info.lapieuvre.pay_android_gateway.model.Pay_GetReport;
import info.lapieuvre.pay_android_gateway.model.Pay_InvoiceItem;
import info.lapieuvre.pay_android_gateway.model.Pay_Setup;


 public class LouerActivity extends BaseActivity  {
     // Le request code du plugin (ne pas modifier sa valeur)
     private static final int PAY_PAYMENT_ACTIVITY_RESULT = 1102;
     private Pay_Setup RequestSetup ,ResponseSetup;
     private Button accepted ;
     private LinearLayout contain_pay ;
     private  CardView contain_condition ;
     private  String currentUser ;
     private ActivityLouerBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityLouerBinding.inflate(getLayoutInflater()) ;
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        accepted =binding.acceptLouer ;
        contain_pay=binding.containPayBtn ;
        contain_condition=binding.containCondition ;
        contain_pay.setVisibility(View.GONE);

        currentUser =getCurrentUser().getUid() ;
        String house =getIntent().getStringExtra("idHouse") ;

        loadCondion() ;



        ligdicash();

        accepted.setOnClickListener(v -> {
            contain_pay.setVisibility(View.VISIBLE);
            contain_condition.setVisibility(View.GONE);
        });

        binding.payByOrangBtn.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext() ,FinalLouerActivity.class) ;
            i.putExtra("IdHouse" ,house) ;
            startActivity(i); });
    }

    private void ligdicash(){
        String appName =getString(R.string.app_name);
        RequestSetup = new Pay_Setup();
        ResponseSetup = new Pay_Setup();

        RequestSetup.setApi_public_key(getString(R.string.key_pay));
        RequestSetup.setApi_token(getString(R.string.token_pay));


        // Définition du mode d'utilisation. Soit 'test' soit 'live', exemple : RequestSetup.setMode("live") ou RequestSetup.setMode("test")

        RequestSetup.setMode("live");
        RequestSetup.setMethod(1);

        RequestSetup.addInvoiceitem(new Pay_InvoiceItem("Jean Gucci",
                3, 150, 450, "Jean bleu, de marque Gucci"));
        RequestSetup.addInvoiceitem(new Pay_InvoiceItem("Jean Prada",
                2, 100, 200, "Jean noir, de marque Prada"));


        RequestSetup.setDescription("Achat de "+
                RequestSetup.getInvoiceItems().size()+" article(s) pour "+
                RequestSetup.getTotal_amount()+" franc(s) dans "+appName );
        RequestSetup.setTotal_amount(650);
    }
     public void pay_now(View view) {
         Intent PaymentActivity = new Intent(getApplicationContext(), pay_payment_activity.class);
         Bundle PaymentActivitySetup = new Bundle();
         PaymentActivitySetup.putParcelable("setup",RequestSetup);
         PaymentActivity.putExtras(PaymentActivitySetup);
         startActivityForResult(PaymentActivity, PAY_PAYMENT_ACTIVITY_RESULT);

     }

     private  void loadCondion(){
         TextView condition = findViewById(R.id.condition_louer);
        AdminHelper.getLouerCondition().get()
                .addOnSuccessListener(documentSnapshot -> condition.setText(documentSnapshot.get("conditions").toString())) ;

     }


     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if (requestCode == PAY_PAYMENT_ACTIVITY_RESULT && resultCode == RESULT_OK) {
             Bundle PaymentActivitySetup = data.getExtras();
             Pay_GetReport report = PaymentActivitySetup.getParcelable("report");
             ResponseSetup = PaymentActivitySetup.getParcelable("setup");
             if ("00".equals(report.getResponse_code())) {
                 if (report.getStatus() == "completed") {
                     // ecrivez ici votre code de paiement réussi
                     Toast.makeText(getApplicationContext(), "Succes", Toast.LENGTH_SHORT).show();
                 } else {
                     //ecrivez ici votre code paiement échoué
                     Toast.makeText(getApplicationContext(), "Echec ", Toast.LENGTH_SHORT).show();
                 }
                 // pour recuperer le token de cette transaction, faites ResponseSetup.getInvoiceToken();
             } else {//ecrivez ici votre code paiement échoué
                 Toast.makeText(getApplicationContext(), "Echec", Toast.LENGTH_SHORT).show();
                 // affichage du motif de l'echec
                 Toast.makeText(getApplicationContext(), "" + report.getResponse_text(), Toast.LENGTH_SHORT).show();
                 // pour recuperer le token de cette transaction, faites ResponseSetup.getInvoiceToken();
             }
         }
     }

     @Override
     public void onBackPressed() {
         super.onBackPressed();

     }

     @Override
     public boolean onSupportNavigateUp() {
        onBackPressed();
         return true;
     }
 }