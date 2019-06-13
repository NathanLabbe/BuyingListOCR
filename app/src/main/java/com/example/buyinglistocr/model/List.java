package com.example.buyinglistocr.model;

import android.os.Parcel;
import android.os.Parcelable;

public class List implements Parcelable {

    private long id;
    private String name;
    private double spent;
    private int status;
    private long idUser;

    public List(long id, String name, double spent, int status, long idUser) {

        this.id = id;
        this.name = name;
        this.spent = spent;
        this.status = status;
        this.idUser = idUser;

    }

    protected List(Parcel in) {

        id = in.readLong();
        name = in.readString();
        spent = in.readDouble();

    }

    public long getId() {

        return this.id;

    }

    public void setId(long id) {

        this.id = id;

    }

    public String getName() {

        return this.name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public double getSpent() {

        return this.spent;

    }

    public void setSpent(double spent) {

        this.spent = spent;

    }

    public int getStatus() {

        return status;

    }

    public void setStatus(int status) {

        this.status = status;

    }

    public long getIdUser() {

        return idUser;

    }

    public void setIdUser(long idUser) {

        this.idUser = idUser;

    }

    public static final Creator<List> CREATOR = new Creator<List>() {

        @Override
        public List createFromParcel(Parcel in) {

            return new List(in);

        }

        @Override
        public List[] newArray(int size) {

            return new List[size];

        }

    };

    @Override
    public int describeContents() {

        return 0;

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeLong(this.id);
        parcel.writeString(this.name);
        parcel.writeDouble(this.spent);

    }

}



