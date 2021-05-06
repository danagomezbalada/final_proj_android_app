package dam2021.projecte.aplicacioandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import dam2021.projecte.aplicacioandroid.ui.ftp.ClientFTP;
import dam2021.projecte.aplicacioandroid.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //scheduleSplashScreen();
        //ClientFTP.downloadAndSaveFile()
    }

    private void scheduleSplashScreen(){
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 3*1000);
    }

}