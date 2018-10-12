package com.example.ash.appackers;

// class to hold the information of item from country_spinner_row.xml layout
public class CountryItem {

    // this class contain resource id for the image and the name for the country

    private String mCountryName;
    private int mFlagImage; // because the resource id for the images are integer values

    public CountryItem(String countryName, int flagImage){

        mCountryName = countryName;
        mFlagImage = flagImage;

    }

    public String getCountryName() {
        return mCountryName;
    }

    public int getFlagImage() {
        return mFlagImage;
    }

}
