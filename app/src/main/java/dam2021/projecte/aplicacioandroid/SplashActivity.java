package dam2021.projecte.aplicacioandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import dam2021.projecte.aplicacioandroid.ui.ftp.ClientFTP;
import dam2021.projecte.aplicacioandroid.ui.home.Esdeveniments;
import dam2021.projecte.aplicacioandroid.ui.home.EsdevenimentsXML;
import dam2021.projecte.aplicacioandroid.ui.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    public SQLiteDatabase baseDades;
    private static Context mContext;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = this;

        DBMS db = DBMS.getInstance(SplashActivity.getContext());
        this.baseDades = db.getWritableDatabase();

        checkVerDescFitxers();

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
            String hola = "";
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
            carregarEsdevenimentsXMLaBD();
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

        String query = "DELETE FROM esdeveniment";
        baseDades.execSQL(query);

        new descarregarEsdeveniments().execute();
        new descarregarCategories().execute();
        new descarregarActivitats().execute();
        new descarregarReserves().execute();

    }

    // Establim la descàrrega del fitxer al servidor FTP
    private boolean establirFTP(String nomFitxer) {

        try {
            FileOutputStream fitxer;
            File sdcard = getFilesDir();
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
                return true;
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

    // Funció que comprova la versió i es descarrega els fitxers del FTP
    private void checkVerDescFitxers(){
        // Creem o obrim el fitxer versio.txt local
        File directori = getFilesDir();
        File versio = new File(directori, "versio.txt");

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
                    versio = new File(directori, "versio.txt");
                    BufferedReader brNou = new BufferedReader(new FileReader(versio));
                    String lineNou;

                    // Llegim el fitxer i comprovem si té contingut a la primera línia
                    if ((lineNou = brNou.readLine()) != null) {
                        Double versioNou = Double.parseDouble(lineNou);

                        // Comparem les versions dels fitxers versió (local vs descarregat FTP)
                        int retval = Double.compare(versioNou, versioLocal);
                        if (retval > 0){
                            Toast.makeText(getApplicationContext(), R.string.new_version_available, Toast.LENGTH_SHORT).show();
                            descarregarXML();
                            scheduleSplashScreen();
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.already_last_version, Toast.LENGTH_SHORT).show();
                            descarregarXML();
                            scheduleSplashScreen();
                        }

                    }

                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            br.close();
        } catch (FileNotFoundException ef) {

            // Si no troba el fitxer de la versió al dispositiu, descarrega tots els fitxers i passem a la LoginActivity
            Toast.makeText(getApplicationContext(), R.string.new_version_available, Toast.LENGTH_SHORT).show();
            new descarregarVersio().execute();
            descarregarXML();
            scheduleSplashScreen();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funció que llegeix els esdeveniments del fitxer XML i els afegeix a la BD
    private void carregarEsdevenimentsXMLaBD() {

        File directori = getFilesDir();
        File esdevenimentsFitxer = new File(directori, "esdeveniments.xml");

        Serializer ser = new Persister();
        EsdevenimentsXML esdevenimentsXML = null;

        try {
            esdevenimentsXML = ser.read(EsdevenimentsXML.class, esdevenimentsFitxer);

            ArrayList<Esdeveniments> esdeveniments = (ArrayList<Esdeveniments>) esdevenimentsXML.getEsdeveniments();
            int errCount = 0;

            for(int i = 0; i<esdeveniments.size(); i++){
                Esdeveniments aux = esdeveniments.get(i);

                String sqlQuery = "INSERT INTO esdeveniment (id, any, nom, descripcio, actiu) " +
                        "VALUES ('"+aux.getId()+"', '"+aux.getAny()+"', '"+aux.getNom()+
                        "', '"+aux.getDescripcio()+"', '"+aux.isActiu()+"');";

                // Executem la consulta i mostrem un missatge d'estat OK o un missatge d'error
                try {
                    baseDades.execSQL(sqlQuery);
                }catch (SQLException e){
                    if (e.getMessage().contains("UNIQUE")){
                        errCount++;
                    }
                }
            }

            if (errCount > 0){
                Toast.makeText(this, "Error afegint XML" + errCount, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "XML afegit correctament", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext(){
        return mContext;
    }

}