package com.example.contactapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class contactsAdapter  extends RecyclerView.Adapter<contactsAdapter.ViewHolder> {
    private ArrayList<Contact> contactsList;
    private ArrayList<Contact> filteredList;

    public contactsAdapter(ArrayList<Contact> contactsList) {
        this.contactsList = contactsList;
        this.filteredList = new ArrayList<>(contactsList);
    }


    @NonNull
    @Override
    public contactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);

        return new ViewHolder(view);
    }
    public void setContactList(ArrayList<Contact> newContactList) {
        contactsList= newContactList;
        filteredList = new ArrayList<>(newContactList); // Cập nhật danh sách liên hệ đã được lọc
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull contactsAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(contactsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            tvName  = (TextView) view.findViewById(R.id.tv_name);
        }

//        public TextView getTextView() {
//            return textView;
//        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void filterList(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(contactsList);
        } else {
            query = query.toLowerCase();
            for (Contact contact : contactsList) {
                if (contact.getName().toLowerCase().contains(query)) {
                    filteredList.add(contact);
                }
            }
        }
        notifyDataSetChanged();
    }





}
