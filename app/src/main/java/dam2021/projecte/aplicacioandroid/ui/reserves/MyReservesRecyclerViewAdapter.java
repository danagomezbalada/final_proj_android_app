package dam2021.projecte.aplicacioandroid.ui.reserves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import dam2021.projecte.aplicacioandroid.ui.home.Esdeveniment;
import dam2021.projecte.aplicacioandroid.ui.reserves.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
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
            holder.mDataView.setTextColor(Color.parseColor("#CB2806"));
        }else if (llistaReserves.get(position).toString().equalsIgnoreCase("confirmada")) {
            holder.mDataView.setTextColor(Color.parseColor("#3C9A07"));
        }else{
            holder.mDataView.setTextColor(Color.parseColor("#FFBD33"));
        }

        holder.mDataView.setText(llistaReserves.get(position).getActivitat().getTitol() + "\n" + llistaReserves.get(position).toString());
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
        public final TextView mDataView;
        public Reserva mItem;
        public final CardView mCard;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDataView = view.findViewById(R.id.data);
            mCard = view.findViewById(R.id.card);
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mDataView.getText() + "'";
        }
    }
}