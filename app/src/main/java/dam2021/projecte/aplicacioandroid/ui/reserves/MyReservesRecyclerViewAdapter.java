package dam2021.projecte.aplicacioandroid.ui.reserves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dam2021.projecte.aplicacioandroid.R;

import dam2021.projecte.aplicacioandroid.ui.reserves.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyReservesRecyclerViewAdapter extends RecyclerView.Adapter<MyReservesRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private Bundle data;

    public MyReservesRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
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
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDetailsView.setText(mValues.get(position).details);
        holder.mCard.setOnClickListener(v -> {
            data = new Bundle();
            String nom = ((TextView) v.findViewById(R.id.details)).getText().toString();
            data.putString("id", nom);
            Navigation.findNavController(v)
                    .navigate(R.id.action_navigation_reservesFragment_to_activitatDetallFragment, data);

        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDetailsView;
        public DummyItem mItem;
        public final CardView mCard;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
            mDetailsView = view.findViewById(R.id.details);
            mCard = view.findViewById(R.id.card);
        }

        @Override
        @NonNull
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}