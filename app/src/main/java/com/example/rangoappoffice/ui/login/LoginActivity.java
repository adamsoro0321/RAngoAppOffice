package com.example.rangoappoffice.ui.login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.rangoappoffice.R ;

import com.example.rangoappoffice.BuildConfig;
import com.example.rangoappoffice.activity.MainActivity;
import com.example.rangoappoffice.activity.SetupActivity;
import com.example.rangoappoffice.api.UserHelper;
import com.example.rangoappoffice.databinding.ActivityLoginBinding ;
import com.example.rangoappoffice.model.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;

public class LoginActivity extends AppCompatActivity {
    public static  int CONSATNT_NEW =8 ;
   private  ActivityLoginBinding binding ;
    private ProgressDialog dialog ;

  private FirebaseAuth auth ;
  private    FirebaseUser firebaseUser ;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            (result) -> {
                if (result.getResultCode() == RESULT_OK) {
                   firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;

                    User user = new User(firebaseUser.getUid() ," ", firebaseUser.getPhoneNumber()," "," "," ");
                    saveUser(user);
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater()) ;
        setContentView(R.layout.activity_login);
        dialog =new ProgressDialog(this) ;
        dialog.setCancelable(true);
        dialog.setMessage("chargement...");

        auth = FirebaseAuth.getInstance() ;


     if (auth.getUid()!= null) {
      startActivity(new Intent(this , MainActivity.class));
        } else { startSignIn(); }
  }
  private void startSignIn() {
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.PhoneBuilder().build()))
                .setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */, true /* hints */)
                .build();

        signInLauncher.launch(signInIntent);
    }

    private void saveUser(User user){
        dialog.show();
        UserHelper.getUserDoc(user.getUid())
                .set(user).addOnSuccessListener(aVoid -> {
            dialog.dismiss();
            Intent i = new Intent(this , SetupActivity.class);
            i.putExtra("user" ,user);
            i.putExtra("constant",CONSATNT_NEW);
            startActivity(i);
            finish();
        });
    }

}