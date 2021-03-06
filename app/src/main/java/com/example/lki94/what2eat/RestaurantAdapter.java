package com.example.lki94.what2eat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView addressTextView;
        public TextView cuisinesTextView;
        public TextView averageCostTextView;
        public TextView ratingView;

        public ViewHolder (View itemView, final OnItemClickListener listener) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.restaurant_name);
            addressTextView = (TextView) itemView.findViewById(R.id.restaurant_address);
            cuisinesTextView = (TextView) itemView.findViewById(R.id.restaurant_cuisines);
            averageCostTextView = (TextView) itemView.findViewById(R.id.restaurant_averageCost);
            ratingView = (TextView) itemView.findViewById(R.id.restaurant_userRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }
    }

    private List<RestaurantModel> mRestaurantlists;
    private Context mContext;

    public RestaurantAdapter(Context context, List<RestaurantModel> restaurantlists){
        mRestaurantlists = restaurantlists;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View restaurantView = inflater.inflate(R.layout.model_restaurant, parent, false);

        ViewHolder viewHolder = new ViewHolder(restaurantView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantAdapter.ViewHolder viewHolder, int position) {
        RestaurantModel restaurantItem = mRestaurantlists.get(position);

        TextView textView = viewHolder.nameTextView;
        textView.setText(restaurantItem.getName());

        TextView textView2 = viewHolder.addressTextView;
        textView2.setText(restaurantItem.getAddress());

        TextView textView3 = viewHolder.cuisinesTextView;
        textView3.setText(restaurantItem.getCuisines());

        TextView textView4 = viewHolder.averageCostTextView;
        textView4.setText("Average Cost: " + String.format("%d", restaurantItem.getAverageCost()));

        TextView textView5 = viewHolder.ratingView;

        if(restaurantItem.getUserRating() == 0.0) {
            textView5.setText("User Rating: N/A");
        } else {
            textView5.setText("User Rating: " + String.format("%1$,.1f", restaurantItem.getUserRating()));
        }

    }

    @Override
    public int getItemCount() {
        return mRestaurantlists.size();
    }

    public void updateAdapter() {
        notifyDataSetChanged();
    }

}
