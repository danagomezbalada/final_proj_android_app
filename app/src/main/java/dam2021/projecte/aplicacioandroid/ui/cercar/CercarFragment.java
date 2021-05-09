package dam2021.projecte.aplicacioandroid.ui.cercar;

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

import dam2021.projecte.aplicacioandroid.R;

public class CercarFragment extends Fragment {

    private CercarViewModel cercarViewModel;
    private Bundle data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cercarViewModel =
                new ViewModelProvider(this).get(CercarViewModel.class);
        View view = inflater.inflate(R.layout.fragment_cercar, container, false);


        Spinner spinner = view.findViewById(R.id.spinner);
        //TODO: canviar tipus de spinner per obtenir valors de BBDD encomptes de fitxer
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.test_array, R.layout.spinner_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        view.findViewById(R.id.botoCerca).setOnClickListener(view1 -> {
            String test = spinner.getSelectedItem().toString();
            data = new Bundle();
            data.putString("id", test);
            Navigation.findNavController(view1)
                    .navigate(R.id.action_navigation_cercarFragment_to_activitatFragment, data);
        });

        return view;
    }
}