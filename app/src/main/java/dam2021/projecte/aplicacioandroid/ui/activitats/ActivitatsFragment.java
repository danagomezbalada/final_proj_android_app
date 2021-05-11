package dam2021.projecte.aplicacioandroid.ui.activitats;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dam2021.projecte.aplicacioandroid.DBMS;
import dam2021.projecte.aplicacioandroid.R;
import dam2021.projecte.aplicacioandroid.ui.activitats.dummy.DummyContent;
import dam2021.projecte.aplicacioandroid.ui.home.Esdeveniment;

/**
 * A fragment representing a list of Items.
 */
public class ActivitatsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 2;

    Bundle b;

    private SQLiteDatabase baseDades;
    private List<Activitat> activitats = new ArrayList<>();

    private DateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");

    public ActivitatsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ActivitatsFragment newInstance(int columnCount) {
        ActivitatsFragment fragment = new ActivitatsFragment();
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
        View view = inflater.inflate(R.layout.fragment_activitats_list, container, false);

        activitats = new ArrayList<>();
        DBMS db = DBMS.getInstance(getActivity());
        this.baseDades = db.getWritableDatabase();
        afegirActivitats(baseDades);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyActivitatsRecyclerViewAdapter(activitats));
        }
        return view;
    }

    private void afegirActivitats(SQLiteDatabase baseDades) {

        b = getArguments();

        Integer id = b.getInt("id");
        String origen = b.getString("origen");

        String query = "";

        if (origen.equals("esdeveniment")){
            query = "SELECT id, titol, data FROM activitat WHERE id_esdeveniment = "+ id +" AND date('now') BETWEEN data_inici_mostra AND data_fi_mostra ;";
        }else {
            query = "SELECT a.id, a.titol, a.data, a.data_inici_mostra, a.data_fi_mostra FROM activitat_categoria ap JOIN activitat a ON ap.id_activitat = a.id WHERE ap.id_categoria = " + id + " AND date('now') BETWEEN a.data_inici_mostra AND a.data_fi_mostra ;";
        }

        Cursor resultat = baseDades.rawQuery(query, null);

        if (resultat == null)
            return;

        // Si la consulta ha obtingut resultats, anem afegint aquests a l'ArrayList
        try {
            while (resultat.moveToNext()){
                Activitat aux = new Activitat();
                aux.setId(resultat.getInt(0));
                aux.setTitol(resultat.getString(1));

                Date data = formatData.parse(resultat.getString(2));

                aux.setData(data);

                activitats.add(aux);
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