package dam2021.projecte.aplicacioandroid.ui.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dam2021.projecte.aplicacioandroid.R;
import dam2021.projecte.aplicacioandroid.ui.home.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class EsdevenimentFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private Bundle data;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
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
            recyclerView.setAdapter(new MyEsdevenimentRecyclerViewAdapter(DummyContent.ITEMS));
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(context, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        data = new Bundle();
                        String nom = ((TextView) recyclerView.findViewById(R.id.details)).getText().toString();
                        data.putString("id", nom);
                        NavHostFragment.findNavController(EsdevenimentFragment.this)
                                .navigate(R.id.action_navigation_home_to_homeFragment, data);

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
            );
        }
        return view;
    }
}