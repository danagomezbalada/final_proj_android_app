package dam2021.projecte.aplicacioandroid.ui.activitats;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dam2021.projecte.aplicacioandroid.DBMS;
import dam2021.projecte.aplicacioandroid.R;

public class ActivitatDetallFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

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

        return view;
    }

    private void afegirActivitat(SQLiteDatabase baseDades) {
        b = getArguments();
        Integer id = b.getInt("id");

        String query = "";
        if (id > 0){
            query = "SELECT id, titol, data, ubicacio, descripcio, departament, ponent " +
                    "FROM activitat WHERE id = "+ id +" AND date('now') BETWEEN data_inici_mostra AND data_fi_mostra ;";
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
}