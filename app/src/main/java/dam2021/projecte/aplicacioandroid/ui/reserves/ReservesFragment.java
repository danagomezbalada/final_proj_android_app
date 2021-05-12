package dam2021.projecte.aplicacioandroid.ui.reserves;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dam2021.projecte.aplicacioandroid.DBMS;
import dam2021.projecte.aplicacioandroid.R;
import dam2021.projecte.aplicacioandroid.ui.activitats.Activitat;

public class ReservesFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private DateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");

    private Bundle data;
    private SQLiteDatabase baseDades;
    private List<Reserva> reserves;

    public ReservesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReservesFragment newInstance(int columnCount) {
        ReservesFragment fragment = new ReservesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserves_list, container, false);

        reserves = new ArrayList<>();
        DBMS db = DBMS.getInstance(getActivity());
        this.baseDades = db.getWritableDatabase();
        afegirReserves(baseDades);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyReservesRecyclerViewAdapter(reserves));
        }
        return view;
    }

    private void afegirReserves(SQLiteDatabase baseDades) {

        // Obtenim l'email de l'usuari
        FirebaseUser usuari = FirebaseAuth.getInstance().getCurrentUser();
        String emailUsuari = usuari.getEmail();

        String query = "SELECT id, email, id_activitat, data, codi_transaccio, estat FROM reserva WHERE email = \"" + emailUsuari + "\" ORDER BY data ASC;";
        Cursor resultat = baseDades.rawQuery(query, null);

            // Si la consulta ha obtingut resultats, anem afegint aquests a l'ArrayList
            try {
                while (resultat.moveToNext()) {
                    Date dataReserva = formatData.parse(resultat.getString(3));

                    int idActivitat = resultat.getInt(2);
                    Activitat activitatReserva = obtenirActivitat(idActivitat);

                    reserves.add(new Reserva(resultat.getInt(0), resultat.getString(1), resultat.getInt(2),
                            activitatReserva, dataReserva, resultat.getString(4), resultat.getInt(5)));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                // Tanquem el cursor un cop hem acabat
                resultat.close();
            }
    }

    // Obtenim l'activitat a partir de la seva id
    private Activitat obtenirActivitat(int id) {
        String query = "SELECT id, titol, data, ubicacio, descripcio, departament, ponent, preu, places_totals, places_actuals, id_esdeveniment, data_inici_mostra, data_fi_mostra FROM activitat WHERE id = " + id + ";";
        Cursor resultat = baseDades.rawQuery(query, null);
        resultat.moveToNext();


        try {
            Date data = formatData.parse(resultat.getString(2));
            Date dataIniciMostra = formatData.parse(resultat.getString(11));
            Date dataFiMostra = formatData.parse(resultat.getString(12));

            return new Activitat(resultat.getInt(0), resultat.getString(1), data, resultat.getString(3),
                    resultat.getString(4), resultat.getString(5), resultat.getString(6), resultat.getDouble(7), resultat.getInt(8),
                    resultat.getInt(9), resultat.getInt(10), dataIniciMostra, dataFiMostra);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}