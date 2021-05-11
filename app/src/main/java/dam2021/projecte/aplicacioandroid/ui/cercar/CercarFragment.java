package dam2021.projecte.aplicacioandroid.ui.cercar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;

import dam2021.projecte.aplicacioandroid.DBMS;
import dam2021.projecte.aplicacioandroid.R;
import dam2021.projecte.aplicacioandroid.ui.home.Esdeveniment;

public class CercarFragment extends Fragment {

    private CercarViewModel cercarViewModel;
    private Bundle data;
    private SQLiteDatabase baseDades;
    private List<Categoria> categories = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBMS db = DBMS.getInstance(getActivity());
        this.baseDades = db.getWritableDatabase();
        afegirCategories(baseDades);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cercarViewModel =
                new ViewModelProvider(this).get(CercarViewModel.class);
        View view = inflater.inflate(R.layout.fragment_cercar, container, false);


        Spinner spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(getContext(),
                R.layout.spinner_layout, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        view.findViewById(R.id.botoCerca).setOnClickListener(view1 -> {
            Categoria categoria = (Categoria) spinner.getSelectedItem();
            data = new Bundle();
            data.putInt("id", categoria.getId());
            Navigation.findNavController(view1)
                    .navigate(R.id.action_navigation_cercarFragment_to_activitatFragment, data);
        });

        return view;
    }

    private void afegirCategories(SQLiteDatabase baseDades) {


        String query = "SELECT id, nom FROM categoria";
        Cursor resultat = baseDades.rawQuery(query, null);

        if (resultat == null)
            return;

        // Si la consulta ha obtingut resultats, anem afegint aquests a l'ArrayList
        try {
            while (resultat.moveToNext()){
                categories.add(new Categoria(resultat.getInt(0), resultat.getString(1)));
            }
        }
        finally {
            // Tanquem el cursor un cop hem acabat
            resultat.close();
        }
    }
}