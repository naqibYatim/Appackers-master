package com.example.ash.appackers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CountryAdapter extends ArrayAdapter<CountryItem> {

    public CountryAdapter(Context context, ArrayList<CountryItem> countryList){
        super(context, 0, countryList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get hte view from the method that have been created
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    // create other method that will returned a viewer for both of the methods above
    private View initView(int position, View convertView, ViewGroup parent){
        // convertView is basically the view we want to recycle
        // first we need to check if the viewer already existing
        // if it is already exist, we don't want to create it from scratch, we just want to used the old one
        if(convertView == null){
            // only on this case we want to inflate the convertView layout
            // pass the context with get context
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.country_spinner_row, parent, false
            );
        }

        // references for our imageView and textView
        ImageView imageViewFlag = convertView.findViewById(R.id.image_view_flag);
        TextView textViewName = convertView.findViewById(R.id.text_view_name);

        // reference to our current item at it's position using get item
        CountryItem currentItem = getItem(position);

        if(currentItem != null) {
            // get name and flag image out of this above item and show it to our imageView and textView
            imageViewFlag.setImageResource(currentItem.getFlagImage());
            textViewName.setText(currentItem.getCountryName());
        }

        return  convertView;

    }

}
