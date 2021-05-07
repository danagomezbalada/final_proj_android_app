package dam2021.projecte.aplicacioandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import dam2021.projecte.aplicacioandroid.ui.ftp.ClientFTP;
import dam2021.projecte.aplicacioandroid.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //scheduleSplashScreen();
        if (!checkPermission()){
            requestPermission();
        }else{
            comprovarVersioIDescarregarFitxers();
        }

    }

    private void scheduleSplashScreen() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 3 * 1000);
    }

    // Classe que descarrega la versió amb asincronía
    private class descarregarVersio extends AsyncTask<Void, Integer, Long> {
        @Override
        protected Long doInBackground(Void... voids) {
            establirFTP(ClientFTP.VERSIO);
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {

        }
    }

    // Classe que descarrega els esdeveniments amb asincronía
    private class descarregarEsdeveniments extends AsyncTask<Void, Integer, Long> {
        @Override
        protected Long doInBackground(Void... voids) {
            establirFTP(ClientFTP.ESDEVENIMENTS);
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {

        }
    }

    // Classe que descarrega les categories amb asincronía
    private class descarregarCategories extends AsyncTask<Void, Integer, Long> {
        @Override
        protected Long doInBackground(Void... voids) {
            establirFTP(ClientFTP.CATEGORIES);
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {

        }
    }

    // Classe que descarrega les activitats amb asincronía
    private class descarregarActivitats extends AsyncTask<Void, Integer, Long> {
        @Override
        protected Long doInBackground(Void... voids) {
            establirFTP(ClientFTP.ACTIVITATS);
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {

        }
    }

    // Classe que descarrega les reserves amb asincronía
    private class descarregarReserves extends AsyncTask<Void, Integer, Long> {
        @Override
        protected Long doInBackground(Void... voids) {
            establirFTP(ClientFTP.RESERVES);
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {

        }
    }

    // Funció que executa la descàrrega de tots els XML
    private void descarregarXML(){
        new descarregarEsdeveniments().execute();
        new descarregarCategories().execute();
        new descarregarActivitats().execute();
        new descarregarReserves().execute();
    }

    // Establim la descàrrega del fitxer al servidor FTP
    private boolean establirFTP(String nomFitxer) {

        try {
            FileOutputStream fitxer;
            File sdcard = Environment.getExternalStorageDirectory();
            File targetFile = new File(sdcard, nomFitxer);

            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(ClientFTP.SERVER, ClientFTP.PORT_NUMBER);
                ftpClient.enterLocalPassiveMode();
                ftpClient.login(ClientFTP.USER, ClientFTP.PASSWORD);


                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// Used for video
                targetFile.createNewFile();
                fitxer = new FileOutputStream(targetFile);
                boolean result = ftpClient.retrieveFile("/" + nomFitxer, fitxer);

                ftpClient.disconnect();
                fitxer.close();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Funció que comprova si tenim permisos d'escriptura
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    // Funció per demanar permisos d'escriptura
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    comprovarVersioIDescarregarFitxers();
                } else {
                    System.exit(0);
                }
                break;
        }
    }

    private void comprovarVersioIDescarregarFitxers(){
        // Creem o obrim el fitxer versio.txt local
        File sdcard = Environment.getExternalStorageDirectory();
        File versio = new File(sdcard, "versio.txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(versio));
            String line;

            // Llegim el fitxer i comprovem si té contingut a la primera línia
            if ((line = br.readLine()) != null) {

                // Desem la versió del fitxer local
                Double versioLocal = Double.parseDouble(line);

                try {
                    // Descarreguem el fitxer versió del FTP
                    new descarregarVersio().execute();
                    versio = new File(sdcard, "versio.txt");
                    BufferedReader brNou = new BufferedReader(new FileReader(versio));
                    String lineNou;

                    // Llegim el fitxer i comprovem si té contingut a la primera línia
                    if ((lineNou = brNou.readLine()) != null) {
                        Double versioNou = Double.parseDouble(lineNou);

                        // Comparem les versions dels fitxers versió (local vs descarregat FTP)
                        int retval = Double.compare(versioNou, versioLocal);
                        if (retval > 0){
                            Toast.makeText(getApplicationContext(), "Hi ha una nova versió disponible, descarregant actualitzacions", Toast.LENGTH_SHORT).show();
                            descarregarXML();
                            scheduleSplashScreen();
                        }else{
                            Toast.makeText(getApplicationContext(), "Tens l'última versió", Toast.LENGTH_SHORT).show();
                            scheduleSplashScreen();
                        }

                    }

                    br.close();
                } catch (FileNotFoundException ef) {
                    // Si no troba el fitxer de la versió al dispositiu, descarrega tots els fitxers i passem a la LoginActivity
                    Toast.makeText(getApplicationContext(), "Hi ha una nova versió disponible, descarregant actualitzacions", Toast.LENGTH_SHORT).show();
                    new descarregarVersio().execute();
                    descarregarXML();
                    scheduleSplashScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            br.close();
        } catch (FileNotFoundException ef) {

            // Si no troba el fitxer de la versió al dispositiu, descarrega tots els fitxers i passem a la LoginActivity
            new descarregarVersio().execute();
            descarregarXML();
            scheduleSplashScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}