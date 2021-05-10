package dam2021.projecte.aplicacioandroid.ui.home;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dam2021.projecte.aplicacioandroid.R;

public class MyEsdevenimentRecyclerViewAdapter extends RecyclerView.Adapter<MyEsdevenimentRecyclerViewAdapter.ViewHolder> {

    private final List<Esdeveniment> llistaEsdeveniments;
    private Bundle data;

    public MyEsdevenimentRecyclerViewAdapter(List<Esdeveniment> items) {
        llistaEsdeveniments = items;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_esdeveniment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = llistaEsdeveniments.get(position);
        holder.mNomView.setText(llistaEsdeveniments.get(position).getNom());

        holder.mCard.setOnClickListener(v -> {
            data = new Bundle();
            data.putInt("idEsdeveniment", llistaEsdeveniments.get(position).getId());
            Navigation.findNavController(v)
                    .navigate(R.id.action_navigation_home_to_activitatFragment, data);

        });
    }

    @Override
    public int getItemCount() {
        return llistaEsdeveniments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNomView;
        public Esdeveniment mItem;
        public final CardView mCard;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNomView = view.findViewById(R.id.nom_esdeveniment);
            mCard = view.findViewById(R.id.card);
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString();
        }
    }
}