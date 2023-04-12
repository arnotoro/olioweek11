package com.example.week11;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;

import kotlinx.coroutines.MainCoroutineDispatcher;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> listItems;

    public ViewAdapter(Context context, ArrayList<String> item) {
        this.context = context;
        this.listItems = item;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemText.setText(listItems.get(position));
        holder.deleteIcon.setImageResource(R.drawable.trash);
        holder.editIcon.setImageResource(R.drawable.edit);
        holder.deleteIcon.setOnClickListener(view -> deleteItem(holder.getAdapterPosition()));
        holder.editIcon.setOnClickListener(view -> editItem(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText;
        ImageButton deleteIcon;
        ImageButton editIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.itemText);
            deleteIcon = itemView.findViewById(R.id.deleteButton);
            editIcon = itemView.findViewById(R.id.editButton);
        }
    }

    public void deleteItem(int position) {
        // get position of item to remove on the screen and relative position in datalists
        String itemToRemove = listItems.get(position);
        int indexToRemove = MainActivity.items.indexOf(itemToRemove);
        int sortedIndexToRemove = MainActivity.sortedData.indexOf(itemToRemove);

        System.out.println("Index to remove: " + indexToRemove);
        System.out.println("Sorted index to remove: " + sortedIndexToRemove);

        if (indexToRemove >= 0 && sortedIndexToRemove >= 0) {
            MainActivity.items.remove(indexToRemove);
            MainActivity.sortedData.remove(sortedIndexToRemove);
            notifyDataSetChanged();
        }
    }

    public void editItem(int position) {
        // launch a dialog to edit the item and update the data
        final EditText input = new EditText(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Muuta tuotetta");
        builder.setView(input);

        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            String item = input.getText().toString();
            int indexToChange = MainActivity.items.indexOf(listItems.get(position));
            int sortedIndexToChange = MainActivity.sortedData.indexOf(listItems.get(position));
            MainActivity.items.set(indexToChange, item);
            MainActivity.sortedData.set(sortedIndexToChange, item);
            notifyItemChanged(position);
        });
        builder.setNegativeButton("Takaisin", (dialogInterface, i) -> dialogInterface.cancel());

        builder.show();
    }
}
