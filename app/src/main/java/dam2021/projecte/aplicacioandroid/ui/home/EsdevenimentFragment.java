package dam2021.projecte.aplicacioandroid.ui.home;

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

import java.util.ArrayList;
import java.util.List;

import dam2021.projecte.aplicacioandroid.DBMS;
import dam2021.projecte.aplicacioandroid.R;

public class EsdevenimentFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private SQLiteDatabase baseDades;
    private List<Esdeveniment> esdeveniments = new ArrayList<>();


    public EsdevenimentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EsdevenimentFragment newInstance(int columnCount) {
        EsdevenimentFragment fragment = new EsdevenimentFragment();
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
        DBMS db = DBMS.getInstance(getActivity());
        this.baseDades = db.getWritableDatabase();
        afegirEsdeveniments(baseDades);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_esdeveniment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyEsdevenimentRecyclerViewAdapter(esdeveniments));
        }
        return view;
    }

    private void afegirEsdeveniments(SQLiteDatabase baseDades) {


        String query = "SELECT id, any, nom, descripcio, actiu FROM esdeveniment";
        Cursor resultat = baseDades.rawQuery(query, null);

        if (resultat == null)
            return;

        // Si la consulta ha obtingut resultats, anem afegint aquests a l'ArrayList
        try {
            while (resultat.moveToNext()){
                esdeveniments.add(new Esdeveniment(resultat.getInt(0), resultat.getInt(1),
                        resultat.getString(2), resultat.getString(3), resultat.getString(4)));
            }
        }
        finally {
            // Tanquem el cursor un cop hem acabat
            resultat.close();
        }
    }
}