package com.example.cinemhub.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter  { //extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable

    /*class SearchViewHolder extends RecyclerView.ViewHolder {
        SearchViewHolder(View itemView) {
            super(itemView);
        }
    }

    ExampleAdapter(List<ExampleItem> exampleList) {
        this.exampleList = exampleList;
        exampleListFull = new ArrayList<>(exampleList);
    }
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,
                parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ExampleItem currentItem = exampleList.get(position);
        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView1.setText(currentItem.getText1());
        holder.textView2.setText(currentItem.getText2());
    }
    @Override
    public int getItemCount() {
        return exampleList.size();
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ExampleItem> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ExampleItem item : exampleListFull) {
                    if (item.getText2().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            exampleList.clear();
            exampleList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };*/
}