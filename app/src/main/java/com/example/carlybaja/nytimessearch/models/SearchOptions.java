package com.example.carlybaja.nytimessearch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carlybaja on 2/12/16.
 */
public class SearchOptions implements Parcelable {

    /*
      Begin Date (using a date picker)
      News desk values (Arts, Fashion & Style, Sports)
      Sort order (oldest or newest)
  */

    public String beginDate;
    public String sortOrder;
    public String deskValues;
    public String searchTerm;
    public String start;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(beginDate);
        out.writeString(sortOrder);
        out.writeString(deskValues);
        out.writeString(searchTerm);
        out.writeString(start);

    }

    public static final Parcelable.Creator<SearchOptions> CREATOR
            = new Parcelable.Creator<SearchOptions>() {
        @Override
        public SearchOptions createFromParcel(Parcel in) {
            return new SearchOptions(in);
        }

        @Override
        public SearchOptions[] newArray(int size) {
            return new SearchOptions[size];
        }
    };

    private SearchOptions(Parcel in) {
        beginDate = in.readString();
        sortOrder = in.readString();
        deskValues = in.readString();
        searchTerm = in.readString();
        start = in.readString();
    }

    public SearchOptions(String query) {
        searchTerm = query;

        // default values
          beginDate = "";
          sortOrder = "";
          deskValues = "";
    }

}
