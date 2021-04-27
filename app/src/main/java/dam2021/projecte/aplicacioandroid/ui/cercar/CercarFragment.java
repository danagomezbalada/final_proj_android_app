package dam2021.projecte.aplicacioandroid.ui.cercar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import dam2021.projecte.aplicacioandroid.R;

public class CercarFragment extends Fragment {

    private CercarViewModel cercarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cercarViewModel =
                new ViewModelProvider(this).get(CercarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cercar, container, false);

        return root;
    }
}