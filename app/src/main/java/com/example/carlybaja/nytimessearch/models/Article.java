package com.example.carlybaja.nytimessearch.models;

import android.os.Parcelable;
import android.os.Parcel;


/**
 * Created by carlybaja on 2/12/16.
 */
public class Article implements Parcelable {


    public String webUrl;
    public String headline;
    public String thumbNail;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webUrl);
        dest.writeString(headline);
        dest.writeString(thumbNail);

    }

    public static final Parcelable.Creator<Article> CREATOR
            = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    private Article(Parcel in) {
        webUrl = in.readString();
        headline = in.readString();
        thumbNail = in.readString();

    }

    public Article() {}


}

