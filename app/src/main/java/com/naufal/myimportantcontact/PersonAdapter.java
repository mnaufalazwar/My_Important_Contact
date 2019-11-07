package com.naufal.myimportantcontact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    ArrayList<Person> list;

    ItemClick activity;

    public interface ItemClick{
        void onItemClick(int index);
    }

    public PersonAdapter(Context context, ArrayList<Person> list) {
        this.list = list;
        activity = (ItemClick) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvNumber, tvRelation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvRelation = itemView.findViewById(R.id.tvRelation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClick(list.indexOf((Person) v.getTag()));
                }
            });

        }
    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(list.get(position));

        holder.tvName.setText(list.get(position).getName());
        holder.tvNumber.setText(list.get(position).getNumber());
        holder.tvRelation.setText(list.get(position).getRelation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
