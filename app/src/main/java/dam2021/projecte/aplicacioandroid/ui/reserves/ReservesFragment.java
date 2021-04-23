package dam2021.projecte.aplicacioandroid.ui.reserves;

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

public class ReservesFragment extends Fragment {

    private ReservesViewModel reservesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reservesViewModel =
                new ViewModelProvider(this).get(ReservesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reserves, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        reservesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}