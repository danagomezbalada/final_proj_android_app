package dam2021.projecte.aplicacioandroid.ui.activitats;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import dam2021.projecte.aplicacioandroid.DBMS;
import dam2021.projecte.aplicacioandroid.R;
import dam2021.projecte.aplicacioandroid.ui.reserves.Reserva;
import dam2021.projecte.aplicacioandroid.ui.reserves.ReservaFB;

public class ActivitatDetallFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private FirebaseUser usuari = FirebaseAuth.getInstance().getCurrentUser();
    private String emailUsuari = usuari.getEmail();

    Bundle b;

    private SQLiteDatabase baseDades;
    private Activitat activitat;

    private DateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");
    String pattern = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public ActivitatDetallFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ActivitatDetallFragment newInstance(String param1, String param2) {
        ActivitatDetallFragment fragment = new ActivitatDetallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_activitat_detall, container, false);

        DBMS db = DBMS.getInstance(getActivity());
        this.baseDades = db.getWritableDatabase();
        afegirActivitat(baseDades);

        TextView titol = view.findViewById(R.id.titol);
        TextView data = view.findViewById(R.id.data);
        TextView ubicacio = view.findViewById(R.id.ubicacio);
        TextView descripcio = view.findViewById(R.id.descripcio);
        TextView departament = view.findViewById(R.id.departament);
        TextView ponent = view.findViewById(R.id.ponent);
        ponent.setVisibility(View.INVISIBLE);
        TextView categories = view.findViewById(R.id.categoria);
        Button reservar = view.findViewById(R.id.boto_reservar);

        if (comprovarReservaFeta()){
            reservar.setVisibility(View.INVISIBLE);
            view.findViewById(R.id.divisor_boto_reservar).setVisibility(View.INVISIBLE);
        }

        titol.setText(activitat.getTitol());
        String dataActivitat = simpleDateFormat.format(activitat.getData());
        data.setText(dataActivitat);
        ubicacio.setText("Ubicacio: " + activitat.getUbicacio());
        descripcio.setText(activitat.getDescripcio());
        departament.setText("Departament: " + activitat.getDepartament());
        if (!activitat.getPonent().equals("null")){
            ponent.setVisibility(View.VISIBLE);
            ponent.setText("Ponent(s): " + activitat.getPonent());
        }
        categories.setText("Categoria(es): " + obtenirCategories(activitat.getId()));
        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afegirReserva();
                if (comprovarReservaFeta()){
                    reservar.setVisibility(View.INVISIBLE);
                    view.findViewById(R.id.divisor_boto_reservar).setVisibility(View.INVISIBLE);
                }
            }
        });

        return view;
    }

    private void afegirActivitat(SQLiteDatabase baseDades) {
        b = getArguments();
        Integer id = b.getInt("id");
        String origen = b.getString("origen");
        String query = "";

        if (origen.equals("activitats")){
            query = "SELECT id, titol, data, ubicacio, descripcio, departament, ponent " +
                    "FROM activitat WHERE id = "+ id +" AND date('now') BETWEEN data_inici_mostra AND data_fi_mostra ;";
        }else{
            query = "SELECT id, titol, data, ubicacio, descripcio, departament, ponent " +
                    "FROM activitat WHERE id = "+ id +";";
        }


        Cursor resultat = baseDades.rawQuery(query, null);

        if (resultat == null)
            return;

        // Si la consulta ha obtingut resultats, afegim les dades obtingudes a una instancia d'Activitat
        try {
            if (resultat.moveToNext()){
                activitat = new Activitat();
                activitat.setId(resultat.getInt(0));
                activitat.setTitol(resultat.getString(1));
                Date data = formatData.parse(resultat.getString(2));
                activitat.setData(data);
                activitat.setUbicacio(resultat.getString(3));
                activitat.setDescripcio(resultat.getString(4));
                activitat.setDepartament(resultat.getString(5));
                activitat.setPonent(resultat.getString(6));

            }
        } catch (Exception e){
            e.printStackTrace();
            Log.e("SQL",resultat.getString(2));
        }
        finally {
            // Tanquem el cursor un cop hem acabat
            resultat.close();
        }
    }

    private void afegirReserva(){

        char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        boolean afegit = false;


        while (!afegit){

            String codiTrans = generarCodiTrans(CHARSET_AZ_09, 8);
            String query = "";
            query = "SELECT * FROM reserva WHERE codi_transaccio = \""+ codiTrans + "\";";
            Cursor resultat = baseDades.rawQuery(query, null);

            if (!resultat.moveToNext()){
                query = "INSERT INTO reserva (email, id_activitat, data, codi_transaccio, estat) " +
                        "VALUES (\""+ emailUsuari + "\", " + activitat.getId() + ", date('now'), \"" + codiTrans + "\"," + 0 + ");";
                try {
                    baseDades.execSQL(query);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                query = "SELECT * FROM reserva WHERE codi_transaccio = \""+ codiTrans + "\";";
                resultat = baseDades.rawQuery(query, null);
                resultat.moveToNext();

                try {

                    Date dataReservaActual = formatData.parse(resultat.getString(3));
                    Reserva reservaActual = new Reserva(resultat.getInt(0),resultat.getString(1),resultat.getInt(2),dataReservaActual,resultat.getString(4),resultat.getInt(5));

                    FirebaseDatabase database = FirebaseDatabase.getInstance("https://aplicacio-android-default-rtdb.europe-west1.firebasedatabase.app/");
                    DatabaseReference myRef = database.getReference("R" + reservaActual.getId());

                    String patternReserva = "yyyy-MM-dd";
                    SimpleDateFormat simpleDateFormatReserva = new SimpleDateFormat(patternReserva);
                    String dataReserva = simpleDateFormatReserva.format(reservaActual.getData());

                    ReservaFB reservaFB = new ReservaFB(reservaActual.getEmail(), reservaActual.getIdActivitat(),dataReserva,reservaActual.getCodiTransaccio(), reservaActual.getEstat());

                    Map<String, ReservaFB> reserves = new HashMap<>();
                    reserves.put("R" + reservaActual.getId(), reservaFB);

                    myRef.setValue(reservaFB);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                afegit = true;
            }
        }


    }

    private String generarCodiTrans(char[] characterSet, int length){

        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            // picks a random index out of character set > random character
            int randomCharIndex = random.nextInt(characterSet.length);
            result[i] = characterSet[randomCharIndex];
        }
        return new String(result);
    }

    private boolean comprovarReservaFeta(){
        String query = "SELECT * FROM reserva WHERE id_activitat = "+ b.getInt("id") + " AND email = \"" + emailUsuari + "\";";
        Cursor resultat = baseDades.rawQuery(query, null);

        if (resultat.moveToNext()){
            return true;
        }
        return false;
    }

    private String obtenirCategories(int idActivitat){
        String query = "SELECT c.nom FROM activitat_categoria ac LEFT JOIN categoria c ON ac.id_categoria=c.id LEFT JOIN activitat a ON ac.id_activitat=a.id WHERE a.id = " + idActivitat + ";";
        Cursor resultat = baseDades.rawQuery(query, null);

        String aux = "";
        for (int i = 0; resultat.moveToNext();i++){
            if (i > 0){
                aux += ", " + resultat.getString(0);
            }else{
                aux += resultat.getString(0);
            }

        }

        return aux;
    }
}