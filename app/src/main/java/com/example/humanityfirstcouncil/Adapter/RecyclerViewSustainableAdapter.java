package com.example.humanityfirstcouncil.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.humanityfirstcouncil.Card.SustainableItemCard;
import com.example.humanityfirstcouncil.R;
import com.example.humanityfirstcouncil.model.GoalsResponse;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerViewSustainableAdapter extends RecyclerView.Adapter<RecyclerViewSustainableAdapter.ViewHolder>
        implements View.OnLongClickListener
{

    View.OnLongClickListener onLongClickListener;
    private Context context;
    private ArrayList<GoalsResponse.GoalList> list;
    public RecyclerViewSustainableAdapter(Context context){
        this.context = context;
    }

    public void setCategoryItemList(ArrayList<GoalsResponse.GoalList> categoryItemList) {
        this.list = categoryItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.sustainable_development_item,parent,false);
        ViewHolder view = new ViewHolder(listItem);
        listItem.setOnLongClickListener(this);
        return view;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GoalsResponse.GoalList causeInfo = list.get(position);
        holder.text.setText(causeInfo.getGoalTittle());
        // Image to be set using glide till that we place the image as image holder
    }


    @Override
    public int getItemCount() {
        try {
            return list.size();
        }catch (Exception e){
            return 0;
        }
    }
    public  boolean onLongClickListener(View.OnLongClickListener onLongClickListener)
    {
        this.onLongClickListener = onLongClickListener;
        return  true;
    }
    @Override
    public boolean onLongClick(View v) {
        onLongClickListener.onLongClick(v);
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView text;

        @SuppressLint("CutPasteId")
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.foodImageView);
            text = itemView.findViewById(R.id.foodForAllTextView);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
