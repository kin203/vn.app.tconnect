package vn.app.tconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import vn.app.tconnect.R;

public class Splash extends AppCompatActivity {
    LauncherManager launcherManager;
    FirebaseAuth auth;

//    private boolean backButtonPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        auth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=  auth.getCurrentUser();
        launcherManager =new LauncherManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(launcherManager.isFirstTime()){
                    launcherManager.setFirstLaunch(false);
                    startActivity(new Intent(getApplicationContext(),Slider.class));
                }else{
                    if(currentUser!=null){
                        startActivity(new Intent(Splash.this,MainActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                }
                finish();
            }
        },2000);
    }
}