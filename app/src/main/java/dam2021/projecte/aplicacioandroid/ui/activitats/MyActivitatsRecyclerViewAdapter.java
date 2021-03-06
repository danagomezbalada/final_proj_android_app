package dam2021.projecte.aplicacioandroid.ui.activitats;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dam2021.projecte.aplicacioandroid.R;

import java.text.SimpleDateFormat;
import java.util.List;


public class MyActivitatsRecyclerViewAdapter extends RecyclerView.Adapter<MyActivitatsRecyclerViewAdapter.ViewHolder> {

    private final List<Activitat> llistaActivitats;
    private Bundle data;
    String pattern = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public MyActivitatsRecyclerViewAdapter(List<Activitat> items) {
        llistaActivitats = items;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_activitats, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = llistaActivitats.get(position);
        String dataActivitat = simpleDateFormat.format(llistaActivitats.get(position).getData());
        holder.mTitolView.setText(llistaActivitats.get(position).getTitol());
        holder.mDataView.setText(dataActivitat);

        //holder.mDataView.setText(dataActivitat);

        holder.mCard.setOnClickListener(v -> {
            data = new Bundle();
            int id = llistaActivitats.get(position).getId();
            // Enviem al fragment de destí la informació amb un Bundle
            data.putInt("id", id);
            data.putString("origen", "activitats");
            Navigation.findNavController(v)
                    .navigate(R.id.action_navigation_activitatFragment_to_activitatDetallFragment, data);

        });
    }

    @Override
    public int getItemCount() {
        return llistaActivitats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitolView;
        public final TextView mDataView;
        public Activitat mItem;
        public final CardView mCard;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitolView = view.findViewById(R.id.titol);
            mDataView = view.findViewById(R.id.data);
            mCard = view.findViewById(R.id.card);
        }
    }
}