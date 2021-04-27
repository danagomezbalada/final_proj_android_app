package dam2021.projecte.aplicacioandroid.ui.home;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import dam2021.projecte.aplicacioandroid.ui.home.dummy.DummyContent.DummyItem;

import java.util.List;

import dam2021.projecte.aplicacioandroid.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEsdevenimentRecyclerViewAdapter extends RecyclerView.Adapter<MyEsdevenimentRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;

    public MyEsdevenimentRecyclerViewAdapter(List<DummyItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_esdeveniment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDetailsView.setText(mValues.get(position).details);
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mDetailsView = (TextView) view.findViewById(R.id.details);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}