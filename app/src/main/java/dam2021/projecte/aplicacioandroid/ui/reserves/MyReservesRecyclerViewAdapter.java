package dam2021.projecte.aplicacioandroid.ui.reserves;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dam2021.projecte.aplicacioandroid.R;


import java.util.List;

public class MyReservesRecyclerViewAdapter extends RecyclerView.Adapter<MyReservesRecyclerViewAdapter.ViewHolder> {

    private final List<Reserva> llistaReserves;
    private Bundle data;

    public MyReservesRecyclerViewAdapter(List<Reserva> items) {
        llistaReserves = items;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_reserves, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = llistaReserves.get(position);

        if (llistaReserves.get(position).toString().equalsIgnoreCase("rebutjada")) {
            holder.mEstatView.setTextColor(Color.parseColor("#CC0033"));
        }else if (llistaReserves.get(position).toString().equalsIgnoreCase("confirmada")) {
            holder.mEstatView.setTextColor(Color.parseColor("#3C9A07"));
        }else{
            holder.mEstatView.setTextColor(Color.parseColor("#FFBD33"));
        }

        holder.mTitolView.setText(llistaReserves.get(position).getActivitat().getTitol());
        holder.mEstatView.setText(llistaReserves.get(position).toString());
        holder.mCard.setOnClickListener(v -> {
            data = new Bundle();
            int id = llistaReserves.get(position).getActivitat().getId();
            data.putInt("id", id);
            data.putString("origen", "reserves");
            Navigation.findNavController(v)
                    .navigate(R.id.action_navigation_reservesFragment_to_activitatDetallFragment, data);

        });
    }

    @Override
    public int getItemCount() {
        return llistaReserves.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitolView;
        public final TextView mEstatView;
        public Reserva mItem;
        public final CardView mCard;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitolView = view.findViewById(R.id.titol);
            mEstatView = view.findViewById(R.id.estat);
            mCard = view.findViewById(R.id.card);
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mTitolView.getText() + "'";
        }
    }
}