package com.example.buyinglistocr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SavePurchase implements Parcelable {
    private long id;
    private String name;

    public SavePurchase(long id, String name) {
        this.id=id;
        this.name = name;
    }
    public SavePurchase( String name) {

        this.name = name;
    }

    protected SavePurchase(Parcel in) {
        id = in.readLong();
        name = in.readString();
    }

    public static final Creator<SavePurchase> CREATOR = new Creator<SavePurchase>() {
        @Override
        public SavePurchase createFromParcel(Parcel in) {
            return new SavePurchase(in);
        }

        @Override
        public SavePurchase[] newArray(int size) {
            return new SavePurchase[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
    }
}
