package com.example.assightnment1_notes_pr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    Context c;
    ArrayList<item> data;
    public MyAdapter(Context c, ArrayList<item> data) {
        this.c=c;
        this.data=data;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater li = LayoutInflater.from(c);
        View root=  li.inflate(R.layout.list_item, null,false);

         TextView type =  root.findViewById(R.id.my_type);
        TextView date = root.findViewById(R.id.date_now);
        TextView note = root.findViewById(R.id.edit_note);
        ImageView image = root.findViewById(R.id.image_item);
        TextView status = root.findViewById(R.id.text_status);

        type.setText(data.get(i).getType());
        date.setText(data.get(i).getDate());
        note.setText(data.get(i).getNote());
        image.setImageResource(data.get(i).getImageResource());
        status.setText(data.get(i).getStatus());

        return root;
    }


    // Method to update an item in the list
    public void updateItem(int position, item updatedItem) {
        if (position >= 0 && position < data.size()) {
            // Update the item at the specified position
            data.set(position, updatedItem);

            // Notify the adapter that the data has changed
            notifyDataSetChanged();
        }
    }
}
